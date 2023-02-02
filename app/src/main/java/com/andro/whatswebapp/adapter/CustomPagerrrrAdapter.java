package com.andro.whatswebapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.andro.whatswebapp.R;
import com.andro.whatswebapp.models.VideosModel;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class CustomPagerrrrAdapter extends PagerAdapter {
    List<VideosModel> arrayList;
    JZVideoPlayerStandard jzVideoPlayerStandard;
    Context mContext;
    LayoutInflater mLayoutInflater;
    JZVideoPlayer.JZAutoFullscreenListener sensorEventListener;
    SensorManager sensorManager;
    String str;

    public CustomPagerrrrAdapter(Context context, List<VideosModel> list) {
        this.arrayList = list;
        this.mContext = context;
        mLayoutInflater = ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    }

    public int getCount() {
        return this.arrayList.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == ((LinearLayout) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.mLayoutInflater.inflate(R.layout.pager_item, viewGroup, false);
        this.jzVideoPlayerStandard = (JZVideoPlayerStandard) inflate.findViewById(R.id.videoplayer);
        this.sensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        this.sensorEventListener = new JZVideoPlayer.JZAutoFullscreenListener();
        try {
            this.jzVideoPlayerStandard.setUp(this.arrayList.get(i).getVideoPath(), 0, "");
            Bitmap bitmap = this.arrayList.get(i).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            Glide.with(this.mContext).load(byteArrayOutputStream.toByteArray()).asBitmap().into(this.jzVideoPlayerStandard.thumbImageView);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        viewGroup.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((LinearLayout) obj);
    }

}
