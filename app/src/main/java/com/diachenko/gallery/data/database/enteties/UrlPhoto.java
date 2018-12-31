package com.diachenko.gallery.data.database.enteties;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;

@Entity
public class UrlPhoto {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String url;
    private long timestamp;

    public UrlPhoto(String url) {
        this.url = url;
        timestamp = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;

    }

    @Override
    public String toString() {
        return "UrlPhoto{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlPhoto urlPhoto = (UrlPhoto) o;
        return id == urlPhoto.id &&
                Objects.equals(url, urlPhoto.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, url);
    }
}
