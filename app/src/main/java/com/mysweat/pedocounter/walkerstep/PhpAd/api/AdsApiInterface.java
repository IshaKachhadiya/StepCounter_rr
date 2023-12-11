package com.mysweat.pedocounter.walkerstep.PhpAd.api;

import com.mysweat.pedocounter.walkerstep.Database.WithdrawalModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AdsApiInterface {
    @FormUrlEncoded
    @POST("com.mysweat.pedocounter.walkerstep.php")
    Call<Object> getid(@Field("arg1") String str, @Field("arg2") String type);

}
