package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import android.content.Intent;

import com.example.rafaelsavaris.noteapplicationmvp.BasePresenter;
import com.example.rafaelsavaris.noteapplicationmvp.BaseView;
import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;

import java.util.List;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesContract {

    interface Presenter extends BasePresenter {

        void setFilter(NotesFilterType notesFilterType);

        NotesFilterType getFilter();

        void result(int requestCode, int resultCode);

        void loadNotes(boolean forceUpdate);

        void markNote(Note markedNote);

        void unMarkNote(Note markedNote);

        void clearMarkedNotes();

        void addNewNote();

        void openNoteDetails(Note note);

    }

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void showNotes(List<Note> notes);

        void showNoNotes();

        void showNoMarkedNotes();

        void showAllFilterLabel();

        void showMarkedFilterLabel();

        void showLoadingNotesError();

        void showNoteMarked();

        void showNoteUnMarked();

        void showNotesCleared();

        void showFilteringPopUpMenu();

        void showAddNewNote();

        void showSuccessfullySavedMessage();

        void showNoteDetailUi(String noteId);

    }

}
