package pepse.world;

import danogl.GameObject;
import danogl.util.Vector2;

/**
 * Used in the infinite world,
 * creates a surface for the area we are currently in using the InfiniteWorldCreator
 * removes unused surface for performance using the RemoveUnusedGameObjects
 *
 * @author Tomer Goldberg
 */
public class WorldSurfaceHandler {

    private static final int GAME_DISPLAY_REMOVE_ITEM_BUFFER = 300;

    private final InfiniteWorldCreator infiniteWorldCreator;
    private final RemoveUnusedGameObjects removeUnusedGameObjects;
    private final Vector2 windowDim;
    private final GameObject objectToFollow;

    /**
     * creates a new WorldSurfaceHandler
     *
     * @param infiniteWorldHandler    InfiniteWorldCreator object
     * @param removeUnusedGameObjects RemoveUnusedGameObjects object
     * @param windowDim               the window size
     * @param objectToFollow          the game object to follow on
     */
    public WorldSurfaceHandler(InfiniteWorldCreator infiniteWorldHandler,
                               RemoveUnusedGameObjects removeUnusedGameObjects,
                               Vector2 windowDim,
                               GameObject objectToFollow) {

        this.infiniteWorldCreator = infiniteWorldHandler;
        this.removeUnusedGameObjects = removeUnusedGameObjects;
        this.windowDim = windowDim;
        this.objectToFollow = objectToFollow;
    }

    /**
     * update the game surfaces,
     * create new surfaces in the area around our object to follow
     * remove old surfaces (with a buffer) in a area that is not near our object to follow
     */
    public void updateSurface() {
        this.infiniteWorldCreator.updateSurfaces((int) this.objectToFollow.getCenter().x());

        var validObjectRangeFrom = this.objectToFollow.getCenter().x() -
                ((this.windowDim.x() / 2)
                        + GAME_DISPLAY_REMOVE_ITEM_BUFFER);

        var validObjectRangeTo = this.objectToFollow.getCenter().x() + (this.windowDim.x() / 2)
                + GAME_DISPLAY_REMOVE_ITEM_BUFFER;

        removeUnusedGameObjects.removeGameObjectsNotInRange((int) validObjectRangeFrom,
                (int) validObjectRangeTo);
    }
}