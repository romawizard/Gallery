package com.diachenko.gallery.di.modules;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.diachenko.gallery.data.ExternalPhotosDataSource;
import com.diachenko.gallery.data.PhotoRepository;
import com.diachenko.gallery.data.PhotoRepositoryImpl;
import com.diachenko.gallery.data.PhotosDataSource;
import com.diachenko.gallery.data.api.ImgurApi;
import com.diachenko.gallery.data.database.GalleryDatabase;
import com.diachenko.gallery.data.database.dao.UrlDao;
import com.diachenko.gallery.di.ActivityScope;
import com.diachenko.gallery.ui.viewmodels.PhotoViewModel;
import com.diachenko.gallery.ui.viewmodels.ViewModelProviderFactory;
import com.diachenko.gallery.utils.Constants;
import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class MainActivityModule {

    @ActivityScope
    @Provides
    PhotoViewModel providePhotoViewModel(PhotoRepository repository, Executor executor, UrlDao dao){
        return new PhotoViewModel(repository,executor,dao);
    }

    @ActivityScope
    @Provides
    ViewModelProvider.Factory provideViewModelFactory(PhotoViewModel model){
        return  new ViewModelProviderFactory<>(model);
    }

    @ActivityScope
    @Provides
    PhotosDataSource providePhotoDataSource(Context context){
        return new ExternalPhotosDataSource(context);
    }

    @ActivityScope
    @Provides
    PhotoRepository providePhotoRepository(PhotosDataSource photosDataSource, ImgurApi imgurApi){
        return  new PhotoRepositoryImpl(photosDataSource,imgurApi);
    }

    @ActivityScope
    @Provides
    Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    @ActivityScope
    @Provides
    ImgurApi provideImgurApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .build();

        return retrofit.create(ImgurApi.class);
    }

    @ActivityScope
    @Provides
    UrlDao provideUrlDao(Context context){
       GalleryDatabase database = Room.databaseBuilder(context, GalleryDatabase.class,
                Constants.DATA_BASE_NAME).build();
       return database.getUrlDao();
    }
    @ActivityScope
    @Provides
    Context providesContext(Application application) {
        return application;
    }
}
