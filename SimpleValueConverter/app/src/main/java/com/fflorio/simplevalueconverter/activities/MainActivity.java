package com.fflorio.simplevalueconverter.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fflorio.simplevalueconverter.R;
import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this);
        presenter.getCurrencyList();
        presenter.convertCurrency(new ConvertCurrencyParams.Builder()
                                      .currencyFrom("EUR")
                                      .currencyTo("CAD")
                                      .valueToConvert(1)
                                      .build());
    }

    @Override protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
