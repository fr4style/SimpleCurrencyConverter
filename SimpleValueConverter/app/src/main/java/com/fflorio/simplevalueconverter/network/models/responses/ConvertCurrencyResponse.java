package com.fflorio.simplevalueconverter.network.models.responses;

import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class ConvertCurrencyResponse {
    public final ConvertCurrencyParams originalRequest;
    public final double convertedValue;

    private ConvertCurrencyResponse(Builder builder) {
        originalRequest = builder.originalRequest;
        convertedValue = builder.convertedValue;
    }

    public static final class Builder {
        private ConvertCurrencyParams originalRequest;
        private double convertedValue;
        public Builder() {}
        public Builder originalRequest(ConvertCurrencyParams val) {
            originalRequest = val;
            return this;
        }
        public Builder convertedValue(double val) {
            convertedValue = val;
            return this;
        }
        public ConvertCurrencyResponse build() {return new ConvertCurrencyResponse(this);}
    }
}
