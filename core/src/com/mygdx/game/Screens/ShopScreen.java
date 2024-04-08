package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Stats.Stats;

public class ShopScreen implements Screen {
    private MyGdxGame game;
    public Stats stats;
    private Texture textureThorneSword;
    private Image thorneSwordImage;
    private Stage stage;
    private int price= 18;

    public ShopScreen(MyGdxGame game){
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        int Usercoins = stats.getCoins();

        TextButton back = new TextButton("Back", skin);
        back.setPosition(580 ,440);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.switchToScreen("Start");
            }
        });

        textureThorneSword = new Texture(Gdx.files.internal("assets/Character/Thorne_sword.png"));
        thorneSwordImage = new Image(textureThorneSword);
        thorneSwordImage.setPosition(100, 500);


        TextButton buyButtonthorneSword = new TextButton("Buy", skin);

        buyButtonthorneSword.setPosition(85, 250);

        buyButtonthorneSword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Usercoins >= price){
                    stats.setCoins(Usercoins - price);
                    stats.setThorne_Sword(true);
                    System.out.println("Comprado");
                } else {
                    System.out.println("Not enough money");
                }
            }
        });



        float scale_factor = 2f;
        thorneSwordImage.setScale(scale_factor ,scale_factor);

        stage.addActor(thorneSwordImage);
        stage.addActor(buyButtonthorneSword);
        stage.addActor(back);
    }
    @Override
    public void show() {

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

    }
}
