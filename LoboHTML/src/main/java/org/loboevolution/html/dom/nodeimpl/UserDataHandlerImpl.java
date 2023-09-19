/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package org.loboevolution.html.dom.nodeimpl;


import org.loboevolution.html.dom.UserDataHandler;
import org.loboevolution.html.node.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility implementation of UserDataHandler that captures all
 * notifications
 */
public class UserDataHandlerImpl implements UserDataHandler {
    private final List<UserDataNotification> notifications = new ArrayList<>();

    /**
     * Public constructor
     */
    public UserDataHandlerImpl() {
    }

    @Override
    public void handle(short operation, String key, Object data, Node src, Node dst) {
        notifications.add(new UserDataNotification(operation, key, data, src, dst));
    }

    /**
     * Gets list of notifications
     *
     * @return List of notifications, may not be null.
     */
    public final List<UserDataNotification> getAllNotifications() {
        return new ArrayList(notifications);
    }


}
