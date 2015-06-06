package com.cgn.android.models;

/**
 * Created by Anil on 24/3/15.
 */
public class NavItem {

    private int mResId;
    private String mName;
    private int mItemBg;

    public NavItem(int mResId, String mName, int mItemBg) {
        this.mResId = mResId;
        this.mName = mName;
        this.mItemBg = mItemBg;
    }

    public int getmResId() {
        return mResId;
    }

    public void setmResId(int mResId) {
        this.mResId = mResId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmItemBg() { return mItemBg; }

    public void setmItemBg(int mItemBg) { this.mItemBg = mItemBg; }
}