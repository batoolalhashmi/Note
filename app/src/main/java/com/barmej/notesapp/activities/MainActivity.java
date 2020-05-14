package com.barmej.notesapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.LocaleHelper;
import com.barmej.notesapp.R;
import com.barmej.notesapp.adapter.NoteAdapter;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.listener.CheckBoxClickListener;
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;
import com.barmej.notesapp.viewmodel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private static ArrayList<Note> mItems;
    private NoteAdapter mAdapter;
    private NoteViewModel noteViewModel;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                mItems.clear();
                mItems.addAll(notes);
                mAdapter.notifyDataSetChanged();
            }
        });
        mItems = new ArrayList<>();
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
        }
        );
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
        startActivity(intent);
    }

    public void changeChecked(int position, boolean checked) {
        note = mItems.get(position);
        if (checked) {
            note.setChecked(true);
        } else {
            note.setChecked(false);
        }
    }

    private void editNote(int position) {
        note = mItems.get(position);
        Intent intent = new Intent(this, AddNewNoteActivity.class);
        intent.putExtra(Constants.EXTRA_ID, note.getId());
        intent.putExtra(Constants.EXTRA_NOTE, note);
        startActivity(intent);
    }

    private void deleteItem(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.delete_confirmation)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        noteViewModel.delete(mItems.get(position));
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
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage(R.string.delete_confirmation)
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            noteViewModel.deleteAllNotes();
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