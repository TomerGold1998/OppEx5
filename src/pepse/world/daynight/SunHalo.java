package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class SunHalo {

    private static int SUN_HALO_SIZE = 220;

    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            GameObject sun,
            Color color) {

        var haloRenderable = new OvalRenderable(color);
        var sunHalo = new GameObject(Vector2.ZERO,
                new Vector2(SUN_HALO_SIZE, SUN_HALO_SIZE),
                haloRenderable);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setCenter(sun.getCenter());
        sunHalo.setTag("sunHalo");

        sunHalo.addComponent((deltaTime -> sunHalo.setCenter(sun.getCenter())));
        gameObjects.addGameObject(sunHalo, layer);
        return sunHalo;
    }
}
