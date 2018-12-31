package com.diachenko.gallery.ui.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.diachenko.gallery.data.PhotoRepository;

public class PhotoViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final PhotoRepository repository;

    public PhotoViewModelFactory(PhotoRepository repository) {
        super();
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == PhotoViewModel.class) {
            return (T) new PhotoViewModel(repository);
        }
        return null;
    }
}
