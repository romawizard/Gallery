package com.diachenko.gallery.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.diachenko.gallery.data.database.dao.UrlDao;
import com.diachenko.gallery.data.database.enteties.UrlPhoto;

@Database(entities = UrlPhoto.class,version = 1)
public abstract class GalleryDatabase extends RoomDatabase {

    public abstract UrlDao getUrlDao();

}
