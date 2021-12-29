package pepse;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

import java.awt.*;

public class PepseGameManager extends GameManager {
    private GameObjectCollection gameObjects;
    private Vector2 windowDim;
    private static Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.gameObjects = gameObjects();
        this.windowDim = windowController.getWindowDimensions();
        Terrain terrain = new Terrain(this.gameObjects, Layer.STATIC_OBJECTS, this.windowDim, 42);
        terrain.createInRange(0, (int) (this.windowDim.x() + 100));
        Sky.create(this.gameObjects, this.windowDim, Layer.BACKGROUND);
        Night.create(gameObjects, Layer.FOREGROUND, windowDim, 30);
        var sun = Sun.create(gameObjects, Layer.BACKGROUND +1, windowDim, 25);
        SunHalo.create(gameObjects, Layer.BACKGROUND + 2, sun, SUN_HALO_COLOR);
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
