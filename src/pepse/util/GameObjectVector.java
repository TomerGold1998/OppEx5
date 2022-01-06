package pepse.util;

import danogl.util.Vector2;

/**
 * game obj vector class
 */
public class GameObjectVector {
    private final float width;
    private final float height;
    private final float x;
    private final float y;

    /**
     * constructor
     * @param x x coordinate
     * @param y y coordinate
     * @param width width
     * @param height height
     */
    public GameObjectVector(float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    /**
     * gets size
     * @return size
     */
    public Vector2 getSizeVector() {
        return new Vector2(this.width, this.height);
    }

    /**
     * gets location
     * @return location
     */
    public Vector2 getLocationVector(){
        return new Vector2(this.x, this.y);
    }
}
