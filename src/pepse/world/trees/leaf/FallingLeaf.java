package pepse.world.trees.leaf;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.Transition;
import danogl.util.Vector2;
import pepse.configuration.TransitionConfiguration;
import pepse.util.TransitionCreator;
import pepse.world.ReactingBlock;

public class FallingLeaf extends Leaf {
    private final Transition transitionToExecute;

    public FallingLeaf(Leaf originalLeaf, TransitionCreator transitionCreator) {
        super(originalLeaf.getTopLeftCorner(),
                originalLeaf.getDimensions(),
                originalLeaf.renderer().getRenderable());
        this.renderer().setOpaqueness(originalLeaf.renderer().getOpaqueness());
        this.transitionToExecute = transitionCreator.createTransitions(
                TransitionConfiguration.LEAF_WOOBLING_CYCLE,
                this)[0];
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof ReactingBlock;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.removeComponent(transitionToExecute);
        //remove component does not work well without an immediate update
        this.update(0);
        this.transform().setAcceleration(Vector2.ZERO);
        this.transform().setVelocity(Vector2.ZERO);
    }
}
