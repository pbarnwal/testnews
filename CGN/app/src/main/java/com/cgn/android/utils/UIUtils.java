package com.cgn.android.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Toast;

import com.cgn.android.R;


/**
 * Created by DEV-103 on 19/3/15.
 */
public class UIUtils {
    public static void showToast(String data) {
        Toast.makeText(CGNApplication.getAppContext(), data, Toast.LENGTH_SHORT).show();
    }

    public static void showDebugToast(String data) {

        if (AppConstants.LOG) {
            showToast(data);
        }
    }

    public static Typeface getTypeFace(Context context, String typeFace) {
        return Typeface.createFromAsset(context.getAssets(), typeFace);
    }

    public static Typeface getDefaultTypeFace(Context context) {
        return Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.lobster_1_3));
    }


}
