package pepse.configuration;

import danogl.collisions.Layer;

/**
 * used in order to save the different game objects layers
 */
public class GameLayers {
    public final static int SKY_LAYER = Layer.BACKGROUND;
    public final static int SUN_LAYER = Layer.BACKGROUND + 1;
    public final static int SUN_HALO_LAYER = Layer.BACKGROUND + 2;
    public final static int BLOCK_LAYER = Layer.STATIC_OBJECTS;
    public final static int AVATAR_LAYER = Layer.DEFAULT;
    public final static int NIGHT_LAYER = Layer.FOREGROUND;

    public final static int TREE_LAYER = Layer.STATIC_OBJECTS;
}
