package com.barmej.notesapp.databinding;

import android.net.Uri;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.barmej.notesapp.R;

public class BinddingAdapter {
    @BindingAdapter("imageUriString")
    public static void setImageUriString(ImageView imageView, String uriString) {
        if (uriString == null) {
            imageView.setImageURI(Uri.parse(String.valueOf(R.drawable.ic_photo)));
        } else {
            Uri uri = Uri.parse(uriString);
            imageView.setImageURI(uri);
        }
    }

    @BindingAdapter("backgroundColor")
    public static void backgroundColor(CardView cardView, int background) {
        if (background == 0) {
            int color = R.color.blue;
            cardView.setBackgroundColor(ContextCompat.getColor(cardView.getContext(), color));
        } else {
            cardView.setBackgroundColor(background);
        }
    }

}
