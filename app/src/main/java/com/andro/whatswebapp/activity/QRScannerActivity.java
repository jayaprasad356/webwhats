package com.andro.whatswebapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.InputDeviceCompat;

import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;


public class QRScannerActivity extends AppCompatActivity {
    public static TextView tvresult;
    private int storagePermissionCode = 23;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_qrscanner);
        tvresult = (TextView) findViewById(R.id.tvresult);

        Ads ads = new Ads();
        FrameLayout nativeAdContainer = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(QRScannerActivity.this, nativeAdContainer);

        Button btn = (Button) findViewById(R.id.btn);
        Button btnShare = (Button) findViewById(R.id.btn_share);
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle.setText("QR Generator");
        ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(QRScannerActivity.this, MainActivity.class));
                finish();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isReadStorageAllowed()) {
                    startActivity(new Intent(QRScannerActivity.this, StartScannerActivity.class));
                    return;
                }
                requestStoragePermission();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (QRScannerActivity.tvresult.getText().toString().equalsIgnoreCase("Result will be here")) {
                    Toast.makeText(QRScannerActivity.this, "Please first Scan QRCode", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", QRScannerActivity.tvresult.getText().toString());
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "send to"));
            }
        });
    }


    public void requestStoragePermission() {
        ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CAMERA");
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, storagePermissionCode);
    }


    public boolean isReadStorageAllowed() {
        return ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0;
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i != storagePermissionCode) {
            return;
        }
        if (iArr.length <= 0 || iArr[0] != 0) {
            Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
        } else {
            startActivity(new Intent(this, StartScannerActivity.class));
        }
    }

}
