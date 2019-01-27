package com.diachenko.gallery.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.diachenko.gallery.R;

import com.diachenko.gallery.databinding.UrlItemBinding;
import com.diachenko.gallery.ui.entities.UrlPhotoUI;

import java.util.List;

public class ListUrlAdapter extends RecyclerView.Adapter<ListUrlAdapter.UrlHolder> {

    private List<UrlPhotoUI> urls;

    @NonNull
    @Override
    public UrlHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UrlItemBinding binding = DataBindingUtil.inflate(inflater,R.layout.url_item,viewGroup,
                false);
        return new UrlHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UrlHolder urlHolder, int i) {
        urlHolder.bind(urls.get(i));
    }

    @Override
    public int getItemCount() {
        return urls == null ? 0 : urls.size();
    }

    public void setUrls(List<UrlPhotoUI> urls) {
        this.urls = urls;
        notifyDataSetChanged();
    }

    public class UrlHolder extends RecyclerView.ViewHolder{

        private UrlItemBinding binding;

        public UrlHolder(@NonNull UrlItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UrlPhotoUI urlPhoto) {
            binding.setUrlPhoto(urlPhoto);
            binding.executePendingBindings();
        }
    }
}
