package com.u1626889.warwickdrama;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs = 2;

    public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                CreatePostTab1 tab1 = new CreatePostTab1();
                return tab1;
            case 1:
                CreatePostTab2 tab2 = new CreatePostTab2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}