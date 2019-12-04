package com.barmej.notesapp.Data;

import android.content.res.ColorStateList;

public class Note {

    private String text;
    private ColorStateList background;

    public NoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    public enum NoteType {
        TEXT, PHOTO, CHECKBOX;
    }

    private NoteType noteType;

    public Note(String text, ColorStateList background, NoteType noteType) {
        this.text = text;
        this.background = background;
        this.noteType = noteType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ColorStateList getBackground() {
        return background;
    }

    public void setBackground(ColorStateList background) {
        this.background = background;
    }
}
