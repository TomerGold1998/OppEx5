package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;

/**
 * wooboling horizontal movement transition executor
 *
 * @author Ruth Yukhnovetsky
 */
public class HorizontalTransitionCreator implements TransitionCreator {

    private final float horizontalSpeed;
    private final Transition.Interpolator<Float> interpolator;
    private final Transition.TransitionType transitionType;
    private final Runnable onTransitionFinishedCallback;

    /**
     * constructor
     *
     * @param horizontalSpeed              max speed of the horizontal movement
     * @param interpolator                 interpolator
     * @param transitionType               type of transition
     * @param onTransitionFinishedCallback callback
     */
    public HorizontalTransitionCreator(float horizontalSpeed,
                                       Transition.Interpolator<Float> interpolator,
                                       Transition.TransitionType transitionType,
                                       Runnable onTransitionFinishedCallback) {

        this.horizontalSpeed = horizontalSpeed;
        this.interpolator = interpolator;
        this.transitionType = transitionType;
        this.onTransitionFinishedCallback = onTransitionFinishedCallback;
    }

    /**
     * Creates a horizontal movement transition
     *
     * @param cycleLength the transition cycle length
     * @param gameObject  the game object that transition wil be executed on
     * @return created transitions
     */
    @Override
    public Transition[] createTransitions(float cycleLength, GameObject gameObject) {
        var horizontalTransition = new Transition<>(
                gameObject,
                gameObject.transform()::setVelocityX,
                this.horizontalSpeed * -1,
                this.horizontalSpeed,
                this.interpolator,
                cycleLength,
                this.transitionType,
                this.onTransitionFinishedCallback);

        return new Transition[]{horizontalTransition};
    }
}
