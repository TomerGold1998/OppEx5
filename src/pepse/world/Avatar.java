package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.configuration.GameObjectsConfiguration;
import pepse.util.unique_game_objects.AnimatedGameObject;
import pepse.util.MovementOptions;
import pepse.world.movement.*;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * Avatar creator
 * @author Tomer Goldberg
 */
public class Avatar extends AnimatedGameObject {

    private final static int LEFT_RIGHT_MOVEMENT_SPEED = 300;
    private final static int UP_DOWN_MOVEMENT_SPEED = 300;
    private final static int FLY_MOVEMENT_SPEED = 10;
    private final static int GRAVITY = 500;
    private final static int MAX_CRASH_SPEED = 700;

    private final MovementAnimation animationHandler;
    private final UserInputListener userInputListener;
    private final HashMap<MovementOptions, MovementHandler> keyToMovement;
    private final AvatarEnergyHandler energyHandler;
    private MovementOptions currentMovementOption;


    /**
     * constructor
     * @param topLeftCorner top left corner of avatar
     * @param dimensions dimensions if avatar
     * @param renderable renderable obj
     * @param userInputListener user's input
     * @param keyToMovement movement keys
     * @param energyHandler energy handler
     * @param animationHandler animation handler
     */
    public Avatar(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener userInputListener,
                  HashMap<MovementOptions, MovementHandler> keyToMovement,
                  AvatarEnergyHandler energyHandler,
                  MovementAnimation animationHandler) {
        super(topLeftCorner, dimensions, renderable);

        this.userInputListener = userInputListener;
        this.keyToMovement = keyToMovement;
        this.energyHandler = energyHandler;
        this.animationHandler = animationHandler;

        this.transform().setAccelerationY(GRAVITY);
        currentMovementOption = MovementOptions.Standing;
    }

    /**
     * gets current movement
     * @return current movement
     */
    @Override
    public MovementOptions getCurrentMovement() {
        return currentMovementOption;
    }

    /**
     * Execute update on the game object, handles key change events
     * @param deltaTime time passed since last update
     */
    public void update(float deltaTime) {
        super.update(deltaTime);
        handleAvatarCrash();
        handleAvatarMovement();
        HandleAvatarEnergy();

        this.animationHandler.updateRender(this);
    }

    private void handleAvatarCrash() {
        if (this.getVelocity().y() > MAX_CRASH_SPEED)
            this.transform().setVelocityY(MAX_CRASH_SPEED);
    }

    private void HandleAvatarEnergy() {
        if (this.getVelocity().y() == 0) {
            // object is resting, increase energy level
            this.energyHandler.increaseLevel();
        }
    }

    private boolean tryExecuteMove(MovementOptions option) {
        var moved = keyToMovement.get(option).move(this);
        if (moved)
            currentMovementOption = option;
        return moved;
    }

    private void handleAvatarMovement() {
        var keyPressed = false;

        //Left movement
        if (userInputListener.isKeyPressed(KeyEvent.VK_LEFT) &&
                !userInputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            keyPressed = tryExecuteMove(MovementOptions.Left);
        }

        //Right movement
        if (userInputListener.isKeyPressed(KeyEvent.VK_RIGHT) &&
                !userInputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            keyPressed = tryExecuteMove(MovementOptions.Right);
        }

        //Flying movement
        if (userInputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                userInputListener.isKeyPressed(KeyEvent.VK_SHIFT)) {
            keyPressed = tryExecuteMove(MovementOptions.Flying);
        } else if (userInputListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            //jumping movement
            keyPressed = tryExecuteMove(MovementOptions.Jumping);
        }

        if (!keyPressed) {
            currentMovementOption = MovementOptions.Standing;
            this.setVelocity(new Vector2(0, this.getVelocity().y()));
        }
    }

    /**
     * creating avatar
     * @param gameObjects obj collection
     * @param layer layer of avatar
     * @param topLeftCorner top left corner
     * @param inputListener input listener
     * @param imageReader image reader
     * @return avatar image
     */
    public static Avatar create(GameObjectCollection gameObjects,
                                int layer,
                                Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader) {

        var energyHandler = new AvatarEnergyHandler(GameObjectsConfiguration.AVATAR_ENERGY);
        //Cannot use DI because of the must-have create functions!!
        var movementHashMap = new HashMap<MovementOptions, MovementHandler>();
        movementHashMap.put(MovementOptions.Left,
                new AvatarMoveLeftAndRightHandler(LEFT_RIGHT_MOVEMENT_SPEED, Vector2.LEFT));
        movementHashMap.put(MovementOptions.Right,
                new AvatarMoveLeftAndRightHandler(LEFT_RIGHT_MOVEMENT_SPEED, Vector2.RIGHT));
        movementHashMap.put(MovementOptions.Jumping,
                new AvatarMoveUpHandler(UP_DOWN_MOVEMENT_SPEED));
        movementHashMap.put(MovementOptions.Flying,
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
        avatar.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        gameObjects.addGameObject(avatar, layer);
        return avatar;
    }

}
