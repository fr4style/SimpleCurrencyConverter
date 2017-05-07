package com.fflorio.simplevalueconverter.converter;

import android.os.Bundle;

import com.fflorio.simplevalueconverter.R;
import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;

import java.lang.ref.WeakReference;

/**
 * Created by francesco on 2017-05-07.
 */

public class ConverterPresenter implements ConverterMVP.ViewPresenter, ConverterMVP.ModelPresenter {

    private final String FROM_CURRENCY = "FROM_CURRENCY";
    private final String TO_CURRENCY = "TO_CURRENCY";

    private ConverterModel model;
    private WeakReference<ConverterMVP.View> viewWeakReference;

    public ConverterPresenter(final ConverterMVP.View view) {
        viewWeakReference = new WeakReference<>(view);
        model = new ConverterModel();
    }

    //region UI_ACTION
    @Override
    public void getCurrencyList() {
        viewWeakReference.get().showLoadingView(R.string.msg__download_valute);
        model.getCurrencyList(this);
    }

    @Override
    public void convertValue(ConvertCurrencyParams convertCurrencyParams) {
        viewWeakReference.get().showLoadingView(R.string.msg__conversione_in_corso);
        model.convertCurrency(convertCurrencyParams, this);
    }

    @Override
    public Bundle saveCurrentValueIntoBundle(String fromCurrencyCode, String toCurrencyCode) {
        Bundle bundle = new Bundle();
        bundle.putString(FROM_CURRENCY, fromCurrencyCode);
        bundle.putString(TO_CURRENCY, toCurrencyCode);
        return bundle;
    }

    @Override
    public void restoreDefaultValues(Bundle savedBundle) {
        if (savedBundle != null) {
            final String fromValue = savedBundle.getString(FROM_CURRENCY);
            final String toValue = savedBundle.getString(TO_CURRENCY);
            model.restoreSavedState(fromValue, toValue);
        }
    }
    //endregion

    //region EVENT currencyList
    @Override
    public void onCurrencyListReady(CurrencyList currencyList, String fromCurrencyCode, String toCurrencyCode) {
        viewWeakReference.get().hideLoadingView();
        viewWeakReference.get().updateCurrencyLists(currencyList);
        viewWeakReference.get().updateSpinners(fromCurrencyCode, toCurrencyCode);

    }

    @Override
    public void onCurrencyListError(Throwable throwable) {
        viewWeakReference.get().hideLoadingView();
        viewWeakReference.get().showWarningDialog(R.string.error__service_failed);
    }
    //endregion

    //region EVENT currency
    @Override
    public void onConvertCurrency(String currency, double value) {
        viewWeakReference.get().hideLoadingView();
        viewWeakReference.get().showConversion(currency, value);
    }

    @Override
    public void onConvertCurrencyError(Throwable throwable) {
        viewWeakReference.get().hideLoadingView();
        viewWeakReference.get().showWarningDialog(R.string.error__service_failed);
    }
    //endregion


    @Override
    public void unbind() {
        model.release();
        viewWeakReference.clear();
    }
}
