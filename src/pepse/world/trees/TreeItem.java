package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.GameObjectsContainer;
import pepse.util.TemporaryItem;
import pepse.util.TransitionExecuter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * represent a game tree
 */
public class TreeItem extends GameObjectsContainer implements TemporaryItem {

    private final static Color TRUNK_COLOR = new Color(100, 50, 20);
    private final static Color LEAVES_COLOR = new Color(50, 200, 30);

    private final static int TREE_BOTTOM_BUFFER = 5;
    private final static int LEAF_SIZE = 30;

    private final ArrayList<GameObject> leafList;

    private final TransitionExecuter leafOpacityTransitionExecuter;
    private final TransitionExecuter leafAngleTransitionExecuter;
    private final GameObjectCollection collection;
    private final int layer;
    private final Random random;

    /**
     * constrctor for the tree object
     *
     * @param topLeftCorner                 tree top left position
     * @param dimensions                    tree truck size
     * @param renderable                    truck
     * @param layer                         layer number to be added
     * @param collection                    game object collections
     * @param leafOpacityTransitionExecuter transition executer for the leaf color change
     * @param random                        random seed
     */
    public TreeItem(Vector2 topLeftCorner,
                    Vector2 dimensions,
                    Renderable renderable,
                    int layer,
                    GameObjectCollection collection,
                    TransitionExecuter leafOpacityTransitionExecuter,
                    TransitionExecuter leafAngleTransitionExecuter,
                    Random random) {
        super(topLeftCorner, dimensions, renderable);

        this.leafOpacityTransitionExecuter = leafOpacityTransitionExecuter;
        this.leafAngleTransitionExecuter = leafAngleTransitionExecuter;
        this.collection = collection;
        this.layer = layer;
        this.random = random;
        this.leafList = new ArrayList<>();

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
                var leaf = new Leaf(new Vector2(i, j),
                        new Vector2(LEAF_SIZE, LEAF_SIZE),
                        leafRectangle,
                        leafOpacityTransitionExecuter,
                        leafAngleTransitionExecuter,
                        5,
                        this.random);
                this.leafList.add(leaf);
                this.collection.addGameObject(leaf, layer);
            }
        }
    }

    @Override
    public List<GameObject> getInnerGameObjects() {
        return this.leafList;
    }

    /**
     * Create function for the tree item
     *
     * @param collection     game object collection
     * @param bottomPosition tree bottom position
     * @param layer          tree layer id
     * @param windowsDim     game windom dim
     * @param treeHeight     tree height
     * @param truckWidth     tree trunk width
     * @param leafOpacity    leaf transaction
     * @param random           random number seed
     * @return new created tree item
     */
    public static TreeItem create(
            GameObjectCollection collection,
            Vector2 bottomPosition,
            int layer,
            Vector2 windowsDim,
            int treeHeight,
            int truckWidth,
            TransitionExecuter leafOpacity,
            TransitionExecuter leafMovement,
            Random random) {

        var treeTopY = windowsDim.y() - bottomPosition.y() - treeHeight + TREE_BOTTOM_BUFFER;

        return new TreeItem(new Vector2(bottomPosition.x(), treeTopY),
                new Vector2(truckWidth, treeHeight),
                new RectangleRenderable(TRUNK_COLOR),
                layer,
                collection,
                leafOpacity,
                leafMovement,
                random);
    }
}
