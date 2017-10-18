package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesContract {

    interface Presenter {

        void setFilter(NotesFilterType notesFilterType);

        NotesFilterType getFilter();

        }

    interface View {}

}
