package pepse.configuration;

import danogl.util.Vector2;

import java.awt.*;

/**
 * Used in order to store game objects consts
 */
public class GameObjectsConfiguration {
    //GENERAL
    public final static int SEED = 42;


    //SUN
    public final static Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);

    //TERRAIN
    public final static int TERRAIN_X_BUFFER = 100;

    //AVATAR
    public final static String STATIC_MALE_PATH = "./src/static_man.png";
    public final static String LEFT_MALE_PATH = "./src/left_male.png";
    public final static String RIGHT_MALE_PATH = "./src/right_male.png";
    public final static String FLYING_MALE_PATH = "./src/flying_man.png";

    public final static Vector2 AVATAR_SIZE = new Vector2(50, 50);

    public final static int AVATAR_ENERGY = 100;

    //TREE
    public static final int TREE_HEIGHT = 250;
}
