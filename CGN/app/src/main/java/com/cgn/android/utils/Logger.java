package com.cgn.android.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {

    // Used it by Logger and showDebugToast only (True during development only)
    public static final boolean LOG = true;

    public static void i(String tag, String string) {
        if (LOG && !TextUtils.isEmpty(string)) {
            android.util.Log.i(tag, string);

            appendLog("\n" + tag + "\t" + string);
        }
    }

    public static void e(String tag, String string) {
        if (LOG&& !TextUtils.isEmpty(string)) {
            android.util.Log.e(tag, string);
            appendLog("\n" + tag + "\t" + string);
        }
    }

    public static void d(String tag, String string) {
        if (LOG&& !TextUtils.isEmpty(string)) {
            android.util.Log.d(tag, string);
        }
    }

    public static void v(String tag, String string) {
        if (LOG&& !TextUtils.isEmpty(string)) {
            android.util.Log.v(tag, string);
            appendLog("\n" + tag + "\t" + string);
        }
    }

    public static void w(String tag, String string) {
        if (LOG&& !TextUtils.isEmpty(string)) {
            android.util.Log.w(tag, string);
        }
    }

    private static void appendLog(String text) {
        if(TextUtils.isEmpty(text)){
            return;
        }
        File logFile = new File(Environment.getExternalStorageDirectory() + "/TWS_log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //BufferedWriter for performance, true to set append to file flag
        BufferedWriter buf;
        try {
            if (logFile.length() > (2 * 1000 * 1024)) {  //file size greather than 2MB
                buf = new BufferedWriter(new FileWriter(logFile, false));
            } else {
                buf = new BufferedWriter(new FileWriter(logFile, true));
            }
            Date date = new Date();
            buf.append(date.toString() + ": " + text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}