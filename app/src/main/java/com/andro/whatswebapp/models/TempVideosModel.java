package com.andro.whatswebapp.models;

import java.io.Serializable;

public class TempVideosModel implements Serializable {
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
}
