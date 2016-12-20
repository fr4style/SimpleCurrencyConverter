package com.fflorio.simplevalueconverter.network.models.requests;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class ConvertCurrencyParams {
    public final String currencyFrom;
    public final String currencyTo;
    public final double valueToConvert;

    private ConvertCurrencyParams(Builder builder) {
        currencyFrom = builder.currencyFrom;
        currencyTo = builder.currencyTo;
        valueToConvert = builder.valueToConvert;
    }

    public static final class Builder {
        private String currencyFrom;
        private String currencyTo;
        private double valueToConvert;
        public Builder() {}
        public Builder currencyFrom(String val) {
            currencyFrom = val;
            return this;
        }
        public Builder currencyTo(String val) {
            currencyTo = val;
            return this;
        }
        public Builder valueToConvert(double val) {
            valueToConvert = val;
            return this;
        }
        public ConvertCurrencyParams build() {return new ConvertCurrencyParams(this);}
    }
}
