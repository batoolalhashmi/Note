package com.barmej.notesapp.Data;

import android.content.res.ColorStateList;

public class CheckboxNote extends Note {
    private boolean checkBox;

    public CheckboxNote(String text, ColorStateList background, boolean checkBox, NoteType noteType) {
        super(text, background, noteType);
        this.checkBox = checkBox;
    }

    public boolean getCheckBox() {
        return checkBox;
    }
}
