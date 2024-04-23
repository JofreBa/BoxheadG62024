import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class MyAssetManager {

    private static AssetManager assetManager;
    private static String basePath;

    private MyAssetManager() {}

    public static void setBasePath(String basePath) {
        MyAssetManager.basePath = basePath;
    }

    public static void loadTextures() {
        if (basePath == null || basePath.isEmpty()) {
            throw new IllegalStateException("Base path for assets is not set.");
        }
        assetManager = new AssetManager();
        loadTexturesRecursive(new FileHandle(basePath));
        assetManager.finishLoading();
    }

    private static void loadTexturesRecursive(FileHandle directory) {
        for (FileHandle file : directory.list()) {
            if (file.isDirectory()) {
                loadTexturesRecursive(file);
            } else {
                if (file.extension().equalsIgnoreCase("png") || file.extension().equalsIgnoreCase("jpg")) {
                    String assetName = file.parent().name() + "_" + file.nameWithoutExtension();
                    assetManager.load(assetName, Texture.class, file);
                }
            }
        }
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }
}
