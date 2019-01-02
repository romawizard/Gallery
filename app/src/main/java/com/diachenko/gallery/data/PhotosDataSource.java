package com.diachenko.gallery.data;

import android.content.Context;

import com.diachenko.gallery.data.Photo;

import java.util.List;

public interface PhotosDataSource {

     List<Photo> loadPhoto(Context context);
}
