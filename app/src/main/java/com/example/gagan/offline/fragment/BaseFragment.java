package com.example.gagan.offline.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.Observable;

import butterknife.Unbinder;

/**
 * Created by Gagan on 6/13/2018.
 */

public class BaseFragment extends Fragment {
    protected Unbinder unbinder;
    protected Object _detectorHelper;

    public void setDetectorHelper(@NonNull Object baseDetector) {
        _detectorHelper = baseDetector;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }

    public String getTitleOfThis() {
        return "Help";
    }
}
