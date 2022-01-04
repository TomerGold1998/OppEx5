package pepse.world.trees.leaf;

import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.configuration.GameObjectsConfiguration;
import pepse.configuration.TransitionConfiguration;
import pepse.util.TransitionExecuter;

import java.util.Random;

/**
 * handle with leaf life and death cycle
 *
 * @author Ruth Yukhnovetsky
 */
public class LeafLifeDeathCycle {
    private final TransitionExecuter leafFallowingTransformation;
    private final Random random;
    private final static int GRAVITY = 70;

    public LeafLifeDeathCycle(
            TransitionExecuter leafFallowingTransformation,
            Random random) {
        this.leafFallowingTransformation = leafFallowingTransformation;
        this.random = random;
    }

    public void executeCycle(Leaf leaf) {
        var leafOriginLocation = leaf.getCenter();
        var lifeLength = getLeafLifeLength();
        new ScheduledTask(
                leaf,
                lifeLength,
                false,
                () -> executeLeafFall(leaf, leafOriginLocation));
    }

    private void executeLeafFall(Leaf leaf, Vector2 leafOriginLocation) {
        var horizontalMovement = this.leafFallowingTransformation.createTransitions(
                TransitionConfiguration.LEAF_WOOBLING_CYCLE,
                leaf
        )[0];
        leaf.setTemporaryTransition(horizontalMovement);
        leaf.transform().setAccelerationY(GRAVITY);

        leaf.renderer().fadeOut(
                TransitionConfiguration.LEAF_FADEOUT,
                () -> {
                    leaf.setCenter(leafOriginLocation);
                    this.executeCycle(leaf);
                });

    }

    private float getLeafLifeLength() {
        return this.random.nextInt(GameObjectsConfiguration.LEAF_MAX_LIFE -
                GameObjectsConfiguration.LEAF_MIN_LIFE) + GameObjectsConfiguration.LEAF_MIN_LIFE;
    }
}
