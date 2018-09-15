package com.example.a01020072846.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SummaryPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;
    String userId;

    public SummaryPagerAdapter(FragmentManager fm, String userId) {
        super(fm);

        this.userId = userId;

        list = new ArrayList<Fragment>();
        list.add(new SalesTrendFragment(userId));
        list.add(new SummaryFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "매출 동향";
        else if(position == 1)
            return "종합";
        else
            return position+"번째";
    }
}
