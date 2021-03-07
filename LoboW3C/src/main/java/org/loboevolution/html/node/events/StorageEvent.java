package org.loboevolution.html.node.events;

import org.loboevolution.html.node.js.webstorage.Storage;

/**
 * A StorageEvent is sent to a window when a storage area it has access to is
 * changed within the context of another document.
 */
public interface StorageEvent extends Event {

	String getKey();

	/**
	 * Returns the new value of the key of the storage item whose value is being
	 * changed.
	 */

	String getNewValue();

	/**
	 * Returns the old value of the key of the storage item whose value is being
	 * changed.
	 */

	String getOldValue();

	/**
	 * Returns the Storage object that was affected.
	 */

	Storage getStorageArea();

	/**
	 * Returns the URL of the document whose storage item changed.
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
