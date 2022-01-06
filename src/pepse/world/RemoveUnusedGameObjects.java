package pepse.world;

import danogl.collisions.GameObjectCollection;
import pepse.util.GameObjectsContainer;
import pepse.util.TemporaryItem;

/**
 * remove unused game obj
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
                    var innerObjects = ((GameObjectsContainer) gameObject).getInnerGameObjects();
                    for (var innerGameObject : innerObjects) {
                        collection.removeGameObject(innerGameObject, layer);
                    }
                }
            }
        }
        this.worldHandler.updateGameSurface(minX, maxX);
    }
}
