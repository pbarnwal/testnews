package com.cgn.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public enum NETWORK_TYPE {

        TYPE_MOBILE(ConnectivityManager.TYPE_MOBILE),   //ConnectivityManager.TYPE_MOBILE == 0
        TYPE_WIFI(ConnectivityManager.TYPE_WIFI),     //ConnectivityManager.TYPE_WIFI ==1
        TYPE_NOT_CONNECTED(-1);  //No Connection

        private final int ID;

        NETWORK_TYPE(int id) {
            ID = id;
        }

        public static NETWORK_TYPE getNetworkTypeFromId(int id) {

            if (id == TYPE_WIFI.ID) {
                return TYPE_WIFI;

            } else if (id == TYPE_MOBILE.ID) {
                return TYPE_MOBILE;

            } else {
                return TYPE_NOT_CONNECTED;
            }
        }
    }

    public static NETWORK_TYPE getConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        if (isConnected) {
            return NETWORK_TYPE.getNetworkTypeFromId(activeNetwork.getType());
        }

        return NETWORK_TYPE.TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {

        String status = null;
        NETWORK_TYPE conn = NetworkUtil.getConnectivityStatus(context);

        switch (conn) {

            case TYPE_WIFI:
                status = "Wifi enabled";
                break;

            case TYPE_MOBILE:
                status = "Mobile data enabled";
                break;

            case TYPE_NOT_CONNECTED:
                status = "Not connected to Internet";
                break;
        }
        return status;
    }

    public static Boolean isInternetConnected(Context ctx) {
        ConnectivityManager con = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con != null) {
            NetworkInfo activeNetwork = con.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static void checkInternetConnection(Context ctx) {
        if (!isInternetConnected(ctx)) {
//            UIUtils.showDebugToast("Not connected to Internet");
        }
    }

}
