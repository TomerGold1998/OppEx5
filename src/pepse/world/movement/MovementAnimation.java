package pepse.world.movement;

import pepse.util.unique_game_objects.AnimatedGameObject;

/**
 * interface for executing render change and animations
 * @author Tomer Goldberg
 */
public interface MovementAnimation {
    void updateRender(AnimatedGameObject gameObject);
}
