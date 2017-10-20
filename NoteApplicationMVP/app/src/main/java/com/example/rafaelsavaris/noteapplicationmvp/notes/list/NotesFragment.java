package com.example.rafaelsavaris.noteapplicationmvp.notes.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rafaelsavaris.noteapplicationmvp.R;
import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesFragment extends Fragment implements NotesContract.View {

    private NotesContract.Presenter presenter;

    private NotesAdapter notesAdapter;

    private TextView mFilteringLabelView;

    private LinearLayout mNotesView;

    private View mNoNotesView;

    private ImageView mNoNoteIcon;

    private TextView mNoNoteMainView;

    private TextView mNoNoteAddView;

    public static NotesFragment newInstance(){
        return new NotesFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesAdapter = new NotesAdapter(new ArrayList<Note>(0), notesItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void setPresenter(NotesContract.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        presenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.notes_fragment, container, false);

        // Set up tasks view
        ListView listView = (ListView) root.findViewById(R.id.notes_list);

        listView.setAdapter(notesAdapter);

        mFilteringLabelView = (TextView) root.findViewById(R.id.filteringLabel);

        mNotesView = (LinearLayout) root.findViewById(R.id.notesLL);

        // Set up  no tasks view
        mNoNotesView = root.findViewById(R.id.noNotes);
        mNoNoteIcon = (ImageView) root.findViewById(R.id.noNOtesIcon);
        mNoNoteMainView = (TextView) root.findViewById(R.id.noNotesMain);
        mNoNoteAddView = (TextView) root.findViewById(R.id.noNOtesAdd);
        mNoNoteAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showAddTask();
            }
        });

        // Set up floating action button
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_note);

        fab.setImageResource(R.drawable.ic_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.addNewTask();
            }
        });

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.refresh_layout);

        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(listView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mPresenter.loadTasks(false);
            }
        });

        setHasOptionsMenu(true);

        return root;
    }




    NotesItemListener notesItemListener = new NotesItemListener() {

        @Override
        public void onNoteClick(Note clickedNote) {

        }

        @Override
        public void onMarkedNoteClick(Note markedNote) {

        }

    };

    private static class NotesAdapter extends BaseAdapter {

        private List<Note> mNotes;

        private NotesItemListener mNotesItemListener;

        public NotesAdapter(List<Note> notes, NotesItemListener notesItemListener){
            setList(notes);
            mNotesItemListener = notesItemListener;
        }

        public void replaceData(List<Note> notes){
            setList(notes);
            notifyDataSetChanged();
        }

        private void setList(List<Note> notes){
            mNotes = checkNotNull(notes);
        }

        @Override
        public int getCount() {
            return mNotes.size();
        }

        @Override
        public Note getItem(int i) {
            return mNotes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View viewRoot = view;

            if (viewRoot == null){

                LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

                viewRoot = layoutInflater.inflate(R.layout.note_item, viewGroup, false);

            }

            final Note note = getItem(i);

            TextView title = (TextView) viewRoot.findViewById(R.id.title);

            title.setText(note.getTitle());

            CheckBox checkBox = (CheckBox) viewRoot.findViewById(R.id.mark);

            checkBox.setChecked(note.isMarked());

            if (note.isMarked()){
                viewRoot.setBackgroundDrawable(viewGroup.getContext().getResources().getDrawable(R.drawable.list_completed_touch_feedback));
            } else {
                viewRoot.setBackgroundDrawable(viewGroup.getContext().getResources().getDrawable(R.drawable.touch_feedback));
            }

            checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mNotesItemListener.onMarkedNoteClick(note);
                }
            });

            viewRoot.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    mNotesItemListener.onNoteClick(note);
                }
            });

            return viewRoot;

        }
    }

    public interface NotesItemListener {

        void onNoteClick(Note clickedNote);

        void onMarkedNoteClick(Note markedNote);

    }

}
