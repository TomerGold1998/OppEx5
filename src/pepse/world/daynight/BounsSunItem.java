package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * used in order to case our bonus on the sun
 * @author Tomer Goldberg
 */
public class BounsSunItem extends GameObject {
    private final ImageReader imageReader;
    private final Vector2 originalDim;
    private String lastText = "";

    private final static float SUN_DIMENSION_CENTER_DELTA = 6;
    private final static float TEXT_DIMENSION_DELTA = 1.1f;
    private final static Color TEXT_COLOR = Color.PINK;

    /**
     * constrctor for BounsSunItem
     *
     * @param imageReader image reader, used in order to load the game object as image
     * @param sun         sun object to follow
     */
    public BounsSunItem(ImageReader imageReader,
                        GameObject sun) {

        super(sun.getTopLeftCorner(), sun.getDimensions().mult(0.5f), null);
        this.originalDim = sun.getDimensions().mult(0.5f);
        this.imageReader = imageReader;
        this.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.setCenter(sun.getCenter());
        this.setTag("sunBonus");
        this.addComponent(
                (deltaTime ->
                        this.setCenter(sun.getCenter().subtract(
                                new Vector2(
                                        sun.getDimensions().x() / SUN_DIMENSION_CENTER_DELTA,
                                        0)))));

    }

    /**
     * Sets the current render of this item to be a string
     *
     * @param text text to be rendered
     */
    public void setAsText(String text) {
        if (!lastText.equals(text)) {
            this.setDimensions(this.originalDim);
            var textRenderable = new TextRenderable(text);
            textRenderable.setColor(TEXT_COLOR);
            this.setDimensions(
                    new Vector2(this.getDimensions().x() / (text.length() / TEXT_DIMENSION_DELTA),
                            this.getDimensions().y() / (text.length() / TEXT_DIMENSION_DELTA)));
            this.renderer().setRenderable(textRenderable);
            lastText = text;
        }
    }

    /**
     * Sets the current render of the game object to be an image
     *
     * @param imgPath image path
     */
    public void setAsImage(String imgPath) {
        this.setDimensions(this.originalDim);
        var renderable = this.imageReader.readImage(imgPath, true);
        this.renderer().setRenderable(renderable);
    }
}
