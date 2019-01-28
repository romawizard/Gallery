package com.diachenko.gallery.data;

import com.diachenko.gallery.data.api.response.UploadPhotoResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public interface PhotoRepository {

    List<Photo> getPhotos();

    Response<UploadPhotoResponse> uploadPhoto(Photo photo) throws IOException;
}
