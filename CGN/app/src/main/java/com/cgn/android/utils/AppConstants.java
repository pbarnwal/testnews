package com.cgn.android.utils;

/**
 * Created by Anil on 26/1/15.
 */
public class AppConstants {

    public static final int DISABLE_DURATION = 500;


    //AllSCREENS
    public enum AllSCREENS {

        LAUNCH_NEWS_SCREEN,  //
        LAUNCH_ARCHIVES_SCREEN,
        LAUNCH_SHARE_TO_FRIENDS_SCREEN,
        LAUNCH_RATE_OUR_APP,
        DEFAULT_ACTION;

        public final int ID;

        AllSCREENS() {
            this.ID = ordinal();
        }


        public static AllSCREENS getScreenName(int id) {
            if (id >= values().length || id == -1)
                return DEFAULT_ACTION;
            return values()[id];
        }
    }

    // Used it by Logger and showDebugToast only (True during development only)
    public static final boolean LOG = true;


    //APPSETTINGS SCREENS
    public enum APP_SETTINGS {
        TAB_7CPC,
        TAB_DOPT,
        TAB_EXPECTED_DA,
        TAB_OTHER1,
        TAB_OTHER2;

        public final int ID;

        APP_SETTINGS() {
            this.ID = ordinal();
        }

        public static APP_SETTINGS getFieldId(int id) {
            return values()[id];
        }

    }

}
