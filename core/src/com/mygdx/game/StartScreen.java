package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Login.LoginScreen;

public class StartScreen implements Screen {
    private MyGdxGame game;
    private Guts guts = new Guts();
    private Thorne thorne = new Thorne();
    private com.mygdx.game.Login.LoginScreen LoginScreen = new LoginScreen(game);
    private Texture textureStaticGuts, textureStaticThorne;
    private Image gutsImage, thorneImage;
    private Stage stage;


    public StartScreen(MyGdxGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //background = new Texture(Gdx.files.internal("fondo.jpg"));
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("Register", skin);
        TextButton startButton = new TextButton("Start", skin);

        textureStaticGuts = guts.getTextureStatic();
        textureStaticThorne = thorne.getTextureStatic();
        gutsImage = new Image(textureStaticGuts);
        thorneImage = new Image(textureStaticThorne);

        loginButton.setPosition(500,440);
        registerButton.setPosition(560,440);
        startButton.setPosition(300, 85);

        gutsImage.setPosition(220, 150);
        thorneImage.setPosition(65, 200);

        float scale_factor = 3.0f;
        gutsImage.setScale(scale_factor, scale_factor);
        thorneImage.setScale(scale_factor - 0.5f,scale_factor - 0.5f);

        stage.addActor(loginButton);
        stage.addActor(registerButton);
        stage.addActor(startButton);
        stage.addActor(gutsImage);
        stage.addActor(thorneImage);

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
                game.switchToScreen("Game");
            }
        });
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        boolean selected;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            gutsImage.setPosition(350, 165);
            thorneImage.setPosition(240, 150);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            gutsImage.setPosition(100, 165);

        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            gutsImage.setPosition(220, 140);
            thorneImage.setPosition(65, 200);
        }

        stage.act(delta);
        stage.draw();


    }

    @Override
    public void show() {

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
