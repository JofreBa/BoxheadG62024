package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity {
    private MyGdxGame game;
    public LoginActivity(MyGdxGame game){
        this.game = game;
    }
    public static void Login(String Username, String Password){
        ApiService service = RetrofitClient.getClient("http://192.168.19.107:3000/api/login/");

        LoginRequest loginRequest = new LoginRequest(Username, Password);

        Call<LoginResponse> call = service.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse result = response.body();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {

                        }
                    }, 2);
                } else {
                    System.out.println("Error en la respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("Error en la conexion: " + t.getMessage());
            }
        });
    }
}

