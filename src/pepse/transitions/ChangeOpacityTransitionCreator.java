package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;

/**
 * creates a transition that is changing opacity of an object
 *
 * @author Ruth Yukhnovetsky
 */
public class ChangeOpacityTransitionCreator implements TransitionCreator {

    private final float initialOpacity;
    private final float finalOpacity;
    private final Transition.Interpolator<Float> interpolator;
    private final Transition.TransitionType type;
    private final Runnable onEnd;

    /**
     * creates new ChangeOpacityTransitionExecutor
     * @param initialOpacity inital opacity for the transition
     * @param finalOpacity   final opacity for the transition
     * @param interpolator   Transition interpolator
     * @param type           TransitionType
     * @param onEnd          function to execute on end of transition
     */
    public ChangeOpacityTransitionCreator(float initialOpacity,
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
     * creaating and executing the opacity changer transition
     * @param cycleLength length of transition cycle
     * @param gameObject  game object to be transitioned
     */
    @Override
    public Transition[] createTransitions(float cycleLength, GameObject gameObject) {
        var transition = new Transition<>(
                gameObject,
                gameObject.renderer()::setOpaqueness,
                this.initialOpacity,
                this.finalOpacity,
                interpolator,
                cycleLength / 2,
                type,
                onEnd);
        return new Transition[]{transition};
    }
}
