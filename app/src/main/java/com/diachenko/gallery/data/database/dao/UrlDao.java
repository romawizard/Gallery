package com.diachenko.gallery.data.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.diachenko.gallery.data.database.entities.UrlPhoto;

import java.util.List;

@Dao
public interface UrlDao {

    @Query("select * from UrlPhoto order by timestamp desc")
    LiveData<List<UrlPhoto>> getAllUploadedUrl();

    @Insert
    void saveUrl(UrlPhoto photo);
}
