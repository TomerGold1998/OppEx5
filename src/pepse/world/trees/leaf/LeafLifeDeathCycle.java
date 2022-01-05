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
 *
 * @author Ruth Yukhnovetsky
 */
public class LeafLifeDeathCycle {
    private final TransitionCreator leafFallowingTransformation;
    private final GameObjectCollection collection;
    private final Random random;
    private final static int GRAVITY = 70;

    public LeafLifeDeathCycle(
            TransitionCreator leafFallowingTransformation,
            GameObjectCollection collection,
            Random random) {
        this.leafFallowingTransformation = leafFallowingTransformation;
        this.collection = collection;
        this.random = random;
    }

    public void executeCycle(Leaf leaf) {
        var lifeLength = getLeafLifeLength();
        new ScheduledTask(
                leaf,
                lifeLength + TransitionConfiguration.LEAF_FADEOUT,
                true,
                () -> executeLeafFall(leaf));
    }

    private void executeLeafFall(Leaf leaf) {
        collection.removeGameObject(leaf, GameLayers.LEAF_LAYER);
        var fallingLeaf = new FallingLeaf(leaf, this.leafFallowingTransformation);

        fallingLeaf.transform().setAccelerationY(GRAVITY);
        collection.addGameObject(fallingLeaf, GameLayers.LEAF_LAYER);

        fallingLeaf.renderer().fadeOut(
                TransitionConfiguration.LEAF_FADEOUT,
                () -> {
                    collection.removeGameObject(fallingLeaf, GameLayers.LEAF_LAYER);
                    collection.addGameObject(leaf, GameLayers.LEAF_LAYER);
                });

    }

    private float getLeafLifeLength() {
        return this.random.nextInt(GameObjectsConfiguration.LEAF_MAX_LIFE -
                GameObjectsConfiguration.LEAF_MIN_LIFE) + GameObjectsConfiguration.LEAF_MIN_LIFE;
    }
}
