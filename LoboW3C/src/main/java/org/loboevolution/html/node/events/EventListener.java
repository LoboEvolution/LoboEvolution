package org.loboevolution.html.node.events;

@FunctionalInterface
public interface EventListener<E extends Event> {

    void handleEvent(E evt);
}
