package pepse.world;

import danogl.collisions.GameObjectCollection;
import pepse.util.TemporaryItem;
import pepse.util.unique_game_objects.GameObjectsContainer;

/**
 * remove unused game obj, in oreder to support the infinite world
 *
 * @author Tomer Goldberg
 */
public class RemoveUnusedGameObjects {
    private final GameObjectCollection collection;
    private final int layer;
    private final InfiniteWorldCreator worldHandler;

    /**
     * constructor
     * @param collection obj collection
     * @param layer layer of obj
     * @param worldHandler world handler
     */
    public RemoveUnusedGameObjects(GameObjectCollection collection,
                                   int layer,
                                   InfiniteWorldCreator worldHandler) {

        this.collection = collection;
        this.layer = layer;
        this.worldHandler = worldHandler;
    }

    /**
     * remove an obj out of range
     * @param minX starting x coor of range
     * @param maxX ending x coor of range
     */
    public void removeGameObjectsNotInRange(int minX, int maxX) {
        for (var gameObject : collection) {
            if (gameObject instanceof TemporaryItem &&
                    (gameObject.getCenter().x() < minX || gameObject.getCenter().x() > maxX)) {

                collection.removeGameObject(gameObject, layer);
                if (gameObject instanceof GameObjectsContainer) {
                    ((GameObjectsContainer) gameObject).removeInnerGameObjects();
                }
            }
        }
        this.worldHandler.updateGameSurface(minX, maxX);
    }
}
