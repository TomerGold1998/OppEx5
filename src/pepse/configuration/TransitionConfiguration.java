package pepse.configuration;

import danogl.util.Vector2;

/**
 * Used in order to save game objects transitions
 */
public class TransitionConfiguration {
    public final static int NIGHT_CYCLE_LENGTH = 30;
    public final static int SUN_CYCLE_LENGTH = 30;

    public final static float LEAF_SIZE_AND_ANGLE_CYCLE = 4f;
    public final static float LEAF_FADEOUT = 10f;
    public final static float LEAF_WOOBLING_CYCLE = 2f;
    public final static float[] LEAF_DELAYS = new float[]{0.1f, 0.3f, 0.5f, 0.7f, 0.9f, 1.5f};

    public final static int LEAF_START_ANGLE = 0;
    public final static int LEAF_END_ANGLE = 45;
    public final static Vector2 BIG_LEAF_SIZE = new Vector2(35,25);
}
