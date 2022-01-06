package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.TemporaryItem;

/**
 * block object class
 * @author Tomer Goldberg
 */
public class Block extends GameObject implements TemporaryItem {
    public static final int SIZE = 30;

    /**
     * constructor
     * @param topLeftCorner top left corner of block
     * @param renderable renderable obj
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}
