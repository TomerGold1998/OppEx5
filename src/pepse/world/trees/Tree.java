package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.util.GroundHeightCalculator;
import pepse.util.TransitionExecuter;
import java.util.Random;

/**
 * adds trees to the game
 *
 * @author Ruth Yukhnovetsky
 */
public class Tree {

    private static final int TREE_MIN_BUFFER = 100;
    private static final int TREE_RANDOM_CHANCE = 3;

    private final GroundHeightCalculator groundHeightCalculator;
    private final GameObjectCollection gameObjectCollection;
    private final int layer;
    private final Vector2 windowsDim;
    private final int treeHeight;
    private final int treeWidth;
    private final Random random;
    private final int seed;
    private final TransitionExecuter leafTransition;

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
     * @param leafTransition         leaf change opacity transactions
     */
    public Tree(GroundHeightCalculator groundHeightCalculator,
                GameObjectCollection gameObjectCollection,
                Vector2 windowDim,
                int layer,
                int treeHeight,
                int treeWidth,
                int seed,
                TransitionExecuter leafTransition) {
        this.groundHeightCalculator = groundHeightCalculator;
        this.gameObjectCollection = gameObjectCollection;
        this.layer = layer;
        this.windowsDim = windowDim;
        this.treeHeight = treeHeight;
        this.treeWidth = treeWidth;
        this.seed = seed;
        this.random = new Random(seed);
        this.leafTransition = leafTransition;
    }

    /**
     * creating trees in the game
     *
     * @param minX start range of tree creation
     * @param maxX end range of tree creation
     */
    public void createInRange(int minX, int maxX) {
        for (int i = minX; i < maxX; i += TREE_MIN_BUFFER) {
            if (random.nextInt(TREE_RANDOM_CHANCE) == 1) {
                var treeBottom = new Vector2(i, this.groundHeightCalculator.groundHeightAt(i));
                var createdTree = TreeItem.create(
                        this.gameObjectCollection,
                        treeBottom,
                        layer,
                        windowsDim,
                        treeHeight,
                        treeWidth,
                        this.leafTransition,
                        this.seed
                );
                gameObjectCollection.addGameObject(createdTree, layer);
            }
        }
    }

}
