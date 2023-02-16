package com.vedha.whatsstatussaver.models;

import java.io.File;
import java.io.Serializable;

public class ImagesModel implements Serializable {
    String mImageName;
    String mImagePath;

    public ImagesModel() {}
    public ImagesModel(String mImageName, String mImagePath) {
        this.mImageName = mImageName;
        this.mImagePath = mImagePath;
    }

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
