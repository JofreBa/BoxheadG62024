package com.mygdx.game.Register;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Login.LoginActivity;
import com.mygdx.game.Screens.MyGdxGame;


public class RegisterScreen implements Screen {
    private MyGdxGame game;
    private Stage stage;
    private Skin skin;
    private TextField usernameField;
    private TextField passwordField;
    private TextField emailField;

    public RegisterScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.setPosition(50, Gdx.graphics.getHeight() - 100);
        backButton.setSize(200, 60);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.switchToScreen("Start");
            }
        });
        stage.addActor(backButton);

        // Create username field
        usernameField = new TextField("", skin);
        usernameField.setMessageText("Username");
        usernameField.setPosition(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 + 100);
        usernameField.setSize(300, 60);
        stage.addActor(usernameField);

        // Create password field
        passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setPosition(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2);
        passwordField.setSize(300, 60);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        stage.addActor(passwordField);

        // Create email field
        emailField = new TextField("", skin);
        emailField.setMessageText("Email");
        emailField.setPosition(Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 - 100);
        emailField.setSize(300, 60);
        stage.addActor(emailField);

        // Create register button
        TextButton registerButton = new TextButton("Register", skin);
        registerButton.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2 - 200);
        registerButton.setSize(200, 60);
        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();
                // Perform registration process
            }
        });
        stage.addActor(registerButton);
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
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
