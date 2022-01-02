package pepse;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.configuration.GameLayers;
import pepse.configuration.GameObjectsConfiguration;
import pepse.configuration.TransitionConfiguration;
import pepse.transitions.ChangeOpacityTransitionExecutor;
import pepse.util.SurfaceCreator;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;

import java.util.ArrayList;

public class PepseGameManager extends GameManager {

    private static final int GAME_DISPLAY_CREATE_BUFFER = 200;
    private static final int GAME_DISPLAY_REMOVE_ITEM_BUFFER = 300;

    private Vector2 windowDim;
    private Avatar avatar;
    private InfiniteWorldHandler infiniteWorldHandler;
    private RemoveUnusedGameObjects removeUnusedGameObjects;


    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObjectCollection gameObjects = gameObjects();
        this.windowDim = windowController.getWindowDimensions();

        Terrain terrain = new Terrain(gameObjects, GameLayers.BLOCK_LAYER, this.windowDim, GameObjectsConfiguration.SEED);

        Sky.create(gameObjects, this.windowDim, GameLayers.SKY_LAYER);
        Night.create(gameObjects, GameLayers.NIGHT_LAYER, windowDim, TransitionConfiguration.NIGHT_CYCLE_LENGTH);

        var sun = Sun.create(gameObjects, GameLayers.SUN_LAYER, windowDim, TransitionConfiguration.SUN_CYCLE_LENGTH);
        SunHalo.create(gameObjects, GameLayers.SUN_HALO_LAYER, sun, GameObjectsConfiguration.SUN_HALO_COLOR);

        this.avatar = Avatar.create(gameObjects, GameLayers.AVATAR_LAYER, new Vector2(
                        this.windowDim.x() / 2,
                        this.windowDim.y() - terrain.groundHeightAt(this.windowDim.x() / 2) - 150),
                inputListener,
                imageReader);

        this.setCamera(new Camera(
                this.avatar,
                this.windowDim.mult(0.5f).subtract(this.avatar.getCenter()),
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()
        ));

        var leafTransition = new ChangeOpacityTransitionExecutor(
                0.3f,
                0.6f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        Tree tree = new Tree(terrain,
                gameObjects,
                windowDim,
                GameLayers.TREE_LAYER,
                GameObjectsConfiguration.TREE_HEIGHT,
                15,
                GameObjectsConfiguration.SEED,
                leafTransition);

        var surfaces = new ArrayList<SurfaceCreator>();
        surfaces.add(terrain);
        surfaces.add(tree);

        this.infiniteWorldHandler = new InfiniteWorldHandler(
                surfaces,
                (int) (this.windowDim.x() / 2) + GAME_DISPLAY_CREATE_BUFFER,
                (int) avatar.getCenter().x());
        this.removeUnusedGameObjects = new RemoveUnusedGameObjects(
                gameObjects,
                GameLayers.TREE_LAYER,
                this.infiniteWorldHandler);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.infiniteWorldHandler.updateSurfaces((int) this.avatar.getCenter().x());
        removeUnusedGameObjects.removeGameObjectsNotInRange(
                (int) this.avatar.getCenter().x() -
                        ((int) (this.windowDim.x() / 2)
                                + GAME_DISPLAY_REMOVE_ITEM_BUFFER),
                (int) this.avatar.getCenter().x() + ((int) (this.windowDim.x() / 2)
                        + GAME_DISPLAY_REMOVE_ITEM_BUFFER)
        );

    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
