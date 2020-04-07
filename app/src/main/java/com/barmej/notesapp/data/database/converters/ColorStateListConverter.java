package com.barmej.notesapp.data.database.converters;

import android.content.res.ColorStateList;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class ColorStateListConverter {
    @TypeConverter
    public static ColorStateList fromString(String value){
        Type listType = new TypeToken<ColorStateList>(){}.getType();
        return new Gson().fromJson(value,listType);
    }
    @TypeConverter
    public static String fromNote(ColorStateList background){
        return new Gson().toJson(background);
    }
}
