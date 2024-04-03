package com.mygdx.game.Screens;

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
import com.mygdx.game.Characters.Guts;
import com.mygdx.game.Characters.Thorne;
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
        TextButton singlePlayerButton = new TextButton("Single Player", skin);
        TextButton shopButton = new TextButton("Shop", skin);

        textureStaticGuts = guts.getTextureStatic();
        textureStaticThorne = thorne.getTextureStatic();
        gutsImage = new Image(textureStaticGuts);
        thorneImage = new Image(textureStaticThorne);

        loginButton.setPosition(500,440);
        registerButton.setPosition(560,440);
        startButton.setPosition(300, 85);
        singlePlayerButton.setPosition(350, 85);
        shopButton.setPosition(10, 440);

        gutsImage.setPosition(220, 140);
        thorneImage.setPosition(65, 200);

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
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            gutsImage.setPosition(220, 140);
            gutsImage.setPosition(gutsImage.getX() + 150, gutsImage.getY() + 20);
            thorneImage.setPosition(65, 200);
            thorneImage.setPosition(thorneImage.getX() + 180, thorneImage.getY() - 20);
            GameScreen.SelectedCharacter = "Thorne";
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            gutsImage.setPosition(220, 140);
            gutsImage.setPosition(gutsImage.getX() - 150, gutsImage.getY() + 20);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
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
