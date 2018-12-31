package com.diachenko.gallery.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.diachenko.gallery.R;
import com.diachenko.gallery.data.database.enteties.UrlPhoto;
import com.diachenko.gallery.utils.TimeHalper;

import java.util.List;

public class ListUrlAdapter extends RecyclerView.Adapter<ListUrlAdapter.UrlHolder> {

    private List<UrlPhoto> urls;

    @NonNull
    @Override
    public UrlHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.url_item,viewGroup,false);
        return new UrlHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UrlHolder urlHolder, int i) {
        urlHolder.bind(urls.get(i),i +1);
    }

    @Override
    public int getItemCount() {
        return urls == null ? 0 : urls.size();
    }

    public void setUrls(List<UrlPhoto> urls) {
        this.urls = urls;
        notifyDataSetChanged();
    }

    public class UrlHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView number, url, time;

        public UrlHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            url = itemView.findViewById(R.id.url);
            time = itemView.findViewById(R.id.time);
        }

        public void bind(UrlPhoto urlPhoto, int position) {
            number.setText(String.valueOf(position));
            url.setText(urlPhoto.getUrl());
            time.setText(TimeHalper.getTime(urlPhoto.getTimestamp()));
        }

        @Override
        public void onClick(View v) {

        }
    }
}
