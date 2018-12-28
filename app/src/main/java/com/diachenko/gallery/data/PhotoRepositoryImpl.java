package com.diachenko.gallery.data;

import android.content.Context;

import java.util.List;

public class PhotoRepositoryImpl implements PhotoRepository {
    private ExternalUsersPhoto externalUsersPhoto;

    public PhotoRepositoryImpl(ExternalUsersPhoto externalUsersPhoto) {
        this.externalUsersPhoto = externalUsersPhoto;
    }

    @Override
    public List<Photo> getPhoto(Context context) {
       return externalUsersPhoto.loadPhoto(context);
    }
}
