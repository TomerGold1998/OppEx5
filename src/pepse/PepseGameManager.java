package pepse;

import danogl.GameManager;
import danogl.GameObject;
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
import pepse.transitions.AngleAxisAndSizeChangeTransitionCreator;
import pepse.transitions.ChangeOpacityTransitionCreator;
import pepse.transitions.HorizontalTransitionCreator;
import pepse.util.surface.SurfaceCreator;
import pepse.util.text.GameTextInputGetter;
import pepse.util.text.WordToActionHandler;
import pepse.world.*;
import pepse.world.daynight.BounsSunItem;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Tree;
import pepse.world.trees.TreesCacheLocationGetter;
import pepse.world.trees.leaf.LeafLifeDeathCycle;
import pepse.world.trees.leaf.LeafTransitionHandler;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;

/**
 * Game Manager class
 */
public class PepseGameManager extends GameManager {

    private final static int GAME_DISPLAY_CREATE_BUFFER = 200;
    private WorldSurfaceHandler worldSurfaceHandler;
    private WordToActionHandler wordToActionHandler;
    private BounsSunItem bounsSunItem;
    private Random random;

    /**
     * Starts new Pepse Game!
     *
     * @param imageReader      image reader
     * @param soundReader      sound reader
     * @param inputListener    input listener
     * @param windowController window controller
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        GameObjectCollection gameObjects = gameObjects();
        var windowDim = windowController.getWindowDimensions();

        this.random = new Random(GameObjectsConfiguration.SEED);
        Terrain terrain = new Terrain(
                gameObjects,
                GameLayers.BLOCK_LAYER,
                windowDim,
                GameObjectsConfiguration.SEED);
        Sky.create(
                gameObjects,
                windowDim,
                GameLayers.SKY_LAYER);
        Night.create(
                gameObjects,
                GameLayers.NIGHT_LAYER,
                windowDim,
                TransitionConfiguration.NIGHT_CYCLE_LENGTH);

        var sun = Sun.create(
                gameObjects,
                GameLayers.SUN_LAYER,
                windowDim,
                TransitionConfiguration.SUN_CYCLE_LENGTH);
        SunHalo.create(
                gameObjects,
                GameLayers.SUN_HALO_LAYER,
                sun,
                GameObjectsConfiguration.SUN_HALO_COLOR);


        var avatar = Avatar.create(gameObjects, GameLayers.AVATAR_LAYER, new Vector2(
                        windowDim.x() / 2,
                        windowDim.y() - (terrain.gameObjectHeightAt(windowDim.x() / 2) + GameObjectsConfiguration.AVATAR_SIZE.y())),
                inputListener,
                imageReader);

        this.setCamera(new Camera(
                avatar,
                windowDim.mult(0.5f).subtract(avatar.getCenter()),
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()
        ));

        Tree tree = createTrees(gameObjects, windowDim, terrain, avatar);

        var surfaces = new ArrayList<SurfaceCreator>();
        surfaces.add(terrain);
        surfaces.add(tree);

        var infiniteWorldHandler = new InfiniteWorldCreator(
                surfaces,
                (int) (windowDim.x() / 2) + GAME_DISPLAY_CREATE_BUFFER,
                (int) avatar.getCenter().x());
        var removeUnusedGameObjects = new RemoveUnusedGameObjects(
                gameObjects,
                GameLayers.TREE_LAYER,
                infiniteWorldHandler);

        this.worldSurfaceHandler = new WorldSurfaceHandler(
                infiniteWorldHandler,
                removeUnusedGameObjects,
                windowDim,
                avatar
        );

        this.worldSurfaceHandler.updateSurface();
        createGameBonus(imageReader, inputListener, sun);

        setupLayerCollide();
    }

    private Tree createTrees(GameObjectCollection gameObjects, Vector2 windowDim, Terrain terrain, Avatar avatar) {
        var leafOpacity = new ChangeOpacityTransitionCreator(
                0.3f,
                0.6f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        var leafAngle = new AngleAxisAndSizeChangeTransitionCreator(
                TransitionConfiguration.LEAF_START_ANGLE,
                TransitionConfiguration.LEAF_END_ANGLE,
                TransitionConfiguration.BIG_LEAF_SIZE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Transition.LINEAR_INTERPOLATOR_VECTOR,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);

        var leafWoobling = new HorizontalTransitionCreator(
                TransitionConfiguration.LEAF_WOOBLING_MAX_SPEED,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        var treeRandom = new Random(GameObjectsConfiguration.SEED);
        var treeLocationGetter = new TreesCacheLocationGetter(
                terrain,
                (int) avatar.getCenter().x(),
                treeRandom);
        return new Tree(gameObjects,
                windowDim,
                GameLayers.TREE_LAYER,
                treeLocationGetter,
                new LeafTransitionHandler(
                        leafOpacity,
                        leafAngle,
                        TransitionConfiguration.LEAF_SIZE_AND_ANGLE_CYCLE,
                        this.random),
                new LeafLifeDeathCycle(leafWoobling, gameObjects, this.random));
    }

    private void setupLayerCollide() {
        gameObjects().layers().shouldLayersCollide(
                GameLayers.LEAF_LAYER,
                GameLayers.BLOCK_LAYER,
                true);
    }

    /**
     * GAME BONUS !
     * ENTER keyboard oop to have a message on the sun
     * (might need to be a bit slow LIKE O O P \ y a h e l
     * <p>
     * ENTER the name of yahel for a picture of the best metarelet on the sun!
     *
     * @param imageReader   image reader
     * @param inputListener input listener
     * @param sun           sun game object!
     */
    private void createGameBonus(ImageReader imageReader,
                                 UserInputListener inputListener,
                                 GameObject sun) {

        var keyEventToListen = new HashMap<Integer, Character>();
        keyEventToListen.put(KeyEvent.VK_O, 'o');
        keyEventToListen.put(KeyEvent.VK_P, 'p');
        keyEventToListen.put(KeyEvent.VK_Y, 'y');
        keyEventToListen.put(KeyEvent.VK_A, 'a');
        keyEventToListen.put(KeyEvent.VK_H, 'h');
        keyEventToListen.put(KeyEvent.VK_E, 'e');
        keyEventToListen.put(KeyEvent.VK_L, 'l');

        this.bounsSunItem = new BounsSunItem(imageReader, sun);
        this.gameObjects().addGameObject(bounsSunItem, GameLayers.SUN_HALO_LAYER);

        var wordToActionMap = new HashMap<String, Consumer<String>>();
        wordToActionMap.put("oop", (word) ->
                this.bounsSunItem.setAsText(
                        GameObjectsConfiguration.DESIGN_PATTERNS[
                                this.random.nextInt(GameObjectsConfiguration.DESIGN_PATTERNS.length)]));
        wordToActionMap.put("yahel", (word) ->
                this.bounsSunItem.setAsImage(GameObjectsConfiguration.YAHEL_IMG_PATH));

        var gameTextInputGetter = new GameTextInputGetter(keyEventToListen, inputListener, 10);
        this.wordToActionHandler = new WordToActionHandler(wordToActionMap, gameTextInputGetter);
    }

    /**
     * Called when a game update is called. regenerate game surfaces and reads
     * user keyboard input for our bonus!
     *
     * @param deltaTime time passed since last update in seconds
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        this.worldSurfaceHandler.updateSurface();
        this.wordToActionHandler.runUpdate(deltaTime);
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
