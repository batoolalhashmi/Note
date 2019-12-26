package com.barmej.notesapp.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.Data.CheckboxNote;
import com.barmej.notesapp.Data.Note;
import com.barmej.notesapp.Data.PhotoNote;
import com.barmej.notesapp.MainActivity;
import com.barmej.notesapp.R;
import com.barmej.notesapp.listener.CheckBoxClickListener;
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;

import java.util.ArrayList;

class PhotoNoteViewHolder extends RecyclerView.ViewHolder {
    ImageView photoIv;
    CardView photoBackgroundColor;
    TextView photoTv;

    public PhotoNoteViewHolder(View itemView, final ItemClickListener mItemClickListener, final ItemLongClickListener mItemLongClickListener) {
        super(itemView);
        photoIv = itemView.findViewById(R.id.image_view_list_item_photo);
        photoTv = itemView.findViewById(R.id.text_view_list_item_photo);
        photoBackgroundColor = itemView.findViewById(R.id.card_view_photo);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                mItemClickListener.onClickItem(position);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = getAdapterPosition();
                mItemLongClickListener.onLongClickItem(position);
                return true;
            }
        });

    }
}

class TextNoteViewHolder extends RecyclerView.ViewHolder {
    CardView textBackgroundColor;
    TextView textTv;

    public TextNoteViewHolder(View itemView, final ItemClickListener mItemClickListener, final ItemLongClickListener mItemLongClickListener) {
        super(itemView);
        textTv = itemView.findViewById(R.id.text_note_view);
        textBackgroundColor = itemView.findViewById(R.id.text_note_card_view);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                mItemClickListener.onClickItem(position);
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = getAdapterPosition();
                mItemLongClickListener.onLongClickItem(position);
                return true;
            }
        });
    }
}

class CheckBoxNoteViewHolder extends RecyclerView.ViewHolder {
    CardView checkBoxBackgroundColor;
    TextView checkBoxTv;
    CheckBox checkBox;

    public CheckBoxNoteViewHolder(final View itemView, final ItemClickListener mItemClickListener, final ItemLongClickListener mItemLongClickListener, final CheckBoxClickListener mCheckBoxClickListener) {
        super(itemView);
        checkBoxTv = itemView.findViewById(R.id.check_box_note_view);
        checkBoxBackgroundColor = itemView.findViewById(R.id.check_box_note_card_view);
        checkBox = itemView.findViewById(R.id.check_box);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                mItemClickListener.onClickItem(position);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = getAdapterPosition();
                mItemLongClickListener.onLongClickItem(position);
                return true;
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton checkBox, boolean checked) {
                int position = getAdapterPosition();
                mCheckBoxClickListener.onChecked(position, checked);

            }
        });

    }
}

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;
    private CheckBoxClickListener mCheckBoxClickListener;
    private ArrayList<Note> mItems;


    public NoteAdapter(ArrayList<Note> mItems, ItemClickListener mItemClickListener, ItemLongClickListener mItemLongClickListener, CheckBoxClickListener mCheckedBoxClickListener) {
        this.mItems = mItems;
        this.mItemClickListener = mItemClickListener;
        this.mItemLongClickListener = mItemLongClickListener;
        this.mCheckBoxClickListener = mCheckedBoxClickListener;
    }

    public int getItemViewType(int position) {
        Note note = mItems.get(position);
        if (note instanceof PhotoNote) {
            return (Constants.TYPE_PHOTO_NOTE);
        } else if (note instanceof CheckboxNote) {
            return (Constants.TYPE_CHECK_NOTE);
        } else {
            return (Constants.TYPE_NOTE);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case (Constants.TYPE_PHOTO_NOTE):
                View photoNoteViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_photo, parent, false);
                return new PhotoNoteViewHolder(photoNoteViewHolder, mItemClickListener, mItemLongClickListener);
            case (Constants.TYPE_CHECK_NOTE):
                View checkBoxNoteViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_check, parent, false);
                return new CheckBoxNoteViewHolder(checkBoxNoteViewHolder, mItemClickListener, mItemLongClickListener, mCheckBoxClickListener);
            default:
                View textNoteViewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
                return new TextNoteViewHolder(textNoteViewHolder, mItemClickListener, mItemLongClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case (Constants.TYPE_PHOTO_NOTE):
                PhotoNoteViewHolder photoNoteViewHolder = (PhotoNoteViewHolder) holder;
                PhotoNote photoNotes = (PhotoNote) mItems.get(position);
                photoNoteViewHolder.photoIv.setImageURI(photoNotes.getImageUri());
                photoNoteViewHolder.photoTv.setText(photoNotes.getText());
                photoNoteViewHolder.photoBackgroundColor.setBackgroundTintList(photoNotes.getBackground());
                break;
            case (Constants.TYPE_CHECK_NOTE):
                CheckBoxNoteViewHolder checkBoxNoteViewHolder = (CheckBoxNoteViewHolder) holder;
                CheckboxNote checkNotes = (CheckboxNote) mItems.get(position);
                checkBoxNoteViewHolder.checkBoxTv.setText(checkNotes.getText());
                checkBoxNoteViewHolder.checkBox.setChecked(checkNotes.getIsChecked());
                checkBoxNoteViewHolder.checkBoxBackgroundColor.setBackgroundTintList(checkNotes.getBackground());
                break;
            default:
                TextNoteViewHolder textNoteViewHolder = (TextNoteViewHolder) holder;
                Note textNotes = mItems.get(position);
                textNoteViewHolder.textTv.setText(textNotes.getText());
                textNoteViewHolder.textBackgroundColor.setBackgroundTintList(textNotes.getBackground());
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }
}