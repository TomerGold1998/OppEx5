
package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

/**
 * angle and size transition constructor
 *
 * @author Ruth Yukhnovetsky
 */
public class AngleAxisAndSizeChangeTransitionExecutor implements TransitionExecuter {
    private final float initialAngle;
    private final float finalAngle;
    private final Vector2 finalSize;
    private final Transition.Interpolator<Float> interpolator;
    private final Transition.Interpolator<Vector2> vector2Interpolator;
    private final Transition.TransitionType transitionType;
    private final Runnable onTransitionFinishedCallback;

    /**
     * constructor
     *
     * @param initalAngle                  initial angle
     * @param finalAngle                   final angle
     * @param interpolator                 interpolator
     * @param vector2Interpolator          other interpolator
     * @param transitionType               type of transition
     * @param onTransitionFinishedCallback callback
     */
    public AngleAxisAndSizeChangeTransitionExecutor(float initalAngle,
                                                    float finalAngle,
                                                    Vector2 finalSize,
                                                    Transition.Interpolator<Float> interpolator,
                                                    Transition.Interpolator<Vector2> vector2Interpolator,
                                                    Transition.TransitionType transitionType,
                                                    Runnable onTransitionFinishedCallback) {
        this.initialAngle = initalAngle;
        this.finalAngle = finalAngle;
        this.interpolator = interpolator;
        this.vector2Interpolator = vector2Interpolator;
        this.transitionType = transitionType;
        this.onTransitionFinishedCallback = onTransitionFinishedCallback;
        this.finalSize = finalSize;
    }

    /**
     * create array of transitions for this type of transitions
     * @param cycleLength length of cycle
     * @param gameObject game obj
     * @return array of transitions
     */
    public Transition[] createTransitions(float cycleLength, GameObject gameObject) {
        var angleTransition = new Transition<>(
                gameObject,
                (angle) -> gameObject.renderer().setRenderableAngle(angle),
                this.initialAngle,
                this.finalAngle,
                this.interpolator,
                cycleLength,
                this.transitionType,
                onTransitionFinishedCallback);
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
        return new Transition[]{angleTransition, sizeTransition};
    }
}