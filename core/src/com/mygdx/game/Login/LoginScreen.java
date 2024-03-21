package com.mygdx.game.Login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Login.LoginActivity;
import com.mygdx.game.MyGdxGame;

public class LoginScreen implements Screen {
    private MyGdxGame game;
    private Stage stage;
    private Skin skin;
    private TextField usernameField;
    private TextField passwordField;

    public LoginScreen(MyGdxGame game){
        this.game = game;
    }
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextFieldStyle textFieldStyle = skin.get(TextFieldStyle.class);
        usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");
        usernameField.setPosition(100, 300);
        usernameField.setSize(200, 40);
        stage.addActor(usernameField);

        passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPosition(100, 250);
        passwordField.setSize(200, 40);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        stage.addActor(passwordField);

        TextButton startButton = new TextButton("Start", skin);
        startButton.setPosition(100,200);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                LoginActivity loginActivity = new LoginActivity(game);
                loginActivity.Login(username, password);
            }
        });

        stage.addActor(startButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {stage.dispose();
        skin.dispose();}
}

