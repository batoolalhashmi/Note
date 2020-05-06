package com.barmej.notesapp.data;
import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.barmej.notesapp.data.database.dao.NoteDao;
import com.barmej.notesapp.data.database.NoteDatabase;

import java.util.List;
public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    private NoteDatabase database;

    public NoteRepository(Context context) {
        database = NoteDatabase.getInstance(context);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(final Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(final Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(final Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(final Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}