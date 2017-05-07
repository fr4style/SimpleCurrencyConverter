package com.fflorio.simplevalueconverter.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.StringRes;

import com.fflorio.simplevalueconverter.R;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class ShowProgressDialogHelper {

    private Activity activity;
    private String label;
    private ProgressDialog progressDialog;

    public ShowProgressDialogHelper(Activity activity){
        this.activity = activity;
        this.label = activity.getString(R.string.msg__loading);
    }

    public ShowProgressDialogHelper(Activity activity, @StringRes int stringResId) {
        this.activity = activity;
        this.label = activity.getString(stringResId);
    }

    public void showDialog(String newLabel) {
        if (newLabel != null && !newLabel.equals(label)) {
            this.label = newLabel;
        }
        showDialog();
    }

    public void showDialog() {
        hideDialog();
        progressDialog = ProgressDialog.show(activity, null, label, true, false);
    }

    public void unbind() {
        hideDialog();
        activity = null;

    }


    public synchronized void hideDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}

