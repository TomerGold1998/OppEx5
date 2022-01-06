package pepse.world.trees;

import danogl.util.Vector2;
import pepse.configuration.GameObjectsConfiguration;
import pepse.util.GameObjectVector;
import pepse.util.GroundHeightCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

/**
 * gets a list of location using cache of used locations
 * @author Tomer Goldberg
 */
public class TreesCacheLocationGetter implements TreesLocationGetter {

    private final GroundHeightCalculator groundHeightCalculator;

    private int maxKnownRange;
    private int minKnownRange;
    private final TreeMap<Integer, GameObjectVector> currentTreesLocations;
    private final Random random;

    private final static int RANDOM_TREE_CHANCE = 15;
    private final static int MIN_TREE_BUFFER = 40;

    /**
     * constructor
     * @param groundHeightCalculator calculating ground height at a certain point
     * @param initialLocation initial location
     * @param random random factor
     */
    public TreesCacheLocationGetter(GroundHeightCalculator groundHeightCalculator,
                                    int initialLocation,
                                    Random random) {
        this.groundHeightCalculator = groundHeightCalculator;
        this.currentTreesLocations = new TreeMap<>();
        this.random = random;
        this.maxKnownRange = initialLocation;
        this.minKnownRange = initialLocation;
    }

    /**
     * hash list of created tree locations
     * @param minX staring range
     * @param maxX ending range
     * @return the list
     */
    @Override
    public List<GameObjectVector> getTreesDataInRange(int minX, int maxX) {
        if (maxX > this.maxKnownRange) {
            addRandomTreeLocation(this.maxKnownRange, maxX);
            this.maxKnownRange = maxX;
        }
        if (minX < this.minKnownRange) {
            addRandomTreeLocation(minX, this.minKnownRange);
            this.minKnownRange = minX;
        }
        return new ArrayList<>(this.currentTreesLocations.subMap(minX, maxX).values());
    }

    private void addRandomTreeLocation(int from, int to) {
        //added a tree location to the list
        for (var i : groundHeightCalculator.getPossibleFixedItemsLocation(from, to)) {
            if (this.random.nextInt(RANDOM_TREE_CHANCE) == 1) {
                if (!this.currentTreesLocations.containsKey(i) &&
                        this.currentTreesLocations
                                .subMap(i - MIN_TREE_BUFFER, i + MIN_TREE_BUFFER)
                                .isEmpty()) {
                    var randomSize = generateTreeRandomSize();
                    this.currentTreesLocations.put(i,
                                            new GameObjectVector(i,
                                            groundHeightCalculator.gameObjectHeightAt(i),
                                            randomSize.x(),
                                            randomSize.y()));
                }
            }
        }
    }

    private Vector2 generateTreeRandomSize() {
        //random sizing for trees
        return new Vector2(
                GameObjectsConfiguration.TREE_WIDTH_OPTIONS[
                        this.random.nextInt(GameObjectsConfiguration.TREE_WIDTH_OPTIONS.length)],
                GameObjectsConfiguration.TREE_HEIGHT_OPTIONS[
                        this.random.nextInt(GameObjectsConfiguration.TREE_HEIGHT_OPTIONS.length)]);
    }
}
