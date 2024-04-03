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
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Characters.Character;
import com.mygdx.game.Characters.Guts;
import com.mygdx.game.Characters.Thorne;
import com.mygdx.game.MapsGenerator.MapGenerator;
import com.mygdx.game.MapsGenerator.MapParser;
import com.mygdx.game.MapsGenerator.TileData;

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
    private TileData[][] tileData;
    public TiledMap tiledMap;
    private int mapWidth;
    private int mapHeight;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    public GameScreen(MyGdxGame game) {
        this.game = game;

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.zoom = 0.4f;
        camera.update();

        batch = new SpriteBatch();


        tileData = MapGenerator.generateMap();


        tiledMap = MapParser.parseMap(tileData);

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
        character = new Character(TileData.playerSpawn[0]*16+5, TileData.playerSpawn[1]*16+5, 26, 26, animationFront, animationBack, animationRight, animationLeft, idleSprite);
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
        handleInput(delta);

        // Se dibuja el personaje usando el objeto batch del juego principal
        game.batch.begin();
        game.batch.setProjectionMatrix(camera.combined);
        character.animationTime += Gdx.graphics.getDeltaTime();
        if (character.currentDisplay instanceof Animation) {
            TextureRegion currentFrame = ((Animation<TextureRegion>) character.currentDisplay).getKeyFrame(character.animationTime, true);
            game.batch.draw(currentFrame, character.x-character.width*0.5f, character.y-character.height*0.5f, character.width, character.height);
        } else if (character.currentDisplay instanceof Sprite) {
            game.batch.draw((Sprite) character.currentDisplay, character.x-character.width/2, character.y-character.height/2, character.width, character.height);

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

    private void handleInput(float delta) {
        float dx = 0, dy = 0;
        float atackHitBoxX = 0, atackHitBoxY = 0, atackHitBoxWidth = 0, atackHitBoxHeigth = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if(!playerCollision(character.x-8,character.y)){
            dx -= 20;
            atackHitBoxX = -20;
            atackHitBoxY = 0;
            atackHitBoxWidth = 40;
            atackHitBoxHeigth = 20;
            character.currentDisplay = character.animationLeft;}
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(!playerCollision(character.x+8, character.y)){
            dx += 20;
            atackHitBoxX= 35;
            atackHitBoxY = 0;
            atackHitBoxWidth = 40;
            atackHitBoxHeigth = 20;
            character.currentDisplay = character.animationRight;}
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if(!playerCollision(character.x, character.y+8)){
            dy += 20;
            atackHitBoxY = 40;
            atackHitBoxX= 25;
            atackHitBoxWidth = 20;
            atackHitBoxHeigth = 40;
            character.currentDisplay = character.animationBack;}
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            if(!playerCollision(character.x, character.y-16)){
            dy -= 20;
            atackHitBoxY = -40;
            atackHitBoxX= 25;
            atackHitBoxWidth = 20;
            atackHitBoxHeigth = 40;
            character.currentDisplay = character.animationFront;}
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

        character.move(dx*delta, dy*delta);
    }

    boolean playerCollision(float x, float y) {
        int tileX = (int) (x / 16);
        int tileY = (int) (y / 16);
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        TiledMapTileLayer.Cell cell = layer.getCell(tileX,tileY);
        boolean collision = true;
        try{
            collision =  (boolean) cell.getTile().getProperties().get("collision");
        }catch(NullPointerException e){
            //cell no existe
        }
        return collision;
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

