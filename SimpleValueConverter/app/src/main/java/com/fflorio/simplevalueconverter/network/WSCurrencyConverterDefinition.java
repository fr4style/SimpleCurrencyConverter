package com.fflorio.simplevalueconverter.network;

import com.google.gson.JsonObject;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public interface WSCurrencyConverterDefinition {

    @GET("convert")
    Observable<JsonObject> convertCurrency(@Query("q") String query,
                                           @Query("compact") String compact);

    @GET("currencies")
    Observable<JsonObject> currencyList();
}
