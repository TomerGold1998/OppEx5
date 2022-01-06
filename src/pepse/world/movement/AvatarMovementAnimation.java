package pepse.world.movement;

import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import pepse.util.AnimatedGameObject;
import pepse.util.MovementOptions;

/**
 * Handles the avatar movement render change
 * @author Tomer Goldberg
 */
public class AvatarMovementAnimation implements MovementAnimation {

    private static final float WALKING_CHANGE_ANIMATION = 0.15f;

    private final Renderable staticAvatar;
    private final Renderable walkingLeftAvatar;
    private final Renderable walkingRightAvatar;
    private final Renderable flyingAvatar;
    private MovementOptions lastMovement;

    /**
     * creator for the avatar movement animation
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
        this.walkingLeftAvatar = new AnimationRenderable(new Renderable[]{
                leftAvatar,
                rightAvatar},
                WALKING_CHANGE_ANIMATION);

        this.walkingRightAvatar = new AnimationRenderable(new Renderable[]{
                rightAvatar,
                leftAvatar},
                WALKING_CHANGE_ANIMATION);
        this.flyingAvatar = flyingAvatar;
        this.lastMovement = MovementOptions.Standing;
    }

    /**
     * changes the render of the game object
     *
     * @param gameObject game object to change render
     */
    public void updateRender(AnimatedGameObject gameObject) {
        if (gameObject.getCurrentMovement() != this.lastMovement) {
            changeRender(gameObject);
        }
        if (gameObject.getVelocity().x() != 0)
            gameObject.renderer().setIsFlippedHorizontally(gameObject.getVelocity().x() < 0);
    }

    private void changeRender(AnimatedGameObject gameObject) {
        if (gameObject.getCurrentMovement() == MovementOptions.Standing) {
            gameObject.renderer().setRenderable(this.staticAvatar);
        } else {
            handleAvatarMovement(gameObject);
        }
        this.lastMovement = gameObject.getCurrentMovement();
    }

    private void handleAvatarMovement(AnimatedGameObject gameObject) {
        var gameObjectMovement = gameObject.getCurrentMovement();
        if (gameObject.getVelocity().y() != 0) {
            if (gameObjectMovement == MovementOptions.Flying) {
                gameObject.renderer().setRenderable(this.flyingAvatar);
            } else {
                gameObject.renderer().setRenderable(this.staticAvatar);
            }
        } else {
            if (gameObjectMovement == MovementOptions.Right) {
                gameObject.renderer().setRenderable(this.walkingRightAvatar);
            } else {
                gameObject.renderer().setRenderable(this.walkingLeftAvatar);
            }
        }
    }
}
