package com.fflorio.simplevalueconverter.converter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fflorio.simplevalueconverter.R;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;

import butterknife.BindView;

/**
 * Created by francesco on 2017-05-06.
 */

public class ConverterActivty extends AppCompatActivity implements ConverterMVP.View {

    @BindView(R.id.SpinnerCurrencyFrom)
    Spinner currencyFromSpinner;
    @BindView(R.id.SpinnerCurrencyTo)
    Spinner currencyToSpinner;
    @BindView(R.id.reverseBtn)
    View reverseButton;
    @BindView(R.id.convertBtn)
    View convertButton;
    @BindView(R.id.valueToConvert)
    EditText valueToConvert;
    @BindView(R.id.resultTxt)
    TextView resultTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoadingView(@StringRes int messageId) {
        
    }

    @Override
    public void hideLoadingView() {

    }

    @Override
    public void showWarningDialog(@StringRes int messageResId) {

    }

    @Override
    public void showConversion(String currency, double value) {

    }

    @Override
    public void updateCurrencyLists(CurrencyList currencyList) {

    }
}
