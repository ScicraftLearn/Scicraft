package be.uantwerpen.scicraft.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An event listener method
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventListener {
    /**
     * The type to subscribe to
     *
     * @return The event type
     */
    EventType type();

    /**
     * The desired shift of the event
     *
     * @return The shift
     */
    Shift shift();
}