package pepse.world.trees;

import danogl.util.Vector2;

import java.util.List;

public interface TreesLocationGetter {
    List<Vector2> getTreesLocationInRange(int minX, int maxX);
}
