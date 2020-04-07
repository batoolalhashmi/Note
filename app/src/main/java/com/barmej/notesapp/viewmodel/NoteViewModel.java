package com.barmej.notesapp.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.barmej.notesapp.data.Note;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private String TAG = MainViewModel.class.getSimpleName();

    private MutableLiveData<ArrayList<Note>> mItems;

    LiveData<ArrayList<Note>> getItems() {
        if (mItems == null) {
            mItems = new MutableLiveData<>();
           // loadItems();
        }
        return mItems;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);


    }
}
