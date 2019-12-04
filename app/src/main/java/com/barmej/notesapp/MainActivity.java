package com.barmej.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.barmej.notesapp.Adapter.NoteAdapter;
import com.barmej.notesapp.Data.CheckboxNote;
import com.barmej.notesapp.Data.Note;
import com.barmej.notesapp.Data.PhotoNote;
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    static ArrayList<Note> mItems;
    private NoteAdapter mAdapter;
    private static final int ADD_NOTE = 145;
    private static final int EDIT_NOTE = 155;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoadData();

        mRecycleView = findViewById(R.id.recycler_view_notes);
        mAdapter = new NoteAdapter(mItems, new ItemClickListener() {
            public void onClickItem(int position) {
                editNote(position);
            }
        }, new ItemLongClickListener() {
            public void onLongClickItem(int position) {
                deleteItem(position);
            }
        });

        StaggeredGridLayoutManager mGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mGridLayoutManager);
        mRecycleView.setAdapter(mAdapter);

        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddNewNoteActivity();
            }
        });


    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.MY_PREFERENCE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mItems);
        editor.putString("note_list", json);
        editor.apply();
    }

    private void LoadData() {
        sharedpreferences = getSharedPreferences(Constants.MY_PREFERENCE,
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedpreferences.getString("note_list", null);
        Type type = new TypeToken<ArrayList<Note>>() {
        }.getType();
        mItems = gson.fromJson(json, type);
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
    }

    private void startAddNewNoteActivity() {
        Intent intent = new Intent(this, AddNewNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE) {
            if (resultCode == RESULT_OK && data != null) {

                String NoteString = data.getStringExtra(Constants.EXTRA_TEXT_NOTE);
                ColorStateList NoteColor = data.getParcelableExtra(Constants.EXTRA_BACKGROUND_NOTE);
                Note note = new Note(NoteString, NoteColor, Note.NoteType.TEXT);

                if (data.hasExtra(Constants.EXTRA_PHOTO_URI)) {
                    Uri photoUri = data.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
                    note = new PhotoNote(NoteString, NoteColor, photoUri, Note.NoteType.PHOTO);

                } else if (data.hasExtra(Constants.EXTRA_CHECK_BOX_VISIBLE)) {
                    boolean NoteCheckBox = Objects.requireNonNull(data.getExtras()).getBoolean(Constants.EXTRA_CHECK_BOX_VISIBLE);
                    note = new CheckboxNote(NoteString, NoteColor, NoteCheckBox, Note.NoteType.CHECKBOX);
                }
                addItem(note);
            }

        } else if (requestCode == EDIT_NOTE) {
            if (resultCode == RESULT_OK && data != null) {
                String NoteString = data.getStringExtra(Constants.EXTRA_TEXT_NOTE);
                ColorStateList NoteColor = data.getParcelableExtra(Constants.EXTRA_BACKGROUND_NOTE);
                int id = data.getIntExtra(Constants.EXTRA_ID, 1);
                Note note = new Note(NoteString, NoteColor, Note.NoteType.TEXT);

                if (data.hasExtra(Constants.EXTRA_PHOTO_URI)) {
                    Uri photoUri = data.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
                    note = new PhotoNote(NoteString, NoteColor, photoUri, Note.NoteType.PHOTO);

                } else if (data.hasExtra(Constants.EXTRA_CHECK_BOX_VISIBLE)) {
                    boolean NoteCheckBox = Objects.requireNonNull(data.getExtras()).getBoolean(Constants.EXTRA_CHECK_BOX_VISIBLE);
                    note = new CheckboxNote(NoteString, NoteColor, NoteCheckBox, Note.NoteType.CHECKBOX);
                }
                mItems.set(id, note);
                mAdapter.notifyItemChanged(id);
            }

        } else {
            Toast.makeText(this, R.string.didnt_add_photo, Toast.LENGTH_LONG).show();
        }
        saveData();
    }

    private void addItem(Note note) {
        mItems.add(note);
        mAdapter.notifyItemInserted(mItems.size() - 1);
    }

    private void editNote(int position) {
        Note note = mItems.get(position);
        Note.NoteType noteType = note.getNoteType();
        Intent intent;
        switch (noteType) {
            case TEXT:
                intent = new Intent(this, TextNoteEditorActivity.class);
                intent.putExtra(Constants.EXTRA_ID, position);
                intent.putExtra(Constants.NOTE_STRING, note.getText());
                intent.putExtra(Constants.NOTE_COLOR, note.getBackground());
                startActivityForResult(intent, EDIT_NOTE);
                break;
            case PHOTO:
                PhotoNote photoNote = (PhotoNote) mItems.get(position);
                intent = new Intent(this, PhotoNoteEditorActivity.class);
                intent.putExtra(Constants.EXTRA_ID, position);
                intent.putExtra(Constants.NOTE_STRING, note.getText());
                intent.putExtra(Constants.NOTE_COLOR, note.getBackground());
                intent.putExtra(Constants.EXTRA_PHOTO_URI, photoNote.getImageUri());
                startActivityForResult(intent, EDIT_NOTE);
                break;
            case CHECKBOX:
                CheckboxNote checkboxNote = (CheckboxNote) mItems.get(position);
                intent = new Intent(this, CheckBoxNoteEditorActivity.class);
                intent.putExtra(Constants.EXTRA_ID, position);
                intent.putExtra(Constants.NOTE_STRING, note.getText());
                intent.putExtra(Constants.NOTE_COLOR, note.getBackground());
                intent.putExtra(Constants.EXTRA_CHECK_BOX_VISIBLE, checkboxNote.getCheckBox());
                startActivityForResult(intent, EDIT_NOTE);
                break;
        }
    }

    private void deleteItem(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mItems.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        saveData();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuChangLang) {
            showLanguageDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showLanguageDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_lang_text)
                .setItems(R.array.languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String language = "ar";
                        switch (which) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                        }
                        LocaleHelper.setLocale(MainActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create();
        alertDialog.show();
    }
}