package com.diachenko.gallery.ui.adapters;

import android.support.v7.util.DiffUtil;

import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.utils.MyLog;

import java.util.List;

public class DiffUtilPhoto extends DiffUtil.Callback {

    public static final String TAG = DiffUtilPhoto.class.getSimpleName();

    private List<Photo> oldList;
    private List<Photo> newList;

    public DiffUtilPhoto(List<Photo> oldList, List<Photo> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return oldList.get(i).getPath().equals(newList.get(i1).getPath());
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return oldList.get(i).equals(newList.get(i1));
    }
}
