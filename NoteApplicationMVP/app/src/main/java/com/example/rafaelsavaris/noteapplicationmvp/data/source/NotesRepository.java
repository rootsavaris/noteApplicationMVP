package com.example.rafaelsavaris.noteapplicationmvp.data.source;

import com.example.rafaelsavaris.noteapplicationmvp.data.model.Note;

import java.util.ArrayList;
import java.util.Iterator;
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

    public static NotesRepository getInstance(NotesDatasource notesRemote, NotesDatasource notesLocal) {

        if (instance == null) {
            instance = new NotesRepository(notesRemote, notesLocal);
        }

        return instance;

    }

    private NotesRepository(NotesDatasource notesRemote, NotesDatasource notesLocal) {
        this.notesRemote = notesRemote;
        this.notesLocal = notesLocal;
    }

    public static void destroyInstance() {
        instance = null;
    }


    @Override
    public void getNotes(final LoadNotesCallBack loadNotesCallBack) {

        if (cachedNotes != null && !cacheIsDirty) {

            loadNotesCallBack.onNotesLoaded(new ArrayList<>(cachedNotes.values()));
            return;

        }

        if (cacheIsDirty) {
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

    @Override
    public void refreshNotes() {
        cacheIsDirty = true;
    }

    @Override
    public void markNote(Note note) {

        notesLocal.markNote(note);
        notesRemote.markNote(note);

        Note markedNote = new Note(note.getTitle(), note.getText(), note.getId(), !note.isMarked());

        if (cachedNotes == null){
            cachedNotes = new LinkedHashMap<>();
        }

        cachedNotes.put(markedNote.getId(), markedNote);

    }

    @Override
    public void clearMarkedNotes() {

        notesRemote.clearMarkedNotes();
        notesLocal.clearMarkedNotes();

        if (cachedNotes == null){
            cachedNotes = new LinkedHashMap<>();
        }

        Iterator<Map.Entry<String, Note>> it = cachedNotes.entrySet().iterator();

        while (it.hasNext()){

            Map.Entry<String, Note> entry = it.next();

            if (entry.getValue().isMarked()){
                it.remove();
            }

        }

    }

    private void getNotesFromRemoteDataSource(final LoadNotesCallBack loadNotesCallBack) {

        notesRemote.getNotes(new LoadNotesCallBack() {

            @Override
            public void onNotesLoaded(List<Note> notes) {
                refreshCache(notes);
                refreshLocalDataSource(notes);
                loadNotesCallBack.onNotesLoaded(new ArrayList<Note>(cachedNotes.values()));
            }

            @Override
            public void onDataNotAvailable() {
                loadNotesCallBack.onDataNotAvailable();
            }
        });

    }

    private void refreshCache(List<Note> notes) {

        if (cachedNotes == null) {
            cachedNotes = new LinkedHashMap<>();
        }

        cachedNotes.clear();

        for (Note note : notes) {
            cachedNotes.put(note.getId(), note);
        }

        cacheIsDirty = false;

    }

    private void refreshLocalDataSource(List<Note> notes) {

        notesLocal.deleteAllNotes();

        for (Note note : notes) {
            notesLocal.saveNote(note);
        }

    }



}