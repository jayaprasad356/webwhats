package com.vedha.whatsweb.activity;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vedha.whatsweb.Ads;
import com.vedha.whatsweb.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_READ_FOLDERS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startactivity);
        Ads ads = new Ads();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_READ_FOLDERS);
        } else {
            // Permission has already been granted, continue with your app
        }

        checkWhatsAppPermission();

        FrameLayout nativeAdContainer = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(StartActivity.this, nativeAdContainer);

        ImageView ivShare = findViewById(R.id.iv_share);
        ivShare.setOnClickListener(this);
        ImageView ivReta = findViewById(R.id.iv_reta);
        ivReta.setOnClickListener(this);
        ImageView ivPrivecy = findViewById(R.id.iv_privecy);
        ivPrivecy.setOnClickListener(this);
        ImageView start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        });
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.iv_share:

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                }
                break;

            case R.id.iv_reta:

                ratingDialog(StartActivity.this);
                break;

            case R.id.iv_privecy:

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://google.com/"));
                startActivity(i);
                break;


            default:
                break;
        }
    }

    public static void ratingDialog(Activity activity) {

        Intent i3 = new Intent(Intent.ACTION_VIEW, Uri
                .parse("market://details?id=" + activity.getPackageName()));
        activity.startActivity(i3);
    }

    @Override
    public void onBackPressed() {
        new FancyGifDialog.Builder(this).setTitle("Rate This Application").setMessage("Thank you for using our app.If u really like out app Please rate us 5 stars")
                .setNegativeBtnText("Exit")
                .setPositiveBtnBackground("#0b3540")
                .setPositiveBtnText("Ok").setNegativeBtnBackground("#FFA9A7A8").setGifResource(R.drawable.rateus).isCancellable(true).OnPositiveClicked(new FancyGifDialogListener() {
            public void OnClick() {
                Intent createChooser = Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())), "Share via");
                createChooser.setFlags(268435456);
                startActivity(createChooser);
            }
        }).OnNegativeClicked(new FancyGifDialogListener() {
            public void OnClick() {
                finishAffinity();
            }
        }).build();
    }
    private void checkWhatsAppPermission() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean permissionGranted = prefs.getBoolean("permission_granted", false);
//        if (!permissionGranted) {
//            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//            Uri wa_status_uri = Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fmedia/document/primary%3AAndroid%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses");
//            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, wa_status_uri);
//            startActivityForResult(intent, 10001);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putBoolean("permission_granted", true);
//            editor.apply();
//        }
    }


}
