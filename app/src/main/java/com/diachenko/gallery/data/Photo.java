package com.diachenko.gallery.data;

public class Photo {

    private String folderName;
    private String path;

    public Photo(String folderName, String path) {
        this.folderName = folderName;
        this.path = path;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getPath() {
        return path;
    }
}
