package com.barmej.notesapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class TextNoteEditorActivity extends AppCompatActivity {

    EditText mEditText ;
    View view;
    ColorStateList background;
    int id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Intent intent = getIntent();
        String text = intent.getStringExtra(Constants.NOTE_STRING);
        background = intent.getParcelableExtra(Constants.NOTE_COLOR);
        mEditText = findViewById(R.id.note_edit_text);
        view = findViewById(R.id.note_text_layout);
        mEditText.setText(text);
        view.setBackgroundTintList(background);
        id = getIntent().getIntExtra(Constants.EXTRA_ID,0);

        findViewById(R.id.save_photo_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TextNoteEditorActivity.this, MainActivity.class);
                String editText = mEditText.getText().toString();
                intent.putExtra(Constants.EXTRA_TEXT_NOTE,editText);
                intent.putExtra(Constants.EXTRA_BACKGROUND_NOTE,background);
                intent.putExtra(Constants.EXTRA_ID,id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        }
}
