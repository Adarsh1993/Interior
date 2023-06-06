package com.optic.opticvyu.customviews;

import android.app.Application;

import com.arashivision.sdkcamera.InstaCameraSDK;
import com.arashivision.sdkmedia.InstaMediaSDK;

import io.realm.Realm;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        InstaCameraSDK.init(this);
        InstaMediaSDK.init(this);
    }
}
