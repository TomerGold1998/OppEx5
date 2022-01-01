package pepse.world.movement;

import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Handle fly movement
 * @author Tomer Goldberg
 */
public class AvatarFlyMovementHandler implements MovementHandler {
    private final int speed;
    private final AvatarEnergyHandler energyHandler;

    /**
     * constrctor for AvatarFlyMovementHandler
     * @param speed the upward flying speed
     * @param energyHandler the avatar energy handler
     */
    public AvatarFlyMovementHandler(int speed, AvatarEnergyHandler energyHandler) {

        this.speed = speed;
        this.energyHandler = energyHandler;
    }


    /**
     * moving the object upward if the current energy level allows it
     * @param gameObject the game object to move
     * @return true if moved successfully
     */
    public boolean move(GameObject gameObject) {
        if (energyHandler.getCurrentLevel() > 0) {
            this.energyHandler.decreaseLevel();
            gameObject.setVelocity(Vector2.UP.mult(this.speed).add(gameObject.getVelocity()));
            return true;
        }
        return false;
    }
}
