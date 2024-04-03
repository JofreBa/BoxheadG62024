package com.mygdx.game.MapsGenerator;

public class TileData {
    private final String tile;
    private final boolean collision;

    public TileData(String tile, boolean collision) {
        this.tile = tile;
        this.collision = collision;

    }

    public String getTileName() {
        return tile;
    }

    public boolean getCollision() {
        return collision;
    }
}
