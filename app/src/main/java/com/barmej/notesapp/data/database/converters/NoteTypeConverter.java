package com.barmej.notesapp.data.database.converters;

import androidx.room.TypeConverter;

import com.barmej.notesapp.data.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
/*
public class NoteTypeConverter {
    @TypeConverter
    public static Note.NoteType fromString(String value){
        Type listType = new TypeToken<Note.NoteType>(){}.getType();
        return new Gson().fromJson(value,listType);
    }
    @TypeConverter
    public static String fromNote(Note.NoteType noteType){
        return new Gson().toJson(noteType);
    }
}
*/