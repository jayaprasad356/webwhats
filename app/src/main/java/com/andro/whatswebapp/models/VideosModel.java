package com.andro.whatswebapp.models;

import android.graphics.Bitmap;

public class VideosModel {
    Bitmap mBitmap;
    String mVideoName;
    String mVideoPath;

    public String getVideoName() {
        return this.mVideoName;
    }

    public void setVideoName(String str) {
        this.mVideoName = str;
    }

    public String getVideoPath() {
        return this.mVideoPath;
    }

    public void setVideoPath(String str) {
        this.mVideoPath = str;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
