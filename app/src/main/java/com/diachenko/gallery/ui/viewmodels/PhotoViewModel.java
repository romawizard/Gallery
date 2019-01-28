package com.diachenko.gallery.ui.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.diachenko.gallery.data.Photo;
import com.diachenko.gallery.data.PhotoRepository;
import com.diachenko.gallery.data.api.response.UploadPhotoResponse;
import com.diachenko.gallery.data.database.dao.UrlDao;
import com.diachenko.gallery.data.database.entities.UrlPhoto;
import com.diachenko.gallery.ui.entities.UrlPhotoUI;
import com.diachenko.gallery.utils.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Response;

public class PhotoViewModel extends ViewModel {

    private static final String TAG = PhotoViewModel.class.getSimpleName();
    private PhotoRepository repository;
    private Executor executor;
    private UrlDao urlDao;
    private List<Photo> photosCache = new ArrayList<>();
    private MutableLiveData<List<Photo>> photosLiveData = new MutableLiveData<>();

    public PhotoViewModel(PhotoRepository repository, Executor executor, UrlDao urlDao) {
        this.repository = repository;
        this.executor = executor;
        this.urlDao = urlDao;
    }

    public LiveData<List<Photo>> getPhotos() {
        executor.execute(() -> {
            List<Photo> photoList = repository.getPhotos();
            calculateDifference(photoList);
            photosLiveData.postValue(photosCache);
        });
        return photosLiveData;
    }

    public LiveData<List<UrlPhotoUI>> getUrls() {
        return Transformations.map(urlDao.getAllUploadedUrl(), (input) ->
             Mapper.mapUrlPhoto(input));

    }

    public void uploadPhoto(Photo photo) {
        photo.setLoading(true);
        photo.setFail(false);
        photosLiveData.postValue(photosCache);
        executor.execute(() -> {
            try {
                Response<UploadPhotoResponse> response = repository.uploadPhoto(photo);
                if (response.body() != null) {
                    String link = response.body().getData().getLink();
                    urlDao.saveUrl(new UrlPhoto(link));
                    photo.setLoading(false);
                    photo.setFail(false);
                    photosLiveData.postValue(photosCache);
                } else {
                    photo.setLoading(false);
                    photo.setFail(true);
                    photosLiveData.postValue(photosCache);
                }
            } catch (IOException e) {
                e.printStackTrace();
                photo.setLoading(false);
                photo.setFail(true);
                photosLiveData.postValue(photosCache);
            }
        });
    }

    private void calculateDifference(List<Photo> tempPhotos) {
        for (Photo p : tempPhotos) {
            if (!photosCache.contains(p)) {
                photosCache.add(p);
            }
        }
    }
}
