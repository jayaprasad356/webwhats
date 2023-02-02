package com.andro.whatswebapp.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.andro.whatswebapp.fragments.StorieSaverTabImages;
import com.andro.whatswebapp.fragments.StorieSaverTabVideos;

public class StorieSaverrrTabPager extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public StorieSaverrrTabPager(FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.mNumOfTabs = i;
    }

    public Fragment getItem(int i) {
        if (i == 0) {
            return new StorieSaverTabImages();
        }
        if (i != 1) {
            return null;
        }
        return new StorieSaverTabVideos();
    }

    public int getCount() {
        return this.mNumOfTabs;
    }
}
