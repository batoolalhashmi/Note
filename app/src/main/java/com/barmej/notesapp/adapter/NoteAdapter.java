package com.barmej.notesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.data.Note;
import com.barmej.notesapp.R;
import com.barmej.notesapp.databinding.ItemNoteBinding;
import com.barmej.notesapp.listener.CheckBoxClickListener;
import com.barmej.notesapp.listener.ItemClickListener;
import com.barmej.notesapp.listener.ItemLongClickListener;

import java.util.ArrayList;

class NoteViewHolder extends RecyclerView.ViewHolder {

    private final ItemNoteBinding itemNoteBinding;

    public NoteViewHolder(ItemNoteBinding itemNoteBinding, final ItemClickListener mItemClickListener, final ItemLongClickListener mItemLongClickListener, final CheckBoxClickListener mCheckBoxClickListener) {
        super(itemNoteBinding.getRoot());
        this.itemNoteBinding = itemNoteBinding;
        itemNoteBinding.setCheckBoxNote(Constants.CHECK_BOX_NOTE);
        itemNoteBinding.setPhotoNote(Constants.PHOTO_NOTE);
        itemNoteBinding.setTextNote(Constants.TEXT_NOTE);
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
        itemNoteBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton checkBox, boolean checked) {
                int position = getAdapterPosition();
                mCheckBoxClickListener.onChecked(position, checked);
            }
        });
    }

    void bind(Note note) {
        itemNoteBinding.setNote(note);
    }
}

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;
    private CheckBoxClickListener mCheckBoxClickListener;
    private ArrayList<Note> mItems;

    public NoteAdapter(ArrayList<Note> mItems, ItemClickListener mItemClickListener, ItemLongClickListener mItemLongClickListener, CheckBoxClickListener mCheckBoxClickListener) {
        this.mItems = mItems;
        this.mItemClickListener = mItemClickListener;
        this.mItemLongClickListener = mItemLongClickListener;
        this.mCheckBoxClickListener = mCheckBoxClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding itemNoteBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_note, parent, false);
        return new NoteViewHolder(itemNoteBinding, mItemClickListener, mItemLongClickListener, mCheckBoxClickListener);
    }

    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note notes = mItems.get(position);
        holder.bind(notes);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        } else {
            return mItems.size();
        }
    }
}