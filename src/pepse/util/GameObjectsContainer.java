package pepse.util;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.List;

/**
 * Represent a game object that holds and manage inner game objects
 */

public abstract class GameObjectsContainer extends GameObject {

    /**
     * Construct a new GameObjectsContainer instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public GameObjectsContainer(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }
    public abstract List<GameObject> getInnerGameObjects();
}
