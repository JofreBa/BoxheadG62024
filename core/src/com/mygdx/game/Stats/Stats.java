package com.mygdx.game.Stats;

import com.mygdx.game.Login.ApiService;
import com.mygdx.game.Login.LoginResponse;
import com.mygdx.game.Login.RetrofitClient;
import com.mygdx.game.Screens.MyGdxGame;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Stats {
    private MyGdxGame game;

    private Boolean Thorne_Sword;
    private static String URL = "http://192.168.16.30:3000/api/login/";

    public Stats(MyGdxGame game){
        this.game = game;
    }

    public static void GetStats(){
        // Crear una instancia de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear una instancia del servicio DataService
        DataService service = retrofit.create(DataService.class);

        // Realizar la llamada para obtener datos
        Call<List<StatsRequest>> call = service.getData();
        call.enqueue(new Callback<List<StatsRequest>>() {
            @Override
            public void onResponse(Call<List<StatsRequest>> call, Response<List<StatsRequest>> response) {
                if (response.isSuccessful()) {
                    List<StatsRequest> statsRequests = response.body();
                    System.out.println("funciona");
                } else {
                    System.out.println("Error en la respuesta: Mensaje de error del servidor");
                    System.out.println("Contrasenya o Usuaris mal colocats, torna-ho a probar");
                }
            }

            @Override
            public void onFailure(Call<List<StatsRequest>> call, Throwable t) {
                System.out.println("Error en la conexion: " + t.getMessage());
            }
        });
    }
}
