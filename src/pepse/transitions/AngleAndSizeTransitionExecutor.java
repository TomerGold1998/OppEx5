package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

/**
 * Used in order to create a transition that includes both angle and size change
 *
 * @author Rut
 */
public class AngleAndSizeTransitionExecutor implements TransitionExecuter {

    private final float initialAngle;
    private final float finalAngle;
    private final Vector2 finalSize;
    private final Transition.Interpolator<Float> interpolator;
    private final Transition.Interpolator<Vector2> vector2Interpolator;
    private final Transition.TransitionType transitionType;
    private final Runnable onTransitionFinishedCallback;

    /**
     * Create new AngleAndSizeTransitionExecutor
     *
     * @param initalAngle                  inital angle for movement
     * @param finalAngle                   final angle for movement
     * @param finalSize                    final size of the object
     * @param interpolator                 a transition interpolator for the angle movement
     * @param vector2Interpolator          a transistion interpolator fot the size movement
     * @param transitionType               TransitionType
     * @param onTransitionFinishedCallback runnable to execute when the transition ends
     */
    public AngleAndSizeTransitionExecutor(float initalAngle,
                                          float finalAngle,
                                          Vector2 finalSize,
                                          Transition.Interpolator<Float> interpolator,
                                          Transition.Interpolator<Vector2> vector2Interpolator,
                                          Transition.TransitionType transitionType,
                                          Runnable onTransitionFinishedCallback) {
        this.initialAngle = initalAngle;
        this.finalAngle = finalAngle;
        this.finalSize = finalSize;
        this.interpolator = interpolator;
        this.vector2Interpolator = vector2Interpolator;
        this.transitionType = transitionType;
        this.onTransitionFinishedCallback = onTransitionFinishedCallback;

    }

    /**
     * executes the transition
     *
     * @param cycleLength cycle time for the transition
     * @param gameObject  game object the transition is executed on
     */
    @Override
    public void executeTransition(float cycleLength, GameObject gameObject) {
        // angle change transition
        var angleTransition = new Transition<>(
                gameObject,
                (angle) -> gameObject.renderer().setRenderableAngle(angle),
                this.initialAngle,
                this.finalAngle,
                this.interpolator,
                cycleLength,
                this.transitionType,
                onTransitionFinishedCallback);

        //size change transition
        var sizeTransition = new Transition<>(
                gameObject,
                gameObject::setDimensions,
                gameObject.getDimensions(),
                this.finalSize,
                this.vector2Interpolator,
                cycleLength,
                this.transitionType,
                onTransitionFinishedCallback
        );
    }
}
