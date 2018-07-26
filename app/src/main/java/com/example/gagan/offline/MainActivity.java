package com.example.gagan.offline;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.gagan.offline.adapter.ViewPagerAdapter;
import com.example.gagan.offline.fragment.BaseFragment;
import com.example.gagan.offline.fragment.FireBaseFragment;
import com.example.gagan.offline.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_item)
    TabLayout tabLayout;
    List<BaseFragment> fragments = new ArrayList<>();

    PagerAdapter pagerAdapter;
    private FireBaseFragment firebaseFragment;

    private void initFragment() {
        Utils.initDetectors();
        addFirebaseFragment(Utils.FIREBASE);
    }

    private void addFirebaseFragment(String firebase) {
        firebaseFragment = new FireBaseFragment();
        fragments.add(firebaseFragment);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        ButterKnife.bind(this);
        initAdapter();
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initAdapter() {
        initFragment();
        pagerAdapter = new ViewPagerAdapter(fragments, getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
            if (Build.VERSION.SDK_INT >= 19) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            } else {
                if (Build.VERSION.SDK_INT > 10) {
                    findViewById(android.R.id.content).setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                }
            }

    }

}
