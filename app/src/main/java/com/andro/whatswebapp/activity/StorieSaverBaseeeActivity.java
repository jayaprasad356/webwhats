package com.andro.whatswebapp.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andro.whatswebapp.CustomToast;
import com.andro.whatswebapp.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public abstract class StorieSaverBaseeeActivity extends AppCompatActivity {
    private File mRoot;

    public static void loadImage(Activity activity, ImageView imageView, String str) {
        if (Build.VERSION.SDK_INT >= 17) {
            if (activity == null || activity.isDestroyed()) {
                return;
            }
        } else if (activity == null || activity.isFinishing()) {
            return;
        }
        Glide.with(activity).load(str).into(imageView);
    }

    public static boolean isAppInstalled(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(R.style.AppTheme);
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void showToast(String str) {
        CustomToast customToast = new CustomToast(this);
        customToast.setCustomView(str);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.show();
    }


    public void saveVideoStatus(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String str2 = externalStorageDirectory.getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/Videos/";
        File file = new File(str2);
        if (file.isDirectory() || file.exists()) {
            createNewFile(str2, str);
        } else if (file.mkdirs()) {
            createNewFile(str2, str);
        }
    }

    public void saveImageStatus(String str) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        String str2 = externalStorageDirectory.getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/Images/";
        File file = new File(str2);
        if (file.isDirectory() || file.exists()) {
            createNewFile(str2, str);
        } else if (file.mkdirs()) {
            createNewFile(str2, str);
        }
    }

    private void createNewFile(String str, String str2) {
        String str3 = str + getName(str2);
        File file = new File(str3);
        if (file.isFile() || file.exists()) {
            showToast("File Already Exists, Status Saved");
            Log.i("File Status", "File already exists");
        } else {
            Log.i("File Status", "Doesnt exist");
            try {
                if (file.createNewFile()) {
                    Log.i("File Status", "File Created");
                    copyStatusIntoFile(str3, str2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.mRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/" + getResources().getString(R.string.app_name));
        if (Build.VERSION.SDK_INT >= 19) {
            new SingleMediaScanner(getApplicationContext(), new File(str, getName(str2)));
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + str)));
            return;
        }
        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + str)));
    }

    private void copyStatusIntoFile(String str, String str2) {
        try {
            FileChannel channel = new FileInputStream(new File(str2)).getChannel();
            if (new FileOutputStream(new File(str)).getChannel().transferFrom(channel, 0, channel.size()) > 0) {
                Log.i("Copy Status: ", "Copied");
                showToast("Status Saved");
                reScanSdCard();
                return;
            }
            Log.i("Copy Status: ", "Cant copy");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public String getName(String str) {
        return str.substring(45);
    }

    public void doShareWhatsappVideo(String str) {
        Uri parse = Uri.parse(str);
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("*/*");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.TEXT", getString(R.string.whatsapp_share));
        intent.addFlags(268435457);
        intent.putExtra("android.intent.extra.STREAM", parse);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            showToast("No App Found, Please install it.");
        }
    }

    public void doShareVideo(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
        startActivity(Intent.createChooser(intent, "Share Video..."));
    }

    private void reScanSdCard() {
        if (Build.VERSION.SDK_INT >= 19) {
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            return;
        }
        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
    }


    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
        private File mFile;
        private MediaScannerConnection mMs;

        public SingleMediaScanner(Context context, File file) {
            this.mFile = file;
            this.mMs = new MediaScannerConnection(context, this);
            this.mMs.connect();
        }

        public void onMediaScannerConnected() {
            this.mMs.scanFile(this.mFile.getAbsolutePath(), (String) null);
        }

        public void onScanCompleted(String str, Uri uri) {
            this.mMs.disconnect();
        }
    }
}
