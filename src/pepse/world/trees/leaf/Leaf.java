package pepse.world.trees.leaf;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.TransitionExecuter;
import pepse.world.Block;
import pepse.world.ReactingBlock;

import java.util.Random;

/**
 * Leaf class to control movement
 *
 * @author Ruth Yukhnovetsky
 */
public class Leaf extends GameObject {
    private Transition tempTransition;

    /**
     * constructor of leaf in game
     *
     * @param topLeftCorner top left corner
     * @param dimensions    leaf dimensions
     * @param renderable    rendering
     */
    public Leaf(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);;
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof ReactingBlock;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.removeComponent(this.tempTransition);
        //remove component does not work well without an immediate update
        this.update(0);
        this.transform().setAcceleration(Vector2.ZERO);
        this.transform().setVelocity(Vector2.ZERO);
    }

    public void setTemporaryTransition(Transition transition) {
        this.tempTransition = transition;
    }
}