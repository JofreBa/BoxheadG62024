package com.mygdx.game.Enemys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Goblins {
    private Character character; // referencia al personaje
    private List<Goblins> goblins;
    private Rectangle bounds;



    private int health = 30;
    public float speed = 2.5f;
    private Vector2 position;

    private float attackRange = 30; // rango de ataque del enemigo
    private float attackInterval = 1.5f; // intervalo de ataque del enemigo
    private float lastAttackTime = 0; // tiempo del último ataque
    private Sprite sprite;
    private Animation<TextureRegion> animationFront, animationBack, animationRight, animationLeft;
    private int directionX = 0;
    private int directionY = 0;


    Texture GoblinAtackFront, GoblinAtackBack, GoblinAtackRight, GoblinAtackLeft, GoblinStatic;

    public Goblins(int health, int speed, Vector2 position, Character character, List<Goblins> goblins) {
        this.health = health;
        this.speed = speed;
        this.position = position;
        this.character = character; // inicializa la referencia al personaje
        this.goblins = goblins;

        this.bounds = new Rectangle(position.x, position.y, 64, 64);

        GoblinAtackFront = new Texture(Gdx.files.internal("GoblinAtacFront.png"));
        GoblinAtackBack = new Texture(Gdx.files.internal("GoblinAtacBack.png"));
        GoblinAtackRight = new Texture(Gdx.files.internal("GoblinAtacRight.png"));
        GoblinAtackLeft = new Texture(Gdx.files.internal("GoblinAtacLeft.png"));
        GoblinStatic = new Texture(Gdx.files.internal("GoblinStatic.png"));

        TextureRegion[][] spritesFront = TextureRegion.split(GoblinAtackFront, 64, 52);
        TextureRegion[][] spritesBack = TextureRegion.split(GoblinAtackBack, 64, 52);
        TextureRegion[][] spritesRight = TextureRegion.split(GoblinAtackRight, 64, 52);
        TextureRegion[][] spritesLeft = TextureRegion.split(GoblinAtackLeft, 64, 52);

        sprite = new Sprite(GoblinStatic);
        animationFront = new Animation<>(0.25f, spritesFront[0]);
        animationBack = new Animation<>(0.25f, spritesBack[0]);
        animationRight = new Animation<>(0.25f, spritesRight[0]);
        animationLeft = new Animation<>(0.25f, spritesLeft[0]);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    /*public void attack() {
        character.takeDamage(2.5f);
    }*/

    public void update(float delta) {
      /*  // Calcula la dirección hacia el personaje
        Vector2 direction = new Vector2(character.x - position.x, character.y - position.y);
        direction.nor();
        Vector2 velocity = direction.scl(speed);

        Vector2 nextPosition = new Vector2(position.x + velocity.x, position.y + velocity.y);

        for (Goblins otherEnemy : goblins) {
            if (otherEnemy != this && otherEnemy.getBounds().contains(nextPosition)) {
                return; // Si chocará, no actualiza la posición
            }
        }

        // Si no chocará, actualiza la posición
        position.add(velocity.x, velocity.y);

        // Comprueba si el personaje está dentro del rango de ataque
        if (position.dst(character.x, character.y) <= attackRange) {
            // Comprueba si ha pasado suficiente tiempo desde el último ataque
            if (lastAttackTime >= attackInterval) {
                //attack();
                lastAttackTime = 0; // reinicia el contador del tiempo del último ataque
            } else {
                lastAttackTime += delta; // incrementa el contador del tiempo del último ataque
            }
        }

*/
    }


    public void draw(SpriteBatch batch, float deltaTime) {
        if (directionX > 0) {
            batch.draw(animationRight.getKeyFrame(deltaTime), position.x, position.y);
        } else if(directionX < 0){
            batch.draw(animationLeft.getKeyFrame(deltaTime), position.x, position.y);
        } else if (directionY > 0) {
            batch.draw(animationBack.getKeyFrame(deltaTime), position.x, position.y);
        } else if(directionY < 0){
            batch.draw(animationFront.getKeyFrame(deltaTime), position.x, position.y);
        } else {
            sprite.setPosition(position.x, position.y);
            sprite.draw(batch);
        }
    }


    public void takeDamage(float damage) {
        health -= damage;
    }
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
}
