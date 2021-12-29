package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

/**
 * creating necessary blocks and lets other objects know the height of the terrain at a certain coordinate.
 */
public class Terrain {


    private final GameObjectCollection gameObjects;
    private final int groundLayer;

    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private final Renderable blockRender;


    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer,
                   Vector2 windowDimensions,
                   int seed) {
        // TODO: implement

        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.blockRender =  new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
    }

    /**
     * Execute the math equation for getting the wanted ground height for input x
     *
     * @param x location
     */
    public float groundHeightAt(float x) {
        //TODO: implement
        return -1;
    }

    /**
     * creates the blocks at the wanted x range
     * (uses the groundHeightAt for knowing the wanted height for point x)
     *
     * @param minX the start point of adding the blocks
     * @param maxX the end point of adding the blocks
     */
    public void createInRange(int minX, int maxX) {
        Block block = new Block(Vector2.ZERO, blockRender);
        this.gameObjects.addGameObject(block, this.groundLayer);
    }

}
