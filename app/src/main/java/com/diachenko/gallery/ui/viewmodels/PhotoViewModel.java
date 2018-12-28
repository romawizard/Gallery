package com.diachenko.gallery.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.data.PhotoRepository;

import java.util.List;

public class PhotoViewModel extends ViewModel {

    private MutableLiveData<List<Photo>> liveData = new MutableLiveData<>();
    private PhotoRepository repository;

    public LiveData<List<Photo>> getPhoto(Context context) {
       liveData.setValue(repository.getPhoto(context));
       return liveData;
    }

    public void init(PhotoRepository repository){
        this.repository = repository;
    }
}
