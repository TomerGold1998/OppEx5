package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

import java.util.Random;

/**
 * Leaf class to control movement
 * @author Ruth Yukhnovetsky
 */
public class Leaf extends GameObject {
    private Vector2 newDimensions = new Vector2(25,45);
    private final TransitionExecuter leafAngleTransitionExecuter;
    private final TransitionExecuter leafOpacityTransitionExecuter;

    private final static int LEAF_TRANSACTION_LENGTH = 5;
    private final static int RANDOM_NUMBER_RANGE = 10;
    /**
     * Construct a new GameObject instance.
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
                float cycleLength) {
        super(topLeftCorner, dimensions, renderable);
        this.leafOpacityTransitionExecuter = leafOpacityTransitionExecuter;
        this.leafAngleTransitionExecuter = leafAngleTransitionExecuter;
        Random random = new Random();
        int rand = random.nextInt(RANDOM_NUMBER_RANGE);
        if(rand  == 1){
            leafAngleTransitionExecuter.executeTransition(cycleLength, this);
        }
        if (rand <= 3) {
            leafOpacityTransitionExecuter.executeTransition(LEAF_TRANSACTION_LENGTH, this);
        }
    }
}
