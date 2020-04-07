package com.barmej.notesapp.data.database.converters;

import android.content.res.ColorStateList;

import androidx.room.TypeConverter;

import com.barmej.notesapp.data.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BackgroundConverter {
    @TypeConverter
    public static List<background> fromString(String value){
        Type listType = new TypeToken<List<ColorStateList>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }
    @TypeConverter
    public static String fromNote(List<Note>notes){
        return new Gson().toJson(notes);
    }
}
}
