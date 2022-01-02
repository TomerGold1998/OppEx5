package pepse.world;

import danogl.collisions.GameObjectCollection;

public class RemoveUnusedGameObjects {
    private final GameObjectCollection collection;
    private final int layer;
    private final InfiniteWorldHandler worldHandler;

    public RemoveUnusedGameObjects(GameObjectCollection collection,
                                   int layer,
                                   InfiniteWorldHandler worldHandler) {

        this.collection = collection;
        this.layer = layer;
        this.worldHandler = worldHandler;
    }

    public void removeGameObjectsNotInRange(int minX, int maxX) {
        for (var gameObject : collection) {
            if (gameObject.getCenter().x() < minX || gameObject.getCenter().x() > maxX)
                collection.removeGameObject(gameObject, layer);
        }

        this.worldHandler.updateGameSurface(minX, maxX);
    }
}
