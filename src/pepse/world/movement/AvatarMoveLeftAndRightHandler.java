package pepse.world.movement;

import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Handles left and right movement
 *
 * @author Tomer Goldberg
 */
public class AvatarMoveLeftAndRightHandler implements MovementHandler {

    private final int speed;
    private Vector2 direction;


    /**
     * constrctor for AvatarMoveLeftAndRightHandler
     *
     * @param speed     the left right moving speed
     * @param direction the moving direction (left or right) vector
     */
    public AvatarMoveLeftAndRightHandler(int speed, Vector2 direction) {

        this.speed = speed;
        this.direction = direction;
    }

    /**
     * moves the game object left or right
     *
     * @param gameObject game object to move
     * @return true if moved the object successfully
     */
    public boolean move(GameObject gameObject) {
        gameObject.setVelocity(direction.mult(speed).add(new Vector2(0, gameObject.getVelocity().y())));
        return true;
    }
}
