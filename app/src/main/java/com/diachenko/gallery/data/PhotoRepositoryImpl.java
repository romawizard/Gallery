package com.diachenko.gallery.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.diachenko.gallery.data.api.ImgurApi;
import com.diachenko.gallery.data.api.response.UploadPhotoResponse;
import com.diachenko.gallery.data.database.dao.UrlDao;
import com.diachenko.gallery.data.database.enteties.UrlPhoto;
import com.diachenko.gallery.utils.Constants;
import com.diachenko.gallery.utils.MyLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class PhotoRepositoryImpl implements PhotoRepository {

    private static final String TAG = PhotoRepositoryImpl.class.getSimpleName();

    private PhotosDataSource PhotosDataSource;
    private ImgurApi imgurApi;
    private Executor executor;
    private UrlDao urlDao;
    private MutableLiveData<List<Photo>> liveData = new MutableLiveData<>();
    private List<Photo> photos = new ArrayList<>();

    public PhotoRepositoryImpl(PhotosDataSource PhotosDataSource, ImgurApi imgurApi, Executor executor,
                               UrlDao urlDao) {
        this.PhotosDataSource = PhotosDataSource;
        this.imgurApi = imgurApi;
        this.executor = executor;
        this.urlDao = urlDao;
    }

    @Override
    public LiveData<List<Photo>> getPhoto(final Context context) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Photo> tempPhotos = PhotosDataSource.loadPhoto(context);
                calculateDifference(tempPhotos);
                liveData.postValue(photos);
            }
        });
        return liveData;
    }

    private void calculateDifference(List<Photo> tempPhotos) {
        for (Photo p : tempPhotos){
            if (!photos.contains(p)){
                photos.add(p);
            }
        }
    }

    @Override
    public void uploadPhoto(final Photo photo, final int position) {
        MyLog.log(TAG, photo.toString());

        final Photo p = photos.get(position);
        p.setLoading(true);
        p.setFail(false);
        liveData.setValue(photos);
        MyLog.log(TAG,"start Loading");

        executor.execute(new Runnable() {
            @Override
            public void run() {
                File file = new File(photo.getPath());
                // Create RequestBody
                final RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file);

                try {
                    Response<UploadPhotoResponse> response = imgurApi
                            .uploadPhoto(Constants.getClientAuth(), photo.getFileName(), requestFile)
                            .execute();
                    if (response.body() == null) {
                        //fail to load photo
                        MyLog.log(TAG,"fail to load photo response = null");
                        p.setLoading(false);
                        p.setFail(true);
                        liveData.postValue(photos);
                    } else {
                        // success to load photo
                        MyLog.log(TAG,"success to load photo");
                        p.setLoading(false);
                        p.setFail(false);
                        liveData.postValue(photos);
                        saveUrl(response.body().getData().getLink());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    MyLog.log(TAG,"fail to load photo response " + e.getMessage());
                    p.setLoading(false);
                    p.setFail(true);
                    liveData.postValue(photos);
                }
            }
        });

    }

    @Override
    public LiveData<List<UrlPhoto>> getAllUploadedUrl() {
        return urlDao.getAllUploadedUrl();
    }

    private void saveUrl(final String url) {
        urlDao.saveUrl(new UrlPhoto(url));
    }
}
