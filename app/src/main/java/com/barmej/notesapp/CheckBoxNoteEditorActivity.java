package com.barmej.notesapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class CheckBoxNoteEditorActivity extends AppCompatActivity {
    EditText mEditText;
    View view;
    ColorStateList background;
    CheckBox CheckNoteCb;
    boolean checkBox;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_check_details);
        Intent intent = getIntent();
        CheckNoteCb = findViewById(R.id.check_note_check_box);
        checkBox = Objects.requireNonNull(intent.getExtras()).getBoolean(Constants.EXTRA_CHECK_BOX_VISIBLE);
        String text = intent.getStringExtra(Constants.NOTE_STRING);
        background = intent.getParcelableExtra(Constants.NOTE_COLOR);
        mEditText = findViewById(R.id.check_box_note_edit_text);
        view = findViewById(R.id.note_text_layout);
        mEditText.setText(text);
        view.setBackgroundTintList(background);
        CheckNoteCb.setChecked(checkBox);
        id = getIntent().getIntExtra(Constants.EXTRA_ID, 0);

        findViewById(R.id.save_photo_note).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckBoxNoteEditorActivity.this, MainActivity.class);
                String editText = mEditText.getText().toString();
                intent.putExtra(Constants.EXTRA_CHECK_BOX_VISIBLE, CheckNoteCb.isChecked());
                intent.putExtra(Constants.EXTRA_TEXT_NOTE, editText);
                intent.putExtra(Constants.EXTRA_BACKGROUND_NOTE, background);
                intent.putExtra(Constants.EXTRA_ID, id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}