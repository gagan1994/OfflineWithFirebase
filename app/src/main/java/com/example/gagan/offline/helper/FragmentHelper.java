package com.example.gagan.offline.helper;

import android.content.Context;
import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Gagan on 6/13/2018.
 */

public abstract class FragmentHelper implements Serializable {

//    protected DetectionFragment detectionFragment;

    public abstract String getTitle();

    public abstract void initFirebaseDetectionObj();

    public abstract void initFirebaseVision(Uri uri, Context context);

//    public void addCallBack(DetectionFragment detectionFragment) {
//        this.detectionFragment = detectionFragment;
//    }
}
