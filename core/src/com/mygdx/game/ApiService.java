package com.mygdx.game;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

}

