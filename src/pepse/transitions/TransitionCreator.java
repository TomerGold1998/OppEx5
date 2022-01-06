package pepse.transitions;

import danogl.GameObject;
import danogl.components.Transition;

/**
 * interface for creating transition on a certain object
 *
 * @author Ruth Yukhnovetsky
 */
public interface TransitionCreator {

    /**
     * creates the transitions
     *
     * @param cycleLength the cycle length of the transition
     * @param gameObject  the game object the transition will be added to
     */
    Transition[] createTransitions(float cycleLength, GameObject gameObject);
}
