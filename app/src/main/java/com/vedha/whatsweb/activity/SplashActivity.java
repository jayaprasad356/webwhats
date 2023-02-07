package com.vedha.whatsweb.activity;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.vedha.whatsweb.Adclick;
import com.vedha.whatsweb.Ads;
import com.vedha.whatsweb.R;
import com.github.ybq.android.spinkit.style.CubeGrid;

public class SplashActivity extends AppCompatActivity {
    private Ads ads;

    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";

    private final Handler handler = new Handler();
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.content_homesplash);

        if (!arePermissionDenied()){
            next();
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {

            // If Android 11 Request for Manage File Access Permission
            if (Build.VERSION.SDK_INT >= 30) {
                Intent intent = new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");

                startActivityForResult(intent, REQUEST_PERMISSIONS);
                return;
            }

            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
        }
        ads  = new Ads();
        ads.interstitialload(this);

        ((ProgressBar) findViewById(R.id.progress)).setIndeterminateDrawable(new CubeGrid());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        SplashActivity splashActivity = SplashActivity.this;
                        splashActivity.startActivity(new Intent(splashActivity, StartActivity.class));
                        SplashActivity.this.finish();
                    }
                });
            }
        }, 3200);

    }

    private void next() {

        handler.postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, StartActivity.class));
            finish();
        }, 1000);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @RequiresApi(30)
    boolean checkStorageApi30() {
        AppOpsManager appOps = getApplicationContext().getSystemService(AppOpsManager.class);
        int mode = appOps.unsafeCheckOpNoThrow(
                MANAGE_EXTERNAL_STORAGE_PERMISSION,
                getApplicationContext().getApplicationInfo().uid,
                getApplicationContext().getPackageName()
        );
        return mode != AppOpsManager.MODE_ALLOWED;

    }
    private boolean arePermissionDenied() {

        if (Build.VERSION.SDK_INT >= 30) {
            return checkStorageApi30();
        }

        for (String permissions : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissions) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!arePermissionDenied()) {
            next();
        }
    }
}
