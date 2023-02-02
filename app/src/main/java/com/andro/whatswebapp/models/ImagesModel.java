package com.andro.whatswebapp.models;

import java.io.Serializable;

public class ImagesModel implements Serializable {
    String mImageName;
    String mImagePath;

    public String getImageName() {
        return this.mImageName;
    }

    public void setImageName(String str) {
        this.mImageName = str;
    }

    public String getImagePath() {
        return this.mImagePath;
    }

    public void setImagePath(String str) {
        this.mImagePath = str;
    }
}
