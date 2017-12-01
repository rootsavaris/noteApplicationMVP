package com.example.rafaelsavaris.noteapplicationmvp.notes.add;

import com.example.rafaelsavaris.noteapplicationmvp.BasePresenter;
import com.example.rafaelsavaris.noteapplicationmvp.BaseView;

/**
 * Created by rafael.savaris on 01/12/2017.
 */

public interface AddEditNoteContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
    }


}
