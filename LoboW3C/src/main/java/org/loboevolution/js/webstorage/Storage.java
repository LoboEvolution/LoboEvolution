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

package org.loboevolution.js.webstorage;

/**
 * This Web Storage API interface provides access to a particular domain's
 * session or local storage. It allows, for example, the addition, modification,
 * or deletion of stored data items.
 */
public interface Storage {
	
	/**
	 * <p>getLength.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
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
	 * @param index a {@link java.lang.Integer} object.
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

}
