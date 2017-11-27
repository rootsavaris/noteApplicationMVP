package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesPresenter implements NotesContract.Presenter {

    private final NotesRepository mRepository;

    private final NotesContract.View mView;

    private NotesFilterType mFilterType = NotesFilterType.ALL_NOTES;

    private boolean firstLoad = true;

    public NotesPresenter(NotesRepository mRepository, NotesContract.View mView) {
        this.mRepository = mRepository;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    public void setFilter(NotesFilterType mFilterType){
        this.mFilterType = mFilterType;
    }

    public NotesFilterType getFilter(){
        return this.mFilterType;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {
        loadNotes(false);
    }

    @Override
    public void loadNotes(boolean forceUpdate) {

        loadNotes(forceUpdate || firstLoad, true);

        firstLoad = false;

    }

    @Override
    public void markNote(Note markedNote) {
        mRepository.markNote(markedNote);
        mView.showNoteMarked();
        loadNotes(false, false);
    }

    @Override
    public void clearMarkedNotes() {
        mRepository.clearMarkedNotes();
        mView.showNotesCleared();
        loadNotes(false, false);
    }

    private void loadNotes(boolean forceUpdate, final boolean showLoading){

        if (showLoading){
            mView.setLoadingIndicator(true);
        }

        if (forceUpdate){
            mRepository.refreshNotes();
        }

        mRepository.getNotes(new NotesDatasource.LoadNotesCallBack() {

            @Override
            public void onNotesLoaded(List<Note> notes) {

                List<Note> notesToShow = new ArrayList<>();

                for (Note note : notes){

                    switch (mFilterType){

                        case MARKED_NOTES:

                            if(note.isMarked()){
                                notesToShow.add(note);
                            }

                            break;

                         default:
                             notesToShow.add(note);
                    }

                }

                if (!mView.isActive()){
                    return;
                }

                if (showLoading){
                    mView.setLoadingIndicator(false);
                }

                processNotes(notes);

            }

            @Override
            public void onDataNotAvailable() {

                if (!mView.isActive()){
                    return;
                }

                mView.showLoadingNotesError();

            }

        });

    }

    private void processNotes(List<Note> notes){

        if (notes.isEmpty()){
            processEmptyNotes();
        } else {
            mView.showNotes(notes);
            showFilterLabel();
        }

    }

    private void processEmptyNotes(){

        switch (mFilterType){

            case MARKED_NOTES:
                mView.showNoMarkedNotes();
                break;

            default:
                mView.showNoNotes();
                break;

        }

    }

    private void showFilterLabel(){

        switch (mFilterType){

            case MARKED_NOTES:
                mView.showMarkedFilterLabel();
                break;
            default:
                mView.showAllFilterLabel();
                break;

        }

    }

}
