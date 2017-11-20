package com.example.rafaelsavaris.noteapplicationmvp.data.source;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;

import java.util.List;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public interface NotesDatasource {

    interface LoadNotesCallBack{

        void onNotesLoaded(List<Note> notes);

        void onDataNotAvailable();

    }

    void getNotes(LoadNotesCallBack loadNotesCallBack);

}
