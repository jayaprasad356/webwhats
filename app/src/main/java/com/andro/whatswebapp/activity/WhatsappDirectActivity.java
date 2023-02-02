package com.andro.whatswebapp.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;

import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WhatsappDirectActivity extends AppCompatActivity {
    Button btn_send;
    CountryCodePicker ccp;
    EditText edt_message;
    EditText edt_phonenumber;
    private ImageView ivBack;
    private TextView tvTitle;
    private Ads ads;


    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_whatsdirect);
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);

        ads = new Ads();

        FrameLayout nativeAdContainer = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(WhatsappDirectActivity.this, nativeAdContainer);


        this.tvTitle = (TextView) findViewById(R.id.title);
        this.ivBack = (ImageView) findViewById(R.id.iv_back);
        this.tvTitle.setText("Whats Direct");
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WhatsappDirectActivity.this.startActivity(new Intent(WhatsappDirectActivity.this, MainActivity.class));
                WhatsappDirectActivity.this.finish();
            }
        });
        this.btn_send = (Button) findViewById(R.id.btn_send);
        this.edt_message = (EditText) findViewById(R.id.edt_message);
        this.edt_phonenumber = (EditText) findViewById(R.id.etphonenumber);
        this.ccp = (CountryCodePicker) findViewById(R.id.ccp);
        this.btn_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                WhatsappDirectActivity.this.openWhatsApp();
            }
        });
    }


    @SuppressLint({"WrongConstant"})
    public void openWhatsApp() {
        String str = this.ccp.getSelectedCountryCode() + " " + this.edt_phonenumber.getText().toString();
        if (whatsappInstalledOrNot("com.whatsapp")) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("whatsapp://send/?text=" + URLEncoder.encode(this.edt_message.getText().toString(), "UTF-8") + "&phone=" + str)));
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(this, "Please install WhatsApp",Toast.LENGTH_LONG).show();
            } catch (UnsupportedEncodingException unused2) {
                Toast.makeText(this, "Error while encoding your text message",Toast.LENGTH_LONG).show();
            }
        } else {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.whatsapp"));
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
    }

    private boolean whatsappInstalledOrNot(String str) {
        try {
            getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
