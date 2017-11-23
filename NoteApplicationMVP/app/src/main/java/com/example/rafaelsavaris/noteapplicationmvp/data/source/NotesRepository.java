package com.example.rafaelsavaris.noteapplicationmvp.data.source;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesRepository implements NotesDatasource {

    private static NotesRepository instance = null;

    private final NotesDatasource notesRemote;

    private final NotesDatasource notesLocal;

    Map<String, Note> cachedNotes;

    boolean cacheIsDirty = false;

    public static NotesRepository getInstance(NotesDatasource notesRemote, NotesDatasource notesLocal){

        if (instance == null){
            instance = new NotesRepository(notesRemote, notesLocal);
        }

        return instance;

    }

    private NotesRepository(NotesDatasource notesRemote, NotesDatasource notesLocal){
        this.notesRemote = notesRemote;
        this.notesLocal = notesLocal;
    }

    public static void destroyInstance(){
        instance = null;
    }


    @Override
    public void getNotes(final LoadNotesCallBack loadNotesCallBack) {

        if (cachedNotes != null && !cacheIsDirty){

            loadNotesCallBack.onNotesLoaded(new ArrayList<>(cachedNotes.values()));
            return;

        }

        if (cacheIsDirty){
            getNotesFromRemoteDataSource(loadNotesCallBack);
        } else {

            notesLocal.getNotes(new LoadNotesCallBack() {
                @Override
                public void onNotesLoaded(List<Note> notes) {
                    refreshCache(notes);
                    loadNotesCallBack.onNotesLoaded(notes);
                }

                @Override
                public void onDataNotAvailable() {
                    getNotesFromRemoteDataSource(loadNotesCallBack);
                }
            });

        }

    }

    @Override
    public void deleteAllNotes() {
    }

    @Override
    public void saveNote(Note note) {
    }

    private void getNotesFromRemoteDataSource(final LoadNotesCallBack loadNotesCallBack){

        notesRemote.getNotes(new LoadNotesCallBack() {

            @Override
            public void onNotesLoaded(List<Note> notes) {
                refreshCache(notes);
                refreshLocalDataSource(notes);
            }

            @Override
            public void onDataNotAvailable() {
                loadNotesCallBack.onDataNotAvailable();
            }
        });

    }

    private void refreshCache(List<Note> notes){

        if (cachedNotes == null){
            cachedNotes = new LinkedHashMap<>();
        }

        cachedNotes.clear();

        for (Note note : notes){
            cachedNotes.put(note.getId(), note);
        }

        cacheIsDirty = false;

    }

    private void refreshLocalDataSource(List<Note> notes){

        notesLocal.deleteAllNotes();

        for(Note note : notes){
            notesLocal.saveNote(note);
        }

    }

}
