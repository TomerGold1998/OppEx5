package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;
import pepse.TransitionExecuter;

/**
 * changing opacity of an object
 *
 * @author Ruth Yukhnovetsky
 */
public class ChangeOpacityTransitionExecutor implements TransitionExecuter {

    private float initialOpacity;
    private float finalOpacity;
    private Transition.Interpolator<Float> interpolator;
    private Transition.TransitionType type;
    private Runnable onEnd;

    public ChangeOpacityTransitionExecutor(float initialOpacity,
                                           float finalOpacity,
                                           Transition.Interpolator<Float> interpolator,
                                           Transition.TransitionType type,
                                           Runnable onEnd) {
        this.initialOpacity = initialOpacity;
        this.finalOpacity = finalOpacity;
        this.interpolator = interpolator;
        this.type = type;
        this.onEnd = onEnd;
    }

    /**
     * execute transition
     *
     * @param cycleLength length of transition cycle
     * @param gameObject  game object to be transitioned
     */
    @Override
    public void executeTransition(float cycleLength, GameObject gameObject) {
        var transition = new Transition<Float>(
                gameObject,
                gameObject.renderer()::setOpaqueness,
                this.initialOpacity,
                this.finalOpacity,
                interpolator,
                cycleLength / 2,
                type,
                onEnd
        );

    }
}
