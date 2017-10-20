package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import android.content.Intent;

import com.example.rafaelsavaris.noteapplicationmvp.BasePresenter;
import com.example.rafaelsavaris.noteapplicationmvp.BaseView;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesContract {

    interface Presenter extends BasePresenter {

        void setFilter(NotesFilterType notesFilterType);

        NotesFilterType getFilter();

        void result(int requestCode, int resultCode);

    }

    interface View extends BaseView<Presenter> {

        void onActivityResult(int requestCode, int resultCode, Intent data);




        }

}
