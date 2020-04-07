package com.barmej.notesapp.data.database;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.data.database.dao.NoteDao;
import com.barmej.notesapp.data.database.converters.ColorStateListConverter;

@Database(entities = {Note.class}, version = 39 ,exportSchema = false)
@TypeConverters({ColorStateListConverter.class})
public abstract class NoteDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static NoteDatabase instance;
    public abstract NoteDao noteDao();
    public static NoteDatabase getInstance(Context context){
        if(instance == null) {
            synchronized (LOCK) {
                if(instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                             NoteDatabase.class, "note_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
