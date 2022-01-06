package pepse.world.trees.leaf;

import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import pepse.configuration.GameLayers;
import pepse.configuration.GameObjectsConfiguration;
import pepse.configuration.TransitionConfiguration;
import pepse.util.TransitionCreator;

import java.util.Random;

/**
 * handle with leaf life and death cycle
 * @author Ruth Yukhnovetsky
 */
public class LeafLifeDeathCycle {
    private final TransitionCreator leafFallingTransformation;
    private final GameObjectCollection collection;
    private final Random random;
    private final static int GRAVITY = 70;

    /**
     * constructor
     * @param leafFallingTransformation transition of falling leaf
     * @param collection game obj collection
     * @param random random factor
     */
    public LeafLifeDeathCycle(
            TransitionCreator leafFallingTransformation,
            GameObjectCollection collection,
            Random random) {
        this.leafFallingTransformation = leafFallingTransformation;
        this.collection = collection;
        this.random = random;
    }

    /**
     * executes a cycle for each leaf that was created
     * @param leaf leaf
     */
    public void executeCycle(Leaf leaf) {
        var lifeLength = getLeafLifeLength();
        new ScheduledTask(
                leaf,
                lifeLength + TransitionConfiguration.LEAF_FADEOUT,
                true,
                () -> executeLeafFall(leaf));
    }

    private void executeLeafFall(Leaf leaf) {
        //replace original leaf with falling leaf:
        collection.removeGameObject(leaf, GameLayers.LEAF_LAYER);
        var fallingLeaf = new FallingLeaf(leaf, this.leafFallingTransformation);

        fallingLeaf.transform().setAccelerationY(GRAVITY);
        collection.addGameObject(fallingLeaf, GameLayers.LEAF_LAYER);
        //when fadeout finishes, remove the leaf that fell and replace with a new one on tree
        fallingLeaf.renderer().fadeOut(
                TransitionConfiguration.LEAF_FADEOUT,
                () -> {collection.removeGameObject(fallingLeaf, GameLayers.LEAF_LAYER);
                       collection.addGameObject(leaf, GameLayers.LEAF_LAYER);
                });
    }

    private float getLeafLifeLength() {
        //gets a random lifetime length for a leaf
        return this.random.nextInt(GameObjectsConfiguration.LEAF_MAX_LIFE -
                GameObjectsConfiguration.LEAF_MIN_LIFE) + GameObjectsConfiguration.LEAF_MIN_LIFE;
    }
}
