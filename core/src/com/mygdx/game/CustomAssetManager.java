package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;

public class CustomAssetManager extends AssetManager {
    private static final String ASSETS_DIR = Gdx.files.internal("").file().getAbsolutePath();

    public CustomAssetManager() {
        super();
        // Load assets from the specified directory
        loadAssets(ASSETS_DIR);
    }

    private void loadAssets(String directory) {
        FileHandle dirHandle = new FileHandle(directory);
        if (dirHandle.isDirectory()) {
            for (FileHandle entry : dirHandle.list()) {
                if (entry.isDirectory()) {
                    // If it's a directory, recursively load assets from it
                    loadAssets(entry.path());
                } else {
                    // Load textures
                    if (entry.extension().equalsIgnoreCase("png")) {
                        TextureLoader.TextureParameter parameter = new TextureLoader.TextureParameter();
                        parameter.minFilter = Texture.TextureFilter.Linear;
                        parameter.magFilter = Texture.TextureFilter.Linear;
                        String relativePath = entry.path().substring(directory.length() + 1);
                        String assetKey = relativePath.replaceAll("\\\\", "/");
                        load(assetKey, Texture.class, parameter);

                        System.out.println("Loading texture: " + assetKey);
                    } else if (entry.extension().equalsIgnoreCase("mp3") || entry.extension().equalsIgnoreCase("wav")) {
                        // Load music
                        //load(entry.path(), Music.class);
                        // Use relative path as key
                        //String relativePath = entry.path().substring(directory.length() + 1);
                        //String assetKey = relativePath.replaceAll("\\\\", "/");
                        //System.out.println("Loading music: " + assetKey);
                    }
                    // Add loaders for other asset types as needed
                }
            }
        } else {
            System.err.println("Directory does not exist: " + directory);
        }
    }
}


