package com.barmej.notesapp.databinding;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.barmej.notesapp.R;

public class BinddingAdapter {


    @BindingAdapter("imageUriString")
    public static void setImageUriString(ImageView imageView, String uriString) {
        if(uriString == null) {
            //imageView.setImageURI(Uri.parse("android.resource://" + R.drawable.ic_photo));
            imageView.setImageURI(Uri.parse(String.valueOf(R.drawable.ic_photo)));
        }else {
            Uri uri = Uri.parse(uriString);
            imageView.setImageURI(uri);
        }
    }

    @BindingAdapter("backgroundColor")
    public static void backgroundColor(CardView cardView, ColorStateList colorStateList) {
        if(colorStateList == null) {
            cardView.setBackgroundTintList(ContextCompat.getColorStateList(cardView.getContext(), R.color.blue));
        } else {
            cardView.setBackgroundTintList(colorStateList);
        }
    }


    /*@BindingAdapter("imageUri")
    public static void setImageUri(ImageView imageView, String uriString) {
        Uri uri = Uri.parse(uriString);
        if (uri == null) {
            imageView.setImageURI(Uri.parse(String.valueOf(R.drawable.ic_photo)));
        } else {
            imageView.setImageURI(uri);
        }
    }*/
}
