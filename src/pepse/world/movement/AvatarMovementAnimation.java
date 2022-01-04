package pepse.world.movement;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Handles the avatar movement render change
 */
public class AvatarMovementAnimation implements MovementAnimation {

    private static final float WALKING_CHANGE_ANIMATION = 0.15f;

    private final Renderable staticAvatar;
    private final Renderable leftAvatar;
    private final Renderable rightAvatar;
    private final Renderable flyingAvatar;

    private int animationDirection = 0;
    private float currentTimePassed = 0;
    private float lastXVelocity = 0;
    private boolean isStatic = true;
    private boolean isFlying = false;


    /**
     * ctor for the avatar movement animation
     *
     * @param staticAvatar static motion avatar
     * @param leftAvatar   left moving motion avatar
     * @param rightAvatar  right moving motion avatar
     * @param flyingAvatar 'flying' avatar
     */
    public AvatarMovementAnimation(Renderable staticAvatar,
                                   Renderable leftAvatar,
                                   Renderable rightAvatar,
                                   Renderable flyingAvatar) {

        this.staticAvatar = staticAvatar;
        this.leftAvatar = leftAvatar;
        this.rightAvatar = rightAvatar;
        this.flyingAvatar = flyingAvatar;
    }

    /**
     * changes the render of the game object
     *
     * @param gameObject         game object to change render
     * @param timeFromLastUpdate the time changed since the last call the update render function
     */
    public void updateRender(GameObject gameObject, float timeFromLastUpdate) {
        var velocity = gameObject.getVelocity();
        if (!isStatic && velocity.y() == 0 && velocity.x() == 0) {
            isFlying = false;
            isStatic = true;
            gameObject.renderer().setRenderable(this.staticAvatar);

            currentTimePassed = 0;
        } else {
            handleAvatarMovement(gameObject, timeFromLastUpdate, velocity);
        }

        if (lastXVelocity != 0) {
            var shouldTurnLeft = lastXVelocity < 0;
            if (gameObject.renderer().isFlippedHorizontally() != shouldTurnLeft)
                gameObject.renderer().setIsFlippedHorizontally(shouldTurnLeft);

        }
        lastXVelocity = velocity.x();
    }

    private void handleAvatarMovement(GameObject gameObject, float timeFromLastUpdate, Vector2 velocity) {

        if (velocity.y() != 0) {
            isStatic = false;
            isFlying = true;
            gameObject.renderer().setRenderable(this.flyingAvatar);
            currentTimePassed = 0;
        } else {
            if (velocity.x() != 0) {
                handleWalkingAnimation(gameObject, timeFromLastUpdate, velocity);
            }
        }
    }

    /**
     * Handles the walking animaiton
     *
     * @param gameObject         game object to move
     * @param timeFromLastUpdate the time changed since the last call the update render function
     * @param velocity           the current game object velocity
     */
    private void handleWalkingAnimation(GameObject gameObject, float timeFromLastUpdate, Vector2 velocity) {
        if (isStatic || isFlying) {
            isStatic = false;
            isFlying = false;
            gameObject.renderer().setRenderable(velocity.x() > 0 ? this.rightAvatar : this.leftAvatar);
            animationDirection = velocity.x() > 0 ? 1 : -1;
            currentTimePassed = 0;
        } else {
            // already moving, change renderable for animation style
            currentTimePassed += timeFromLastUpdate;
            if (currentTimePassed >= WALKING_CHANGE_ANIMATION) {
                currentTimePassed = 0;
                animationDirection *= -1;
                gameObject.renderer().setRenderable(animationDirection > 0 ? this.rightAvatar : this.leftAvatar);
            }
        }
    }
}
