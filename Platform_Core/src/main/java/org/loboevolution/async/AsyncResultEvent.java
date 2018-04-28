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

import java.util.EventObject;

/**
 * An asynchronous result event. This is a generic class with a
 * <code>Tresult</code> type parameter, the type of the result.
 *
 * @author J. H. S.
 * @param <Tresult>
 *            the generic type
 * @see org.loboevolution.async.AsyncResultListener
 */
public class AsyncResultEvent<Tresult> extends EventObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The result. */
	private final transient Tresult result;

	/**
	 * Instance constructor.
	 *
	 * @param source
	 *            The event source.
	 * @param result
	 *            The asynchronous result.
	 */
	public AsyncResultEvent(Object source, Tresult result) {
		super(source);
		this.result = result;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public Tresult getResult() {
		return this.result;
	}
}
