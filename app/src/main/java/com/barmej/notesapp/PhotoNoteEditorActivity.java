package com.barmej.notesapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PhotoNoteEditorActivity extends AppCompatActivity {

    EditText mEditText;
    View view;
    ColorStateList background;
    int id;
    Uri photoUri;
    ImageView image;
    private static final int READ_PHOTO_PROM_GALLERY_PERMISSION = 130;
    private static final int PICK_IMAGE = 120;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_photo_details);
        Intent intent = getIntent();
        String text = intent.getStringExtra(Constants.NOTE_STRING);
        background = intent.getParcelableExtra(Constants.NOTE_COLOR);
        photoUri = intent.getParcelableExtra(Constants.EXTRA_PHOTO_URI);
        mEditText = findViewById(R.id.photo_note_edit_text);
        view = findViewById(R.id.note_photo_layout);
        image = findViewById(R.id.photo_view);
        mEditText.setText(text);
        view.setBackgroundTintList(background);
        image.setImageURI(photoUri);
        id = getIntent().getIntExtra(Constants.EXTRA_ID, 0);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
        findViewById(R.id.save_photo_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoNoteEditorActivity.this, MainActivity.class);
                String editText = mEditText.getText().toString();
                intent.putExtra(Constants.EXTRA_TEXT_NOTE, editText);
                intent.putExtra(Constants.EXTRA_BACKGROUND_NOTE, background);
                intent.putExtra(Constants.EXTRA_PHOTO_URI, photoUri);
                intent.putExtra(Constants.EXTRA_ID, id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                setSelectedPhoto(data.getData());
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                Toast.makeText(this, R.string.failed_to_get_image, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setSelectedPhoto(Uri data) {
        image.setImageURI(data);
        photoUri = data;
    }

    private void firePickPhotoIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE);
    }
}