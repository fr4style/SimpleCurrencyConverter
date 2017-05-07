package com.fflorio.simplevalueconverter.converter;

import android.os.Bundle;
import android.support.annotation.StringRes;

import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;

/**
 * Created by francesco on 2017-05-06.
 */

public interface ConverterMVP {

    interface View {
        //loading view
        void showLoadingView(final @StringRes int messageId);

        void hideLoadingView();

        //error dialogs
        void showWarningDialog(@StringRes int messageResId);

        void showConversion(final String currency, final double value);

        void updateCurrencyLists(final CurrencyList currencyList);

        void updateSpinners(final String fromValue, final String toValue);
    }

    interface Model {

        interface CurrencyListCallback {
            void onCurrencyListReady(final CurrencyList currencyList, String fromCurrencyCode, String toCurrencyCode);

            void onCurrencyListError(Throwable throwable);
        }

        interface ConvertCurrencyCallback {
            void onConvertCurrency(final String currency, final double value);

            void onConvertCurrencyError(Throwable throwable);
        }

        void convertCurrency(final ConvertCurrencyParams params, Model.ConvertCurrencyCallback callback);

        void getCurrencyList(final Model.CurrencyListCallback callback);

        void restoreSavedState(final String fromValue, final String toValue);

        void release();

    }

    interface ViewPresenter {
        void getCurrencyList();

        void convertValue(ConvertCurrencyParams convertCurrencyParams);

        Bundle saveCurrentValueIntoBundle(final String fromCurrencyCode, final String toCurrencyCode);

        void restoreDefaultValues(final Bundle savedBundle);

        void unbind();
    }

    interface ModelPresenter extends Model.CurrencyListCallback, Model.ConvertCurrencyCallback {
    }


}
