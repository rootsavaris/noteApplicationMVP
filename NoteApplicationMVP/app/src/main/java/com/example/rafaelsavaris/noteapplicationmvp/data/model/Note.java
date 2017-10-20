package com.example.rafaelsavaris.noteapplicationmvp.data.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Created by rafael.savaris on 09/05/2017.
 */

public class Note {

    private final String id;

    private final String title;

    private final String text;

    private final boolean marked;

    public Note(String title, String text, String id, boolean market) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.marked = market;
    }

    public Note(String title, String text, boolean market) {
        this(title, text, UUID.randomUUID().toString(), market);
    }

    public Note(String title, String text, String id) {
        this(title, text, id, false);
    }

    public Note(String title, String text) {
        this(title, text, UUID.randomUUID().toString(), false);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public boolean isMarked() {
        return marked;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(title) &&
                Strings.isNullOrEmpty(text);
    }


}
