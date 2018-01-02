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

import java.util.EventListener;

/**
 * Listener of asynchronous results.
 *
 * @author J. H. S.
 * @param <TResult>
 *            the generic type
 * @see org.loboevolution.async.AsyncResult
 */
public interface AsyncResultListener<TResult> extends EventListener {
	/**
	 * Receives an asynchronous result. This method is invoked in the event
	 * dispatch thread.
	 *
	 * @param event
	 *            Event containing asynchronous result.
	 */
	void resultReceived(AsyncResultEvent<TResult> event);

	/**
	 * Called when an exception has occurred trying to obtain an asynchronous
	 * result. This method is invoked in the event dispatch thread.
	 *
	 * @param event
	 *            Event containing the exception.
	 */
	void exceptionReceived(AsyncResultEvent<Throwable> event);
}
