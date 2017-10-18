package com.example.rafaelsavaris.noteapplicationmvp.data.source.local;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesRemoteDataSource implements NotesDatasource {

    private static NotesRemoteDataSource instance;

    public static NotesRemoteDataSource getInstance(){

        if (instance == null){
            instance = new NotesRemoteDataSource();
        }

        return instance;

    }

}
