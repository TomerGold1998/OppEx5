package pepse.util.unique_game_objects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.MovementOptions;

/**
 * Represent a game object that have some kind of movement (and therefore an animation)
 */
public abstract class AnimatedGameObject extends GameObject {
    /**
     * Construct a new AnimatedGameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public AnimatedGameObject(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    public abstract MovementOptions getCurrentMovement();
}
