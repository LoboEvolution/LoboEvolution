/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.events;

/**
 * The parameter passed into the onnotificationclick handler, the NotificationEvent interface represents
 * a notification click event that is dispatched on the ServiceWorkerGlobalScope of a ServiceWorker.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/NotificationEvent">NotificationEvent - MDN</a>
 * @see <a href="https://notifications.spec.whatwg.org/#notificationevent"># notificationevent</a>
 */
public interface NotificationEvent extends ExtendableEvent {

    /**
     * Returns the string ID of the notification button the user clicked. This value returns an empty string if the user clicked the notification somewhere other than an action button, or the notification does not have a button. The notification id is set during the creation of the Notification via the actions array attribute and can't be modified unless the notification is replaced.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/NotificationEvent/action">NotificationEvent.action - MDN</a>
     * @see <a href="https://notifications.spec.whatwg.org/#dom-notification-actions">action - Notifications API</a>
     */
    String action();

    /**
     * The notification read-only property of the NotificationEvent interface returns the instance of the Notification that was clicked to fire the event. The Notification provides read-only access to many properties that were set at the instantiation time of the Notification such as tag and data attributes that allow you to store information for defered use in the notificationclick event.
     *
     * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/NotificationEvent/notification">NotificationEvent.notification - MDN</a>
     * @see <a href="https://notifications.spec.whatwg.org/#dom-notificationevent-notification">notification - Notifications API</a>
     */
    Object notification();
}
