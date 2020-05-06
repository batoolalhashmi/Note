package com.barmej.notesapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.databinding.ActivityAddNewNoteBinding;
import com.barmej.notesapp.viewmodel.NoteViewModel;


public class AddNewNoteActivity extends AppCompatActivity {
    private static final int READ_PHOTO_PROM_GALLERY_PERMISSION = 130;
    private static final int PICK_IMAGE = 120;
    private String mTextNoteEt;
    private int id;
    private ActivityAddNewNoteBinding binding;
    private NoteViewModel noteViewModel;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_note);
        binding.setActivity(this);
        binding.setBlueColor(getColor(R.color.blue));
        binding.setYellowColor(getColor(R.color.yellow));
        binding.setRedColor(getColor(R.color.red));
        binding.setTextNote(Constants.TEXT_NOTE);
        binding.setPhotoNote(Constants.PHOTO_NOTE);
        binding.setCheckBoxNote(Constants.CHECK_BOX_NOTE);
        id = getIntent().getIntExtra(Constants.EXTRA_ID, 0);
        note = getIntent().getParcelableExtra(Constants.EXTRA_NOTE);
        if (note == null) {
            note = new Note();
            note.setBackground(getColor(R.color.blue));
            note.setType(Constants.PHOTO_NOTE);
        }
        binding.setNote(note);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_PHOTO_PROM_GALLERY_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                firePickPhotoIntent();
            } else {
                Toast.makeText(this, R.string.read_permission_needed_to_access_files, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                setSelectedPhoto(data.getData());
            } else {
                Toast.makeText(this, R.string.failed_to_get_image, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void selectPhoto(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_PHOTO_PROM_GALLERY_PERMISSION);
        } else {
            firePickPhotoIntent();
        }
    }

    private void setSelectedPhoto(Uri data) {
        binding.noteImageView.setImageURI(data);
        note.setStringImageUri(data.toString());
    }

    public void submit(View view) {
        mTextNoteEt = binding.noteEditText.getText().toString();
        if (note.getType() == Constants.PHOTO_NOTE) {
            if (binding.noteImageView != null && !mTextNoteEt.matches("")) {
                if (id == 0) {
                    noteViewModel.insert(new Note(mTextNoteEt, note.getBackground(), note.getStringImageUri(), note.isChecked(), Constants.PHOTO_NOTE));
                } else {
                    note.setText(mTextNoteEt);
                    note.setType(Constants.PHOTO_NOTE);
                    noteViewModel.update(note);
                }
                finish();
            } else {
                Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
            }
        } else if (note.getType() == Constants.TEXT_NOTE) {
            if (!mTextNoteEt.matches("")) {
                if (id == 0) {
                    noteViewModel.insert(new Note(mTextNoteEt, note.getBackground(), note.getStringImageUri(), note.isChecked(), Constants.TEXT_NOTE));
                } else {
                    note.setText(mTextNoteEt);
                    note.setType(Constants.TEXT_NOTE);
                    noteViewModel.update(note);
                }
                finish();
            } else {
                Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
            }
        } else if (note.getType() == Constants.CHECK_BOX_NOTE) {
            if (!mTextNoteEt.matches("")) {
                if (id == 0) {
                    noteViewModel.insert(new Note(mTextNoteEt, note.getBackground(), note.getStringImageUri(), binding.noteCheckBox.isChecked(), Constants.CHECK_BOX_NOTE));
                } else {
                    note.setText(mTextNoteEt);
                    note.setType(Constants.CHECK_BOX_NOTE);
                    note.setChecked(binding.noteCheckBox.isChecked());
                    noteViewModel.update(note);
                }
                finish();
            } else {
                Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
        }
    }

    private void firePickPhotoIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE);
    }

    public void changeBackground(int resId) {
        note.setBackground(resId);
        binding.setNote(note);
    }

    public void setNoteType(int type) {
        note.setType(type);
        binding.setNote(note);
    }
}