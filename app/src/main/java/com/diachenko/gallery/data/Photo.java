package com.diachenko.gallery.data;

import android.text.TextUtils;

import java.util.Objects;

public class Photo {

    private String folderName;
    private String path;
    private String fileName;
    private boolean isLoading = false;
    private boolean isFail = false;

    public Photo(String folderName, String path, String fileName) {
        this.folderName = folderName;
        this.path = path;
        this.fileName = fileName;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isFail() {
        return isFail;
    }

    public void setFail(boolean fail) {
        isFail = fail;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "folderName='" + folderName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return isLoading == photo.isLoading &&
                isFail == photo.isFail &&
                Objects.equals(path, photo.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(path, isLoading, isFail);
    }
}
