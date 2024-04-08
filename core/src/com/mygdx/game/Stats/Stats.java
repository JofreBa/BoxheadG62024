package com.mygdx.game.Stats;

import com.mygdx.game.Login.ApiService;
import com.mygdx.game.Login.LoginResponse;
import com.mygdx.game.Login.RetrofitClient;
import com.mygdx.game.Screens.MyGdxGame;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Stats {
    private MyGdxGame game;
    private int points, coins;
    private Boolean Thorne_Sword;
    private String URL = "http://192.168.18.213:3000/api/login/";

    public Stats(MyGdxGame game, int points, int coins){
        this.points = points;
        this.coins = coins;
        this.game = game;
    }

    public void GetStats(String[] Skins, int Score, int Coins){
        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia del servicio DataService
        DataService service = retrofit.create(DataService.class);

        // Realizar la llamada para obtener datos
        Call<StatsResponse> call = service.getData();
        call.enqueue(new Callback<StatsResponse>() {
            @Override
            public void onResponse(Call<StatsResponse> call, Response<StatsResponse> response) {
                if (response.isSuccessful()) {
                    StatsResponse statsResponse = response.body();
                    System.out.println("funciona");
                } else {
                    System.out.println("Error en la respuesta: Mensaje de error del servidor");
                    System.out.println("Contrasenya o Usuaris mal colocats, torna-ho a probar");
                }
            }

            @Override
            public void onFailure(Call<StatsResponse> call, Throwable t) {
                System.out.println("Error en la conexion: " + t.getMessage());
            }
        });
    }
}
