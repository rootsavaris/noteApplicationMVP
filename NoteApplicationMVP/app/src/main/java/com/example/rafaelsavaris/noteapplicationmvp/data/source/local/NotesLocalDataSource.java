package com.example.rafaelsavaris.noteapplicationmvp.data.source.local;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import java.util.logging.LogRecord;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesLocalDataSource implements NotesDatasource {

    private static NotesLocalDataSource instance;

    private NotesDataBaseHelper notesDataBaseHelper;

    private NotesLocalDataSource(Context context) {
        notesDataBaseHelper = new NotesDataBaseHelper(context);
    }

    public static NotesLocalDataSource getInstance(Context context) {

        if (instance == null) {
            instance = new NotesLocalDataSource(context);
        }

        return instance;

    }

    @Override
    public void getNotes(final LoadNotesCallBack loadNotesCallBack) {

        List<Note> notes = new ArrayList<>();

        SQLiteDatabase db = notesDataBaseHelper.getReadableDatabase();

        String[] projection = {
                NotesPersistenceContract.NotesEntry.COLUMN_NAME_ENTRY_ID,
                NotesPersistenceContract.NotesEntry.COLUMN_NAME_TITLE,
                NotesPersistenceContract.NotesEntry.COLUMN_NAME_DESCRIPTION
        };

        Cursor c = db.query(
                NotesPersistenceContract.NotesEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {

            while (c.moveToNext()) {

                String itemId = c.getString(c.getColumnIndexOrThrow(NotesPersistenceContract.NotesEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(NotesPersistenceContract.NotesEntry.COLUMN_NAME_TITLE));
                String description =
                        c.getString(c.getColumnIndexOrThrow(NotesPersistenceContract.NotesEntry.COLUMN_NAME_DESCRIPTION));

                Note note = new Note(title, description, itemId);

                notes.add(note);

            }
        }

        if (c != null) {
            c.close();
        }

        db.close();

        if (notes.isEmpty()){
            loadNotesCallBack.onDataNotAvailable();
        } else {
            loadNotesCallBack.onNotesLoaded(notes);
        }

    }

    @Override
    public void deleteAllNotes() {

        SQLiteDatabase database = notesDataBaseHelper.getWritableDatabase();

        database.delete(NotesPersistenceContract.NotesEntry.TABLE_NAME, null, null);

        database.close();

    }

    @Override
    public void saveNote(Note note) {

        SQLiteDatabase database = notesDataBaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NotesPersistenceContract.NotesEntry.COLUMN_NAME_ENTRY_ID, note.getId());
        values.put(NotesPersistenceContract.NotesEntry.COLUMN_NAME_TITLE, note.getTitle());
        values.put(NotesPersistenceContract.NotesEntry.COLUMN_NAME_DESCRIPTION, note.getText());

        database.insert(NotesPersistenceContract.NotesEntry.TABLE_NAME, null, values);

        database.close();

    }

}
