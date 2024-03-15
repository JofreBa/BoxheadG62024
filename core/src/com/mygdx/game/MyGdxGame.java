package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import jdk.javadoc.internal.tool.Start;

public class MyGdxGame extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		setScreen(new GameScreen(this));
		batch = new SpriteBatch();
	}

	public void switchToScreen(String Screen){
		switch (Screen){
			case "Start": setScreen(new StartScreen(this));
			break;
			case "Login": setScreen(new LoginScreen(this));
			break;
			case "Game": setScreen(new GameScreen(this));
		}
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
