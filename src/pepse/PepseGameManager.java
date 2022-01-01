package pepse;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.configuration.GameLayers;
import pepse.configuration.GameObjectsConfiguration;
import pepse.configuration.TransitionConfiguration;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.TreeManger;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;

import java.awt.*;

public class PepseGameManager extends GameManager {
    private GameObjectCollection gameObjects;
    private Vector2 windowDim;
    private static final int NUM_OF_TREES_TO_PLANT = 10;

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.gameObjects = gameObjects();
        this.windowDim = windowController.getWindowDimensions();

        Terrain terrain = new Terrain(this.gameObjects, GameLayers.BLOCK_LAYER, this.windowDim, GameObjectsConfiguration.TERRAIN_SEED);
        terrain.createInRange(0, (int) (this.windowDim.x() + GameObjectsConfiguration.TERRAIN_X_BUFFER));

        Sky.create(this.gameObjects, this.windowDim, GameLayers.SKY_LAYER);
        Night.create(gameObjects, GameLayers.NIGHT_LAYER, windowDim, TransitionConfiguration.NIGHT_CYCLE_LENGTH);

        var sun = Sun.create(gameObjects, GameLayers.SUN_LAYER, windowDim, TransitionConfiguration.SUN_CYCLE_LENGTH);
        SunHalo.create(gameObjects, GameLayers.SUN_HALO_LAYER, sun, GameObjectsConfiguration.SUN_HALO_COLOR);

        var avatar = Avatar.create(gameObjects, GameLayers.AVATAR_LAYER, new Vector2(
                        this.windowDim.x() / 2,
                        this.windowDim.y() - terrain.groundHeightAt(this.windowDim.x() / 2) - 150),
                inputListener,
                imageReader);
        TreeManger treeManager = new TreeManger(terrain, gameObjects, windowDim, GameLayers.TREE_LAYER);
        treeManager.plantTrees(NUM_OF_TREES_TO_PLANT);

    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
