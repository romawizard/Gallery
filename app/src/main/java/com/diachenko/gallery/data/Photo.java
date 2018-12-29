package com.diachenko.gallery.data;

import java.util.Objects;

public class Photo {

    private String folderName;
    private String path;
    private String fileName;
    private boolean isLoading = false;

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
        return Objects.equals(folderName, photo.folderName) &&
                Objects.equals(path, photo.path);
    }

    @Override
    public int hashCode() {

        return Objects.hash(folderName, path);
    }
}
