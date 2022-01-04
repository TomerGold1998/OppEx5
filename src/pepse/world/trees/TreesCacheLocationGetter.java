package pepse.world.trees;

import danogl.util.Vector2;
import pepse.util.GroundHeightCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * gets a list of location using cache of used locations
 */
public class TreesCacheLocationGetter implements TreesLocationGetter {

    private final GroundHeightCalculator groundHeightCalculator;

    private int maxKnownRange;
    private int minKnownRange;
    private final TreeMap<Integer, Vector2> currentTreesLocations;
    private final Random random;

    private final static int RANDOM_TREE_CHANCE = 15;


    public TreesCacheLocationGetter(GroundHeightCalculator groundHeightCalculator,
                                    int initalLocation,
                                    Random random) {
        this.groundHeightCalculator = groundHeightCalculator;
        this.currentTreesLocations = new TreeMap<>();
        this.random = random;
        this.maxKnownRange = initalLocation;
        this.minKnownRange = initalLocation;
    }

    @Override
    public List<Vector2> getTreesLocationInRange(int minX, int maxX) {
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
        for (var i : groundHeightCalculator.getPossibleFixedItemsLocation(from, to)) {
            if (this.random.nextInt(RANDOM_TREE_CHANCE) == 1)
                this.currentTreesLocations.put(
                        i,
                        new Vector2(i, groundHeightCalculator.gameObjectHeightAt(i)));
        }
    }
}
