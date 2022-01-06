package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.configuration.GameObjectsConfiguration;
import pepse.configuration.TransitionConfiguration;
import pepse.transitions.AngularMovementTransitionCreator;

import java.awt.*;

/**
 * sun obj at the simulator
 * @author Tomer Goldberg
 */
public class Sun {

    /**
     * creator of sun
     * @param gameObjects object collection
     * @param layer layer of sun
     * @param windowDimensions window dimensions
     * @param cycleLength length of cycle
     * @return sun
     */
    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            Vector2 windowDimensions,
            float cycleLength) {

        var transition = new AngularMovementTransitionCreator(
                TransitionConfiguration.SUN_INITAL_ANGLE,
                TransitionConfiguration.SUN_FINAL_ANGLE,
                windowDimensions,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        var sunRenderable = new OvalRenderable(Color.YELLOW);
        var sun = new GameObject(Vector2.ZERO,
                GameObjectsConfiguration.SUN_SIZE,
                sunRenderable);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");

        transition.createTransitions(cycleLength, sun);
        gameObjects.addGameObject(sun, layer);

        return sun;
    }

}
