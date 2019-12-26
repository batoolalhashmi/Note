package com.barmej.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class AddNewNoteActivity extends AppCompatActivity {
    private static final int READ_PHOTO_PROM_GALLERY_PERMISSION = 130;
    private static final int PICK_IMAGE = 120;
    ImageView mNewPhotoIv;
    EditText mPhotoNoteEt, mNoteEt, mCheckNoteEt;
    String mNewPhotoNoteEt, mNewNoteEt, mNewCheckNoteEt;
    ColorStateList mBackgroundPhotoNoteColor, mBackgroundTextNoteColor, mBackgroundCheckBoxNoteColor, background;
    CheckBox mCheckNoteCb;
    Uri mSelectedPhotoUri, photoUri;
    CardView cardViewPhoto, cardViewNote, cardViewCheckNote;
    boolean checkBox;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);
        mNewPhotoIv = findViewById(R.id.photo_view);
        mPhotoNoteEt = findViewById(R.id.photo_note_edit_text);
        mNoteEt = findViewById(R.id.note_edit_text);
        mCheckNoteEt = findViewById(R.id.check_note_edit_text);
        mCheckNoteCb = findViewById(R.id.check_note_check_box);
        cardViewPhoto = findViewById(R.id.cardViewPhoto);
        cardViewNote = findViewById(R.id.card_view_note);
        cardViewCheckNote = findViewById(R.id.card_view_checkNote);
        Button selectPhotoNoteBt = findViewById(R.id.photo_note_button);
        Button selectCheckedNoteBt = findViewById(R.id.checked_note_button);
        Button selectTextNoteBt = findViewById(R.id.text_note_button);
        Button selectBlueBt = findViewById(R.id.blue_button);
        Button selectYellowBt = findViewById(R.id.yellow_button);
        Button selectRedBt = findViewById(R.id.red_button);
        Button submitBt = findViewById(R.id.button_submit);
        Intent intent = getIntent();
        checkBox = intent.getBooleanExtra(Constants.EXTRA_CHECK_BOX_VISIBLE, false);
        photoUri = intent.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
        String text = intent.getStringExtra(Constants.NOTE_STRING);
        background = intent.getParcelableExtra(Constants.NOTE_COLOR);
        if (background != null) {
            cardViewPhoto.setBackgroundTintList(background);
            cardViewNote.setBackgroundTintList(background);
            cardViewCheckNote.setBackgroundTintList(background);
        } else {
            cardViewPhoto.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
            cardViewNote.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
            cardViewCheckNote.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        }
        mNoteEt.setText(text);
        mPhotoNoteEt.setText(text);
        mCheckNoteEt.setText(text);
        mNewPhotoIv.setImageURI(photoUri);
        mCheckNoteCb.setChecked(checkBox);
        Log.i("AddActivity", String.valueOf(checkBox));
        id = getIntent().getIntExtra(Constants.EXTRA_ID, 0);
        mNewPhotoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
        selectPhotoNoteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewPhoto.setVisibility(View.VISIBLE);
                cardViewCheckNote.setVisibility(View.GONE);
                cardViewNote.setVisibility(View.GONE);
            }
        });
        selectCheckedNoteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewCheckNote.setVisibility(View.VISIBLE);
                cardViewPhoto.setVisibility(View.GONE);
                cardViewNote.setVisibility(View.GONE);
            }
        });
        selectTextNoteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewNote.setVisibility(View.VISIBLE);
                cardViewPhoto.setVisibility(View.GONE);
                cardViewCheckNote.setVisibility(View.GONE);
            }
        });
        selectBlueBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewPhoto.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                cardViewNote.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                cardViewCheckNote.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
            }
        });
        selectYellowBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewPhoto.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.yellow));
                cardViewNote.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.yellow));
                cardViewCheckNote.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.yellow));
            }
        });
        selectRedBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardViewPhoto.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.red));
                cardViewNote.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.red));
                cardViewCheckNote.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.red));
            }
        });
        submitBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
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

    private void selectPhoto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_PHOTO_PROM_GALLERY_PERMISSION);
        } else {
            firePickPhotoIntent();
        }
    }

    private void setSelectedPhoto(Uri data) {
        mNewPhotoIv.setImageURI(data);
        mSelectedPhotoUri = data;
    }

    private void submit() {
        if (cardViewPhoto.getVisibility() == View.VISIBLE) {
            if (mSelectedPhotoUri != null && !mPhotoNoteEt.getText().toString().matches("")) {
                mNewPhotoNoteEt = mPhotoNoteEt.getText().toString();
                mBackgroundPhotoNoteColor = cardViewPhoto.getBackgroundTintList();
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_PHOTO_URI, mSelectedPhotoUri);
                intent.putExtra(Constants.EXTRA_TEXT_NOTE, mNewPhotoNoteEt);
                intent.putExtra(Constants.EXTRA_BACKGROUND_NOTE, mBackgroundPhotoNoteColor);
                intent.putExtra(Constants.EXTRA_ID, id);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
            }
        } else if (cardViewNote.getVisibility() == View.VISIBLE) {
            if (!mNoteEt.getText().toString().matches("")) {
                mNewNoteEt = mNoteEt.getText().toString();
                mBackgroundTextNoteColor = cardViewNote.getBackgroundTintList();
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_TEXT_NOTE, mNewNoteEt);
                intent.putExtra(Constants.EXTRA_BACKGROUND_NOTE, mBackgroundTextNoteColor);
                intent.putExtra(Constants.EXTRA_ID, id);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, R.string.add_note, Toast.LENGTH_LONG).show();
            }
        } else if (cardViewCheckNote.getVisibility() == View.VISIBLE) {
            if (!mCheckNoteEt.getText().toString().matches("")) {
                mNewCheckNoteEt = mCheckNoteEt.getText().toString();
                mBackgroundCheckBoxNoteColor = cardViewCheckNote.getBackgroundTintList();
                Intent intent = new Intent();
                intent.putExtra(Constants.EXTRA_CHECK_BOX_VISIBLE, mCheckNoteCb.isChecked());
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

}