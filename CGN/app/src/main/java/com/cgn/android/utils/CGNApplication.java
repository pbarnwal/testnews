package com.cgn.android.utils;

import android.app.Application;
import android.content.Context;


/**
 * Created by Anil on 02/02/15.
 */
public class CGNApplication extends Application {
    private static final String TAG = CGNApplication.class.getSimpleName();
    private static CGNApplication mInstance;
    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mAppContext = getApplicationContext();

    }

    public static CGNApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
