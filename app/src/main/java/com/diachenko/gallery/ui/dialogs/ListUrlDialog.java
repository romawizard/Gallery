package com.diachenko.gallery.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.diachenko.gallery.R;
import com.diachenko.gallery.ui.adapters.ListUrlAdapter;
import com.diachenko.gallery.ui.viewmodels.PhotoViewModel;

public class ListUrlDialog extends DialogFragment {

    private PhotoViewModel model;
    private RecyclerView listView;
    ListUrlAdapter adapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(PhotoViewModel.class);
        model.getUrls().observe(this,(urlPhotoUIS) -> adapter.setUrls(urlPhotoUIS));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.list_url_layout, null);
        listView = view.findViewById(R.id.list_urls);
        initListView();

        builder.setView(view);

        return builder.create();
    }

    private void initListView() {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter =  new ListUrlAdapter();
        listView.setAdapter(adapter);
        listView.setLayoutManager(lm);
    }
}
