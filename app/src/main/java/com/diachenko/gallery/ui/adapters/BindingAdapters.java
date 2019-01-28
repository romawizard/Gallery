package com.diachenko.gallery.ui.adapters;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.diachenko.gallery.R;
import com.squareup.picasso.Picasso;

import java.io.File;

public class BindingAdapters {

    @BindingAdapter({"app:path", "app:placeholder"})
    public static void loadImage(ImageView view, String path, Drawable placeholder) {
        Picasso.get()
                .load(new File(path))
                .fit()
                .centerCrop()
                .placeholder(placeholder)
                .into(view);
    }

    @BindingAdapter("app:animated")
    public static void startAnimation(View view, boolean shouldAnimate) {
        if (shouldAnimate) {
            Animation anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.progress_animation);
            view.startAnimation(anim);
        } else {
            view.clearAnimation();
        }
    }
}
