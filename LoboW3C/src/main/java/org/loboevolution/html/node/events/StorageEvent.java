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

package org.loboevolution.html.node.events;

import org.loboevolution.html.node.js.webstorage.Storage;

/**
 * A StorageEvent is sent to a window when a storage area it has access to is
 * changed within the context of another document.
 *
 *
 *
 */
public interface StorageEvent extends Event {

	/**
	 * <p>getKey.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getKey();

	/**
	 * Returns the new value of the key of the storage item whose value is being
	 * changed.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getNewValue();

	/**
	 * Returns the old value of the key of the storage item whose value is being
	 * changed.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getOldValue();

	/**
	 * Returns the Storage object that was affected.
	 *
	 * @return a {@link org.loboevolution.html.node.js.webstorage.Storage} object.
	 */
	Storage getStorageArea();

	/**
	 * Returns the URL of the document whose storage item changed.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getUrl();

	interface StorageEventInit extends EventInit {

		String getKey();

		void setKey(String key);

		String getNewValue();

		void setNewValue(String newValue);

		String getOldValue();

		void setOldValue(String oldValue);

		Storage getStorageArea();

		void setStorageArea(Storage storageArea);

		String getUrl();

		void setUrl(String url);

	}
}
