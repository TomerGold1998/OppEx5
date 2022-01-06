package pepse.world;

import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * reacting block class, used in order to increase performance \
 * (no need to collid with all of the blocks, just with the ReactingBlocks)
 */
public class ReactingBlock extends Block {
    public ReactingBlock(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, renderable);
    }
}
