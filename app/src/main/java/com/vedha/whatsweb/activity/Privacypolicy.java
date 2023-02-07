package com.vedha.whatsweb.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.vedha.whatsweb.Ads;
import com.vedha.whatsweb.R;

public class Privacypolicy extends AppCompatActivity {
    WebView webview;


    @RequiresApi(api = 26)
    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_privacypolicy);
        this.webview = (WebView) findViewById(R.id.webview);
        this.webview.loadUrl(Ads.privecy);
        ((ImageView) findViewById(R.id.backimage)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Privacypolicy.this.onBackPressed();
            }
        });
    }

}
