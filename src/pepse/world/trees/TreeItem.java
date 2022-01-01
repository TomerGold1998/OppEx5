package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

import java.awt.*;
import java.util.Random;

/**
 * represent a game tree
 */
public class TreeItem extends GameObject {

    private final static Color TRUNK_COLOR = new Color(100, 50, 20);
    private final static Color LEAVES_COLOR = new Color(50, 200, 30);

    private final static int TREE_BOTTOM_BUFFER = 5;
    private final static int LEAF_SIZE = 30;
    private final static int LEAF_TRANSACTION_LENGTH = 5;
    private final static int RANDOM_NUMBER_RANGE = 2;

    private final TransitionExecuter transitionExecuter;
    private final GameObjectCollection collection;
    private final int layer;
    private final Random random;

    /**
     * constrctor for the tree object
     *
     * @param topLeftCorner      tree top left position
     * @param dimensions         tree truck size
     * @param renderable         truck
     * @param layer              layer number to be added
     * @param collection         game object collections
     * @param transitionExecuter transition executer for the leaf color change
     * @param seed               random seed
     */
    public TreeItem(Vector2 topLeftCorner,
                    Vector2 dimensions,
                    Renderable renderable,
                    int layer,
                    GameObjectCollection collection,
                    TransitionExecuter transitionExecuter,
                    int seed) {
        super(topLeftCorner, dimensions, renderable);

        this.transitionExecuter = transitionExecuter;
        this.collection = collection;
        this.layer = layer;
        this.random = new Random(seed);

        createLeaves(topLeftCorner, dimensions);
    }

    private void createLeaves(Vector2 top, Vector2 trunkDimensions) {
        int leafyRange = (int) trunkDimensions.y() / 3;
        int leafTop = (int) (top.x() + trunkDimensions.x() / 2);

        int x0 = leafTop - leafyRange;
        int x1 = leafTop + leafyRange;
        int y0 = (int) top.y() - leafyRange;
        int y1 = (int) top.y() + leafyRange;
        for (int i = x0; i < x1; i += LEAF_SIZE) {

            var leafRectangle = new RectangleRenderable(LEAVES_COLOR);
            for (int j = y0; j < y1; j += LEAF_SIZE) {
                var leaf = new GameObject(new Vector2(i, j),
                        new Vector2(LEAF_SIZE, LEAF_SIZE),
                        leafRectangle);

                int rand = random.nextInt(RANDOM_NUMBER_RANGE);
                if (rand == 1) {
                    transitionExecuter.executeTransition(LEAF_TRANSACTION_LENGTH, leaf);
                }
                this.collection.addGameObject(leaf, layer);
            }
        }


    }

    /**
     * Create function for the tree item
     *
     * @param collection      game object collection
     * @param bottomPosition  tree bottom position
     * @param layer           tree layer id
     * @param windowsDim      game windom dim
     * @param treeHeight      tree height
     * @param truckWidth      tree trunk width
     * @param leafTransaction leaf transaction
     * @param seed            random number seed
     * @return new created tree item
     */
    public static TreeItem create(
            GameObjectCollection collection,
            Vector2 bottomPosition,
            int layer,
            Vector2 windowsDim,
            int treeHeight,
            int truckWidth,
            TransitionExecuter leafTransaction,
            int seed) {

        var treeTopY = windowsDim.y() - bottomPosition.y() - treeHeight + TREE_BOTTOM_BUFFER;

        return new TreeItem(new Vector2(bottomPosition.x(), treeTopY),
                new Vector2(truckWidth, treeHeight),
                new RectangleRenderable(TRUNK_COLOR),
                layer,
                collection,
                leafTransaction,
                seed);
    }

}
