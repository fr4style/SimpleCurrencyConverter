package com.fflorio.simplevalueconverter.ui;

import android.os.Bundle;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public abstract class Presenter {
    public abstract void unbind();

    public Bundle getBundleToSave() { return new Bundle(); }
    public void restoreBundle(Bundle bundle) { /* do nothing*/}

    //TODO more other abstract methods
}
