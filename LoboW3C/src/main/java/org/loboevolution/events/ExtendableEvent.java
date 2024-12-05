package org.loboevolution.events;

/**
 * The ExtendableEvent interface extends the lifetime of the install and activate events dispatched on the global scope as part of the service worker lifecycle. This ensures that any functional events (like FetchEvent) are not dispatched until it upgrades database schemas and deletes the outdated cache entries.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/ExtendableEvent">ExtendableEvent - MDN</a>
 * @see <a href="https://w3c.github.io/ServiceWorker/#extendableevent-interface"># extendableevent-interface</a>
 */
public interface ExtendableEvent extends Event {
}
