package be.uantwerpen.scicraft.event;

public enum Shift {
    /**
     * Before the event happens. Can be cancelled
     */
    PRE,
    /**
     * After the event happened. Cancelling will not throw an exception, but will not do anything
     */
    POST
}