package pepse.util;

import danogl.GameObject;
import danogl.components.Transition;

/**
 * Executes transition of a certain object
 * @author Ruth Yukhnovetsky
 */
public interface TransitionCreator {

    Transition[] createTransitions(float cycleLength, GameObject gameObject);
}
