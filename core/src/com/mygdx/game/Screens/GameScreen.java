package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Guts;
import com.mygdx.game.Characters.Thorne;
import com.mygdx.game.MapsGenerator.MapGenerator;

public class GameScreen implements Screen {
    private MyGdxGame game;
    private Guts guts = new Guts();
    private Thorne thorne = new Thorne();
    private ShapeRenderer shapeRenderer;
    private Character character;
    private Texture textureFront, textureBack, textureRight, textureLeft, textureStatic;
    private TextureRegion[][] spritesFront, spritesBack, spritesRight, spritesLeft;
    private Animation<TextureRegion> animationFront, animationBack, animationRight, animationLeft;
    private Stage stage;
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
        /*mapWidth = MapGenerator.MAP_WIDTH;
        mapHeight = MapGenerator.MAP_HEIGHT;
        map = MapGenerator.generateMap();*/

        // Crear un TiledMap basado en la matriz generada
        TiledMap tiledMap = new TiledMap();
        TiledMapTileLayer layer = new TiledMapTileLayer(mapWidth, mapHeight, 16, 16);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                if (map[x][y] == 1) {
                    // Asignar una textura a las celdas de suelo
                    Texture texture = new Texture(Gdx.files.internal("assets/ParsedTiles dungeon/stonefloor1_0_0.png"));
                    cell.setTile(new StaticTiledMapTile(new TextureRegion(texture)));
                }
                layer.setCell(x, y, cell);
            }
        }
        tiledMap.getLayers().add(layer);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        String SelectedCharacter = "Thorne";

        switch(SelectedCharacter) {
            case "Guts":
                textureFront = guts.getTextureFront();
                textureBack = guts.getTextureBack();
                textureLeft = guts.getTextureLeft();
                textureRight = guts.getTextureRight();
                textureStatic = guts.getTextureStatic();
                spritesFront = TextureRegion.split(textureFront, 64, 60);
                spritesBack = TextureRegion.split(textureBack, 64, 52);
                spritesRight = TextureRegion.split(textureRight, 64, 52);
                spritesLeft = TextureRegion.split(textureLeft, 64, 60);
                break;
            case "Thorne":
                textureStatic = thorne.getTextureStatic();
                textureFront = thorne.getTextureFront();
                textureBack = thorne.getTextureBack();
                textureRight = thorne.getTextureRight();
                textureLeft = thorne.getTextureLeft();
                spritesFront = TextureRegion.split(textureFront, 64, 64);
                spritesBack = TextureRegion.split(textureBack, 64, 60);
                spritesRight = TextureRegion.split(textureRight, 64, 60);
                spritesLeft = TextureRegion.split(textureLeft, 64, 60);
                break;
            default:
                break;
        }

        // Crear las animaciones con los sprites
        animationFront = new Animation<>(0.25f, spritesFront[0]);
        animationBack = new Animation<>(0.25f, spritesBack[0]);
        animationRight = new Animation<>(0.25f, spritesRight[0]);
        animationLeft = new Animation<>(0.25f, spritesLeft[0]);

        // Crear el sprite de reposo
        Sprite idleSprite = new Sprite(textureStatic);
        character = new Character(50, 50, 64, 52, animationFront, animationBack, animationRight, animationLeft, idleSprite);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        shapeRenderer = new ShapeRenderer();

        // Agrega el stage a la vista de la cámara
        stage.setViewport(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera));
    }

    @Override
    public void render(float delta) {
        if (character.getHealth() <= 0) {
            // Cambia a la pantalla de fin de juego
            //game.setScreen(new EndScreen(this));
        }

        // Se limpia la pantalla con un color negro
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(character.x, character.y, 0);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        camera.update();

        // Se maneja la entrada del usuario
        handleInput();

        // Se dibuja el personaje usando el objeto batch del juego principal
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        character.animationTime += Gdx.graphics.getDeltaTime();
        if (character.currentDisplay instanceof Animation) {
            TextureRegion currentFrame = ((Animation<TextureRegion>) character.currentDisplay).getKeyFrame(character.animationTime, true);
            game.batch.draw(currentFrame, character.x, character.y);
        } else if (character.currentDisplay instanceof Sprite) {
            ((Sprite) character.currentDisplay).setPosition(character.x, character.y);
            ((Sprite) character.currentDisplay).draw(game.batch);
        }

        game.batch.end();

        // Comenzar a dibujar formas.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Dibujar el fondo de la barra de vida.
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(10, Gdx.graphics.getHeight() - 20, 100, 10);

        // Dibujar la vida actual. Supongamos que la vida es un valor entre 0 y 1.
        float vida = character.getHealth() / 100.0f;
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(10, Gdx.graphics.getHeight() - 20, vida * 100, 10);

        // Terminar de dibujar formas.
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.end();

        // Se actualiza el escenario del juego
        stage.act(delta);

        // Se dibuja el escenario del juego usando el objeto batch del juego principal
        stage.draw();
    }

    private void handleInput() {
        float dx = 0, dy = 0;
        float atackHitBoxX = 0, atackHitBoxY = 0, atackHitBoxWidth = 0, atackHitBoxHeigth = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dx -= 1;
            atackHitBoxX = -20;
            atackHitBoxY = 0;
            atackHitBoxWidth = 40;
            atackHitBoxHeigth = 20;
            character.currentDisplay = character.animationLeft;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dx += 1;
            atackHitBoxX= 35;
            atackHitBoxY = 0;
            atackHitBoxWidth = 40;
            atackHitBoxHeigth = 20;
            character.currentDisplay = character.animationRight;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            dy += 1;
            atackHitBoxY = 40;
            atackHitBoxX= 25;
            atackHitBoxWidth = 20;
            atackHitBoxHeigth = 40;
            character.currentDisplay = character.animationBack;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            dy -= 1;
            atackHitBoxY = -40;
            atackHitBoxX= 25;
            atackHitBoxWidth = 20;
            atackHitBoxHeigth = 40;
            character.currentDisplay = character.animationFront;
        } else {
            character.currentDisplay = character.idleSprite;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.G)){
            int newHealth = character.getHealth() - 1;
            character.setHealth(Math.max(newHealth, 0));
        }

        if (Gdx.input.isKeyPressed(Input.Keys.V)) {
            character.setSpeed(5.0f);
        } else {
            character.setSpeed(3.0f);
        }

        /*if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            atackhitbox.set(character.x + atackHitBoxX, character.y + atackHitBoxY, atackHitBoxWidth, atackHitBoxHeigth);
        } else {
            atackhitbox.set(0, 0, 0, 0);
        }*/

        character.move(dx, dy);
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

