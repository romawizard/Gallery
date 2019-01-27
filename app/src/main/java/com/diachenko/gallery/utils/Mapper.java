package com.diachenko.gallery.utils;

import com.diachenko.gallery.data.database.entities.UrlPhoto;
import com.diachenko.gallery.ui.entities.UrlPhotoUI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Mapper {

    public static List<UrlPhotoUI> mapUrlPhoto(List<UrlPhoto> photos) {
        List<UrlPhotoUI> result = new ArrayList<>();
        for (int i = 0, n = photos.size(); i < n ; i++) {
            UrlPhotoUI urlPhotoUI = new UrlPhotoUI();
            urlPhotoUI.setPosition(String.valueOf(i+1));

            urlPhotoUI.setUrl(photos.get(i).getUrl());

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());
            Date date = new Date(photos.get(i).getTimestamp());
            urlPhotoUI.setTime(sdf.format(date));
            result.add(urlPhotoUI);
        }
        return result;
    }
}
