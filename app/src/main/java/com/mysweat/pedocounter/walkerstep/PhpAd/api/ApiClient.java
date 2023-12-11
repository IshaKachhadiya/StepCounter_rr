package com.mysweat.pedocounter.walkerstep.PhpAd.api;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    static Gson gson = new GsonBuilder().setLenient().create();

    public static Retrofit getApiClient() {
        return new Retrofit.Builder().baseUrl("http://www.successoft.in/admin/").addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static WithdrawApi getApi() {
        return (WithdrawApi) ApiClient.getApiClient().create(WithdrawApi.class);
    }

}
