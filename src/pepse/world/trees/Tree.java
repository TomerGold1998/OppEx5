package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.TransitionExecuter;
import pepse.transitions.ChangeOpacityTransitionExecutor;
import pepse.world.Terrain;

import java.awt.*;
import java.util.Random;

/**
 * adds trees to the game
 * @author Ruth Yukhnovetsky
 */
public class Tree {

    private Terrain terrain;
    private GameObjectCollection gameObjectCollection;
    private int layer;
    private Vector2 windowsDim;
    private int treeHeight;

    private final Color TRUNK_COLOR = new Color(100, 50, 20);
    private final Color LEAVES_COLOR = new Color(50, 200, 30);

    private final static TransitionExecuter transitionExecuter = new ChangeOpacityTransitionExecutor(
            0.3f,
            0.6f,
            Transition.CUBIC_INTERPOLATOR_FLOAT,
            Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
            null);

    /**
     * tree constructor
     * @param terrain given terrain
     * @param gameObjectCollection game object collection
     * @param windowDim worlds dimensions
     * @param layer layer of tree
     * @param treeHeight height of tree
     */
    public Tree(Terrain terrain,
                GameObjectCollection gameObjectCollection,
                Vector2 windowDim,
                int layer,
                float treeHeight){
        this.terrain = terrain;
        this.gameObjectCollection = gameObjectCollection;
        this.layer = layer;
        this.windowsDim = windowDim;
        this.treeHeight = (int)treeHeight;
    }

    /**
     * creating trees according to a certain height of terrain with width of minX to maxX
     * @param minX left side of trunk
     * @param maxX right side of trunk
     */
    public void createInRange(int minX, int maxX) {
        System.out.println(terrain.groundHeightAt(10));
        System.out.println(terrain.groundHeightAt(150));
        var treeTopY = this.windowsDim.y() - terrain.groundHeightAt(minX) - this.treeHeight;

        // create trunk
        RectangleRenderable trunkRectangleRenderable = new RectangleRenderable(TRUNK_COLOR);
        Vector2 top = new Vector2(minX, treeTopY);
        Vector2 trunkDimensions = new Vector2((maxX - minX), this.treeHeight);
        var trunk = new GameObject(top, trunkDimensions, trunkRectangleRenderable);
        gameObjectCollection.addGameObject(trunk, layer);

        // create leaves
        createLeaves(top, trunkDimensions);
    }

    private void createLeaves(Vector2 top, Vector2 trunkDimensions) {
        int leafyRange = treeHeight / 3;
        int leafTop =(int) (top.x() + trunkDimensions.x()/2);

        int x0 = leafTop - leafyRange;
        int x1 = leafTop + leafyRange;
        int y0 = (int)top.y() - leafyRange;
        int y1 = (int)top.y() + leafyRange;
        for (int i = x0 ; i < x1; i+=10) {
            RectangleRenderable leafRectangleRenderable = new RectangleRenderable(LEAVES_COLOR);
            for (int j = y0 ; j < y1 ; j+=10) {
                var leaf = new GameObject(new Vector2(i, j),
                        new Vector2(8, 8),
                        leafRectangleRenderable);
                Random random = new Random();
                int rand = random.nextInt(4);
                if (rand == 2) {
                    transitionExecuter.executeTransition(5, leaf);
                }
                gameObjectCollection.addGameObject(leaf, layer);
            }
        }


    }


}
