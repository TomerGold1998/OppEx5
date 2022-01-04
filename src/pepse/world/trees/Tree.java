package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.util.SurfaceCreator;
import pepse.util.TransitionExecuter;

import java.util.HashSet;
import java.util.Random;

/**
 * adds trees to the game
 *
 * @author Ruth Yukhnovetsky
 */
public class Tree implements SurfaceCreator {

    private final static int[] TREE_HEIGHT_OPTIONS = new int[]{250, 400, 120, 500};
    private final static int[] TREE_WIDTH_OPTIONS = new int[]{10, 15, 20, 25};

    private final GameObjectCollection gameObjectCollection;
    private final int layer;
    private final Vector2 windowsDim;
    private final Random random;
    private final TreesLocationGetter treesLocationGetter;
    private final TransitionExecuter leafOpacity;
    private final TransitionExecuter leafAngle;

    /**
     * creates tree in the game
     *
     * @param gameObjectCollection game object collections objects
     * @param windowDim            game window dim size
     * @param layer                trees wanted layer ids
     * @param random               tree seed
     * @param leafOpacity          leaf change opacity transactions
     * @param leafMovement         leaf change angle transactions
     */
    public Tree(GameObjectCollection gameObjectCollection,
                Vector2 windowDim,
                int layer,
                Random random,
                TreesLocationGetter treesLocationGetter,
                TransitionExecuter leafOpacity,
                TransitionExecuter leafMovement) {
        this.gameObjectCollection = gameObjectCollection;
        this.layer = layer;
        this.windowsDim = windowDim;
        this.random = random;
        this.treesLocationGetter = treesLocationGetter;
        this.leafOpacity = leafOpacity;
        this.leafAngle = leafMovement;
    }

    /**
     * creating trees in the game
     *
     * @param minX start range of tree creation
     * @param maxX end range of tree creation
     */
    public void createInRange(int minX, int maxX) {
        var locations = this.treesLocationGetter.getTreesLocationInRange(minX, maxX);
        for (var location : locations) {
            var treeBottom = new Vector2(location.x(), location.y());
            var createdTree = TreeItem.create(
                    this.gameObjectCollection,
                    treeBottom,
                    layer,
                    windowsDim,
                    TREE_HEIGHT_OPTIONS[random.nextInt(TREE_HEIGHT_OPTIONS.length)],
                    TREE_WIDTH_OPTIONS[random.nextInt(TREE_WIDTH_OPTIONS.length)],
                    this.leafOpacity,
                    this.leafAngle,
                    this.random);
            gameObjectCollection.addGameObject(createdTree, layer);
        }
    }
}
