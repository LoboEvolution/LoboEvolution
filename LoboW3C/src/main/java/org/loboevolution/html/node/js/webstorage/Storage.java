package org.loboevolution.html.node.js.webstorage;

/**
 * This Web Storage API interface provides access to a particular domain's
 * session or local storage. It allows, for example, the addition, modification,
 * or deletion of stored data items.
 */
public interface Storage {
	
	int getLength();

	/**
	 * Empties the list associated with the object of all key/value pairs, if there
	 * are any.
	 */
	void clear();

	/**
	 * Returns the current value associated with the given key, or null if the given
	 * key does not exist in the list associated with the object.
	 */
	
	Object getItem(String key);

	/**
	 * Returns the name of the nth key in the list, or null if n is greater than or
	 * equal to the number of key/value pairs in the object.
	 */
	
	Object key(int index);

	/**
	 * Removes the key/value pair with the given key from the list associated with
	 * the object, if a key/value pair with the given key exists.
	 */
	void removeItem(String key);

	/**
	 * Sets the value of the pair identified by key to value, creating a new
	 * key/value pair if none existed for key previously.
	 * <p>
	 * Throws a "QuotaExceededError" DOMException exception if the new value
	 * couldn't be set. (Setting could fail if, e.g., the user has disabled storage
	 * for the site, or if the quota has been exceeded.)
	 */
	void setItem(String key, String value);

	Object get(String name);

	void set(String name, String value);

}
