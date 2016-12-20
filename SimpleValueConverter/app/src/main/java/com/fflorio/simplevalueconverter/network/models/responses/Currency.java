package com.fflorio.simplevalueconverter.network.models.responses;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * This Class was created for SimpleValueConverter on 20/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class Currency implements Comparable<Currency> {
    private final static Set<String> favorites = FavoriteCurrencyIds.getInstance();

    @SerializedName("id") public final String code;
    @SerializedName("currencyName") public final String name;
    @SerializedName("currencySymbol") public final String symbol;

    private Currency(Builder builder) {
        code = builder.code;
        name = builder.name;
        symbol = builder.symbol;
    }

    public boolean isFavorite(){ return favorites.contains(code); }

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

    @Override public int compareTo(Currency o) {
        if(o == null) return -1;
        if(isFavorite() && !o.isFavorite()) return -1;
        if(!isFavorite() && o.isFavorite()) return 1;
        if (name == null && o.name == null) return 0;
        if (name == null) return 1;
        if (o.name == null) return -1;
        return name.compareTo(o.name);
    }

    @Override public String toString() {
        return String.format("%s (%s)", name, code);
    }
}
