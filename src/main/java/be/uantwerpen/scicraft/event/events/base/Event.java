package be.uantwerpen.scicraft.event.events.base;

/**
 * <p>An event</p>
 */
public class Event {
    /**
     * Whether the event has been cancelled
     */

    private boolean cancelled = false;

    public boolean getCancelled(){
        return this.cancelled;
    }
    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}