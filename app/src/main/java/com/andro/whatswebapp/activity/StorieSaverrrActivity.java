package com.andro.whatswebapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.InputDeviceCompat;
import androidx.viewpager.widget.ViewPager;

import com.andro.whatswebapp.Ads;
import com.andro.whatswebapp.R;
import com.andro.whatswebapp.adapter.StorieSaverrrTabPager;
import com.google.android.gms.ads.AdView;
import com.google.android.material.tabs.TabLayout;

public class StorieSaverrrActivity extends AppCompatActivity {
    ViewPager mPager;
    TabLayout mTabLayout;


    @Override
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_status);
        getWindow().getDecorView().setSystemUiVisibility(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        TextView tvTitle = (TextView) findViewById(R.id.title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle.setText("Recent Stories");
        AdView llAds = (AdView) findViewById(R.id.ll_ads);
        Ads.bannerad(llAds, this);
        ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(StorieSaverrrActivity.this, MainActivity.class));
                finish();
            }
        });
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        TabLayout tabLayout = mTabLayout;
        tabLayout.addTab(tabLayout.newTab().setText((CharSequence) "Images"));
        TabLayout tabLayout2 = mTabLayout;
        tabLayout2.addTab(tabLayout2.newTab().setText((CharSequence) "Videos"));
        mTabLayout.setTabGravity(0);
        mPager.setAdapter(new StorieSaverrrTabPager(getSupportFragmentManager(), mTabLayout.getTabCount()));
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabReselected(TabLayout.Tab tab) {
            }

            public void onTabUnselected(TabLayout.Tab tab) {
            }

            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
