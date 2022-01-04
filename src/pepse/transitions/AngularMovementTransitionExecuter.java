package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

/**
 * execute angular movement transition
 *
 * @author Tomer Goldberg
 */
public class AngularMovementTransitionExecuter implements TransitionExecuter {

    private final float initalAngle;
    private final float finalAngle;
    private final Vector2 windowDim;
    private final Transition.Interpolator<Float> interpolator;
    private final Transition.TransitionType transitionType;
    private final Runnable onTransitionFinishedCallback;

    private final static float MAJOR_RADIUS_CHANGE = 2f;
    private final static float MINOR_RADIUS_CHANGE = 2.2f;

    /**
     * Creates new AngularMovementTransitionExecuter
     *
     * @param initalAngle                  start angle for the angular movement
     * @param finalAngle                   end angle for the angular movement
     * @param windowDim                    the window size (used for calculating location by size)
     * @param interpolator                 a Transition interpolator
     * @param transitionType               TransitionType
     * @param onTransitionFinishedCallback runnable to be execute when the transition is finshed
     */
    public AngularMovementTransitionExecuter(float initalAngle,
                                             float finalAngle,
                                             Vector2 windowDim,
                                             Transition.Interpolator<Float> interpolator,
                                             Transition.TransitionType transitionType,
                                             Runnable onTransitionFinishedCallback) {

        this.initalAngle = initalAngle;
        this.finalAngle = finalAngle;
        this.windowDim = windowDim;
        this.interpolator = interpolator;
        this.transitionType = transitionType;
        this.onTransitionFinishedCallback = onTransitionFinishedCallback;
    }

    /**
     * execute the transition
     *
     * @param cycleLength trnsition cycle length
     * @param gameObject  game object to be executed on
     */
    @Override
    public Transition[] createTransitions(float cycleLength, GameObject gameObject) {
        var transition = new Transition<>(
                gameObject,
                (angle) -> gameObject.setCenter(calculateSunPosition(angle)),
                this.initalAngle,
                this.finalAngle,
                this.interpolator,
                cycleLength,
                this.transitionType,
                onTransitionFinishedCallback
        );
        return new Transition[]{transition};
    }


    private Vector2 calculateSunPosition(float angle) {
        // gets the location on the screen by the angle
        var majorRadius = this.windowDim.x() / MAJOR_RADIUS_CHANGE;
        var minorRadius = this.windowDim.x() / MINOR_RADIUS_CHANGE;

        var y = Math.sin(Math.toRadians(angle)) * minorRadius;
        var x = Math.cos(Math.toRadians(angle)) * majorRadius;

        return new Vector2((float) ((this.windowDim.x() / 2) - x), (float) (this.windowDim.y() - y));
    }
}
