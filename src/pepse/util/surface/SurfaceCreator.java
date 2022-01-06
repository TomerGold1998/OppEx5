package pepse.util.surface;

/**
 * interface for classes that create surfaces (trees / terrain etc)
 */
public interface SurfaceCreator {

    /**
     * create a surface in a given range
     *
     * @param minX start point
     * @param maxX end point
     */
    void createInRange(int minX, int maxX);
}