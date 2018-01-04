package com.example.rafaelsavaris.noteapplicationmvp.notes.detail;

import com.example.rafaelsavaris.noteapplicationmvp.BasePresenter;
import com.example.rafaelsavaris.noteapplicationmvp.BaseView;

/**
 * Created by rafael.savaris on 02/01/2018.
 */

public interface DetailNoteContract {

    interface Presenter extends BasePresenter {

        void editNote();

        void markNote();

        void deleteNote();

    }

    interface View extends BaseView<Presenter> {

        void showMissingNote();

        void setLoadingIndicator(boolean active);

        boolean isActive();

        void hideTitle();

        void showTitle(String title);

        void hideText();

        void showText(String text);

        void showMarkedStatus(boolean mark);

        void showEditNote(String noteId);

        void showNoteMarked();

        void showNoteDeleted();

    }

}
