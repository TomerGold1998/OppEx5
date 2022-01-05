package pepse.util;

import danogl.util.Vector2;

public class GameObjectVector {
    private final float width;
    private final float height;
    private final float x;
    private final float y;

    public GameObjectVector(float x, float y, float width, float height) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public Vector2 getSizeVector() {
        return new Vector2(this.width, this.height);
    }

    public Vector2 getLocationVector(){
        return new Vector2(this.x, this.y);
    }
}
