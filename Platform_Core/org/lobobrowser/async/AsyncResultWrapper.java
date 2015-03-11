/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Mar 4, 2006
 */
package org.lobobrowser.async;

import java.util.Collection;
import java.util.LinkedList;


/**
 * Internal class.
 *
 * @author J. H. S.
 * @param <TResult> the generic type
 */
public class AsyncResultWrapper<TResult> implements AsyncResult<TResult>,
		AsyncResultListener<TResult> {
	
	/** The ar. */
	private AsyncResult<TResult> ar;
	
	/** The listeners. */
	private final Collection<AsyncResultListener<TResult>> listeners = new LinkedList<AsyncResultListener<TResult>>();

	/**
	 * Instantiates a new async result wrapper.
	 *
	 * @param ar the ar
	 */
	public AsyncResultWrapper(AsyncResult<TResult> ar) {
		super();
		this.ar = ar;
	}

	/**
	 * Sets the async result.
	 *
	 * @param ar            The ar to set.
	 */
	public void setAsyncResult(AsyncResult<TResult> ar) {
		AsyncResult<TResult> oldResult = this.ar;
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
	public AsyncResult<TResult> getAsyncResult() {
		return this.ar;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.clientlet.AsyncResult#addResultListener(org.xamjwg.clientlet
	 * .AsyncResultListener)
	 */
	public void addResultListener(AsyncResultListener<TResult> listener) {
		synchronized (this) {
			this.listeners.add(listener);
		}
		AsyncResult<TResult> ar = this.ar;
		if (ar != null) {
			ar.signal();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.clientlet.AsyncResult#removeResultListener(org.xamjwg.clientlet
	 * .AsyncResultListener)
	 */
	public void removeResultListener(AsyncResultListener<TResult> listener) {
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
	public void exceptionReceived(AsyncResultEvent<Throwable> event) {
		AsyncResultListener<TResult>[] listenersArray;
		synchronized (this) {
			listenersArray = (AsyncResultListener<TResult>[]) this.listeners
					.toArray(new AsyncResultListener[0]);
		}
		for (int i = 0; i < listenersArray.length; i++) {
			AsyncResultListener<TResult> arl = listenersArray[i];
			arl.exceptionReceived(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.clientlet.AsyncResultListener#resultReceived(org.xamjwg.clientlet
	 * .AsyncResultEvent)
	 */
	public void resultReceived(AsyncResultEvent<TResult> event) {
		AsyncResultListener<TResult>[] listenersArray;
		synchronized (this) {
			listenersArray = (AsyncResultListener<TResult>[]) this.listeners
					.toArray(new AsyncResultListener[0]);
		}
		for (int i = 0; i < listenersArray.length; i++) {
			AsyncResultListener<TResult> arl = listenersArray[i];
			arl.resultReceived(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.AsyncResult#signal()
	 */
	public void signal() {
		AsyncResult<TResult> ar = this.ar;
		if (ar != null) {
			ar.signal();
		}
	}
}
