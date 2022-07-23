package be.uantwerpen.scicraft.client;

import be.uantwerpen.scicraft.event.EventListener;
import be.uantwerpen.scicraft.event.EventType;
import be.uantwerpen.scicraft.event.Shift;
import be.uantwerpen.scicraft.event.events.RenderEvent;
import be.uantwerpen.scicraft.renderer.Renderer3d;

public class EventHandler {
    @EventListener(shift = Shift.POST, type = EventType.WORLD_RENDER)
    void worldRendered(RenderEvent event) {
        Renderer3d.renderFadingBlocks(event.getStack());
    }
}