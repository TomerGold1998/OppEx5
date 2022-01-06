package pepse.util.unique_game_objects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.List;

/**
 * Represent a game object that holds and manage inner game objects
 */

public abstract class GameObjectsContainer extends GameObject {

    /**
     * create new GameObjectsContainer
     * @param topLeftCorner topLeftCorner of the game object
     * @param dimensions    dimension of the game object
     * @param renderable    renderable for the game object
     */
    public GameObjectsContainer(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }

    /**
     * @return a list containing all of the game object inner game object
     * for example (tree contains leaves etc.)
     */
    public abstract List<GameObject> getInnerGameObjects();

    /**
     * Called when the game object is needed to remove its inner game objects he has created
     */
    public abstract void removeInnerGameObjects();
}
