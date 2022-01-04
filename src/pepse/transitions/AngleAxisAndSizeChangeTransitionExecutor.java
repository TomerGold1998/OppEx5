
package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

/**
 * angle and size transition constructor
 * @author Ruth Yukhnovetsky
 */
public class AngleAxisAndSizeChangeTransitionExecutor implements TransitionExecuter {
    private float initialAngle;
    private float finalAngle;
    private final Vector2 leafSize = new Vector2(35, 25);
    private Transition.Interpolator<Float> interpolator;
    private Transition.Interpolator<Vector2> vector2Interpolator;
    private Transition.TransitionType transitionType;
    private Runnable onTransitionFinishedCallback;

    /**
     * constructor
     * @param initalAngle initial angle
     * @param finalAngle final angle
     * @param interpolator interpolator
     * @param vector2Interpolator other interpolator
     * @param transitionType type of transition
     * @param onTransitionFinishedCallback callback
     */
    public AngleAxisAndSizeChangeTransitionExecutor(float initalAngle,
                                                    float finalAngle,
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
                leafSize,
                this.vector2Interpolator,
                cycleLength,
                this.transitionType,
                onTransitionFinishedCallback
        );
        return new Transition[]{angleTransition, sizeTransition};
    }
}