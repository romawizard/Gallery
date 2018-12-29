package com.diachenko.gallery.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.diachenko.gallery.data.api.ImgurApi;
import com.diachenko.gallery.data.api.response.UploadPhotoResponse;
import com.diachenko.gallery.utils.Constants;
import com.diachenko.gallery.utils.MyLog;
import com.diachenko.gallery.utils.Resource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class PhotoRepositoryImpl implements PhotoRepository {

    public static final String TAG = PhotoRepositoryImpl.class.getSimpleName();

    private ExternalUsersPhoto externalUsersPhoto;
    private ImgurApi imgurApi;
    private Executor executor;

    public PhotoRepositoryImpl(ExternalUsersPhoto externalUsersPhoto, ImgurApi imgurApi, Executor executor) {
        this.externalUsersPhoto = externalUsersPhoto;
        this.imgurApi = imgurApi;
        this.executor = executor;
    }

    @Override
    public LiveData<List<Photo>> getPhoto(final Context context) {
        final MutableLiveData<List<Photo>> liveData = new MutableLiveData<>();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                liveData.postValue(externalUsersPhoto.loadPhoto(context));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<Resource<UploadPhotoResponse>> uploadPhoto(final Photo photo) {
        MyLog.log(TAG, photo.toString());

        final MutableLiveData<Resource<UploadPhotoResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.<UploadPhotoResponse>loading(null));

        File file = new File(photo.getPath());
        // Create RequestBody
        final RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<UploadPhotoResponse> response = imgurApi
                            .uploadPhoto(Constants.getClientAuth(), photo.getFileName(), requestFile)
                            .execute();
                    if (response.body() == null) {
                        Resource<UploadPhotoResponse> resource = Resource.error("Fail to upload photo", null);
                        liveData.postValue(resource);
                    } else {
                        Resource<UploadPhotoResponse> resource = Resource.success(response.body());
                        liveData.postValue(resource);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Resource<UploadPhotoResponse> resource = Resource.error(e.getMessage(), null);
                    liveData.postValue(resource);
                }
            }
        });

        return liveData;
    }
}
