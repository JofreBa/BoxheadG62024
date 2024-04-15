package com.mygdx.game.Screens;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Stats.StatsCollector;
import com.mygdx.game.Stats.StatsData;

import java.util.List;

public class DeadScreen extends ScreenAdapter {
    private MyGdxGame game;
    private Stage stage;
    int score = 340, games = 5;
    String time = "00:02:34", character = "Guts", id = "1";
    List<String> skins = null;
    public DeadScreen(MyGdxGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage();

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton playAgainButton = new TextButton("Play Again", skin);
        playAgainButton.setPosition(1000, 500);
        playAgainButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.setPosition(1000, 380);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                StatsCollector.collectStats(id, score, time, character, skins, games);
            }
        });

        StatsData statsData = new StatsData(id, score, time, character,skins, games);


        stage.addActor(playAgainButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
    }
}

