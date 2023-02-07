package com.vedha.whatsweb.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.vedha.whatsweb.Ads;
import com.vedha.whatsweb.Common;
import com.vedha.whatsweb.R;
import com.vedha.whatsweb.adapter.CustomPagerimageeeeAdapter;
import com.vedha.whatsweb.adapter.StorieSaverImageeeAdapter;
import com.vedha.whatsweb.models.ImagesModel;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.List;

public class DisplayActivityNewimages extends StorieSaverBaseeeActivity {

    List<ImagesModel> arrayList = StorieSaverImageeeAdapter.mImageResponsesList2;
    ImageView copy;
    int currentPage;
    private String flag;
    int id;
    ImageView imgshare;
    private CustomPagerimageeeeAdapter mCustomPagerAdapter;

    public ViewPager mViewPager;
    ImageView movleft;
    ImageView movright;
    ImageView whatsappshareiv;

    AdView llAds;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_displaynew);
        this.llAds = (AdView) findViewById(R.id.ll_ads);
        Ads.bannerad(this.llAds, this);

        this.imgshare = (ImageView) findViewById(R.id.shareimggggg);
        this.movleft = (ImageView) findViewById(R.id.movleft);
        this.movright = (ImageView) findViewById(R.id.movright);
        this.copy = (ImageView) findViewById(R.id.copyy);
        this.whatsappshareiv = (ImageView) findViewById(R.id.whatsappshareiv);
        this.id = getIntent().getExtras().getInt("id", 0);
        this.flag = getIntent().getStringExtra("flag");
        setTitle(this.flag);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.currentPage = this.id;
        this.mCustomPagerAdapter = new CustomPagerimageeeeAdapter(this, this.arrayList);
        this.mViewPager = (ViewPager) findViewById(R.id.pager);
        this.mViewPager.setAdapter(this.mCustomPagerAdapter);
        this.mViewPager.setCurrentItem(this.id);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
            }
        });
        this.movright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DisplayActivityNewimages.this.mViewPager.setCurrentItem(DisplayActivityNewimages.this.mViewPager.getCurrentItem() + 1);
            }
        });
        this.movleft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DisplayActivityNewimages.this.mViewPager.setCurrentItem(DisplayActivityNewimages.this.mViewPager.getCurrentItem() - 1);
            }
        });
        this.imgshare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DisplayActivityNewimages.this.doShareVideo(new File(((ImagesModel) DisplayActivityNewimages.this.arrayList.get(DisplayActivityNewimages.this.mViewPager.getCurrentItem())).getImagePath()));
            }
        });
        this.whatsappshareiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int currentItem = DisplayActivityNewimages.this.mViewPager.getCurrentItem();
                DisplayActivityNewimages displayActivityNewimages = DisplayActivityNewimages.this;
                displayActivityNewimages.doShareWhatsappVideo(((ImagesModel) displayActivityNewimages.arrayList.get(currentItem)).getImagePath());
            }
        });
        this.copy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int currentItem = DisplayActivityNewimages.this.mViewPager.getCurrentItem();
                DisplayActivityNewimages displayActivityNewimages = DisplayActivityNewimages.this;
                //    displayActivityNewimages.saveImageStatus(((ImagesModel) displayActivityNewimages.arrayList.get(currentItem)).getImagePath());
                Common.copyFiles(((ImagesModel) displayActivityNewimages.arrayList.get(currentItem)).getImagePath(), getApplicationContext());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
