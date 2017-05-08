package com.fflorio.simplevalueconverter.activities;

import android.app.AlertDialog;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.fflorio.simplevalueconverter.R;

/**
 * This Class was created for SimpleValueConverter on 20/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public abstract class AbstractActivity extends AppCompatActivity {

    public void showWarningDialog(@StringRes int messageResId) {
        showMessageDialog(R.string.warning, messageResId);
    }

    public void showInfoDialog(@StringRes int messageResId) {
        showMessageDialog(R.string.info, messageResId);
    }

    public void showMessageDialog(@StringRes int titleResId, @StringRes int messageResId) {
        new AlertDialog.Builder(this)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(R.string.ok, null)
                .create().show();
    }
}
