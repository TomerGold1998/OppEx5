package pepse.world.movement;

import danogl.GameObject;

/**
 * interface for executing render change and animations
 * @author Tomer Goldberg
 */
public interface MovementAnimation {
    void updateRender(GameObject gameObject, float timeFromLastUpdate);
}
