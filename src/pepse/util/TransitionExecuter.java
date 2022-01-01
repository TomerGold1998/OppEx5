package pepse.util;

import danogl.GameObject;

/**
 * Executes transition of a certain object
 * @author Ruth Yukhnovetsky
 */
public interface TransitionExecuter {

    void executeTransition (float cycleLength, GameObject gameObject);
}
