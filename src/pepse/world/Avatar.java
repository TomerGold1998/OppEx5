package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.configuration.GameObjectsConfiguration;
import pepse.world.movement.*;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Avatar extends GameObject {


    private final static int LEFT_RIGHT_MOVEMENT_SPEED = 300;
    private final static int UP_DOWN_MOVEMENT_SPEED = 300;
    private final static int FLY_MOVEMENT_SPEED = 10;
    private final static int GRAVITY = 500;

    private final MovementAnimation animationHandler;
    private final UserInputListener userInputListener;
    private final HashMap<Integer, MovementHandler> keyToMovement;
    private final AvatarEnergyHandler energyHandler;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner     Position of the object, in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        Width and height in window coordinates.
     * @param renderable        The renderable representing the object. Can be null, in which case
     * @param userInputListener user input lister item
     * @param keyToMovement     used in order to map between the movement direction and the movement action
     * @param animationHandler  used in order to change the avatar renderable
     */
    public Avatar(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener userInputListener,
                  HashMap<Integer, MovementHandler> keyToMovement,
                  AvatarEnergyHandler energyHandler,
                  MovementAnimation animationHandler) {
        super(topLeftCorner, dimensions, renderable);

        this.userInputListener = userInputListener;
        this.keyToMovement = keyToMovement;
        this.energyHandler = energyHandler;
        this.animationHandler = animationHandler;
        this.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        this.transform().setAccelerationY(GRAVITY);
    }


    /**
     * Execute update on the game object, handles key change events
     *
     * @param deltaTime time passed since last update
     */
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.animationHandler.updateRender(this, deltaTime);

        var keyPressed = false;

        if (userInputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            keyToMovement.get(KeyEvent.VK_LEFT).move(this);
            keyPressed = true;
        }

        if (userInputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            keyToMovement.get(KeyEvent.VK_RIGHT).move(this);
            keyPressed = true;
        }

        if (userInputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                userInputListener.isKeyPressed(KeyEvent.VK_SHIFT)) {

            keyToMovement.get(KeyEvent.VK_SHIFT).move(this);
            keyPressed = true;
        } else if (userInputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            keyToMovement.get(KeyEvent.VK_SPACE).move(this);
            keyPressed = true;
        }

        if (!keyPressed) {
            this.setVelocity(new Vector2(0, this.getVelocity().y()));
        }

        if (this.getVelocity().y() == 0) {
            // object is resting, increase energy level
            this.energyHandler.increaseLevel();
        }
    }


    public static Avatar create(GameObjectCollection gameObjects,
                                int layer,
                                Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader) {

        var energyHandler = new AvatarEnergyHandler(GameObjectsConfiguration.AVATAR_ENERGY);
        //Cannot use DI because of the must-have create functions!!
        var movementHashMap = new HashMap<Integer, MovementHandler>();
        movementHashMap.put(KeyEvent.VK_LEFT,
                new AvatarMoveLeftAndRightHandler(LEFT_RIGHT_MOVEMENT_SPEED, Vector2.LEFT));
        movementHashMap.put(KeyEvent.VK_RIGHT,
                new AvatarMoveLeftAndRightHandler(LEFT_RIGHT_MOVEMENT_SPEED, Vector2.RIGHT));
        movementHashMap.put(KeyEvent.VK_SPACE,
                new AvatarMoveUpHandler(UP_DOWN_MOVEMENT_SPEED));
        movementHashMap.put(KeyEvent.VK_SHIFT,
                new AvatarFlyMovementHandler(FLY_MOVEMENT_SPEED, energyHandler));

        var animationHandler = new AvatarMovementAnimation(
                imageReader.readImage(GameObjectsConfiguration.STATIC_MALE_PATH, true),
                imageReader.readImage(GameObjectsConfiguration.LEFT_MALE_PATH, true),
                imageReader.readImage(GameObjectsConfiguration.RIGHT_MALE_PATH, true),
                imageReader.readImage(GameObjectsConfiguration.FLYING_MALE_PATH, true));

        var avatar = new Avatar(
                topLeftCorner,
                GameObjectsConfiguration.AVATAR_SIZE,
                imageReader.readImage(GameObjectsConfiguration.STATIC_MALE_PATH, true),
                inputListener,
                movementHashMap,
                energyHandler,
                animationHandler);
        gameObjects.addGameObject(avatar, layer);
        return avatar;
    }
}
