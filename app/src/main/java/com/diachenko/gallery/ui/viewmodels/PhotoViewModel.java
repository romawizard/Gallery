package com.diachenko.gallery.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;

import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.data.PhotoRepository;
import com.diachenko.gallery.data.api.response.UploadPhotoResponse;
import com.diachenko.gallery.utils.Resource;

import java.util.List;

public class PhotoViewModel extends ViewModel {

    public static final String TAG = PhotoViewModel.class.getSimpleName();

    private LiveData<List<Photo>> PhotosLiveData = new MutableLiveData<>();
    private PhotoRepository repository;

    public void init(PhotoRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Photo>> getPhoto(Context context) {
        PhotosLiveData = repository.getPhoto(context);
        return PhotosLiveData;
}

    public LiveData<Resource<UploadPhotoResponse>> uploadPhoto(Photo photo) {
        return repository.uploadPhoto(photo);
    }

}
