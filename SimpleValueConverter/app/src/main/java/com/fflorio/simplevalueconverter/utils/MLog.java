package com.fflorio.simplevalueconverter.utils;

import android.util.Log;

import com.fflorio.simplevalueconverter.BuildConfig;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class MLog {

    public static final void w(Object object) {
        if (BuildConfig.DEBUG) {
            Log.w(Vars.TAG, object.toString());
        }
    }

    public static final void d(Object object) {
        if (BuildConfig.DEBUG) {
            Log.d(Vars.TAG, object.toString());
        }
    }

    public static final void e(Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(Vars.TAG, "Errore:", tr);
        }
    }


    public static final void e(Object object, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(Vars.TAG, object.toString(), tr);
        }
    }

}
