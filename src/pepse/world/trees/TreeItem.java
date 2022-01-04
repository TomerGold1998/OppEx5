package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.configuration.GameLayers;
import pepse.util.ColorSupplier;
import pepse.util.GameObjectsContainer;
import pepse.util.TemporaryItem;
import pepse.world.trees.leaf.Leaf;
import pepse.world.trees.leaf.LeafLifeDeathCycle;
import pepse.world.trees.leaf.LeafTransitionHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * represent a game tree
 */
public class TreeItem extends GameObjectsContainer implements TemporaryItem {

    private final static Color TRUNK_COLOR = new Color(100, 50, 20);
    private final static Color LEAVES_COLOR = new Color(50, 200, 30);

    private final static int LEAF_SIZE = 25;
    private final LeafLifeDeathCycle leafLifeDeathCycle;

    private final ArrayList<GameObject> leafList;
    private final GameObjectCollection collection;
    private final LeafTransitionHandler leafTransitionHandler;

    /**
     * constrctor for the tree object
     *
     * @param topLeftCorner tree top left position
     * @param dimensions    tree truck size
     * @param renderable    truck
     * @param collection    game object collections
     */
    public TreeItem(Vector2 topLeftCorner,
                    Vector2 dimensions,
                    Renderable renderable,
                    GameObjectCollection collection,
                    LeafTransitionHandler leafTransitionHandler,
                    LeafLifeDeathCycle leafLifeDeathCycle) {
        super(topLeftCorner, dimensions, renderable);

        this.collection = collection;
        this.leafTransitionHandler = leafTransitionHandler;
        this.leafLifeDeathCycle = leafLifeDeathCycle;
        this.leafList = new ArrayList<>();

        createLeaves(topLeftCorner, dimensions);

    }

    private void createLeaves(Vector2 top, Vector2 trunkDimensions) {
        //creating leaves for each trunk
        int leafyRange = (int) trunkDimensions.y() / 4;
        int leafTop = (int) (top.x() + trunkDimensions.x() / 2);

        int x0 = leafTop - leafyRange - (int) (trunkDimensions.x() / 2);
        int x1 = leafTop + leafyRange;
        int y0 = (int) top.y() - leafyRange;
        int y1 = (int) top.y() + leafyRange;
        for (int i = x0; i < x1; i += LEAF_SIZE) {

            var leafRectangle = new RectangleRenderable(ColorSupplier.approximateColor(LEAVES_COLOR));
            for (int j = y0; j < y1; j += LEAF_SIZE) {
                var leaf = new Leaf(new Vector2(i, j),
                        new Vector2(LEAF_SIZE, LEAF_SIZE),
                        leafRectangle);

                this.leafTransitionHandler.handleLeafTransition(leaf);
                this.leafLifeDeathCycle.executeCycle(leaf);
                this.leafList.add(leaf);

                this.collection.addGameObject(leaf, GameLayers.LEAF_LAYER);
            }
        }
    }

    /**
     * inner game object getter
     *
     * @return list of leaves
     */
    @Override
    public List<GameObject> getInnerGameObjects() {
        return this.leafList;
    }

    /**
     * constructor of tree item
     *
     * @param collection     collection of game objects
     * @param bottomPosition lowest point
     * @param windowsDim     window dimensions
     * @return tree item
     */
    public static TreeItem create(
            GameObjectCollection collection,
            Vector2 bottomPosition,
            Vector2 windowsDim,
            Vector2 truckDim,
            LeafTransitionHandler leafTransitionHandler,
            LeafLifeDeathCycle leafLifeDeathCycle) {

        var treeTopY = windowsDim.y() - bottomPosition.y() - truckDim.y();

        return new TreeItem(new Vector2(bottomPosition.x(), treeTopY),
                truckDim,
                new RectangleRenderable(TRUNK_COLOR),
                collection,
                leafTransitionHandler,
                leafLifeDeathCycle);
    }
}
