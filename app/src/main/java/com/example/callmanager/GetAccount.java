package com.example.callmanager;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetAccount {
    @GET("/account/user/{phone_number}/phone")
    Call<Account> getAccount(@Path("phone_number") String phone_number);

}
