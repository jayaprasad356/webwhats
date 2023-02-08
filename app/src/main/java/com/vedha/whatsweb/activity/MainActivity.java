package com.vedha.whatsweb.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vedha.whatsweb.Adclick;
import com.vedha.whatsweb.Ads;
import com.vedha.whatsweb.DeletedMessageActivity;
import com.vedha.whatsweb.R;
import com.vedha.whatsweb.activity.stylishfonts.StylishFontsActivity;
import com.vedha.whatsweb.activity.whatsappsticker.StickerPack;
import com.vedha.whatsweb.activity.whatsappsticker.StickerPackDetailsActivity;
import com.vedha.whatsweb.activity.whatsappsticker.StickerPackListActivity;
import com.vedha.whatsweb.activity.whatsappsticker.StickerPackLoader;
import com.vedha.whatsweb.activity.whatsappsticker.StickerPackValidator;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.ads.AdView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    ImageView imgdirectchat,deletedMessage;
    ImageView imgmore;
    ImageView imgprivacypolicy;
    ImageView imgqrgenerator;
    ImageView imgqrscanner;
    ImageView imgshare;
    ImageView imgstatussaver;
    ImageView imgstylishfont;
    ImageView imgwhatsscan;
    ImageView imgwhatssticker;
    AdView llAds;

    public LoadListAsyncTask loadListAsyncTask;
    ProgressDialog progressDialog;
    private ImageView ivback;
    private Ads ads;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_main);


        ads  = new Ads();
        ads.interstitialload(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        this.llAds = (AdView) findViewById(R.id.ll_ads);
        Ads.bannerad(this.llAds, this);
        ivback = (ImageView) findViewById(R.id.iv_back);
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        check();

        this.imgwhatsscan = (ImageView) findViewById(R.id.img_whatsscan);
        this.imgstatussaver = (ImageView) findViewById(R.id.img_statussaver);

        this.imgdirectchat = (ImageView) findViewById(R.id.img_directchat);
        this.imgwhatssticker = (ImageView) findViewById(R.id.img_whatssticker);

        this.imgstylishfont = (ImageView) findViewById(R.id.img_stylishfont);
        this.imgqrgenerator = (ImageView) findViewById(R.id.img_qrgenerator);

        this.imgqrscanner = (ImageView) findViewById(R.id.img_qrscanner);
        this.imgprivacypolicy = (ImageView) findViewById(R.id.img_privacy);

        this.imgshare = (ImageView) findViewById(R.id.img_share);
        this.imgmore = (ImageView) findViewById(R.id.img_more);

        this.deletedMessage=(ImageView)findViewById(R.id.deleted_message);

        this.imgwhatsscan.getLayoutParams().height = (width/2)-80;
        this.imgstatussaver.getLayoutParams().height = (width/2)-80;

        this.imgdirectchat.getLayoutParams().height = (width/2)-80;
        this.imgwhatssticker.getLayoutParams().height = (width/2)-80;

        this.imgstylishfont.getLayoutParams().height = (width/2)-80;
        this.imgqrgenerator.getLayoutParams().height = (width/2)-80;

        this.imgqrscanner.getLayoutParams().height = (width/2)-80;
        this.imgprivacypolicy.getLayoutParams().height = (width/2)-80;

        this.imgshare.getLayoutParams().height = (width/2)-80;
        this.imgmore.getLayoutParams().height = (width/2)-80;

        this.imgwhatsscan.requestLayout();
        this.imgstatussaver.requestLayout();

        this.imgdirectchat.requestLayout();
        this.imgwhatssticker.requestLayout();

        this.imgstylishfont.requestLayout();
        this.imgqrgenerator.requestLayout();

        this.imgqrscanner.requestLayout();
        this.imgprivacypolicy.requestLayout();

        this.imgshare.requestLayout();
        this.imgmore.requestLayout();

        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Loading Stickers...");
        this.progressDialog.setCancelable(false);
        this.imgstylishfont.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, StylishFontsActivity.class));
                    }
                });
            }
        });
        this.imgwhatssticker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        MainActivity.this.progressDialog.show();
                        Fresco.initialize(MainActivity.this);
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.loadListAsyncTask = new LoadListAsyncTask(mainActivity);
                        MainActivity.this.loadListAsyncTask.execute(new Void[0]);
                    }
                });
            }
        });
        this.imgqrgenerator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, GenrateQRActivity.class));
                    }
                });
            }
        });
        this.imgqrscanner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, QRScannerActivity.class));
                    }
                });
            }
        });
        this. imgshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.putExtra("android.intent.extra.SUBJECT", R.string.app_name);
                    intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
                    MainActivity.this.startActivity(Intent.createChooser(intent, "choose one"));
                    return;
            }
        });
        this.imgdirectchat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.startActivity(new Intent(mainActivity, WhatsappDirectActivity.class));
                    }
                });
            }
        });
        this.imgwhatsscan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        MainActivity mainActivity2 = MainActivity.this;
                        mainActivity2.startActivity(new Intent(mainActivity2, WhatzappwebActivity.class));
                    }
                });
            }
        });
        this.deletedMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = MainActivity.this;
                mainActivity.startActivity(new Intent(mainActivity, DeletedMessageActivity.class));
            }
        });
        this.imgstatussaver.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ads.showInd(new Adclick() {
                    @Override
                    public void onclicl() {
                        MainActivity mainActivity = MainActivity.this;
                        mainActivity.startActivity(new Intent(mainActivity, StorieSaverrrActivity.class));
                    }
                });
            }
        });
        this.imgprivacypolicy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://google.com/"));
                startActivity(i);
            }
        });
        this.imgmore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ratingDialog(MainActivity.this);
            }
        });
    }
    public static void ratingDialog(Activity activity) {

        Intent i3 = new Intent(Intent.ACTION_VIEW, Uri
                .parse("market://details?id=" + activity.getPackageName()));
        activity.startActivity(i3);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    public void check() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            requestStoragePermission();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
            Toast.makeText(this, "Permission needed to save status images and videos", Toast.LENGTH_SHORT).show();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i != 0) {
            super.onRequestPermissionsResult(i, strArr, iArr);
        } else if (iArr.length == 1 && iArr[0] == 0) {
            Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Storage permission required\nto save status images & videos", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    static class LoadListAsyncTask extends AsyncTask<Void, Void, Pair<String, ArrayList<StickerPack>>> {
        private final WeakReference<MainActivity> contextWeakReference;

        LoadListAsyncTask(MainActivity mainActivity) {
            this.contextWeakReference = new WeakReference<>(mainActivity);
        }


        public Pair<String, ArrayList<StickerPack>> doInBackground(Void... voidArr) {
            try {
                Context context = (Context) this.contextWeakReference.get();
                if (context == null) {
                    return new Pair<>("could not fetch sticker packs",  null);
                }
                ArrayList<StickerPack> fetchStickerPacks = StickerPackLoader.fetchStickerPacks(context);
                if (fetchStickerPacks.size() == 0) {
                    return new Pair<>("could not find any packs",  null);
                }
                Iterator<StickerPack> it = fetchStickerPacks.iterator();
                while (it.hasNext()) {
                    StickerPackValidator.verifyStickerPackValidity(context, it.next());
                }
                return new Pair<>(null, fetchStickerPacks);
            } catch (Exception e) {
                Log.e("EntryActivity", "error fetching sticker packs", e);
                return new Pair<>(e.getMessage(), null);
            }
        }


        @Override
        public void onPostExecute(Pair<String, ArrayList<StickerPack>> pair) {
            MainActivity mainActivity = (MainActivity) this.contextWeakReference.get();
            if (mainActivity == null) {
                return;
            }
            if (pair.first != null) {
                mainActivity.showErrorMessage((String) pair.first);
            } else {
                mainActivity.showStickerPack((ArrayList) pair.second);
            }
        }
    }


    public void showStickerPack(ArrayList<StickerPack> arrayList) {
        if (arrayList.size() > 1) {
            this.progressDialog.dismiss();
            Intent intent = new Intent(this, StickerPackListActivity.class);
            intent.putParcelableArrayListExtra(StickerPackListActivity.EXTRA_STICKER_PACK_LIST_DATA, arrayList);
            startActivity(intent);
            overridePendingTransition(0, 0);
            return;
        }
        this.progressDialog.dismiss();
        Intent intent2 = new Intent(this, StickerPackDetailsActivity.class);
        intent2.putExtra(StickerPackDetailsActivity.EXTRA_SHOW_UP_BUTTON, false);
        intent2.putExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_DATA, arrayList.get(0));
        startActivity(intent2);
        overridePendingTransition(0, 0);
    }


    public void showErrorMessage(String str) {
        this.progressDialog.dismiss();
        Log.e("Main activity", "error fetching sticker packs, " + str);
        Toast.makeText(this, "" + str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this,StartActivity.class));
        finish();
    }
}
