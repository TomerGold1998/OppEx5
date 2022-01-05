package pepse.world.trees.leaf;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Leaf class to control movement
 *
 * @author Ruth Yukhnovetsky
 */
public class Leaf extends GameObject {
    /**
     * constructor of leaf in game
     *
     * @param topLeftCorner top left corner
     * @param dimensions    leaf dimensions
     * @param renderable    rendering
     */
    public Leaf(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
    }
}