package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends Game {
	@Override
	public void create () {
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
		//setScreen(new StartScreen(this));
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
