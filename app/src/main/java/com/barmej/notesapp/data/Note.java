package com.barmej.notesapp.data;
import android.content.res.ColorStateList;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "note_table")
public class Note implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String text;
    private ColorStateList background;
    private String stringImageUri;
    private boolean isChecked;
    protected Note(Parcel in) {
        id = in.readInt();
        text = in.readString();
        background = in.readParcelable(ColorStateList.class.getClassLoader());
        stringImageUri = in.readString();
        isChecked = in.readByte() != 0;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Note(String text, ColorStateList background, String stringImageUri, boolean isChecked) {
        this.text = text;
        this.background = background;
        this.stringImageUri = stringImageUri;
        this.isChecked = isChecked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ColorStateList getBackground() {
        return background;
    }

    public void setBackground(ColorStateList background) {
        this.background = background;
    }

    public String getStringImageUri() {
        return stringImageUri;
    }

    public void setStringImageUri(String stringImageUri) {
        this.stringImageUri = stringImageUri;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(text);
        parcel.writeParcelable(background, i);
        parcel.writeString(stringImageUri);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
    }
}
