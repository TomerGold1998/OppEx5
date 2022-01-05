package pepse.world.trees;

import pepse.util.GameObjectVector;

import java.util.List;

public interface TreesLocationGetter {
    List<GameObjectVector> getTreesDataInRange(int minX, int maxX);
}
