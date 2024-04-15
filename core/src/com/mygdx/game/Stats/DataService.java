package com.mygdx.game.Stats;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET("/api/getstats")
    Call<List<StatsRequest>> getData();

    @POST("/api/collectStats")
    Call<String> collectStats(@Body StatsData statsData);
}


