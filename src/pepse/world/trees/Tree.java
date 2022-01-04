package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;
import pepse.configuration.GameObjectsConfiguration;
import pepse.world.trees.leaf.LeafLifeDeathCycle;
import pepse.world.trees.leaf.LeafTransitionHandler;
import pepse.util.SurfaceCreator;

import java.util.Random;

/**
 * adds trees to the game
 *
 * @author Ruth Yukhnovetsky
 */
public class Tree implements SurfaceCreator {



    private final GameObjectCollection gameObjectCollection;
    private final int layer;
    private final Vector2 windowsDim;
    private final Random random;
    private final TreesLocationGetter treesLocationGetter;
    private final LeafTransitionHandler leafTransitionHandler;
    private final LeafLifeDeathCycle leafLifeDeathCycle;

    /**
     * creates tree in the game
     *  @param gameObjectCollection game object collections objects
     * @param windowDim            game window dim size
     * @param layer                trees wanted layer ids
     * @param random               tree seed
     * @param leafLifeDeathCycle
     */
    public Tree(GameObjectCollection gameObjectCollection,
                Vector2 windowDim,
                int layer,
                Random random,
                TreesLocationGetter treesLocationGetter,
                LeafTransitionHandler leafTransitionHandler,
                LeafLifeDeathCycle leafLifeDeathCycle) {
        this.gameObjectCollection = gameObjectCollection;
        this.layer = layer;
        this.windowsDim = windowDim;
        this.random = random;
        this.treesLocationGetter = treesLocationGetter;
        this.leafTransitionHandler = leafTransitionHandler;
        this.leafLifeDeathCycle = leafLifeDeathCycle;
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
            var trunkDim = getTrunkDim();

            var createdTree = TreeItem.create(
                    this.gameObjectCollection,
                    treeBottom,
                    windowsDim,
                    trunkDim,
                    this.leafTransitionHandler,
                    this.leafLifeDeathCycle);
            gameObjectCollection.addGameObject(createdTree, layer);
        }
    }

    private Vector2 getTrunkDim() {
        return new Vector2(
                GameObjectsConfiguration.TREE_WIDTH_OPTIONS[
                        random.nextInt(GameObjectsConfiguration.TREE_WIDTH_OPTIONS.length)],
                GameObjectsConfiguration.TREE_HEIGHT_OPTIONS[
                        random.nextInt(GameObjectsConfiguration.TREE_HEIGHT_OPTIONS.length)]);
    }
}
