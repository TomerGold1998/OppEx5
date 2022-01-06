package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.configuration.GameObjectsConfiguration;

import java.awt.*;

/**
 * sun halo creator
 * @author Tomer Goldberg
 */
public class SunHalo {



    /**
     * creating sun halo
     * @param gameObjects this
     * @param layer layer of halo
     * @param sun sun
     * @param color of halo
     * @return halo
     */
    public static GameObject create(
            GameObjectCollection gameObjects,
            int layer,
            GameObject sun,
            Color color) {

        var haloRenderable = new OvalRenderable(color);
        var sunHalo = new GameObject(Vector2.ZERO,
                GameObjectsConfiguration.SUN_HALO_SIZE,
                haloRenderable);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setCenter(sun.getCenter());
        sunHalo.setTag("sunHalo");

        sunHalo.addComponent((deltaTime -> sunHalo.setCenter(sun.getCenter())));
        gameObjects.addGameObject(sunHalo, layer);
        return sunHalo;
    }
}
