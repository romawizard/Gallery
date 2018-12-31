package com.diachenko.gallery.ui.viewmodels;

import android.arch.lifecycle.LiveData;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.data.PhotoRepository;
import com.diachenko.gallery.data.database.enteties.UrlPhoto;


import java.util.List;

public class PhotoViewModel extends ViewModel {

    private static final String TAG = PhotoViewModel.class.getSimpleName();
    private PhotoRepository repository;

    public PhotoViewModel(PhotoRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Photo>> getPhotos(Context context) {
       return repository.getPhoto(context);
    }

    public LiveData<List<UrlPhoto>> getUrls(){
        return repository.getAllUploadedUrl();
    }

    public void uploadPhoto(Photo photo, int position) {
        repository.uploadPhoto(photo,position);
    }
}
