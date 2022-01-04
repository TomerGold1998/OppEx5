package pepse.util;

import java.util.List;

public interface GroundHeightCalculator {
    float groundHeightAt(float x);

    float gameObjectHeightAt(float x);

    List<Integer> getPossibleFixedItemsLocation(int fromRange, int toRange);
}
