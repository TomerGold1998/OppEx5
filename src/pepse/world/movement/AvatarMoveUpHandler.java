package pepse.world.movement;

import danogl.GameObject;
import danogl.util.Vector2;

/**
 * handles 'jump' movement
 *
 * @author Tomer Goldberg
 */
public class AvatarMoveUpHandler implements MovementHandler {

    private final int speed;

    /**
     * constrctor for the AvatarMoveUpHandler
     *
     * @param speed the junmp y axis speed
     */
    public AvatarMoveUpHandler(int speed) {
        this.speed = speed;
    }

    /**
     * execute 'jump' if possible
     *
     * @param gameObject game object to execute jump on
     * @return true if jump was executed
     */
    public boolean move(GameObject gameObject) {
        if (gameObject.getVelocity().y() == 0) {
            gameObject.setVelocity(Vector2.UP
                    .mult(speed)
                    .add(new Vector2(gameObject.getVelocity().x(), 0)));

            return true;
        }
        return false;
    }
}
