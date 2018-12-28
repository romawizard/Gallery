package com.diachenko.gallery.utils;

import android.util.Log;

import com.diachenko.gallery.BuildConfig;

public class MyLog {

    public static void log(String tag, String log) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, log);
        }
    }
}
