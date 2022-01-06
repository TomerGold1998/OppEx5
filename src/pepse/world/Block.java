package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.configuration.GameObjectsConfiguration;
import pepse.util.TemporaryItem;

/**
 * block object class
 * @author Tomer Goldberg
 */
public class Block extends GameObject implements TemporaryItem {

    /**
     * constructor
     * @param topLeftCorner top left corner of block
     * @param renderable renderable obj
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(GameObjectsConfiguration.BLOCK_SIZE), renderable);

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
