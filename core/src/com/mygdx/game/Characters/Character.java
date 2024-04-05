package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemys.Goblins;

import java.util.List;

public class Character {
    // Posición del personaje
    public float x, y;
    // Tamaño de la hitbox
    public float width, height;
    // Velocidad de movimiento
    public float speed = 8.0f;
    public Animation<TextureRegion> animationFront, animationBack, animationRight, animationLeft;
    public Sprite idleSprite;
    public Object currentDisplay;
    public float animationTime = 0;

    public Direction direction = Direction.down;

    enum Direction {
        left, right, up, down
    }

    private int health = 100;
    public int attackDamage = 10;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Character(float x, float y, float width, float height, Animation<TextureRegion> animationFront, Animation<TextureRegion> animationBack, Animation<TextureRegion> animationRight, Animation<TextureRegion> animationLeft, Sprite idleSprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.animationFront = animationFront;
        this.animationBack = animationBack;
        this.animationRight = animationRight;
        this.animationLeft = animationLeft;
        this.idleSprite = idleSprite;
        this.currentDisplay = idleSprite;
    }

    // Método para mover el personaje
    public void move(float dx, float dy) {
        x += dx * speed;
        y += dy * speed;
    }

    // Método para obtener la hitbox del personaje
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage(float damage) {
        health -= damage;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void attack(List<Goblins> goblins) {
        // Calcula la posición de la hitbox del ataque basándote en la posición y dirección del personaje
        float attackX = x;
        float attackY = y;
        switch (direction) {
            case left:
                attackX -= 30; // ajusta la posición de la hitbox hacia la izquierda
                break;
            case right:
                attackX += 30; // ajusta la posición de la hitbox hacia la derecha
                break;
            case up:
                attackY += 30; // ajusta la posición de la hitbox hacia arriba
                break;
            case down:
                attackY -= 30; // ajusta la posición de la hitbox hacia abajo
                break;
        }
        // Itera sobre los goblins y aplica daño a aquellos dentro del rango del ataque
        for (Goblins goblin : goblins) {
            if (Math.abs(goblin.getX() - attackX) <= 30 && Math.abs(goblin.getY() - attackY) <= 30) {
                goblin.takeDamage(attackDamage);
            }
        }
    }
}