package com.barmej.notesapp.Data;

import android.content.res.ColorStateList;

public class CheckboxNote extends Note {
    private boolean isChecked;

    public CheckboxNote(String text, ColorStateList background, boolean isChecked, NoteType noteType) {
        super(text, background, noteType);
        this.isChecked = isChecked;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
