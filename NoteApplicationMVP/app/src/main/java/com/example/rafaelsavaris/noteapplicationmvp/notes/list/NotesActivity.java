package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.rafaelsavaris.noteapplicationmvp.Injection;
import com.example.rafaelsavaris.noteapplicationmvp.R;
import com.example.rafaelsavaris.noteapplicationmvp.utils.ActivityUtils;


public class NotesActivity extends AppCompatActivity {

    private static final String CURRENT_FILTER_KEY = "CURRENT_FILTER_KEY";

    private DrawerLayout drawerLayout;

    private NotesPresenter notesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null){

            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        drawerLayout = findViewById(R.id.drawer_layout);

        drawerLayout.setStatusBarBackground(R.color.colorPrimary);

        NavigationView navigationView = findViewById(R.id.nav_view);

        if (navigationView != null){

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    return true;
                }
            });

        }

        NotesFragment notesFragment = (NotesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (notesFragment == null) {

            notesFragment = NotesFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), notesFragment, R.id.contentFrame);

        }

        notesPresenter = new NotesPresenter(Injection.providesNotesRepository(this), notesFragment);

        if (savedInstanceState != null){

            NotesFilterType notesFilterType = (NotesFilterType) savedInstanceState.getSerializable(CURRENT_FILTER_KEY);

            notesPresenter.setFilter(notesFilterType);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(CURRENT_FILTER_KEY, notesPresenter.getFilter());

        super.onSaveInstanceState(outState);

    }
}
