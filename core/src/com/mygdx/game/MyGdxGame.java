package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MyGdxGame extends Game {
	@Override
	public void create () {
		setScreen(new StartScreen(this));
	}

	public void switchToScreen(int Screen){
		switch (Screen){
			case 0: setScreen(new StartScreen(this));
			break;
			case 1: setScreen(new LoginScreen(this));
			break;
			case 2: setScreen(new GameScreen(this));
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
