package com.barmej.notesapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private int background;
    private String stringImageUri;
    private boolean isChecked;
    private int type;

    public Note() {

    }

    protected Note(Parcel in) {
        id = in.readInt();
        text = in.readString();
        background = in.readInt();
        stringImageUri = in.readString();
        isChecked = in.readByte() != 0;
        type = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(text);
        parcel.writeInt(background);
        parcel.writeString(stringImageUri);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
        parcel.writeInt(type);

    }

    public Note(String text, int background, String stringImageUri, boolean isChecked, int type) {
        this.text = text;
        this.background = background;
        this.stringImageUri = stringImageUri;
        this.isChecked = isChecked;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getStringImageUri() {
        return stringImageUri;
    }

    public void setStringImageUri(String stringImageUri) {
        this.stringImageUri = stringImageUri;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
