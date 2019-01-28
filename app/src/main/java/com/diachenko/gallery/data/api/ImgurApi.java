package com.diachenko.gallery.data.api;


import com.diachenko.gallery.data.api.response.UploadPhotoResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ImgurApi {

    @Multipart
    @POST(Request.UPLOAD_PHOTO_PATH)
    Call<UploadPhotoResponse> uploadPhoto(@Header("Authorization") String auth,
                                          @Query("title") String title,
                                          @Part("image") RequestBody file);
}
