package com.diachenko.gallery;

import android.app.Application;

import com.diachenko.gallery.data.api.ImgurApi;
import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryApplication extends Application {

    private ImgurApi imgurApi;
    private  static GalleryApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRetrofit();
    }

    public static GalleryApplication getInstance() {
        return instance;
    }

    public ImgurApi getImgurApi() {
        return imgurApi;
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
