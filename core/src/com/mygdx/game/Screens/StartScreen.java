package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Characters.Guts;
import com.mygdx.game.Characters.Thorne;
import com.mygdx.game.Login.LoginScreen;

public class StartScreen extends ScreenAdapter {
    private MyGdxGame game;
    private Guts guts = new Guts();
    private Thorne thorne = new Thorne();
    private com.mygdx.game.Login.LoginScreen LoginScreen = new LoginScreen(game);
    private Texture textureStaticGuts, textureStaticThorne;
    private Image gutsImage, thorneImage;
    private final Stage stage;

    private long startTime;
    private boolean isRunning;


    public StartScreen(MyGdxGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //background = new Texture(Gdx.files.internal("fondo.jpg"));
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("Register", skin);
        TextButton startButton = new TextButton("Start", skin);
        TextButton singlePlayerButton = new TextButton("Single Player", skin);
        TextButton shopButton = new TextButton("Shop", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        textureStaticGuts = guts.getTextureStatic();
        textureStaticThorne = thorne.getTextureStatic();
        gutsImage = new Image(textureStaticGuts);
        thorneImage = new Image(textureStaticThorne);

        exitButton.setPosition(10, 1040);
        loginButton.setPosition(1850,1040);
        registerButton.setPosition(1750,1040);
        startButton.setPosition(1000, 250);
        singlePlayerButton.setPosition(970, 185);
        shopButton.setPosition(60, 1040);

        gutsImage.setPosition(920, 440);
        thorneImage.setPosition(750, 500);

        float scale_factor = 3.0f;
        gutsImage.setScale(scale_factor, scale_factor);
        thorneImage.setScale(scale_factor - 0.5f,scale_factor - 0.5f);

        if(!game.Loged){
            System.out.println(game.Loged);
            stage.addActor(loginButton);
            stage.addActor(registerButton);
        }

        stage.addActor(startButton);
        stage.addActor(singlePlayerButton);
        stage.addActor(shopButton);
        stage.addActor(gutsImage);
        stage.addActor(thorneImage);
        stage.addActor(exitButton);

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.switchToScreen("Login");
            }
        });

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.switchToScreen("Register");
            }
        });

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(game.Loged) {
                    game.switchToScreen("Game");
                } else {
                    System.out.println("You need to login first!");
                }
            }
        });
        singlePlayerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                    game.switchToScreen("Game");

            }
        });

        shopButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.switchToScreen("Shop");
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isRunning) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            // Calcula minutos y segundos
            int minutes = (int) (elapsedTime / 1000 / 60);
            int seconds = (int) ((elapsedTime / 1000) % 60);
            // Actualiza la UI o realiza cualquier acción necesaria con el tiempo transcurrido
            System.out.println("Tiempo transcurrido: " + minutes + " minutos " + seconds + " segundos");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            gutsImage.setPosition(920, 440);
            gutsImage.setPosition(gutsImage.getX() + 150, gutsImage.getY() + 20);
            thorneImage.setPosition(750, 500);
            thorneImage.setPosition(thorneImage.getX() + 180, thorneImage.getY() - 20);
            GameScreen.SelectedCharacter = "Thorne";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            gutsImage.setPosition(920, 440);
            gutsImage.setPosition(gutsImage.getX() - 150, gutsImage.getY() + 20);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            gutsImage.setPosition(920, 440);
            thorneImage.setPosition(750, 500);
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        super.show();

        // Inicia el cronómetro
        startTime = System.currentTimeMillis();
        isRunning = true;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {
        stage.clear();
        stage.dispose();
    }

}
