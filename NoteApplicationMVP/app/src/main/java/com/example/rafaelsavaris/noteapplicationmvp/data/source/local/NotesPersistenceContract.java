package com.example.rafaelsavaris.noteapplicationmvp.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by rafael.savaris on 23/11/2017.
 */

public class NotesPersistenceContract {

    public static abstract class NotesEntry implements BaseColumns {

        public static final String TEXT_TYPE = " TEXT";

        public static final String COMMA_SEP = ",";

        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

    }

}
