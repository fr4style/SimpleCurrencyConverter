package com.fflorio.simplevalueconverter.network.helpers;

import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;
import com.fflorio.simplevalueconverter.network.models.responses.ConvertCurrencyResponse;
import com.fflorio.simplevalueconverter.network.models.responses.Currency;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;
import com.fflorio.simplevalueconverter.utils.MLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class WSCurrencyConverterHelper {

    public WSCurrencyConverterHelper(){}

    public ConvertCurrencyResponse convertConvertCurrency(ConvertCurrencyParams request, JsonObject rawResponse){
        double conversionUnit = getConversionUnitFromResponse(rawResponse);
        double value = conversionUnit * request.valueToConvert;
        return new ConvertCurrencyResponse.Builder()
                .originalRequest(request)
                .convertedValue(value)
                .build();
    }

    private double getConversionUnitFromResponse(JsonObject rawResponse){
        if(rawResponse == null) { return 0; }
        Set<Map.Entry<String, JsonElement>> keyset = rawResponse.entrySet();
        if(keyset != null){
            for (Map.Entry<String, JsonElement> entry: keyset) {
                MLog.d(" ## Key found: "+entry.getKey());
                try{ return  entry.getValue().getAsDouble(); }
                catch (Exception ignored){}
            }
        }
        return 0;
    }

    public CurrencyList convertCurrencyList(JsonObject rawResponse){
        CurrencyList currencies = new CurrencyList();
        Gson gson = new GsonBuilder().create();
        JsonObject root = rawResponse.getAsJsonObject("results");
        if(root != null){
            Set<Map.Entry<String, JsonElement>> keyset = root.entrySet();
            if(keyset != null) {
                for (Map.Entry<String, JsonElement> entry : keyset) {
                    try { currencies.add(gson.fromJson(entry.getValue(), Currency.class));}
                    catch (Exception ignored){}
                }
            }
        }

        Collections.sort(currencies);
        return currencies;

    }

}
