package com.mygdx.game.Stats;

import com.mygdx.game.Login.ApiService;
import com.sun.tools.sjavac.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatsCollector {
    private static final String BASE_URL = "http://10.2.2.83:3000/api/collectStats/";

    public static void collectStats(String id, int score, String time, String character, List<String> skins, int games) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService dataService = retrofit.create(DataService.class);

        StatsData statsData = new StatsData(id, score, time, character, skins, games);

        Call<String> call = dataService.collectStats(statsData);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String message = response.body();
                    System.out.println(message);
                } else {
                    // Manejar la respuesta de error del servidor
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("StatsCollector: Error en la conexión: " + t.getMessage());
            }
        });
    }
}

