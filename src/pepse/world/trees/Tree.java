package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.util.GroundHeightCalculator;
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

    private static final int TREE_MIN_BUFFER = 250;
    private static final int TREE_RANDOM_CHANCE = 10;

    private final GroundHeightCalculator groundHeightCalculator;
    private final GameObjectCollection gameObjectCollection;
    private final int layer;
    private final Vector2 windowsDim;
    private final int treeHeight;
    private final int treeWidth;
    private final Random random;
    private final TransitionExecuter leafOpacity;
    private final TransitionExecuter leafAngle;

    private final HashSet<Integer> plantedTreeLocation;

    /**
     * creates tree in the game
     *
     * @param groundHeightCalculator groundHeightCalculator object in order to get the ground height at position
     * @param gameObjectCollection   game object collections objects
     * @param windowDim              game window dim size
     * @param layer                  trees wanted layer ids
     * @param treeHeight             tree height
     * @param treeWidth              tree width
     * @param seed                   tree seed
     * @param leafOpacity         leaf change opacity transactions
     * @param leafMovement        leaf change angle transactions
     */
    public Tree(GroundHeightCalculator groundHeightCalculator,
                GameObjectCollection gameObjectCollection,
                Vector2 windowDim,
                int layer,
                int treeHeight,
                int treeWidth,
                int seed,
                TransitionExecuter leafOpacity,
                TransitionExecuter leafMovement) {
        this.groundHeightCalculator = groundHeightCalculator;
        this.gameObjectCollection = gameObjectCollection;
        this.layer = layer;
        this.windowsDim = windowDim;
        this.treeHeight = treeHeight;
        this.treeWidth = treeWidth;
        this.random = new Random(seed);
        this.leafOpacity = leafOpacity;
        this.leafAngle = leafMovement;
        plantedTreeLocation = new HashSet<>();
    }

    /**
     * creating trees in the game
     *
     * @param minX start range of tree creation
     * @param maxX end range of tree creation
     */
    public void createInRange(int minX, int maxX) {
        for (int i = minX; i < maxX; i += TREE_MIN_BUFFER) {
            if (plantedTreeLocation.contains(i) ||
                random.nextInt(TREE_RANDOM_CHANCE) == 1) {
                var treeBottom = new Vector2(i, this.groundHeightCalculator.groundHeightAt(i));
                var createdTree = TreeItem.create(
                        this.gameObjectCollection,
                        treeBottom,
                        layer,
                        windowsDim,
                        treeHeight,
                        treeWidth,
                        this.leafOpacity,
                        this.leafAngle,
                        this.random);
                plantedTreeLocation.add(i);
                gameObjectCollection.addGameObject(createdTree, layer);
            }
        }
    }

}
