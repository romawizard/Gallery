package com.diachenko.gallery;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.diachenko.gallery.data.ExternalPhotosDataSource;
import com.diachenko.gallery.data.PhotoRepository;
import com.diachenko.gallery.data.PhotoRepositoryImpl;
import com.diachenko.gallery.data.api.ImgurApi;
import com.diachenko.gallery.data.database.GalleryDatabase;
import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;

import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryApplication extends Application {

    private static GalleryApplication instance;
    private ImgurApi imgurApi;
    private GalleryDatabase database;
    private PhotoRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRetrofit();
        initRoom();
        initRepository();
    }

    private void initRepository() {
        repository = new PhotoRepositoryImpl(new ExternalPhotosDataSource(),imgurApi,
                Executors.newSingleThreadExecutor(),database.getUrlDao());
    }

    public static GalleryApplication getInstance() {
        return instance;
    }

    public ImgurApi getImgurApi() {
        return imgurApi;
    }

    public GalleryDatabase getDatabase() {
        return database;
    }

    public PhotoRepository getRepository() {
        return repository;
    }

    private void initRoom() {
        database = Room.databaseBuilder(this,GalleryDatabase.class,
                getString(R.string.database)).build();
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .build();

        imgurApi = retrofit.create(ImgurApi.class);
    }
}
