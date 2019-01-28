package com.diachenko.gallery.data;

import com.diachenko.gallery.data.api.ImgurApi;
import com.diachenko.gallery.data.api.response.UploadPhotoResponse;
import com.diachenko.gallery.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;

public class PhotoRepositoryImpl implements PhotoRepository {

    private static final String TAG = PhotoRepositoryImpl.class.getSimpleName();

    private PhotosDataSource photosDataSource;
    private ImgurApi imgurApi;


    public PhotoRepositoryImpl(PhotosDataSource photosDataSource, ImgurApi imgurApi) {
        this.photosDataSource = photosDataSource;
        this.imgurApi = imgurApi;
    }

    @Override
    public List<Photo> getPhotos() {
        return photosDataSource.loadPhoto();
    }

    @Override
    public Response<UploadPhotoResponse> uploadPhoto(final Photo photo) throws IOException {
        File file = new File(photo.getPath());
        final RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return imgurApi
                .uploadPhoto(Constants.getClientAuth(), photo.getFileName(), requestFile)
                .execute();
    }
}
