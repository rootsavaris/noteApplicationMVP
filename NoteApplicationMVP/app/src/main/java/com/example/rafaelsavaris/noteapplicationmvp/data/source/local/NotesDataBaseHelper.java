package com.example.rafaelsavaris.noteapplicationmvp.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by rafael.savaris on 20/11/2017.
 */

public class NotesDataBaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NotesPersistenceContract.NotesEntry.TABLE_NAME + " (" +
                    NotesPersistenceContract.NotesEntry.COLUMN_NAME_ENTRY_ID + NotesPersistenceContract.NotesEntry.TEXT_TYPE + " PRIMARY KEY," +
                    NotesPersistenceContract.NotesEntry.COLUMN_NAME_TITLE + NotesPersistenceContract.NotesEntry.TEXT_TYPE + NotesPersistenceContract.NotesEntry.COMMA_SEP +
                    NotesPersistenceContract.NotesEntry.COLUMN_NAME_DESCRIPTION + NotesPersistenceContract.NotesEntry.TEXT_TYPE + NotesPersistenceContract.NotesEntry.COMMA_SEP +
                    NotesPersistenceContract.NotesEntry.COLUMN_NAME_MARKED + NotesPersistenceContract.NotesEntry.BOOLEAN_TYPE +
                    " )";

    public NotesDataBaseHelper(Context context) {
        super(context, "Notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
