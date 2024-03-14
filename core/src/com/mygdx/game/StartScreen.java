package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class StartScreen implements Screen {
    private MyGdxGame game = new MyGdxGame();
    private Texture background;
    private Stage stage;


    public StartScreen(MyGdxGame game) {
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //background = new Texture(Gdx.files.internal("fondo.jpg"));
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("Register", skin);

        Table table = new Table();
        table.top().right(); // Alinea la tabla arriba a la derecha
        table.add(loginButton).pad(10); // Agrega el botón de login con un espacio de 10 píxeles
        table.add(registerButton).pad(10); // Agrega el botón de registro con un espacio de 10 píxeles
        stage.addActor(table); // Agrega la tabla a la etapa (stage)

        loginButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Aquí puedes implementar la lógica para iniciar sesión
            }
        });

        registerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Aquí puedes implementar la lógica para el registro
            }
        });


        TextButton StartButton = new TextButton("Start", skin);
        StartButton.setPosition(10, 10);
        StartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        stage.addActor(StartButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
