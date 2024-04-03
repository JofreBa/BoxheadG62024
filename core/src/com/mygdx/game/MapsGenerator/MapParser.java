package com.mygdx.game.MapsGenerator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.mygdx.game.MapsGenerator.TileData;

public class MapParser {

    public static TiledMap parseMap(TileData[][] tileData) {
        TiledMap tiledMap = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(tileData.length, tileData[0].length, 16, 16);
        Texture texture2 = new Texture(Gdx.files.internal("Tiles/stonewall1_2_2.png"));
        // Iterate through tileData and populate the map
        for (int row = 0; row < tileData.length; row++) {
            for (int col = 0; col < tileData[row].length; col++) {
                // Assuming you have a method to get the TiledMapTile for each TileData
                if(tileData[row][col]!= null && tileData[row][col].getTileName()!="") {

                    Texture texture = new Texture(Gdx.files.internal("Tiles/" + tileData[row][col].getTileName()));
                    TextureRegion textureRegion = new TextureRegion(texture);
                    TiledMapTile tile = new StaticTiledMapTile(textureRegion);
                    tile.getProperties().put("collision", tileData[row][col].getCollision());

                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(tile);
                    layer.setCell(row, col, cell);
                }
            }
        }

        tiledMap.getLayers().add(layer);
        return tiledMap;
    }


}
