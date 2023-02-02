package com.andro.whatswebapp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class StartScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mScannerView = new ZXingScannerView(this);
        setContentView((View) this.mScannerView);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mScannerView.setResultHandler(this);
        this.mScannerView.startCamera();
    }

    public void handleResult(Result result) {
        QRScannerActivity.tvresult.setText(result.getText());
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
