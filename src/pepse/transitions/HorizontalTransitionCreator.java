package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;
import pepse.util.TransitionCreator;

/**
 * horizontal movement transition executor
 *
 * @author Ruth Yukhnovetsky
 */
public class HorizontalTransitionCreator implements TransitionCreator {

    private final float finalValue;
    private final Transition.Interpolator<Float> interpolator;
    private final Transition.TransitionType transitionType;
    private final Runnable onTransitionFinishedCallback;

    /**
     * constructor
     *
     * @param finalValue                   final location of horizontal movement
     * @param interpolator                 interpolator
     * @param transitionType               type of transition
     * @param onTransitionFinishedCallback callback
     */
    public HorizontalTransitionCreator(float finalValue,
                                       Transition.Interpolator<Float> interpolator,
                                       Transition.TransitionType transitionType,
                                       Runnable onTransitionFinishedCallback) {

        this.finalValue = finalValue;
        this.interpolator = interpolator;
        this.transitionType = transitionType;
        this.onTransitionFinishedCallback = onTransitionFinishedCallback;
    }

    @Override
    public Transition[] createTransitions(float cycleLength, GameObject gameObject) {
        var horizontalTransition = new Transition<>(
                gameObject,
                gameObject.transform()::setVelocityX,
                this.finalValue * -1,
                this.finalValue,
                this.interpolator,
                cycleLength,
                this.transitionType,
                this.onTransitionFinishedCallback);

        return new Transition[]{horizontalTransition};
    }
}
