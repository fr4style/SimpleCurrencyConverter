package com.fflorio.simplevalueconverter.converter;

import com.fflorio.simplevalueconverter.network.WSCurrencyConverter;
import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;
import com.fflorio.simplevalueconverter.network.models.responses.ConvertCurrencyResponse;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;
import com.fflorio.simplevalueconverter.utils.MLog;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by francesco on 2017-05-07.
 */

public class ConverterModel implements ConverterMVP.Model {

    private String defaultFromValue = "CAD";
    private String defaultToValue = "EUR";

    private CompositeSubscription compositeSubscriptions = new CompositeSubscription();
    private WSCurrencyConverter wsCurrencyConverter;

    public ConverterModel() {
        wsCurrencyConverter = WSCurrencyConverter.create();
    }

    @Override
    public void convertCurrency(final ConvertCurrencyParams params,
                                final ConvertCurrencyCallback callback) {
        compositeSubscriptions.add(
                wsCurrencyConverter.convertCurrency(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ConvertCurrencyResponse>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                MLog.e(e);
                                callback.onConvertCurrencyError(e);
                            }

                            @Override
                            public void onNext(ConvertCurrencyResponse convertCurrencyResponse) {
                                callback.onConvertCurrency(
                                        convertCurrencyResponse.originalRequest.currencyTo,
                                        convertCurrencyResponse.convertedValue);
                            }
                        }));
    }

    @Override
    public void getCurrencyList(final CurrencyListCallback callback) {
        compositeSubscriptions.add(
                wsCurrencyConverter.currencyList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CurrencyList>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                MLog.e(e);
                                callback.onCurrencyListError(e);
                            }

                            @Override
                            public void onNext(CurrencyList currencyList) {
                                callback.onCurrencyListReady(currencyList, defaultFromValue, defaultToValue);
                            }
                        }));
    }

    @Override
    public void restoreSavedState(String fromValue, String toValue) {
        if (fromValue != null) {
            this.defaultFromValue = fromValue;
        }
        if (toValue != null) {
            this.defaultToValue = toValue;
        }
    }

    @Override
    public void release() {
        compositeSubscriptions.unsubscribe();
    }
}
