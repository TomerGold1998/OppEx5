package pepse.world;

import pepse.util.SurfaceCreator;

import java.util.List;

/**
 * infinite world creator
 * @author Tomer Goldberg
 */
public class InfiniteWorldCreator {

    private final List<SurfaceCreator> surfaces;
    private final int xBufferFromPoint;

    private int minSurfaceGenerated = 0;
    private int maxSurfaceGenerated = 0;

    private static final int minAreaToRegenerate = 100;

    /**
     * constructor
     * @param surfaces surface of terrain
     * @param xBufferFromPoint buffer porint
     * @param initalX initial point coordinate x
     */
    public InfiniteWorldCreator(List<SurfaceCreator> surfaces,
                                int xBufferFromPoint,
                                int initalX) {

        this.surfaces = surfaces;
        this.xBufferFromPoint = xBufferFromPoint;
        this.minSurfaceGenerated = initalX;
        this.maxSurfaceGenerated = initalX;
        updateSurfaces(initalX);

    }

    /**
     * update surface
     * @param currentX current x coordinate
     */
    public void updateSurfaces(int currentX) {
        if (currentX + this.xBufferFromPoint < maxSurfaceGenerated &&
                currentX - this.xBufferFromPoint > minSurfaceGenerated) {
            // no need for change, area already been buffered
            return;
        }
        var deltaXPositive = (currentX + this.xBufferFromPoint) - this.maxSurfaceGenerated;
        var deltaXNegetive = this.minSurfaceGenerated - (currentX - this.xBufferFromPoint);

        if (deltaXPositive > minAreaToRegenerate) {
            for (var surface : this.surfaces)
                surface.createInRange(
                        this.maxSurfaceGenerated,
                        this.maxSurfaceGenerated + deltaXPositive);
            maxSurfaceGenerated += deltaXPositive;
        }

        if (deltaXNegetive > minAreaToRegenerate) {
            for (var surface : this.surfaces)
                surface.createInRange(
                        this.minSurfaceGenerated - deltaXNegetive,
                        this.minSurfaceGenerated);
            minSurfaceGenerated -= deltaXNegetive;
        }

    }

    /**
     * update game surface
     * @param minX starting range
     * @param maxX ending range
     */
    public void updateGameSurface(int minX, int maxX) {
        if(minX > this.minSurfaceGenerated) {
            this.minSurfaceGenerated = minX;
        }
        if (maxX < this.maxSurfaceGenerated) {
            this.maxSurfaceGenerated = maxX;
        }
    }
}
