package com.diachenko.gallery.data.database;

import android.content.Context;

import com.diachenko.gallery.data.Photo;

import java.util.List;

public interface UsersPhoto {

    public List<Photo> loadPhoto(Context context);
}
