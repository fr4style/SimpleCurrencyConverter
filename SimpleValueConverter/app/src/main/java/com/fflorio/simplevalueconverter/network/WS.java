package com.fflorio.simplevalueconverter.network;

import com.fflorio.simplevalueconverter.BuildConfig;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This Class was created for SimpleValueConverter on 19/12/16
 * Designed and developed by Francesco Florio
 * All Right Reserved.
 */
public abstract class WS {

    protected static Retrofit createRetrofit(OkHttpClient okHttpClient, Executor executor, String baseUrl){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .callbackExecutor(executor)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(createGsonConverterFactory())
                .build();
        return retrofit;
    }

    protected static Executor createExecutor(){
        return Executors.newCachedThreadPool();
    }

    protected static Interceptor createInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public static GsonBuilder getGsonBuilder(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder;
    }

    protected static GsonConverterFactory createGsonConverterFactory(){
        return GsonConverterFactory.create(getGsonBuilder().create());
    }

    protected static OkHttpClient createOkHttpClient(Interceptor interceptor){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(WSConfig.CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(WSConfig.READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        if(BuildConfig.DEBUG){ builder.addInterceptor(interceptor); }

        return builder.build();
    }

    protected static Retrofit createDefaultRetrofitConfiguration(){
        final Interceptor interceptor = createInterceptor();
        final OkHttpClient okHttpClient = createOkHttpClient(interceptor);
        final Executor executor = createExecutor();
        return createRetrofit(okHttpClient, executor, WSConfig.BASE_URL);
    }
}
