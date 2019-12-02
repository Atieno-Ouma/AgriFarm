package com.fyp.agrifarm.beans;

import android.graphics.Bitmap;

public class YouTubeVideo {

    String id;
    String title;
    Bitmap thumbnail;

    public YouTubeVideo(String id, String title, Bitmap thumbnail) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
