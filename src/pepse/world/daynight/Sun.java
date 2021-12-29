package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Sun {

    private static int SUN_SIZE = 200;

    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            Vector2 windowDimensions,
            float cycleLength) {

        var sunRenderable = new OvalRenderable(Color.YELLOW);
        var gameObject = new GameObject(Vector2.ZERO,
                new Vector2(SUN_SIZE, SUN_SIZE),
                sunRenderable);

        gameObject.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2));
        gameObjects.addGameObject(gameObject, layer);
        return gameObject;
    }

}
