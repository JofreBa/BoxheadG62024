package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class GameScreen implements Screen {
    private MyGdxGame game;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    public GameScreen(MyGdxGame game) {
        this.game = game;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();

        batch = new SpriteBatch();

        // Generar el mapa
        mapWidth = MapGenerator.MAP_WIDTH;
        mapHeight = MapGenerator.MAP_HEIGHT;
        map = MapGenerator.generateMap();

        // Crear un TiledMap basado en la matriz generada
        TiledMap tiledMap = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth, mapHeight, 16, 16);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (map[x][y] == 1) {
                    // Asignar una textura a las celdas de suelo
                    Texture texture = new Texture(Gdx.files.internal("assets/tiles/stonefloor1_0_0.png"));
                    cell.setTile(new StaticTiledMapTile(new TextureRegion(texture)));
                }
                layer.setCell(x, y, cell);
            }
        }
        tiledMap.getLayers().add(layer);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

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
    public void dispose () {
        batch.dispose();
        tiledMapRenderer.dispose();
    }
}

