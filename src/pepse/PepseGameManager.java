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

public class PepseGameManager extends GameManager {
    private GameObjectCollection gameObjects;
    private Vector2 windowDim;

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
        Sun.create(gameObjects, Layer.BACKGROUND +1, windowDim, 20);
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
