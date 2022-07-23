package be.uantwerpen.scicraft.event.events;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A block entity was rendered
 */
@SuppressWarnings("unused")
public class BlockEntityRenderEvent extends RenderEvent {

    /**
     * The BlockEntity which has been rendered
     */

    final BlockEntity entity;
    public BlockEntity getEntity(){
        return this.entity;
    }
    /**
     * Constructs a new event
     *
     * @param stack  The context MatrixStack
     * @param entity The BlockEntity that was rendered
     */
    public BlockEntityRenderEvent(MatrixStack stack, BlockEntity entity) {
        super(stack);
        this.entity = entity;
    }
}