package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.configuration.GameLayers;
import pepse.world.trees.Tree;


import java.awt.*;


/**
 * creating items of trees
 * @author Ruth Yukhnovestky
 */
public class TreeManger {
    private static final float TREE_HEIGHT = 100;
    private Terrain terrain;
    private GameObjectCollection gameObjectCollection;
    private int layer;
    private Vector2 windowsDim;

    public TreeManger(Terrain terrain,
                      GameObjectCollection gameObjectCollection,
                      Vector2 windowDim,
                      int layer) {
        this.terrain = terrain;
        this.gameObjectCollection = gameObjectCollection;
        this.layer = layer;
        this.windowsDim = windowDim;
    }

    public void plantTrees(int numOfPlant) {
        Tree tree = new Tree(terrain,
                             this.gameObjectCollection,
                             this.windowsDim,
                             GameLayers.TREE_LAYER,
                             TREE_HEIGHT);
        for (int i = 10 ; i < windowsDim.x() ; i+=250){
            tree.createInRange(i, i+10);
        }
    }
}