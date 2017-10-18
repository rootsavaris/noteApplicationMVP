package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesPresenter implements NotesContract.Presenter {

    private final NotesRepository notesRepository;

    private final NotesContract.View notesView;

    private NotesFilterType notesFilterType = NotesFilterType.ALL_NOTES;

    public NotesPresenter(NotesRepository notesRepository, NotesContract.View notesView) {
        this.notesRepository = notesRepository;
        this.notesView = notesView;
    }

    public void setFilter(NotesFilterType notesFilterType){
        this.notesFilterType = notesFilterType;
    }

    public NotesFilterType getFilter(){
        return this.notesFilterType;
    }


}
