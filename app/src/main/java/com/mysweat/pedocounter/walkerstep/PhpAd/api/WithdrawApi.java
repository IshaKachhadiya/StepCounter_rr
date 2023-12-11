package com.mysweat.pedocounter.walkerstep.PhpAd.api;


import com.mysweat.pedocounter.walkerstep.Database.WithdrawalModel;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WithdrawApi {

    @POST("api_storewithdrawal.php")
    Call<WithdrawalModel> getWithdrawal(@Query("username") String name, @Query("mobileno") String mobilenumber, @Query("coin") Integer coin);

}