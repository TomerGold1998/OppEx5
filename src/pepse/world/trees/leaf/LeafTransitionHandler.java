package pepse.world.trees.leaf;

import danogl.components.ScheduledTask;
import pepse.configuration.TransitionConfiguration;
import pepse.util.TransitionCreator;

import java.util.Random;

public class LeafTransitionHandler {
    private final TransitionCreator opacityTransition;
    private final TransitionCreator angleAndSizeTransition;
    private final float cycleLength;
    private final Random random;

    private final static int OPACITY_MIN_CHANCE = 3;

    private final static int RANDOM_NUMBER_RANGE = 5;

    public LeafTransitionHandler(TransitionCreator opacityTransition,
                                 TransitionCreator angleAndSizeTransition,
                                 float cycleLength,
                                 Random random) {

        this.opacityTransition = opacityTransition;
        this.angleAndSizeTransition = angleAndSizeTransition;
        this.cycleLength = cycleLength;
        this.random = random;
    }

    public void handleLeafTransition(Leaf leaf) {
        int randomNumber = this.random.nextInt(RANDOM_NUMBER_RANGE);
        boolean repeat = this.random.nextBoolean();
        float waitTime = TransitionConfiguration.LEAF_DELAYS[
                this.random.nextInt(TransitionConfiguration.LEAF_DELAYS.length)];

        if (randomNumber == 1) { //activating angle and size transaction
            new ScheduledTask(leaf,
                    waitTime,
                    repeat,
                    () -> this.angleAndSizeTransition.createTransitions(cycleLength, leaf));
        }
        if (randomNumber <= OPACITY_MIN_CHANCE) {
            this.opacityTransition.createTransitions(cycleLength - waitTime, leaf);
        }
    }
}
