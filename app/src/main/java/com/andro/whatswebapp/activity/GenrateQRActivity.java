package com.andro.whatswebapp.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;

import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class GenrateQRActivity extends AppCompatActivity {
    public static final int Q_RCODE_WIDTH = 500;
    String edittextvalue;
    Bitmap bitmap;
    Button button;
    Button buttonshare;
    EditText editText;
    ImageView imageView;
    private ImageView ivback;
    RelativeLayout rl;
    private TextView textView;
    private Ads ads;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_qrgenrator);
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);

        ads = new Ads();

        FrameLayout nativeAdContainer = findViewById(R.id.native_ad_container);
        ads.loadNativeAd(GenrateQRActivity.this, nativeAdContainer);

        this.imageView = (ImageView) findViewById(R.id.imageView);
        this.editText = (EditText) findViewById(R.id.editText);
        this.button = (Button) findViewById(R.id.button);
        this.buttonshare = (Button) findViewById(R.id.buttonshare);
        this.rl = (RelativeLayout) findViewById(R.id.rl);
        this.textView = (TextView) findViewById(R.id.title);
        this.ivback = (ImageView) findViewById(R.id.iv_back);
        this.textView.setText("QR Generator");
        this.ivback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GenrateQRActivity.this.startActivity(new Intent(GenrateQRActivity.this, MainActivity.class));
                GenrateQRActivity.this.finish();
            }
        });
        this.buttonshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GenrateQRActivity.this.rl.setDrawingCacheEnabled(true);
                GenrateQRActivity.this.rl.setDrawingCacheEnabled(true);
                GenrateQRActivity.this.rl.buildDrawingCache();
                Bitmap drawingCache = GenrateQRActivity.this.rl.getDrawingCache();
                drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.STREAM", Uri.parse(MediaStore.Images.Media.insertImage(GenrateQRActivity.this.getContentResolver(), drawingCache, "", (String) null)));
                intent.setType("image/*");
                GenrateQRActivity.this.startActivity(Intent.createChooser(intent, "Share image via..."));
            }
        });
        this.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GenrateQRActivity genrateQRActivity = GenrateQRActivity.this;
                genrateQRActivity.edittextvalue = genrateQRActivity.editText.getText().toString();
                try {
                    GenrateQRActivity.this.bitmap = GenrateQRActivity.this.textToImageEncode(GenrateQRActivity.this.edittextvalue);
                    GenrateQRActivity.this.imageView.setImageBitmap(GenrateQRActivity.this.bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public Bitmap textToImageEncode(String str) throws WriterException {
        int i;
        Resources resources;
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix encode = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, Q_RCODE_WIDTH, Q_RCODE_WIDTH, (Map<EncodeHintType, ?>) null);
            int width = encode.getWidth();
            int height = encode.getHeight();
            int[] iArr = new int[(width * height)];
            for (int i2 = 0; i2 < height; i2++) {
                int i3 = i2 * width;
                for (int i4 = 0; i4 < width; i4++) {
                    int i5 = i3 + i4;
                    if (encode.get(i4, i2)) {
                        resources = getResources();
                        i = R.color.black;
                    } else {
                        resources = getResources();
                        i = R.color.white;
                    }
                    iArr[i5] = resources.getColor(i);
                }
            }
            Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
            createBitmap.setPixels(iArr, 0, Q_RCODE_WIDTH, 0, 0, width, height);
            return createBitmap;
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
