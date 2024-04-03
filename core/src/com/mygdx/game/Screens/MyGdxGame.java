package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Login.LoginScreen;
import com.mygdx.game.Register.RegisterScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public boolean Loged;
	@Override
	public void create () {
		setScreen(new StartScreen(this));
		batch = new SpriteBatch();
	}


	public void switchToScreen(String Screen) {
		switch (Screen) {
			case "Start":
				setScreen(new StartScreen(this));
				break;
			case "Login":
				setScreen(new LoginScreen(this));
				break;
			case "Register":
				setScreen(new RegisterScreen(this));
				break;
			case "Game":
				setScreen(new GameScreen(this));
				break;
			case "Shop":
				setScreen(new ShopScreen(this));
				break;
			default:
				break;
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
