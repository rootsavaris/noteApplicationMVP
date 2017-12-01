package com.example.rafaelsavaris.noteapplicationmvp.notes.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.rafaelsavaris.noteapplicationmvp.R;

/**
 * Created by rafael.savaris on 01/12/2017.
 */

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String SAVED_INSTANCE_NOTE = "SAVED_INSTANCE_NOTE";

    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_note_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        AddEditNoteFragment addEditNoteFragment = (AddEditNoteFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        String noteId = getIntent().getStringExtra()


    }
}
