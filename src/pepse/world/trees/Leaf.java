package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

import java.util.Random;

/**
 * Leaf class to control movement
 *
 * @author Ruth Yukhnovetsky
 */
public class Leaf extends GameObject {
    private Random random;
    private Vector2 newDimensions = new Vector2(25, 45);
    private final float[] arrayOfDelay = new float[]{0.1f, 0.3f, 0.5f};

    private final static int LEAF_TRANSACTION_LENGTH = 5;
    private final static int RANDOM_NUMBER_RANGE = 10;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Leaf(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable,
                TransitionExecuter leafOpacityTransitionExecuter,
                TransitionExecuter leafAngleTransitionExecuter,
                float cycleLength,
                Random random) {
        super(topLeftCorner, dimensions, renderable);
        this.random = random;
        executeRandomTransactions(leafOpacityTransitionExecuter, leafAngleTransitionExecuter, cycleLength);
    }

    private void executeRandomTransactions(TransitionExecuter leafOpacityTransitionExecuter,
                                           TransitionExecuter leafAngleTransitionExecuter,
                                           float cycleLength) {
        int leafRandomNumber = this.random.nextInt(RANDOM_NUMBER_RANGE);
        boolean repeat = this.random.nextBoolean();
        float waitTime = this.arrayOfDelay[this.random.nextInt(this.arrayOfDelay.length)];
        if (leafRandomNumber == 1) {
            new ScheduledTask(this,
                    waitTime,
                    repeat,
                    () -> leafAngleTransitionExecuter.executeTransition(cycleLength, this));
        }
        if (leafRandomNumber <= 3) {
            leafOpacityTransitionExecuter.executeTransition(LEAF_TRANSACTION_LENGTH, this);
        }
    }

}