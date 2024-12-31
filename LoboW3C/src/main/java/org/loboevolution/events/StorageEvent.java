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

import org.loboevolution.js.webstorage.Storage;

/**
 * The StorageEvent interface is implemented by the storage event, which is sent to a window when a storage
 * area the window has access to is changed within the context of another document.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/StorageEvent">StorageEvent - MDN</a>
 * @see <a href="https://html.spec.whatwg.org/multipage/webstorage.html#the-storageevent-interface"># the-storageevent-interface</a>
 */
public interface StorageEvent extends Event {


    String getKey();

    String getNewValue();

    String getOldValue();

    Storage getStorageArea();

    String getUrl();

    void initStorageEvent(String type, Boolean bubbles, boolean cancelable,
                          String key, String oldValue, String newValue,
                          String url, Storage storageArea);

    void initStorageEvent(String type, Boolean bubbles, boolean cancelable,
                          String key, String oldValue, String newValue,
                          String url);

    void initStorageEvent(String type, Boolean bubbles, boolean cancelable,
                          String key, String oldValue, String newValue);

    void initStorageEvent(String type, Boolean bubbles, boolean cancelable,
                          String key, String oldValue);

    void initStorageEvent(String type, Boolean bubbles, boolean cancelable,
                          String key);

    void initStorageEvent(String type, Boolean bubbles, boolean cancelable);

    void initStorageEvent(String type, Boolean bubbles);

    void initStorageEvent(String type);
}
