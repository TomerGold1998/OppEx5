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
 * @author Tomer Goldberg
 */
public class Terrain implements GroundHeightCalculator, SurfaceCreator {

    private final GameObjectCollection gameObjects;
    private final int groundLayer;
    private final Vector2 windowDimensions;
    private final PerlinNoise noiseCreator;

    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;
    private static final int INITAL_GROUND_LEVEL = 200;
    private static final int NOISE_FACTOR = 8;
    private static final int GROUND_HEIGHT_NEIGHBORS_RANGE = 30;

    private final HashMap<Integer, Float> groundHeights;

    /**
     * constructor
     * @param gameObjects obj collection
     * @param groundLayer layer of ground
     * @param windowDimensions window dimensions
     * @param seed random seed factor
     */
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
        var leftNeighboorValue = getRawGroundHeight(x - GROUND_HEIGHT_NEIGHBORS_RANGE);
        var rightNeighborValue = getRawGroundHeight(x + GROUND_HEIGHT_NEIGHBORS_RANGE);

        var myAvgValue = (leftNeighboorValue + rightNeighborValue) / 2;
        groundHeights.put((int) x, myAvgValue);
        return myAvgValue;
    }

    private float getRawGroundHeight(float x) {
        // using the ground height in a raw way - no averaging
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

    private ArrayList<Block> createBlocksByDepth(float x, float y) {
        //Create a list of blocks from the X,Y location * number of TERRAIN_DEPTH
        ArrayList<Block> blocks = new ArrayList<>();
        for (var i = 0; i < TERRAIN_DEPTH; i++) {
            if (i < 2) {
                blocks.add(new ReactingBlock(
                        new Vector2(x, this.windowDimensions.y() - (y - (Block.SIZE * i))),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR))));
            } else {
                blocks.add(new Block(
                        new Vector2(x, this.windowDimensions.y() - (y - (Block.SIZE * i))),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR))));
            }
        }
        return blocks;
    }

    /**
     * get a location of item
     * @param fromRange from
     * @param toRange   to
     * @return optional locations
     */
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


    private int getClosestToBlockSize(int x) {
        //calculate the real 'range' to move on (as a result of Block size)
        return x - x % Block.SIZE;
    }

}
