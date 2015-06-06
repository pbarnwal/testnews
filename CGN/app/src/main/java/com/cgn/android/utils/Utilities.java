package com.cgn.android.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import java.text.SimpleDateFormat;
import java.util.Locale;

//import com.facebook.Session;


/**
 * Created by Anil on 4/11/14.
 */
public class Utilities {

    private static final String TAG = Utilities.class.getSimpleName();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());


    public static Message getMessage(int what) {
        Message message = new Message();
        message.what = what;
        return message;
    }

    public static Message getMessage(int what, Bundle bundle) {
        Message message = new Message();
        message.what = what;
        message.setData(bundle);
        return message;
    }

   /* public static String getFbImageUrl(String fbId) {
        return String.format(APIsConstants.FB_PROFILE_PIC_URL, fbId);
    }*/


    public static String getString(int resId) {
        return CGNApplication.getAppContext().getString(resId);
    }

    public static long getInteger(int resId) {
        return CGNApplication.getAppContext().getResources().getInteger(resId);
    }

    public static Drawable getDrawable(int resId) {
        return CGNApplication.getAppContext().getResources().getDrawable(resId);
    }

    public static int getColor(int resId) {
        return CGNApplication.getAppContext().getResources().getColor(resId);
    }
    public static float dp2px(Resources resources, float dp) {
        final float scale = resources.getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public static float sp2px(Resources resources, float sp) {
        final float scale = resources.getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static void setVideoViewDimensions(final VideoView videoView) {
        // Setting videoView's width equal to it's parent's, regardless of aspect ratio
//        ((View) videoView.getParent()).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
        int h = ((View) videoView.getParent()).getHeight();
        int w = ((View) videoView.getParent()).getWidth();

        ViewGroup.LayoutParams params = videoView.getLayoutParams();
        params.height = h;
        params.width = w;
        videoView.setLayoutParams(params);
//            }
//        });
    }
}
