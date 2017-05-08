package com.fflorio.simplevalueconverter.converter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fflorio.simplevalueconverter.R;
import com.fflorio.simplevalueconverter.activities.AbstractActivity;
import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;
import com.fflorio.simplevalueconverter.network.models.responses.Currency;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;
import com.fflorio.simplevalueconverter.ui.CurrencySpinnerAdapter;
import com.fflorio.simplevalueconverter.ui.ShowProgressDialogHelper;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by francesco on 2017-05-06.
 */

public class ConverterActivty extends AbstractActivity implements ConverterMVP.View {

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

    private ShowProgressDialogHelper showProgressDialogHelper;
    private SpinnerItemSelectedListener spinnerItemSelectedListener;
    private ConverterMVP.ViewPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main);
        ButterKnife.bind(this);
        presenter = new ConverterPresenter(this);
        showProgressDialogHelper = new ShowProgressDialogHelper(this);
        spinnerItemSelectedListener = new SpinnerItemSelectedListener();
        reverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invertCurrencies();
            }
        });
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertValue();
            }
        });
        valueToConvert.setOnEditorActionListener(new OnActionPressedListener());
        currencyFromSpinner.setAdapter(new CurrencySpinnerAdapter());
        currencyToSpinner.setAdapter(new CurrencySpinnerAdapter());
        presenter.restoreDefaultValues(savedInstanceState);
        presenter.getCurrencyList();
    }

    @Override
    public void showLoadingView(@StringRes int messageId) {
        showProgressDialogHelper.showDialog(getString(messageId));
    }

    @Override
    public void hideLoadingView() {
        showProgressDialogHelper.hideDialog();
    }

    @Override
    public void showWarningDialog(@StringRes int messageResId) {
        showMessageDialog(R.string.warning, messageResId);
    }

    @Override
    public void showConversion(String currency, double value) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        format.setCurrency(java.util.Currency.getInstance(currency));
        resultTxt.setText(format.format(value));
    }

    @Override
    public void updateCurrencyLists(CurrencyList currencyList) {
        ((CurrencySpinnerAdapter) currencyFromSpinner.getAdapter()).updateList(currencyList);
        ((CurrencySpinnerAdapter) currencyToSpinner.getAdapter()).updateList(currencyList);
        currencyFromSpinner.setOnItemSelectedListener(spinnerItemSelectedListener);
        currencyToSpinner.setOnItemSelectedListener(spinnerItemSelectedListener);
    }

    @Override
    public void updateSpinners(String fromValue, String toValue) {
        int fromIndex = ((CurrencySpinnerAdapter) currencyFromSpinner.getAdapter()).getPositionByCode(fromValue);
        int toIndex = ((CurrencySpinnerAdapter) currencyToSpinner.getAdapter()).getPositionByCode(toValue);

        if (fromIndex > 0) {
            currencyFromSpinner.setSelection(fromIndex, true);
        }
        if (toIndex > 0) {
            currencyToSpinner.setSelection(toIndex, true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        final Bundle valuesToSave = presenter.saveCurrentValueIntoBundle(
                ((Currency) currencyFromSpinner.getSelectedItem()).code,
                ((Currency) currencyToSpinner.getSelectedItem()).code);
        outState.putAll(valuesToSave);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showProgressDialogHelper.unbind();
        presenter.unbind();
    }

    //UI Actions
    private void invertCurrencies() {
        int fromIndex = currencyFromSpinner.getSelectedItemPosition();
        int toIndex = currencyToSpinner.getSelectedItemPosition();

        currencyFromSpinner.setSelection(toIndex, true);
        currencyToSpinner.setSelection(fromIndex, true);
    }

    private void convertValue() {
        String valueRaw = valueToConvert.getText().toString();
        double value = 0;
        try {
            value = Double.parseDouble(valueRaw);
            final ConvertCurrencyParams params = new ConvertCurrencyParams.Builder()
                    .currencyFrom(getSelectedItemFrom(currencyFromSpinner))
                    .currencyTo(getSelectedItemFrom(currencyToSpinner))
                    .valueToConvert(value)
                    .build();
            presenter.convertValue(params);
        } catch (Exception ignored) {
            showWarningDialog(R.string.error__invalid_value);
        }
    }

    private String getSelectedItemFrom(Spinner spinner) {
        Currency item = (Currency) spinner.getSelectedItem();
        return item.code;
    }

    private class SpinnerItemSelectedListener implements Spinner.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (!valueToConvert.getText().toString().isEmpty()) {
                convertValue();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    private class OnActionPressedListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                convertValue();
                return true;
            }
            return false;
        }
    }
}
