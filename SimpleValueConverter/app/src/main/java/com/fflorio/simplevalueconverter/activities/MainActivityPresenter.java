package com.fflorio.simplevalueconverter.activities;

import android.util.Log;

import com.fflorio.simplevalueconverter.R;
import com.fflorio.simplevalueconverter.network.WSCurrencyConverter;
import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;
import com.fflorio.simplevalueconverter.network.models.responses.ConvertCurrencyResponse;
import com.fflorio.simplevalueconverter.network.models.responses.Currency;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;
import com.fflorio.simplevalueconverter.ui.Presenter;
import com.fflorio.simplevalueconverter.ui.ShowProgressDialogHelper;
import com.fflorio.simplevalueconverter.utils.MLog;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class MainActivityPresenter extends Presenter{

    CompositeSubscription compositeSubscriptions = new CompositeSubscription();
    ShowProgressDialogHelper showProgressDialogHelper;
    MainActivity activity;
    WSCurrencyConverter wsCurrencyConverter;

    MainActivityPresenter(MainActivity activity){
        this.activity = activity;
        wsCurrencyConverter = WSCurrencyConverter.create();
        showProgressDialogHelper = new ShowProgressDialogHelper(activity, 0);
    }

    public void convertCurrency(ConvertCurrencyParams params){
        showProgressDialogHelper.showDialog(activity.getString(R.string.msg__conversione_in_corso));
        compositeSubscriptions.add(
                wsCurrencyConverter.convertCurrency(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConvertCurrencyResponse>() {
                    @Override public void onCompleted() {}
                    @Override public void onError(Throwable e) {
                        MLog.e(e);
                        showProgressDialogHelper.hideDialog();
                    }
                    @Override public void onNext(ConvertCurrencyResponse convertCurrencyResponse) {
                        showProgressDialogHelper.hideDialog();
                        MLog.d("Il valore "+convertCurrencyResponse.originalRequest.valueToConvert+
                                     " "+convertCurrencyResponse.originalRequest.currencyFrom+
                                     " corrisponde a "+convertCurrencyResponse.convertedValue+
                                     " "+convertCurrencyResponse.originalRequest.currencyTo);
                    }
                }));
    }

    public void getCurrencyList(){
        showProgressDialogHelper.showDialog(activity.getString(R.string.msg__download_valute));
        compositeSubscriptions.add(
                wsCurrencyConverter.currencyList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<CurrencyList>() {
                            @Override public void onCompleted() {}
                            @Override public void onError(Throwable e) {
                                MLog.e(e);
                                showProgressDialogHelper.hideDialog();
                            }
                            @Override public void onNext(CurrencyList currencyList) {
                                showProgressDialogHelper.hideDialog();
                                for(Currency c : currencyList){ MLog.d(c);}
                            }
                        }));
    }

    @Override public void unbind() {
        compositeSubscriptions.unsubscribe();
        showProgressDialogHelper.unbind();
        activity = null;
    }
}
