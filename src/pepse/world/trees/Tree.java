package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.util.SurfaceCreator;
import pepse.world.trees.leaf.LeafLifeDeathCycle;
import pepse.world.trees.leaf.LeafTransitionHandler;

/**
 * adds trees to the game
 * @author Ruth Yukhnovetsky
 */
public class Tree implements SurfaceCreator {

    private final GameObjectCollection gameObjectCollection;
    private final int layer;
    private final Vector2 windowsDim;
    private final TreesLocationGetter treesLocationGetter;
    private final LeafTransitionHandler leafTransitionHandler;
    private final LeafLifeDeathCycle leafLifeDeathCycle;

    /**
     * tree constructor
     * @param gameObjectCollection game obj collection
     * @param windowDim window dimensions
     * @param layer layer of tree
     * @param treesLocationGetter gets location
     * @param leafTransitionHandler leaf transition handler
     * @param leafLifeDeathCycle leaf's life and death cycle
     */
    public Tree(GameObjectCollection gameObjectCollection,
                Vector2 windowDim,
                int layer,
                TreesLocationGetter treesLocationGetter,
                LeafTransitionHandler leafTransitionHandler,
                LeafLifeDeathCycle leafLifeDeathCycle) {
        this.gameObjectCollection = gameObjectCollection;
        this.layer = layer;
        this.windowsDim = windowDim;
        this.treesLocationGetter = treesLocationGetter;
        this.leafTransitionHandler = leafTransitionHandler;
        this.leafLifeDeathCycle = leafLifeDeathCycle;
    }

    /**
     * creating trees in the game
     * @param minX start range of tree creation
     * @param maxX end range of tree creation
     */
    public void createInRange(int minX, int maxX) {
        var treesData = this.treesLocationGetter.getTreesDataInRange(minX, maxX);
        for (var treeData : treesData) {

            var treeBottom = treeData.getLocationVector();
            var treeDim = treeData.getSizeVector();

            var createdTree = TreeItem.create(
                    this.gameObjectCollection,
                    treeBottom,
                    windowsDim,
                    treeDim,
                    this.leafTransitionHandler,
                    this.leafLifeDeathCycle);

            gameObjectCollection.addGameObject(createdTree, layer);
        }
    }
}
