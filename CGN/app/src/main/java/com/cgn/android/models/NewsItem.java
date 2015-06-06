package com.cgn.android.models;

/**
 * Created by anil on 6/6/15.
 */
public class NewsItem {
    private String mTitle;
    private String mDept;
    private String mTime;

    public NewsItem(String mTitle, String mTime, String mDept) {
        this.mTitle = mTitle;
        this.mTime = mTime;
        this.mDept = mDept;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDept() {
        return mDept;
    }

    public void setmDept(String mDept) {
        this.mDept = mDept;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }
}
