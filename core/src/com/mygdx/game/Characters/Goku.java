package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Goku {
    private Texture textureFront, textureBack, textureRight, textureLeft, textureStatic;
    public Goku(){
        // Cargar las texturas
        //textureFront = new Texture(Gdx.files.internal("assets/Character/Guts-Sprites-Front.png"));
        //textureBack = new Texture(Gdx.files.internal("assets/Character/Guts-Sprites-Back.png"));
        //textureRight = new Texture(Gdx.files.internal("assets/Character/Guts-Sprites-Dreta.png"));
        //textureLeft = new Texture(Gdx.files.internal("assets/Character/Guts-Sprites-Esquerra.png"));
        textureStatic = new Texture(Gdx.files.internal("assets/Character/Goku Sprites 64x43.png"));
    }

    public Texture getTextureStatic() {
        return textureStatic;
    }

    public void setTextureStatic(Texture textureStatic) {
        this.textureStatic = textureStatic;
    }
}
