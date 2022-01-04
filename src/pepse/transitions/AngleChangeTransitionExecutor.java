package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

public class AngleChangeTransitionExecutor implements TransitionExecuter {

    private float initialAngle;
    private float finalAngle;
    private Vector2 windowDim;
    private Transition.Interpolator<Float> interpolator;
    private Transition.TransitionType transitionType;
    private Runnable onTransitionFinishedCallback;

    public AngleChangeTransitionExecutor(float initialAngle,
                                         float finalAngle,
                                         Vector2 windowDim,
                                         Transition.Interpolator<Float> interpolator,
                                         Transition.TransitionType transitionType,
                                         Runnable onTransitionFinishedCallback) {

        this.initialAngle = initialAngle;
        this.finalAngle = finalAngle;
        this.windowDim = windowDim;
        this.interpolator = interpolator;
        this.transitionType = transitionType;
        this.onTransitionFinishedCallback = onTransitionFinishedCallback;
    }

    private Vector2 calculateSunPosition(float angle) {
        var majorRadius = this.windowDim.x() / 2;
        var minorRadius = this.windowDim.x() / 2.2;
        var y = Math.sin(Math.toRadians(angle)) * minorRadius;
        var x = Math.cos(Math.toRadians(angle)) * majorRadius;

        return new Vector2((float) ((this.windowDim.x() / 2) - x), (float) (this.windowDim.y() - y));
    }

    @Override
    public Transition[] createTransitions(float cycleLength, GameObject gameObject) {
        var transition = new Transition<>(
                gameObject,
                (angle) -> gameObject.setCenter(calculateSunPosition(angle)),
                this.initialAngle,
                this.finalAngle,
                this.interpolator,
                cycleLength,
                this.transitionType,
                onTransitionFinishedCallback
        );
        return new Transition[]{transition};
    }
}
