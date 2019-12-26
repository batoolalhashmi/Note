package com.barmej.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.barmej.notesapp.Adapter.NoteAdapter;
import com.barmej.notesapp.Data.CheckboxNote;
import com.barmej.notesapp.Data.Note;
import com.barmej.notesapp.Data.PhotoNote;
import com.barmej.notesapp.listener.CheckBoxClickListener;
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    private static final int ADD_NOTE = 145;
    private static final int EDIT_NOTE = 155;
    private RecyclerView mRecycleView;
    static ArrayList<Note> mItems;
    private NoteAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mRecycleView = findViewById(R.id.recycler_view_notes);
        mAdapter = new NoteAdapter(mItems, new ItemClickListener() {
            public void onClickItem(int position) {
                editNote(position);
            }
        }, new ItemLongClickListener() {
            public void onLongClickItem(int position) {
                deleteItem(position);
            }
        }, new CheckBoxClickListener() {
            @Override
            public void onChecked(int position, boolean checked) {
                changeChecked(position, checked);
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

    private void startAddNewNoteActivity() {
        Intent intent = new Intent(this, AddNewNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE || requestCode == EDIT_NOTE) {
            if (resultCode == RESULT_OK && data != null) {

                String noteString = data.getStringExtra(Constants.EXTRA_TEXT_NOTE);
                ColorStateList noteColor = data.getParcelableExtra(Constants.EXTRA_BACKGROUND_NOTE);
                Note note;

                if (data.hasExtra(Constants.EXTRA_PHOTO_URI)) {
                    Uri photoUri = data.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
                    note = new PhotoNote(noteString, noteColor, photoUri, Note.NoteType.PHOTO);

                } else if (data.hasExtra(Constants.EXTRA_CHECK_BOX_VISIBLE)) {
                    boolean noteCheckBox = Objects.requireNonNull(data.getExtras()).getBoolean(Constants.EXTRA_CHECK_BOX_VISIBLE);
                    note = new CheckboxNote(noteString, noteColor, noteCheckBox, Note.NoteType.CHECKBOX);
                } else {
                    note = new Note(noteString, noteColor, Note.NoteType.TEXT);
                }
                if (requestCode == ADD_NOTE) {
                    mItems.add(note);
                    mAdapter.notifyItemInserted(mItems.size() - 1);
                } else {
                    int id = data.getIntExtra(Constants.EXTRA_ID, 1);
                    mItems.set(id, note);
                    mAdapter.notifyItemChanged(id);
                }
            }

        }
    }

    private void changeChecked(int position, boolean checked) {
        CheckboxNote checkboxNote = (CheckboxNote) mItems.get(position);
        if (checked) {
            checkboxNote.setIsChecked(true);
        } else {
            checkboxNote.setIsChecked(false);
        }
    }

    private void editNote(int position) {
        Note note = mItems.get(position);
        Note.NoteType noteType = note.getNoteType();
        Intent intent;
        intent = new Intent(this, AddNewNoteActivity.class);
        intent.putExtra(Constants.EXTRA_ID, position);
        intent.putExtra(Constants.NOTE_STRING, note.getText());
        intent.putExtra(Constants.NOTE_COLOR, note.getBackground());
        switch (noteType) {
            case PHOTO:
                PhotoNote photoNote = (PhotoNote) mItems.get(position);
                intent.putExtra(Constants.EXTRA_PHOTO_URI, photoNote.getImageUri());
                break;
            case CHECKBOX:
                CheckboxNote checkboxNote = (CheckboxNote) mItems.get(position);
                intent.putExtra(Constants.EXTRA_CHECK_BOX_VISIBLE, checkboxNote.getIsChecked());
                Log.i("mainActivity", String.valueOf(checkboxNote.getIsChecked()));
                break;
        }
        startActivityForResult(intent, EDIT_NOTE);
    }

    private void deleteItem(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mItems.remove(position);
                        mAdapter.notifyItemRemoved(position);
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
                        recreate();
                    }
                }).create();
        alertDialog.show();
    }
}