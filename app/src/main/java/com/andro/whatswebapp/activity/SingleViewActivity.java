package com.andro.whatswebapp.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.core.content.FileProvider;
import androidx.core.view.InputDeviceCompat;

import com.andro.whatswebapp.R;
import com.andro.whatswebapp.extras.Constants;
import com.andro.whatswebapp.models.ImagesModel;
import com.andro.whatswebapp.models.TempVideosModel;

import java.io.File;

public class SingleViewActivity extends StorieSaverBaseeeActivity {
    private String flag;
    ImageView ivBack;
    ImageView mAGalleryIvThumbnail;
    VideoView mAGalleryVvThumbnailVideo;
    int mASType;
    ImagesModel mImageModel;
    TempVideosModel mVideosModel;
    private int stopPosition;
    TextView tvTitle;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_displayimageorvideo);
        getSupportActionBar().hide();
        this.mAGalleryIvThumbnail = (ImageView) findViewById(R.id.aGallery_ivThumbnail);
        this.mAGalleryVvThumbnailVideo = (VideoView) findViewById(R.id.aGallery_vvThumbnailVideo);
        this.ivBack = (ImageView) findViewById(R.id.iv_back);
        this.tvTitle = (TextView) findViewById(R.id.title);
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        this.flag = getIntent().getStringExtra("flag");
        this.tvTitle.setText(this.flag);
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SingleViewActivity.this.startActivity(new Intent(SingleViewActivity.this, StorieSaverrrActivity.class));
                SingleViewActivity.this.finish();
            }
        });
        if (getIntent().getSerializableExtra(Constants.IMAGES) != null) {
            this.mASType = 1;
            this.mAGalleryIvThumbnail.setVisibility(View.VISIBLE);
            this.mAGalleryVvThumbnailVideo.setVisibility(View.GONE);
            this.mImageModel = (ImagesModel) getIntent().getSerializableExtra(Constants.IMAGES);
            StorieSaverBaseeeActivity.loadImage(this, this.mAGalleryIvThumbnail, this.mImageModel.getImagePath());
        } else if (getIntent().getSerializableExtra(Constants.VIDEOS) != null) {
            this.mASType = 2;
            this.mAGalleryIvThumbnail.setVisibility(View.GONE);
            this.mAGalleryVvThumbnailVideo.setVisibility(View.VISIBLE);
            this.mVideosModel = (TempVideosModel) getIntent().getSerializableExtra(Constants.VIDEOS);
            this.mAGalleryVvThumbnailVideo.setVideoURI(Uri.parse(this.mVideosModel.getVideoPath()));
            this.mAGalleryVvThumbnailVideo.requestFocus();
            this.mAGalleryVvThumbnailVideo.start();
            this.mAGalleryVvThumbnailVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    SingleViewActivity.this.finish();
                }
            });
        }
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aGallery_ivBack :
                finish();
                return;
            case R.id.aGallery_ivSave :
                if (this.mASType == 1) {
                    saveImageStatus(this.mImageModel.getImagePath());
                    return;
                } else {
                    saveVideoStatus(this.mVideosModel.getVideoPath());
                    return;
                }
            case R.id.aGallery_ivSend :
                if (this.mASType == 1) {
                    File file = new File(this.mImageModel.getImagePath());
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.SEND");
                    intent.setType("video/*");
                    intent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.whatsapp_share));
                    intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, "com.andro.whatswebapp.stickercontentprovider", file));
                    startActivity(Intent.createChooser(intent, "Share Video..."));
                    return;
                }
                File file2 = new File(this.mVideosModel.getVideoPath());
                Intent intent2 = new Intent();
                intent2.setAction("android.intent.action.SEND");
                intent2.setType("video/*");
                intent2.setPackage("com.whatsapp");
                intent2.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.whatsapp_share));
                intent2.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this, "com.andro.whatswebapp.stickercontentprovider", file2));
                startActivity(Intent.createChooser(intent2, "Share Video..."));
                return;
            case R.id.aGallery_ivWhatsapp :
                if (!isAppInstalled(getApplicationContext(), "com.whatsapp")) {
                    showToast(getString(R.string.install_app_first));
                } else if (this.mASType == 1) {
                    doShareWhatsappVideo(this.mImageModel.getImagePath());
                } else {
                    doShareWhatsappVideo(this.mVideosModel.getVideoPath());
                }
                return;
            default:
                return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        VideoView videoView = this.mAGalleryVvThumbnailVideo;
        if (videoView != null) {
            this.stopPosition = videoView.getCurrentPosition();
            this.mAGalleryVvThumbnailVideo.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        VideoView videoView = this.mAGalleryVvThumbnailVideo;
        if (videoView != null) {
            videoView.seekTo(this.stopPosition);
            this.mAGalleryVvThumbnailVideo.start();
        }
    }


    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mScaleFactor = 1.0f;

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            this.mScaleFactor *= scaleGestureDetector.getScaleFactor();
            this.mScaleFactor = Math.max(0.1f, Math.min(this.mScaleFactor, 10.0f));
            SingleViewActivity.this.mAGalleryIvThumbnail.setScaleX(this.mScaleFactor);
            SingleViewActivity.this.mAGalleryIvThumbnail.setScaleY(this.mScaleFactor);
            return true;
        }
    }
}
