package pepse.world.trees;

import pepse.util.unique_game_objects.GameObjectVector;

import java.util.List;

/**
 * Hash list interface of tree locations
 * @author Tomer Goldberg
 */
public interface TreesLocationGetter {
    List<GameObjectVector> getTreesDataInRange(int minX, int maxX);
}
