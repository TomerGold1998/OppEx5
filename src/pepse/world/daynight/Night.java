package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.WindowController;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {
    /**
     * creating night layer according to window dimensions and change its
     * opacity level through cycle length
     * @param gameObjects the rectangle
     * @param layer the layer to be added onto
     * @param windowDimensions the game's window dim
     * @param cycleLength cycle length of day and night
     * @return the rectangle
     */
    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer,
                                    Vector2 windowDimensions,
                                    float cycleLength){
        Renderable renderable = new RectangleRenderable(Color.BLACK);
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, renderable);
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(night,layer);
        night.setTag("night");
        return night;
    }
}
