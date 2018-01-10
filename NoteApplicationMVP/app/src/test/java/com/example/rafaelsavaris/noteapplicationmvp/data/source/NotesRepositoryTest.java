package com.example.rafaelsavaris.noteapplicationmvp.data.source;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesDatasource;
import com.example.rafaelsavaris.noteapplicationmvp.data.source.NotesRepository;
import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.verify;

/**
 * Created by rafael.savaris on 10/01/2018.
 */

public class NotesRepositoryTest {

    private final static String NOTE_TITLE = "title";
    private final static String NOTE_TEXT = "text";

    private final static String NOTE_TITLE2 = "title2";
    private final static String NOTE_TEXT2 = "text2";

    private final static String NOTE_TITLE3 = "title3";
    private final static String NOTE_TEXT3 = "text3";

    private static List<Note> NOTES = Lists.newArrayList(new Note(NOTE_TITLE, NOTE_TEXT), new Note(NOTE_TITLE2, NOTE_TEXT2));

    private NotesRepository mNotesRepository;

    @Mock
    private NotesDatasource mNotesDatasourceRemote;

    @Mock
    private NotesDatasource mNotesDatasourceLocal;

    @Mock
    private NotesDatasource.LoadNotesCallBack mLoadNotesCallBack;

    @Mock
    private NotesDatasource.GetNoteCallBack mGetNoteCallBack;

    @Captor
    private ArgumentCaptor<NotesDatasource.LoadNotesCallBack> mNotesCallBackArgumentCaptor;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);

        mNotesRepository = NotesRepository.getInstance(mNotesDatasourceRemote, mNotesDatasourceLocal);

    }

    @After
    public void destroy(){
        NotesRepository.destroyInstance();
    }

    @Test
    public void getNotes_repositoryCachesAfterFirstApiCall(){

        twoNotesLoadCallsToRepository(mLoadNotesCallBack);

        verify(mNotesDatasourceRemote).getNotes(any(NotesDatasource.LoadNotesCallBack.class));

    }

    @Test
    public void getNotes_requestAllNotesFromLocalDataSource(){

        mNotesRepository.getNotes(mLoadNotesCallBack);

        verify(mNotesDatasourceLocal).getNotes(any(NotesDatasource.LoadNotesCallBack.class));

    }

    @Test
    public void saveNote_savesNoteToServiceApi(){

        Note note = new Note(NOTE_TITLE, NOTE_TEXT);

        mNotesRepository.saveNote(note);

        verify(mNotesDatasourceRemote).saveNote(note);
        verify(mNotesDatasourceLocal).saveNote(note);

        assertThat(mNotesRepository.mCachedNotes.size(), is(1));

    }

    @Test
    public void markNote_marksNoteServiceApiUpdatesCache(){

        Note note = new Note(NOTE_TITLE, NOTE_TEXT);

        mNotesRepository.saveNote(note);

        mNotesRepository.markNote(note);

        verify(mNotesDatasourceRemote).markNote(note);

        verify(mNotesDatasourceLocal).markNote(note);

        assertThat(mNotesRepository.mCachedNotes.size(), is(1));

        assertThat(mNotesRepository.mCachedNotes.get(note.getId()).isMarked(), is(true));

    }



    private void twoNotesLoadCallsToRepository(NotesDatasource.LoadNotesCallBack loadNotesCallBack){

        mNotesRepository.getNotes(loadNotesCallBack);

        verify(mNotesDatasourceLocal).getNotes(mNotesCallBackArgumentCaptor.capture());

        mNotesCallBackArgumentCaptor.getValue().onDataNotAvailable();

        verify(mNotesDatasourceRemote).getNotes(mNotesCallBackArgumentCaptor.capture());

        mNotesCallBackArgumentCaptor.getValue().onNotesLoaded(NOTES);

        mNotesRepository.getNotes(loadNotesCallBack);

    }

}
