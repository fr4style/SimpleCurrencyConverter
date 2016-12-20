package com.fflorio.simplevalueconverter.network.models.responses;

import java.util.HashSet;

/**
 * This Class was created for SimpleValueConverter on 20/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class FavoriteCurrencyIds extends HashSet<String> {
    private static FavoriteCurrencyIds instance;
    public static FavoriteCurrencyIds getInstance(){
        if(instance == null){ instance = new FavoriteCurrencyIds(); }
        return instance;
    }

    private FavoriteCurrencyIds(){
        add("USD");
        add("EUR");
        add("CAD");
        add("GBP");
    }
}
