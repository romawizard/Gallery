package com.diachenko.gallery.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.diachenko.gallery.data.api.response.UploadPhotoResponse;
import com.diachenko.gallery.utils.Resource;

import java.util.List;

public interface PhotoRepository {

    LiveData<List<Photo>> getPhoto(Context context);

    LiveData<Resource<UploadPhotoResponse>> uploadPhoto(Photo photo);
}
