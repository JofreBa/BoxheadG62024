package com.mygdx.game;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity {
    public static void Login(MyGdxGame game){
        ApiService service = RetrofitClient.getClient("http://192.168.19.107:3000/login");

        LoginRequest loginRequest = new LoginRequest("usuario", "contraseña");

        Call<String> call = service.login(loginRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String result = response.body();
                    game.switchToScreen(1);
                } else {
                    // Manejar errores de respuesta
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Manejar errores de conexión
            }
        });
    }
}

