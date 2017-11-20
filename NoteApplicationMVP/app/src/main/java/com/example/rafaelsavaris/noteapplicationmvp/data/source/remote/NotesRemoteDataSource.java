package com.example.rafaelsavaris.noteapplicationmvp.data.source.remote;

import android.os.Handler;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesRemoteDataSource implements NotesDatasource {

    private static NotesRemoteDataSource instance;

    private static final int TIME_SERVICE = 5000;

    private final static Map<String, Note> NOTES_DATA;

    static {
        NOTES_DATA = new LinkedHashMap<>(2);
        addNote("Note 1", "This is the Note1");
        addNote("Note 2", "This is the Note2");
    }

    public static NotesRemoteDataSource getInstance(){

        if (instance == null){
            instance = new NotesRemoteDataSource();
        }

        return instance;

    }

    private static void addNote(String title, String description) {
        Note note = new Note(title, description);
        NOTES_DATA.put(note.getId(), note);
    }

    @Override
    public void getNotes(final LoadNotesCallBack loadNotesCallBack) {

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                loadNotesCallBack.onNotesLoaded(Lists.newArrayList(NOTES_DATA.values()));
            }

        }, TIME_SERVICE);

    }

}
