package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.configuration.GameLayers;
import pepse.configuration.GameObjectsConfiguration;
import pepse.util.ColorSupplier;
import pepse.util.TemporaryItem;
import pepse.util.unique_game_objects.GameObjectsContainer;
import pepse.world.trees.leaf.Leaf;
import pepse.world.trees.leaf.LeafLifeDeathCycle;
import pepse.world.trees.leaf.LeafTransitionHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * represent a tree item
 * @author Ruth Yukhnovetsky
 */
public class TreeItem extends GameObjectsContainer implements TemporaryItem {

    private final static Color TRUNK_COLOR = new Color(100, 50, 20);
    private final static Color LEAVES_COLOR = new Color(50, 200, 30);
    private final LeafLifeDeathCycle leafLifeDeathCycle;

    private final ArrayList<GameObject> leafList;
    private final GameObjectCollection collection;
    private final LeafTransitionHandler leafTransitionHandler;

    /**
     * constructor for tree object
     * @param topLeftCorner top left corner of tree trunk
     * @param dimensions tree dimensions
     * @param renderable renderable
     * @param collection obj collection
     * @param leafTransitionHandler transition handler of leaf
     * @param leafLifeDeathCycle leaf's life and cycle
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

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);

        createLeaves(topLeftCorner, dimensions);
    }

    /**
     * Creating leafs around the trunk
     *
     * @param topLeftPosition the tree top location
     * @param trunkDimensions the tree's trunk dimensions
     */
    private void createLeaves(Vector2 topLeftPosition, Vector2 trunkDimensions) {
        //creating leaves for each trunk
        int leafyRange = (int) trunkDimensions.y() / 4;
        int leafTop = (int) (topLeftPosition.x() + trunkDimensions.x() / 2);

        int minLeafRangeX = leafTop - leafyRange - (int) (trunkDimensions.x() / 2);
        int maxLeafRangeX = leafTop + leafyRange;
        int minLeafRangeY = (int) topLeftPosition.y() - leafyRange;
        int maxLeafRangeY = (int) topLeftPosition.y() + leafyRange;

        for (int x = minLeafRangeX; x < maxLeafRangeX; x += GameObjectsConfiguration.LEAF_BASE_SIZE.x())
            for (int y = minLeafRangeY; y < maxLeafRangeY; y += GameObjectsConfiguration.LEAF_BASE_SIZE.y())
                addLeafToGame(new Vector2(x, y));


    }

    /**
     * adds leaf to the game
     *
     * @param location location to add the leaf to
     */
    private void addLeafToGame(Vector2 location) {
        var leafRectangle = new RectangleRenderable(ColorSupplier.approximateColor(LEAVES_COLOR));
        var leaf = new Leaf(location,
                GameObjectsConfiguration.LEAF_BASE_SIZE,
                leafRectangle);

        // handle the adding for transitions for the tree
        this.leafTransitionHandler.handleLeafTransition(leaf);

        // handle the leaf life time
        this.leafLifeDeathCycle.executeCycle(leaf);
        this.leafList.add(leaf);

        this.collection.addGameObject(leaf, GameLayers.LEAF_LAYER);
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
     * removes all of the leafs from the game!
     */
    @Override
    public void removeInnerGameObjects() {
        for (var leaf : this.leafList) {
            this.collection.removeGameObject(leaf, GameLayers.LEAF_LAYER);
        }
    }

    /**
     * creation of tree item
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
        //the top left corner where leaves will start to grow
        var treeTopY = windowsDim.y() - bottomPosition.y() - truckDim.y();

        return new TreeItem(new Vector2(bottomPosition.x(), treeTopY),
                truckDim,
                new RectangleRenderable(TRUNK_COLOR),
                collection,
                leafTransitionHandler,
                leafLifeDeathCycle);
    }
}
