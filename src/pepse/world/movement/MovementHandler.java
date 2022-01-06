package pepse.world.movement;

import danogl.GameObject;

/**
 * interface for handling different types of movements
 * @author Tomer Goldberg
 */
public interface MovementHandler {
    boolean move(GameObject gameObject);
}
