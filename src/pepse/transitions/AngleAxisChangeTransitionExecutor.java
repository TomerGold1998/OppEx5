package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;

public class AngleAxisChangeTransitionExecutor implements TransitionExecuter {
    private float initialAngle;
    private float finalAngle;
    private Transition.Interpolator<Float> interpolator;
    private Transition.TransitionType transitionType;
    private Runnable onTransitionFinishedCallback;

    public AngleAxisChangeTransitionExecutor(float initalAngle,
                                             float finalAngle,
                                             Transition.Interpolator<Float> interpolator,
                                             Transition.TransitionType transitionType,
                                             Runnable onTransitionFinishedCallback){
        this.initialAngle = initalAngle;
        this.finalAngle = finalAngle;
        this.interpolator = interpolator;
        this.transitionType = transitionType;
        this.onTransitionFinishedCallback = onTransitionFinishedCallback;

    }
    @Override
    public void executeTransition(float cycleLength, GameObject gameObject) {
        var transition = new Transition<Float>(
                gameObject,
                (angle) -> gameObject.renderer().setRenderableAngle(angle),
                this.initialAngle,
                this.finalAngle,
                this.interpolator,
                cycleLength,
                this.transitionType,
                onTransitionFinishedCallback);
    }
}
