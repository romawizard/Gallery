package com.diachenko.gallery.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.diachenko.gallery.R;
import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.utils.MyLog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    public static final String TAG = PhotoAdapter.class.getSimpleName();
    public PhotoAdapterListener listener;
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
        photoHolder.bind(photos.get(i),i);
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

    public void setListener(PhotoAdapterListener listener) {
        this.listener = listener;
    }

    public void stopLoadingAnimation(int position) {
        photos.get(position).setLoading(false);
        notifyItemChanged(position);
    }

    public void startLoadingAnimation(int position) {
        Photo p = photos.get(position);
        p.setFail(false);
        p.setLoading(true);
        notifyItemChanged(position);
    }

    public void showFailLoading(int position) {
        Photo p = photos.get(position);
        p.setFail(true);
        p.setLoading(false);
        notifyItemChanged(position);
    }

    public interface PhotoAdapterListener{
        void onClick(Photo photo, int position);
     }

    public class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private ImageView loadingImage;
        private ImageView errorImage;
        private Photo photo;
        private int position;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.image);
            loadingImage = itemView.findViewById(R.id.loading_image);
            errorImage = itemView.findViewById(R.id.error_image);
        }

        public void bind(Photo photo, int position) {
            this.photo = photo;
            this.position = position;
            showPhoto(photo);
            showLoadingStatus(photo);

        }

        private void showLoadingStatus(Photo photo) {
            if (photo.isLoading()){
                loadingImage.setVisibility(View.VISIBLE);
                Animation rotation = AnimationUtils.loadAnimation(imageView.getContext(),
                        R.anim.progress_animation);
                loadingImage.startAnimation(rotation);
            }
            else{
                loadingImage.setVisibility(View.GONE);
            }

            if (photo.isFail()){
                errorImage.setVisibility(View.VISIBLE);
            }else {
                errorImage.setVisibility(View.GONE);
            }
        }

        private void showPhoto(Photo photo) {
            Picasso.get()
                    .load(new File(photo.getPath()))
                    .resize(500,500)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            if (listener != null && !photo.isLoading()){
                listener.onClick(photo,position);
            }
        }
    }
}
