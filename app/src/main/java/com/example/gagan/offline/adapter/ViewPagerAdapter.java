package com.example.gagan.offline.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.gagan.offline.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Gagan on 6/13/2018.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<BaseFragment> mData;

    public ViewPagerAdapter(List<BaseFragment> baseFragments, FragmentManager fm) {
        super(fm);
        this.mData = baseFragments;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    public void addFragment(BaseFragment fragment) {
        mData.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getTitleOfThis();
    }
}
