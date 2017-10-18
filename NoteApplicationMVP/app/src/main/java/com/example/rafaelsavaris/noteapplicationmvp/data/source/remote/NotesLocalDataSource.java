package com.example.rafaelsavaris.noteapplicationmvp.data.source.remote;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesLocalDataSource implements NotesDatasource {

    private static NotesLocalDataSource instance;

    public static NotesLocalDataSource getInstance(){

        if (instance == null){
            instance = new NotesLocalDataSource();
        }

        return instance;

    }

}
