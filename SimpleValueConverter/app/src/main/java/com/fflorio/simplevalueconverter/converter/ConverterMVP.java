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
    }

    interface Model {

        interface CurrencyListCallback {
            void onCurrencyListReady(final CurrencyList currencyList);

            void onCurrencyListError(Throwable throwable);
        }

        interface ConvertCurrencyCallback {
            void onConvertCurrency(final String currency, final double value);

            void onConvertCurrencyError(Throwable throwable);
        }

        interface DefaultValuesCallback {
            void onDefaultLoaded(final String fromCurrencyCode, final String toCurrencyCode);
        }

        void convertCurrency(final ConvertCurrencyParams params, Model.ConvertCurrencyCallback callback);

        void getCurrencyList(final Model.CurrencyListCallback callback);

        void loadDefaultValues(final DefaultValuesCallback callback);
    }

    interface ViewPresenter {
        void getCurrencyList();

        void convertValue(ConvertCurrencyParams convertCurrencyParams);

        void saveCurrentValueIntoBundle(final String fromCurrencyCode, final String toCurrencyCode);

        void restoreDefaultValue(final Bundle savedBundle);
    }

    interface ModelPresenter extends Model.CurrencyListCallback, Model.ConvertCurrencyCallback,
            Model.DefaultValuesCallback {
    }


}
