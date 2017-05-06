package com.fflorio.simplevalueconverter.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fflorio.simplevalueconverter.R;
import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;
import com.fflorio.simplevalueconverter.network.models.responses.Currency;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;
import com.fflorio.simplevalueconverter.ui.CurrencySpinnerAdapter;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AbstractActivity implements Spinner.OnItemSelectedListener{

    @BindView(R.id.SpinnerCurrencyFrom) Spinner currencyFromSpinner;
    @BindView(R.id.SpinnerCurrencyTo) Spinner currencyToSpinner;
    @BindView(R.id.reverseBtn) View reverseButton;
    @BindView(R.id.convertBtn) View convertButton;
    @BindView(R.id.valueToConvert) EditText valueToConvert;
    @BindView(R.id.resultTxt) TextView resultTxt;

    private MainActivityPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);
        ButterKnife.bind(this);
        presenter = new MainActivityPresenter(this);
        presenter.restoreDefaultValueFrom(savedInstanceState);
        valueToConvert.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    convertValue();
                    return true;
                }
                return false;
            }
        });

        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                invertCurrencies();
            }
        });
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertValue();
            }
        });
        currencyFromSpinner.setAdapter(new CurrencySpinnerAdapter());
        currencyToSpinner.setAdapter(new CurrencySpinnerAdapter());
        currencyFromSpinner.setOnItemSelectedListener(this);
        currencyToSpinner.setOnItemSelectedListener(this);
        presenter.getCurrencyList();
    }

    private void convertValue(){
        String valueRaw = valueToConvert.getText().toString();
        double value = 0;
        try{ value = Double.parseDouble(valueRaw); }
        catch (Exception ignored){}
        presenter.convertCurrency(new ConvertCurrencyParams.Builder()
                .currencyFrom(getSelectedItemFrom(currencyFromSpinner))
                .currencyTo(getSelectedItemFrom(currencyToSpinner))
                .valueToConvert(value)
                .build());
    }

    private String getSelectedItemFrom(Spinner spinner){
        Currency item = (Currency) spinner.getSelectedItem();
        return item.code;
    }

    void updateAdapterWith(CurrencyList currencyList){
        ((CurrencySpinnerAdapter)currencyFromSpinner.getAdapter()).updateList(currencyList) ;
        ((CurrencySpinnerAdapter)currencyToSpinner.getAdapter()).updateList(currencyList) ;
        presetValues();
    }

    void updateAfterConversion(String outputCurrency, double convertedValue){
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(java.util.Currency.getInstance(outputCurrency));
        resultTxt.setText(format.format(convertedValue));
    }

    private void invertCurrencies(){
        int fromIndex = currencyFromSpinner.getSelectedItemPosition();
        int toIndex = currencyToSpinner.getSelectedItemPosition();

        currencyFromSpinner.setSelection(toIndex, true);
        currencyToSpinner.setSelection(fromIndex, true);
    }

    private void presetValues(){
        String fromCode = presenter.getDefaultFromValue();
        String toCode = presenter.getDefaultToValue();
        int fromIndex = ((CurrencySpinnerAdapter)currencyFromSpinner.getAdapter()).getPositionByCode(fromCode);
        int toIndex = ((CurrencySpinnerAdapter)currencyToSpinner.getAdapter()).getPositionByCode(toCode);

        if(fromIndex >0){currencyFromSpinner.setSelection(fromIndex, true); }
        if(toIndex >0){currencyToSpinner.setSelection(toIndex, true); }
    }

    @Override public void onNothingSelected(AdapterView<?> parent) { }
    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(!valueToConvert.getText().toString().isEmpty()){
            convertValue();
        }
    }

    @Override protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override protected void onSaveInstanceState(Bundle outState) {
        outState.putAll(presenter.saveCurrentValuesInBundle(((Currency)currencyFromSpinner.getSelectedItem()).code,
                                                            ((Currency)currencyToSpinner.getSelectedItem()).code));
        super.onSaveInstanceState(outState);
    }
}
