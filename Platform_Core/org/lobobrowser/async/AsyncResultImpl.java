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
 * Created on Mar 31, 2005
 */
package org.lobobrowser.async;

import java.awt.EventQueue;
import java.util.EventObject;

import org.lobobrowser.util.EventDispatch;
import org.lobobrowser.util.GenericEventListener;
import org.lobobrowser.util.Objects;


/**
 * The Class AsyncResultImpl.
 *
 * @author J. H. S.
 * @param <TResult> the generic type
 */
public class AsyncResultImpl<TResult> implements AsyncResult<TResult> {
	
	/** The evt result. */
	private final EventDispatch evtResult = new EventDispatch();
	
	/** The result. */
	private TResult result;
	
	/** The exception. */
	private Throwable exception;
	
	/** The has result. */
	private boolean hasResult = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.dom.AsyncResult#addResultListener(org.xamjwg.dom.
	 * AsyncResultListener)
	 */
	public void addResultListener(final AsyncResultListener<TResult> listener) {
		synchronized (this) {
			if (this.hasResult) {
				if (this.exception != null) {
					final Throwable exception = this.exception;
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							// Invoke holding no locks
							AsyncResultEvent<Throwable> are = new AsyncResultEvent<Throwable>(
									AsyncResultImpl.this, exception);
							listener.exceptionReceived(are);
						}
					});

				} else {
					final TResult result = this.result;
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							// Invoke holding no locks
							AsyncResultEvent<TResult> are = new AsyncResultEvent<TResult>(
									AsyncResultImpl.this, result);
							listener.resultReceived(are);
						}
					});
				}
			}
			evtResult.addListener(new EventListenerWrapper<TResult>(listener));
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
		this.evtResult.removeListener(new EventListenerWrapper<TResult>(
				listener));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.AsyncResult#signal()
	 */
	public void signal() {
		synchronized (this) {
			if (this.hasResult) {
				if (this.exception != null) {
					final Throwable exception = this.exception;
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							// Invoke holding no locks
							AsyncResultEvent<Throwable> are = new AsyncResultEvent<Throwable>(
									AsyncResultImpl.this, exception);
							evtResult.fireEvent(are);
						}
					});

				} else {
					final TResult result = this.result;
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							// Invoke holding no locks
							AsyncResultEvent<TResult> are = new AsyncResultEvent<TResult>(
									AsyncResultImpl.this, result);
							evtResult.fireEvent(are);
						}
					});
				}
			}
		}
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(final TResult result) {
		synchronized (this) {
			this.result = result;
			this.hasResult = true;
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					// Invoke holding no locks
					evtResult.fireEvent(new AsyncResultEvent<TResult>(
							AsyncResultImpl.this, result));
				}
			});
		}
	}

	/**
	 * Sets the exception.
	 *
	 * @param exception the new exception
	 */
	public void setException(final Throwable exception) {
		synchronized (this) {
			this.exception = exception;
			this.hasResult = true;
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					// Invoke holding no locks
					evtResult.fireEvent(new AsyncResultEvent<Throwable>(
							AsyncResultImpl.this, exception));
				}
			});
		}
	}

	/**
	 * The Class EventListenerWrapper.
	 *
	 * @param <TR> the generic type
	 */
	private static class EventListenerWrapper<TR> implements
			GenericEventListener {
		
		/** The listener. */
		private final AsyncResultListener<TR> listener;

		/**
		 * Instantiates a new event listener wrapper.
		 *
		 * @param listener the listener
		 */
		public EventListenerWrapper(AsyncResultListener<TR> listener) {
			super();
			this.listener = listener;
		}

		/* (non-Javadoc)
		 * @see org.lobobrowser.util.GenericEventListener#processEvent(java.util.EventObject)
		 */
		public void processEvent(EventObject event) {
			// Invoke holding no locks
			AsyncResultEvent are = (AsyncResultEvent) event;
			if (are.getResult() instanceof Exception) {
				this.listener.exceptionReceived(are);
			} else {
				this.listener.resultReceived(are);
			}
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object other) {
			if (!(other instanceof EventListenerWrapper)) {
				return false;
			}
			EventListenerWrapper elw = (EventListenerWrapper) other;
			return Objects.equals(elw.listener, this.listener);
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode() {
			return this.listener.hashCode();
		}
	}
}
