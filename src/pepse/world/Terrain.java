package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.GroundHeightCalculator;
import pepse.util.PerlinNoise;
import pepse.util.SurfaceCreator;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * creating necessary blocks and lets other objects know the height of the terrain at a certain coordinate.
 */
public class Terrain implements GroundHeightCalculator, SurfaceCreator {


    private final GameObjectCollection gameObjects;
    private final int groundLayer;
    private final Vector2 windowDimensions;
    private final PerlinNoise noiseCreator;

    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final int INITAL_GROUND_LEVEL = 100;
    private static final int NOISE_FACTOR = 2;
    private final HashMap<Integer, Float> groundHeights;


    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer,
                   Vector2 windowDimensions,
                   int seed) {
        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        this.noiseCreator = new PerlinNoise(seed);
        groundHeights = new HashMap<>();
    }

    /**
     * Execute the math equation for getting the wanted ground height for input x
     *
     * @param x location
     */
    public float groundHeightAt(float x) {
        if (groundHeights.containsKey((int) x))
            return groundHeights.get((int) x);
        var value = INITAL_GROUND_LEVEL +
                (float) this.noiseCreator.noise(x) * NOISE_FACTOR * Block.SIZE;
        groundHeights.put((int) x, value);
        return value;
    }

    /**
     * Used in order to calcaulate the real ground location at x position
     * using the known block size
     *
     * @param x input location
     * @return the y location of the game object (relative to 0,0 being the bottom left position
     */
    @Override
    public float gameObjectHeightAt(float x) {
        return (float) Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE;
    }


    /**
     * creates the blocks at the wanted x range
     * (uses the groundHeightAt for knowing the wanted height for point x)
     *
     * @param minX the start point of adding the blocks
     * @param maxX the end point of adding the blocks
     */
    public void createInRange(int minX, int maxX) {
        for (var x : getPossibleFixedItemsLocation(minX, maxX)) {
            var blockHeight = this.gameObjectHeightAt(x);
            var blocksToAdd = createBlocksByDepth(x, blockHeight);
            for (var blockToAdd : blocksToAdd)
                this.gameObjects.addGameObject(blockToAdd, this.groundLayer);
        }
    }

    /**
     * Create a list of blocks from the X,Y location * number of TERRAIN_DEPTH
     *
     * @param x block x location
     * @param y block top y location
     * @return list of blocks
     */
    private ArrayList<Block> createBlocksByDepth(float x, float y) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (var i = 0; i < TERRAIN_DEPTH; i++) {
            blocks.add(
                    new Block(new Vector2(x, this.windowDimensions.y() - (y - (Block.SIZE * i))),
                            new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR))));
        }
        return blocks;
    }

    @Override
    public List<Integer> getPossibleFixedItemsLocation(int fromRange, int toRange) {
        var options = new ArrayList<Integer>();
        var realFrom = getClosestToBlockSize(fromRange);
        var realTo = getClosestToBlockSize(toRange);
        for (var x = realFrom; x <= realTo; x += Block.SIZE) {
            options.add(x);
        }
        return options;
    }

    /**
     * calculate the real 'range' to move on (as a result of Block size
     *
     * @param x wanted location
     * @return the real x size
     */
    private int getClosestToBlockSize(int x) {
        return x - x % Block.SIZE;
    }

}
