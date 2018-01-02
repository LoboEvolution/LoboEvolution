/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Mar 31, 2005
 */
package org.loboevolution.async;

/**
 * Used by methods that need to return results asynchronously. Results are
 * received in the event dispatch thread. This is a generic class that takes a
 * type parameter <code>TResult</code>, the type of the expected result object.
 *
 * @author J. H. S.
 * @param <TResult>
 *            the generic type
 */
public interface AsyncResult<TResult> {

	/**
	 * Registers a listener of asynchronous results.
	 *
	 * @param listener
	 *            the listener
	 */
	void addResultListener(AsyncResultListener<TResult> listener);

	/**
	 * Removes a listener.
	 *
	 * @param listener
	 *            the listener
	 */
	void removeResultListener(AsyncResultListener<TResult> listener);

	/**
	 * Forces listeners to be notified of a result if there is one.
	 */
	void signal();
}
