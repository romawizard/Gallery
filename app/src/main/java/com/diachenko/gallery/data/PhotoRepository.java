package com.diachenko.gallery.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.diachenko.gallery.data.database.enteties.UrlPhoto;


import java.util.List;

public interface PhotoRepository {

    LiveData<List<Photo>> getPhoto(Context context);

    LiveData<List<UrlPhoto>> getAllUploadedUrl();

    void uploadPhoto(Photo photo, int position);
}
