package pepse.world;

import danogl.collisions.GameObjectCollection;
import pepse.util.GameObjectsContainer;
import pepse.util.TemporaryItem;

public class RemoveUnusedGameObjects {
    private final GameObjectCollection collection;
    private final int layer;
    private final InfiniteWorldCreator worldHandler;

    public RemoveUnusedGameObjects(GameObjectCollection collection,
                                   int layer,
                                   InfiniteWorldCreator worldHandler) {

        this.collection = collection;
        this.layer = layer;
        this.worldHandler = worldHandler;
    }

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
