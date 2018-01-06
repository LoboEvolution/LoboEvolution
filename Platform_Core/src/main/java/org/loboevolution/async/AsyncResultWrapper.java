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
 * Created on Mar 4, 2006
 */
package org.loboevolution.async;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Internal class.
 *
 * @author J. H. S.
 * @param <Tresult>
 *            the generic type
 */
public class AsyncResultWrapper<Tresult> implements AsyncResult<Tresult>, AsyncResultListener<Tresult> {

	/** The ar. */
	private AsyncResult<Tresult> ar;

	/** The listeners. */
	private final Collection<AsyncResultListener<Tresult>> listeners = new LinkedList<AsyncResultListener<Tresult>>();

	/**
	 * Instantiates a new async result wrapper.
	 *
	 * @param ar
	 *            the ar
	 */
	public AsyncResultWrapper(AsyncResult<Tresult> ar) {
		super();
		this.ar = ar;
	}

	/**
	 * Sets the async result.
	 *
	 * @param ar
	 *            the new async result
	 */
	public void setAsyncResult(AsyncResult<Tresult> ar) {
		AsyncResult<Tresult> oldResult = this.ar;
		if (oldResult != null) {
			oldResult.removeResultListener(this);
		}
		if (ar != null) {
			ar.addResultListener(this);
		}
		this.ar = ar;
	}

	/**
	 * Gets the async result.
	 *
	 * @return the async result
	 */
	public AsyncResult<Tresult> getAsyncResult() {
		return this.ar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.clientlet.AsyncResult#addResultListener(org.xamjwg.clientlet
	 * .AsyncResultListener)
	 */
	@Override
	public void addResultListener(AsyncResultListener<Tresult> listener) {
		synchronized (this) {
			this.listeners.add(listener);
		}
		AsyncResult<Tresult> ar = this.ar;
		if (ar != null) {
			ar.signal();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.AsyncResult#removeResultListener(org.xamjwg.
	 * clientlet .AsyncResultListener)
	 */
	@Override
	public void removeResultListener(AsyncResultListener<Tresult> listener) {
		synchronized (this) {
			this.listeners.remove(listener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.clientlet.AsyncResultListener#exceptionReceived(org.xamjwg
	 * .clientlet.AsyncResultEvent)
	 */
	@Override
	public void exceptionReceived(AsyncResultEvent<Throwable> event) {
		AsyncResultListener<Tresult>[] listenersArray;
		synchronized (this) {
			listenersArray = this.listeners.toArray(new AsyncResultListener[0]);
		}
		for (AsyncResultListener<Tresult> arl : listenersArray) {
			arl.exceptionReceived(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.AsyncResultListener#resultReceived(org.xamjwg.
	 * clientlet .AsyncResultEvent)
	 */
	@Override
	public void resultReceived(AsyncResultEvent<Tresult> event) {
		AsyncResultListener<Tresult>[] listenersArray;
		synchronized (this) {
			listenersArray = this.listeners.toArray(new AsyncResultListener[0]);
		}
		for (AsyncResultListener<Tresult> arl : listenersArray) {
			arl.resultReceived(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.AsyncResult#signal()
	 */
	@Override
	public void signal() {
		AsyncResult<Tresult> ar = this.ar;
		if (ar != null) {
			ar.signal();
		}
	}
}
