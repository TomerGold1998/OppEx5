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

    //AVATAR
    public final static String STATIC_MALE_PATH = "./src/assets/static_man.png";
    public final static String LEFT_MALE_PATH = "./src/assets/left_male.png";
    public final static String RIGHT_MALE_PATH = "./src/assets/right_male.png";
    public final static String FLYING_MALE_PATH = "./src/assets/flying_man.png";

    public final static Vector2 AVATAR_SIZE = new Vector2(60, 80);

    public final static int AVATAR_ENERGY = 100;

    //TREE
    public final static int[] TREE_HEIGHT_OPTIONS = new int[]{250, 200, 120, 300};
    public final static int[] TREE_WIDTH_OPTIONS = new int[]{10, 15, 20, 25};

    //LEAF
    public final static int LEAF_MAX_LIFE = 180;
    public final static int LEAF_MIN_LIFE = 3;
    public final static Vector2 LEAF_BASE_SIZE = new Vector2(30,30);


    //BONUS
    public static final String[] DESIGN_PATTERNS = {"DECORATOR", "STRATEGY", "FACADE", "SINGLETON"};
    public final static String YAHEL_IMG_PATH = "./src/assets/yahel.png";
}
