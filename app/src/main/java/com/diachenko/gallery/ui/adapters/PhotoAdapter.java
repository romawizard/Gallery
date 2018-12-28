package com.diachenko.gallery.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.diachenko.gallery.R;
import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.utils.MyLog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    public static final String TAG = PhotoAdapter.class.getSimpleName();

    private List<Photo> photos;

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.photo_layout,viewGroup,false);

        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder photoHolder, int i) {
        photoHolder.bind(photos.get(i));
    }

    @Override
    public int getItemCount() {
        return photos == null ? 0 : photos.size();
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        MyLog.log(TAG,"new List of photo. size = " + photos.size());
        notifyDataSetChanged();
    }

    public class PhotoHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }

        public void bind(Photo photo) {
            MyLog.log(TAG,"photo path = " + photo.getPath());

            Picasso.get()
                    .load(new File(photo.getPath()))
                    .resize(500,500)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }
    }
}
