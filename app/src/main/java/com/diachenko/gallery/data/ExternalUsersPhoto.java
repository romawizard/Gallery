package com.diachenko.gallery.data;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.diachenko.gallery.utils.MyLog;

import java.util.ArrayList;
import java.util.List;

public class ExternalUsersPhoto {

    public static final String TAG = ExternalUsersPhoto.class.getSimpleName();

    // content:// style URI for the "primary" external storage volume
    private Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    // which image properties are we querying
    private String[] projection = new String[]{
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DATA
    };

    public List<Photo> loadPhoto(Context context) {
        List<Photo> listOfAllImages = new ArrayList<Photo>();

        int columnIndexData, columnIndexFolderName;
        String absolutePathOfImage;
        String folderName;
        Photo photo;

        Cursor cursor = context.getContentResolver().query(images, projection, null,
                null, null);

        MyLog.log(TAG,"row count = " + cursor.getCount());

        columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        columnIndexFolderName = cursor.
                getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(columnIndexData);
            folderName = cursor.getString(columnIndexFolderName);
            photo = new Photo(folderName,absolutePathOfImage);

            listOfAllImages.add(photo);
        }
        cursor.close();
        return listOfAllImages;
    }

}
