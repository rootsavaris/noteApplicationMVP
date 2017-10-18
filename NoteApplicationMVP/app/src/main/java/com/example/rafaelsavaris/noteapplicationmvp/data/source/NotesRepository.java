package com.example.rafaelsavaris.noteapplicationmvp.data.source;

/**
 * Created by rafael.savaris on 18/10/2017.
 */

public class NotesRepository implements NotesDatasource {

    private static NotesRepository instance = null;

    private NotesDatasource notesRemote;

    private NotesDatasource notesLocal;

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

}
