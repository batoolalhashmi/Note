package com.barmej.notesapp.Data;

import android.content.res.ColorStateList;
import android.net.Uri;

public class PhotoNote extends Note {

    private Uri imageUri;

    public PhotoNote(String text, ColorStateList background, Uri imageUri, NoteType noteType) {
        super(text, background, noteType);
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

}
