package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PausedScreen implements Screen {
    private MyGdxGame game;
    private final Stage stage;
    private final TextButton resumeButton, exitButton;

    public PausedScreen(MyGdxGame game){
        this.game = game;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        resumeButton = new TextButton("Resume", skin);
        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition(10, 1040);
        resumeButton.setPosition(1000, 250);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.addActor(resumeButton);
        stage.addActor(exitButton);
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
        //resumeButton.remove();
        //exitButton.remove();
    }
}
