package com.fflorio.simplevalueconverter.network.models.responses;

import com.google.gson.annotations.SerializedName;

/**
 * This Class was created for SimpleValueConverter on 20/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class Currency {

    @SerializedName("id") public final String code;
    @SerializedName("currencyName") public final String name;
    @SerializedName("currencySymbol") public final String symbol;

    private Currency(Builder builder) {
        code = builder.code;
        name = builder.name;
        symbol = builder.symbol;
    }

    public static final class Builder {
        private String code;
        private String name;
        private String symbol;

        public Builder() {}

        public Builder code(String val) {
            code = val;
            return this;
        }
        public Builder name(String val) {
            name = val;
            return this;
        }
        public Builder symbol(String val) {
            symbol = val;
            return this;
        }
        public Currency build() {return new Currency(this);}
    }

    @Override public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
