package com.cgn.android.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cgn.android.R;
import com.cgn.android.utils.Utilities;


/**
 * Created by Anil on 26/1/15.
 */
public abstract class ParentActivity extends ActionBarActivity implements Handler.Callback{

    private static final String TAG = ParentActivity.class.getSimpleName();

    protected Toolbar mToolbar;
    protected boolean mShowTitleBar = true;
    protected boolean mShowTitleText;
    protected boolean mShowCloseBtn;
    protected boolean mShowCommonBtn = true;
    protected boolean mShowTitleImage = true;
    protected boolean mShowCloseImage = false;

    protected boolean mNetworkStatus;

    // Fragment related: ContainerId, Currently Displayed Fragment, add/replace Animations.
    protected int mContainerID;
    protected Fragment mDisplayedFragment;
    protected int mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setTitleBar();
    }

    protected void addFragment(Fragment fragment, String tag) {
        addFragment(fragment, tag, false,
                mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim);
    }

    protected void replaceFragment(Fragment fragment, String tag) {
        replaceFragment(fragment, tag, false,
                mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim);
    }

    protected void addFragment(Fragment fragment, String tag, boolean addToBackStack) {
        addFragment(fragment, tag, addToBackStack,
                mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim);
    }

    protected void replaceFragment(Fragment fragment, String tag, boolean addToBackStack) {
        replaceFragment(fragment, tag, addToBackStack,
                mFragEnterAnim, mFragExitAnim, mFragPopEnterAnim, mFragPopExitAnim);
    }

    protected void addFragment(Fragment fragment, String tag, boolean addToBackStack,
                               int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
        mDisplayedFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
                .add(mContainerID, fragment, tag);

        if (addToBackStack) transaction.addToBackStack(tag);
        transaction.commit();
    }

    protected void replaceFragment(Fragment fragment, String tag, boolean addToBackStack,
                                   int enterAnim, int exitAnim, int popEnterAnim, int popExitAnim) {
        mDisplayedFragment = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
                .replace(mContainerID, fragment, tag);

        if (addToBackStack) transaction.addToBackStack(tag);
        transaction.commit();
    }

    protected void setTitleBar() {

        if (mToolbar == null) mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            return;
        }
        if (mShowTitleBar) {
            if (mToolbar.getVisibility() == View.GONE) {
                mToolbar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_top));;
            }
            mToolbar.setVisibility(Toolbar.VISIBLE);

            //Title Text
            TextView mTitleText = (TextView) findViewById(R.id.TITLE_text);
            if (mShowTitleText) {
                mTitleText.setVisibility(View.VISIBLE);
//                mTitleText.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up));
            } else {
                mTitleText.setVisibility(View.GONE);
//                mTitleText.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_down));
            }

            //Title Text
            ImageView mTitleCloseBtnImg = (ImageView) findViewById(R.id.TITLE_back_btn);
            if (mShowCloseBtn) {
                mTitleCloseBtnImg.setVisibility(View.VISIBLE);
                mTitleCloseBtnImg.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        handleMessage(Utilities.getMessage(R.id.TITLE_back_btn));
                    }
                });
            } else {
                mTitleCloseBtnImg.setVisibility(View.GONE);
            }

        } else {
            if (mToolbar.getVisibility() == View.VISIBLE) {
                mToolbar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out_top));
            }
            mToolbar.setVisibility(Toolbar.GONE);
        }
    }

    public void invalidateToolBar() {
        setTitleBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void updateTitleBar(boolean titleBar, boolean titleText, boolean closeButton, boolean commonButton, boolean titleImage, int titleTextResId, int commonBtnTextResId, boolean closeImage) {

        mShowTitleBar = titleBar;
        mShowTitleText = titleText;
        mShowCloseBtn = closeButton;
        mShowCommonBtn = commonButton;
        mShowTitleImage = titleImage;
        mShowCloseImage = closeImage;

        /*if (mShowTitleText && titleTextResId != Integer.MIN_VALUE) {
            ((TextView) findViewById(R.id.TITLE_text)).setText(Utilities.getString(titleTextResId));
        }
        if (mShowCommonBtn && commonBtnTextResId != Integer.MIN_VALUE) {
            ((TextView) findViewById(R.id.TITLE_btn_txt)).setText(Utilities.getString(commonBtnTextResId));
        }*/

        //Invalidate toolbar
        invalidateToolBar();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }
}
