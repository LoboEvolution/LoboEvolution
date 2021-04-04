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

package org.loboevolution.html.node.js.webstorage;

/**
 * This Web Storage API interface provides access to a particular domain's
 * session or local storage. It allows, for example, the addition, modification,
 * or deletion of stored data items.
 *
 *
 *
 */
public interface Storage {
	
	/**
	 * <p>getLength.</p>
	 *
	 * @return a int.
	 */
	int getLength();

	/**
	 * Empties the list associated with the object of all key/value pairs, if there
	 * are any.
	 */
	void clear();

	/**
	 * Returns the current value associated with the given key, or null if the given
	 * key does not exist in the list associated with the object.
	 *
	 * @param key a {@link java.lang.String} object.
	 * @return a {@link java.lang.Object} object.
	 */
	Object getItem(String key);

	/**
	 * Returns the name of the nth key in the list, or null if n is greater than or
	 * equal to the number of key/value pairs in the object.
	 *
	 * @param index a int.
	 * @return a {@link java.lang.Object} object.
	 */
	Object key(int index);

	/**
	 * Removes the key/value pair with the given key from the list associated with
	 * the object, if a key/value pair with the given key exists.
	 *
	 * @param key a {@link java.lang.String} object.
	 */
	void removeItem(String key);

	/**
	 * Sets the value of the pair identified by key to value, creating a new
	 * key/value pair if none existed for key previously.
	 * <p>
	 * Throws a "QuotaExceededError" DOMException exception if the new value
	 * couldn't be set. (Setting could fail if, e.g., the user has disabled storage
	 * for the site, or if the quota has been exceeded.)
	 *
	 * @param key a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	void setItem(String key, String value);

	/**
	 * <p>get.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.lang.Object} object.
	 */
	Object get(String name);

	/**
	 * <p>set.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	void set(String name, String value);

}
