package com.diachenko.gallery.data;

import android.content.Context;

import java.util.List;

public interface PhotoRepository {

    List<Photo> getPhoto(Context context);
}
