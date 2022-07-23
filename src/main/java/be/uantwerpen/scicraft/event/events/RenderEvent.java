package be.uantwerpen.scicraft.event.events;

import be.uantwerpen.scicraft.event.events.base.Event;
import net.minecraft.client.util.math.MatrixStack;

public class RenderEvent extends Event {

    /**
     * The context MatrixStack
     */
    final MatrixStack stack;

    public MatrixStack getStack() {
        return this.stack;
    }

    public RenderEvent(MatrixStack stack) {
        this.stack = stack;
    }
}