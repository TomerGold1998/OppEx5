package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.transitions.AngleChangeTransitionExecutor;

import java.awt.*;

public class Sun {

    private static int SUN_SIZE = 100;
    private static int SUN_INITAL_ANGLE = 0;
    private static int SUN_FINAL_ANGLE = 360;

    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            Vector2 windowDimensions,
            float cycleLength) {

        var transition = new AngleChangeTransitionExecutor(
                SUN_INITAL_ANGLE,
                SUN_FINAL_ANGLE,
                windowDimensions,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        var sunRenderable = new OvalRenderable(Color.YELLOW);
        var sun = new GameObject(Vector2.ZERO,
                new Vector2(SUN_SIZE, SUN_SIZE),
                sunRenderable);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setCenter(new Vector2(windowDimensions.x() / 4,
                windowDimensions.y() - windowDimensions.y() / 4));
        sun.setTag("sun");

        transition.executeTransition(cycleLength, sun);

        gameObjects.addGameObject(sun, layer);

        return sun;
    }

}
