package com.andro.whatswebapp;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends MultiDexApplication {
    public static final String TAG = "MyApplication";
    public static MyApplication instance;

    public MyApplication() {
        instance = this;
    }

    public static MyApplication getAppInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Fresco.initialize(this);

        Log.i(TAG, "On Create");
    }


}
