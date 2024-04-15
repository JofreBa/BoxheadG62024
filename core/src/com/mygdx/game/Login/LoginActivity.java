package com.mygdx.game.Login;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Screens.MyGdxGame;
import com.mygdx.game.Stats.Stats;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity {
    private MyGdxGame game;
    private String URL = "http://10.2.2.83:3000/api/login/";
    //private String URL = "http://192.168.16.30:3000/api/login/";
    public LoginActivity(MyGdxGame game){
        this.game = game;
    }
    public void Login(String Username, String Password){
        ApiService service = RetrofitClient.getClient(URL);

        LoginRequest loginRequest = new LoginRequest(Username, Password);

        Call<LoginResponse> call = service.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null && loginResponse.getMessage().equals("Entra")) {
                    System.out.println(loginResponse.getMessage());
                    game.Loged = true;
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if(game.Loged){
                                System.out.println(game.Loged);
                                game.switchToScreen("Start");
                                Stats.GetStats();
                            }
                        }
                    }, 1);
                } else {
                    System.out.println("Error en la respuesta: Mensaje de error del servidor");
                    System.out.println("Contrasenya o Usuaris mal colocats, torna-ho a probar");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("Error en la conexión: " + t.getMessage());
                t.printStackTrace(); // Imprimir el seguimiento completo del error
            }
        });


    }
}

