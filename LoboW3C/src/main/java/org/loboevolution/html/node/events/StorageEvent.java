/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
