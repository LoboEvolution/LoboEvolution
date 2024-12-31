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

package org.loboevolution.html.js.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.loboevolution.events.StorageEvent;
import org.loboevolution.js.webstorage.Storage;

/**
 * StorageEventImpl class.
 */
@NoArgsConstructor
@Getter
public class StorageEventImpl extends UIEventImpl implements StorageEvent {

    private String key;
    private String oldValue;
    private String newValue;
    private String url;
    private Storage storageArea;

    /**
     * <p>Constructor for StorageEventImpl.</p>
     *
     * @param params event constructor parameters
     */
    public StorageEventImpl(Object[] params) {
        setParams(params);
    }

    @Override
    public void initStorageEvent(String type, Boolean bubbles, boolean cancelable, String key, String oldValue,
                                 String newValue, String url, Storage storageArea) {
        initStorageEvent(type, bubbles, cancelable, key, oldValue, newValue, url);
        this.storageArea = storageArea;
    }

    @Override
    public void initStorageEvent(String type, Boolean bubbles, boolean cancelable, String key, String oldValue,
                                 String newValue, String url) {
        initStorageEvent(type, bubbles, cancelable, key, oldValue, newValue);
        this.url = url;
    }

    @Override
    public void initStorageEvent(String type, Boolean bubbles, boolean cancelable, String key, String oldValue,
                                 String newValue) {
        initStorageEvent(type, bubbles, cancelable, key, oldValue);
        this.newValue = newValue;
    }

    @Override
    public void initStorageEvent(String type, Boolean bubbles, boolean cancelable, String key, String oldValue) {
        initStorageEvent(type, bubbles, cancelable, key);
        this.oldValue = oldValue;
    }

    @Override
    public void initStorageEvent(String type, Boolean bubbles, boolean cancelable, String key) {
        super.initEvent(type, bubbles, cancelable);
        this.key = key;
    }

    @Override
    public void initStorageEvent(String type, Boolean bubbles, boolean cancelable) {
        super.initEvent(type, bubbles, cancelable);
    }

    @Override
    public void initStorageEvent(String type, Boolean bubbles) {
        super.initEvent(type, bubbles);
    }

    @Override
    public void initStorageEvent(String type) {
        super.initEvent(type);
    }

    @Override
    public String toString() {
        return "[object StorageEvent]";
    }
}
