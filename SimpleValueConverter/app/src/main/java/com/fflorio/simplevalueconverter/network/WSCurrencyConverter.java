package com.fflorio.simplevalueconverter.network;

import com.fflorio.simplevalueconverter.network.helpers.WSCurrencyConverterHelper;
import com.fflorio.simplevalueconverter.network.models.requests.ConvertCurrencyParams;
import com.fflorio.simplevalueconverter.network.models.responses.ConvertCurrencyResponse;
import com.fflorio.simplevalueconverter.network.models.responses.CurrencyList;
import com.google.gson.JsonObject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public class WSCurrencyConverter extends WS {

    private WSCurrencyConverterDefinition wsCurrencyConverterDefinition;

    public static WSCurrencyConverter create(){
        final Retrofit retrofit = createDefaultRetrofitConfiguration();
        return new WSCurrencyConverter(retrofit.create(WSCurrencyConverterDefinition.class));
    }

    WSCurrencyConverter(WSCurrencyConverterDefinition wsCurrencyConverterDefinition){
        this.wsCurrencyConverterDefinition = wsCurrencyConverterDefinition;
    }

    //region API convertCurrency
    public Observable<ConvertCurrencyResponse> convertCurrency(ConvertCurrencyParams params){
        return wsCurrencyConverterDefinition.convertCurrency(String.format("%s_%s", params.currencyFrom,params.currencyTo), "ultra")
                                            .flatMap(processConvertCurrencyResponse(params));
    }

    private Func1<JsonObject, Observable<ConvertCurrencyResponse>> processConvertCurrencyResponse(final ConvertCurrencyParams params){
        return new Func1<JsonObject, Observable<ConvertCurrencyResponse>>(){
            @Override public Observable<ConvertCurrencyResponse> call(JsonObject jsonObject) {
                ConvertCurrencyResponse response =  new WSCurrencyConverterHelper().convertConvertCurrency(params, jsonObject);
                return Observable.from(new ConvertCurrencyResponse[]{response});
            }
        };
    }
    //endregion

    //region API listCurrency
    public Observable<CurrencyList> currencyList(){
        return wsCurrencyConverterDefinition.currencyList()
                                            .flatMap(processCurrencyListResponse());
    }

    private Func1<JsonObject, Observable<CurrencyList>> processCurrencyListResponse(){
        return new Func1<JsonObject, Observable<CurrencyList>>(){
            @Override public Observable<CurrencyList> call(JsonObject jsonObject) {
                CurrencyList response =  new WSCurrencyConverterHelper().convertCurrencyList(jsonObject);
                return Observable.from(new CurrencyList[]{response});
            }
        };
    }
    //endregion
}
