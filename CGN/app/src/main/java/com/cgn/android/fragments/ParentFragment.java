package com.cgn.android.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cgn.android.activities.ParentActivity;
import com.cgn.android.utils.AppConstants;

/**
 * Created by DEV-101 on 26/1/15.
 */
public abstract class ParentFragment extends Fragment implements Handler.Callback, View.OnClickListener {

    public static String TAG = ParentFragment.class.getSimpleName();

    private boolean mIsOneItemClicked = false;

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialiseView(view, savedInstanceState);
        bindListeners(view);
        callInitialApi();
    }

    protected abstract void initialiseView(View view, Bundle savedInstanceState);

    protected abstract void bindListeners(View view);

    protected void callInitialApi() {
    }


    protected ParentActivity getCurrActivity() {
        return (ParentActivity) getActivity();
    }

    public abstract void onClicked(View v);

    @Override
    public void onClick(View v) {
        if (!mIsOneItemClicked) {
            onClicked(v);
            mIsOneItemClicked = true;

            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIsOneItemClicked = false;
                }
            }, AppConstants.DISABLE_DURATION);
        }
    }
}
