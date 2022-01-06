package pepse.util;

import java.util.List;

/**
 * interface for getting grounds heights in the game
 */
public interface GroundHeightCalculator {
    /**
     * calculate ground height for certain X
     * @param x input x
     * @return y level at input x
     */
    float groundHeightAt(float x);

    /**
     * get a game object height at a certine x
     * @param x x location
     * @return a fixed location that can have a game object
     */
    float gameObjectHeightAt(float x);

    /**
     * get possible fixed place game object location in a range
     * @param fromRange from
     * @param toRange   to
     * @return list of possible x index that can have a fixed game object
     */
    List<Integer> getPossibleFixedItemsLocation(int fromRange, int toRange);
}
