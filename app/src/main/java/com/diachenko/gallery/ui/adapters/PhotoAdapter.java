package com.diachenko.gallery.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.diachenko.gallery.R;
import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.databinding.PhotoItemBinding;

import java.util.List;

import javax.inject.Inject;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private static final String TAG = PhotoAdapter.class.getSimpleName();
    private PhotoAdapterListener listener;
    private List<Photo> photos;

    @Inject
    public PhotoAdapter() {
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        PhotoItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.photo_item, viewGroup,
                false);
        return new PhotoHolder(binding);
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
        notifyDataSetChanged();
    }

    public void setListener(PhotoAdapterListener listener) {
        this.listener = listener;
    }


    public interface PhotoAdapterListener {
        void onPhotoClicked(Photo photo);
    }


    public class PhotoHolder extends RecyclerView.ViewHolder implements OnPhotoClickListener {

        private PhotoItemBinding binding;

        PhotoHolder(@NonNull PhotoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

         void bind(Photo photo) {
            binding.setPhoto(photo);
            binding.setClickHandler(this);
            binding.executePendingBindings();
        }

        @Override
        public void onPhotoClicked(Photo photo) {
            if (listener != null && !photo.isLoading()) {
                listener.onPhotoClicked(photo);
            }
        }
    }
}
