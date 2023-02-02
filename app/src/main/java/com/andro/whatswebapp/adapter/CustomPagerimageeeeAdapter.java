package com.andro.whatswebapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.andro.whatswebapp.R;
import com.andro.whatswebapp.models.ImagesModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class CustomPagerimageeeeAdapter extends PagerAdapter {
    List<ImagesModel> arrayList;
    Context mContext;
    LayoutInflater mLayoutInflater;
    String str;

    public CustomPagerimageeeeAdapter(Activity activity, List<ImagesModel> list) {
        this.arrayList = list;
        this.mContext = activity;
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
        View inflate = this.mLayoutInflater.inflate(R.layout.pager_img_item, viewGroup, false);
        try {
            Glide.with(this.mContext).load(this.arrayList.get(i).getImagePath()).asBitmap().into((ImageView) inflate.findViewById(R.id.image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewGroup.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((LinearLayout) obj);
    }
}
