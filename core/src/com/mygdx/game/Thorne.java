package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Thorne extends Actor {
    private Texture textureFront, textureBack, textureRight, textureLeft, textureStatic;
    public Thorne(){
        // Cargar las texturas
        textureFront = new Texture(Gdx.files.internal("assets/Character/Thorne - Front.png"));
        textureBack = new Texture(Gdx.files.internal("assets/Character/Thorne - Back.png"));
        textureRight = new Texture(Gdx.files.internal("assets/Character/Thorne - Right.png"));
        textureLeft = new Texture(Gdx.files.internal("assets/Character/Thorne - Left.png"));
        textureStatic = new Texture(Gdx.files.internal("assets/Character/Thorne-Static.png"));
    }
    public Texture getTextureFront() {
        return textureFront;
    }

    public void setTextureFront(Texture textureFront) {
        this.textureFront = textureFront;
    }

    public Texture getTextureBack() {
        return textureBack;
    }

    public void setTextureBack(Texture textureBack) {
        this.textureBack = textureBack;
    }

    public Texture getTextureRight() {
        return textureRight;
    }

    public void setTextureRight(Texture textureRight) {
        this.textureRight = textureRight;
    }

    public Texture getTextureLeft() {
        return textureLeft;
    }

    public void setTextureLeft(Texture textureLeft) {
        this.textureLeft = textureLeft;
    }

    public Texture getTextureStatic() {
        return textureStatic;
    }

    public void setTextureStatic(Texture textureStatic) {
        this.textureStatic = textureStatic;
    }

}
