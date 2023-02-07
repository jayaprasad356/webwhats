package com.vedha.whatsweb.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vedha.whatsweb.Adclick;
import com.vedha.whatsweb.Ads;
import com.vedha.whatsweb.Common;
import com.vedha.whatsweb.CustomToast;
import com.vedha.whatsweb.R;
import com.vedha.whatsweb.activity.DisplayActivity;
import com.vedha.whatsweb.models.TempVideosModel;
import com.vedha.whatsweb.models.VideosModel;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class StorieSaverrrVideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static ArrayList<VideosModel> mVideoResponsesList2;
    private final Ads ads;
    public Activity mContext;
    private File mRoot;
    public ArrayList<TempVideosModel> mTempVideoModels = new ArrayList<>();
    public ArrayList<VideosModel> mVideoResponsesList;

    public StorieSaverrrVideoAdapter(Activity activity, ArrayList<VideosModel> arrayList) {
        ads =  new Ads();
        ads.interstitialload(activity);


        this.mContext = activity;
        this.mVideoResponsesList = arrayList;
        for (int i = 0; i < this.mVideoResponsesList.size(); i++) {
            TempVideosModel tempVideosModel = new TempVideosModel();
            tempVideosModel.setVideoName(this.mVideoResponsesList.get(i).getVideoName());
            tempVideosModel.setVideoPath(this.mVideoResponsesList.get(i).getVideoPath());
            this.mTempVideoModels.add(tempVideosModel);
        }
    }

    public static boolean isAppInstalled(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.item_status, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        viewHolder2.mIStatusIvPlay.setVisibility(View.VISIBLE);
        viewHolder2.bindView(i);
    }

    public int getItemCount() {
        return this.mVideoResponsesList.size();
    }


    public void createNewFile(String str, String str2) {
        String str3 = str + str2.substring(45);
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
        this.mRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/" + this.mContext.getResources().getString(R.string.app_name));
        if (Build.VERSION.SDK_INT >= 19) {
            new SingleMediaScanner(this.mContext.getApplicationContext(), new File(str, str2.substring(45)));
            this.mContext.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + str)));
            return;
        }
        this.mContext.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + str)));
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

    private void reScanSdCard() {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mContext.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            return;
        }
        Activity activity = this.mContext;
        activity.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
    }

    public void showToast(String str) {
        CustomToast customToast = new CustomToast(this.mContext);
        customToast.setCustomView(str);
        customToast.setDuration(0);
        customToast.show();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mIStatusIvLogo;
        ImageView mIStatusIvPlay;
        ImageView mIStatusIvSave;
        ImageView mIStatusIvShare;
        ImageView mIStatusIvWhatsapp;

        ViewHolder(View view) {
            super(view);
            this.mIStatusIvLogo = (ImageView) view.findViewById(R.id.iStatus_ivLogo);
            this.mIStatusIvShare = (ImageView) view.findViewById(R.id.iStatus_ivShare);
            this.mIStatusIvWhatsapp = (ImageView) view.findViewById(R.id.iStatus_ivWhatsapp);
            this.mIStatusIvSave = (ImageView) view.findViewById(R.id.iStatus_ivSave);
            this.mIStatusIvPlay = (ImageView) view.findViewById(R.id.iStatus_ivPlay);
        }


        public void bindView(final int i) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (StorieSaverrrVideoAdapter.this.mVideoResponsesList.get(i).getBitmap() != null) {
                StorieSaverrrVideoAdapter.this.mVideoResponsesList.get(i).getBitmap().compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            }
            Glide.with(StorieSaverrrVideoAdapter.this.mContext).load(byteArrayOutputStream.toByteArray()).into(this.mIStatusIvLogo);
            this.mIStatusIvWhatsapp.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (StorieSaverrrVideoAdapter.isAppInstalled(StorieSaverrrVideoAdapter.this.mContext, "com.whatsapp")) {
                        Uri parse = Uri.parse(StorieSaverrrVideoAdapter.this.mVideoResponsesList.get(i).getVideoPath());

                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.SEND");
                        intent.setType("video/*");
                        intent.setPackage("com.whatsapp");
                        intent.putExtra("android.intent.extra.TEXT", StorieSaverrrVideoAdapter.this.mContext.getResources().getString(R.string.whatsapp_share));
                        intent.putExtra("android.intent.extra.STREAM", parse);
                        StorieSaverrrVideoAdapter.this.mContext.startActivity(Intent.createChooser(intent, "Share Video..."));
                        return;
                    }
                    Toast.makeText(StorieSaverrrVideoAdapter.this.mContext, StorieSaverrrVideoAdapter.this.mContext.getResources().getString(R.string.install_app_first), Toast.LENGTH_SHORT).show();
                }
            });
            this.mIStatusIvSave.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    String videoPath = StorieSaverrrVideoAdapter.this.mVideoResponsesList.get(i).getVideoPath();
                    Common.copyFiles(videoPath, mContext.getApplicationContext());
                    File externalStorageDirectory = Environment.getExternalStorageDirectory();
                    String str = externalStorageDirectory.getAbsolutePath() + "/" + StorieSaverrrVideoAdapter.this.mContext.getResources().getString(R.string.app_name) + "/Videos/";
                    File file = new File(str);
                    if (file.isDirectory() || file.exists()) {
                        StorieSaverrrVideoAdapter.this.createNewFile(str, videoPath);
                    } else if (file.mkdirs()) {
                        StorieSaverrrVideoAdapter.this.createNewFile(str, videoPath);
                    }
                }
            });
            this.mIStatusIvShare.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Uri parse = Uri.parse(StorieSaverrrVideoAdapter.this.mVideoResponsesList.get(i).getVideoPath());
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.SEND");
                    intent.setType("video/*");
                    intent.setPackage("com.whatsapp");
                    intent.putExtra("android.intent.extra.TEXT", StorieSaverrrVideoAdapter.this.mContext.getResources().getString(R.string.whatsapp_share));
                    intent.putExtra("android.intent.extra.STREAM", parse);
                    StorieSaverrrVideoAdapter.this.mContext.startActivity(Intent.createChooser(intent, "Share Video..."));
                }
            });
            this.mIStatusIvLogo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (Ads.statusclick != Ads.statusclickcount) {
                        Ads.statusclickcount ++;
                        StorieSaverrrVideoAdapter.mVideoResponsesList2 = StorieSaverrrVideoAdapter.this.mVideoResponsesList;
                        Intent intent = new Intent(StorieSaverrrVideoAdapter.this.mContext, DisplayActivity.class);
                        intent.putExtra("id", i);
                        intent.putExtra("flag", "Videos");
                        StorieSaverrrVideoAdapter.this.mContext.startActivity(intent);
                        return;
                    }
                    ads.showInd(new Adclick() {
                        @Override
                        public void onclicl() {
                            Ads.statusclickcount = 0;
                            StorieSaverrrVideoAdapter.mVideoResponsesList2 = StorieSaverrrVideoAdapter.this.mVideoResponsesList;
                            Intent intent = new Intent(StorieSaverrrVideoAdapter.this.mContext, DisplayActivity.class);
                            intent.putExtra("id", i);
                            intent.putExtra("flag", "Videos");
                            StorieSaverrrVideoAdapter.this.mContext.startActivity(intent);
                        }
                    });
                }
            });
        }
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
