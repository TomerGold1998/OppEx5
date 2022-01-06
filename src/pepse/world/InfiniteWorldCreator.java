package pepse.world;

import pepse.util.surface.SurfaceCreator;

import java.util.List;

/**
 * infinite world creator
 * Handles the creation for new surfaces
 *
 * @author Tomer Goldberg
 */
public class InfiniteWorldCreator {

    private final List<SurfaceCreator> surfaces;
    private final int xBufferFromPoint;

    private int minSurfaceGenerated;
    private int maxSurfaceGenerated;

    private static final int minAreaToRegenerate = 100;

    /**
     * constructor
     *
     * @param surfaces         list of SurfaceCreators
     * @param xBufferFromPoint buffer print
     * @param initalX          initial point coordinate x
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
     *
     * @param currentX current x coordinate
     */
    public void updateSurfaces(int currentX) {
        if (currentX + this.xBufferFromPoint < maxSurfaceGenerated &&
                currentX - this.xBufferFromPoint > minSurfaceGenerated) {
            // no need for change, area already been buffered
            return;
        }

        generateNewSurface(currentX);
    }

    private void generateNewSurface(int currentX) {
        var deltaXPositive = (currentX + this.xBufferFromPoint) - this.maxSurfaceGenerated;
        var deltaXNegative = this.minSurfaceGenerated - (currentX - this.xBufferFromPoint);

        if (deltaXPositive > minAreaToRegenerate) {
            for (var surface : this.surfaces)
                surface.createInRange(
                        this.maxSurfaceGenerated,
                        this.maxSurfaceGenerated + deltaXPositive);
            maxSurfaceGenerated += deltaXPositive;
        }

        if (deltaXNegative > minAreaToRegenerate) {
            for (var surface : this.surfaces)
                surface.createInRange(
                        this.minSurfaceGenerated - deltaXNegative,
                        this.minSurfaceGenerated);
            minSurfaceGenerated -= deltaXNegative;
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
