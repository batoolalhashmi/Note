package com.barmej.notesapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.databinding.ActivityAddNewNoteBinding;

public class AddNewNoteActivity extends AppCompatActivity {
    private static final int READ_PHOTO_PROM_GALLERY_PERMISSION = 130;
    private static final int PICK_IMAGE = 120;
    private String mNewPhotoNoteEt, mNewNoteEt, mNewCheckNoteEt;
    private ColorStateList mBackgroundPhotoNoteColor, mBackgroundTextNoteColor, mBackgroundCheckBoxNoteColor;
    private Uri mSelectedPhotoUri;
    private int id;
    private ActivityAddNewNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_note);
        binding.setActivity(this);
        binding.setBlueColor(R.color.blue);
        binding.setOrangeColor(R.color.yellow);
        binding.setRedColor(R.color.red);
        binding.setNote((Note) getIntent().getParcelableExtra(Constants.EXTRA_NOTE));
        id = getIntent().getIntExtra(Constants.EXTRA_ID, 0);
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
        binding.photoView.setImageURI(data);
        mSelectedPhotoUri = data;
    }

    public void submit(View view) {
        if (binding.cardViewPhoto.getVisibility() == View.VISIBLE) {
            if (mSelectedPhotoUri != null && !binding.photoNoteEditText.getText().toString().matches("")) {
                mNewPhotoNoteEt = binding.photoNoteEditText.getText().toString();
                mBackgroundPhotoNoteColor = binding.cardViewPhoto.getBackgroundTintList();
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_PHOTO_URI, mSelectedPhotoUri.toString());
                intent.putExtra(Constants.EXTRA_TEXT_NOTE, mNewPhotoNoteEt);
                intent.putExtra(Constants.EXTRA_BACKGROUND_NOTE, mBackgroundPhotoNoteColor);
                intent.putExtra(Constants.EXTRA_ID, id);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
            }
        } else if (binding.cardViewNote.getVisibility() == View.VISIBLE) {
            if (!binding.noteEditText.getText().toString().matches("")) {
                mNewNoteEt = binding.noteEditText.getText().toString();
                mBackgroundTextNoteColor = binding.cardViewNote.getBackgroundTintList();
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_TEXT_NOTE, mNewNoteEt);
                intent.putExtra(Constants.EXTRA_BACKGROUND_NOTE, mBackgroundTextNoteColor);
                intent.putExtra(Constants.EXTRA_ID, id);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
            }
        } else if (binding.cardViewCheckNote.getVisibility() == View.VISIBLE) {
            if (!binding.checkNoteEditText.getText().toString().matches("")) {
                mNewCheckNoteEt = binding.checkNoteEditText.getText().toString();
                mBackgroundCheckBoxNoteColor = binding.cardViewCheckNote.getBackgroundTintList();
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_CHECK_BOX_VISIBLE, binding.checkNoteCheckBox.isChecked());
                intent.putExtra(Constants.EXTRA_TEXT_NOTE, mNewCheckNoteEt);
                intent.putExtra(Constants.EXTRA_BACKGROUND_NOTE, mBackgroundCheckBoxNoteColor);
                intent.putExtra(Constants.EXTRA_ID, id);
                setResult(RESULT_OK, intent);
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
        binding.cardViewPhoto.setBackgroundTintList(ContextCompat.getColorStateList(this, resId));
        binding.cardViewNote.setBackgroundTintList(ContextCompat.getColorStateList(this, resId));
        binding.cardViewCheckNote.setBackgroundTintList(ContextCompat.getColorStateList(this, resId));
    }

    public void checkNoteVisible(View view) {
        binding.cardViewPhoto.setVisibility(View.GONE);
        binding.cardViewCheckNote.setVisibility(View.VISIBLE);
        binding.cardViewNote.setVisibility(View.GONE);
    }

    public void textNoteVisible(View view) {
        binding.cardViewNote.setVisibility(View.VISIBLE);
        binding.cardViewPhoto.setVisibility(View.GONE);
        binding.cardViewCheckNote.setVisibility(View.GONE);

    }

    public void photoNoteVisible(View view) {
        binding.cardViewPhoto.setVisibility(View.VISIBLE);
        binding.cardViewCheckNote.setVisibility(View.GONE);
        binding.cardViewNote.setVisibility(View.GONE);
    }
}