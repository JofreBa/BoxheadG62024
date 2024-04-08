package com.mygdx.game.Stats;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("/api/data")
    Call<StatsResponse> getData();

    //@POST("update")
    //Call<UpdateResponse> updateData(@Body UpdateRequest request);
}
