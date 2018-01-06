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

import javax.swing.SwingUtilities;

import org.loboevolution.util.EventDispatch;
import org.loboevolution.util.GenericEventListener;
import org.loboevolution.util.Objects;

/**
 * The Class AsyncResultImpl.
 *
 * @author J. H. S.
 * @param <Tresult>
 *            the generic type
 */
public class AsyncResultImpl<Tresult> implements AsyncResult<Tresult> {

	/** The evt result. */
	private final EventDispatch evtResult = new EventDispatch();

	/** The result. */
	private Tresult result;

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
	@Override
	public void addResultListener(final AsyncResultListener<Tresult> listener) {
		synchronized (this) {
			if (this.hasResult) {
				if (this.exception != null) {
					final Throwable exception = this.exception;
					if (SwingUtilities.isEventDispatchThread()) {
						AsyncResultEvent<Throwable> are = new AsyncResultEvent<Throwable>(AsyncResultImpl.this,
								exception);
						listener.exceptionReceived(are);
					} else {

						SwingUtilities.invokeLater(() -> {
							// Invoke holding no locks
							AsyncResultEvent<Throwable> are = new AsyncResultEvent<Throwable>(AsyncResultImpl.this,
									exception);
							listener.exceptionReceived(are);
						});
					}

				} else {
					final Tresult result = this.result;
					if (SwingUtilities.isEventDispatchThread()) {
						// Invoke holding no locks
						AsyncResultEvent<Tresult> are = new AsyncResultEvent<Tresult>(AsyncResultImpl.this, result);
						listener.resultReceived(are);
					} else {

						SwingUtilities.invokeLater(() -> {
							// Invoke holding no locks
							AsyncResultEvent<Tresult> are = new AsyncResultEvent<Tresult>(AsyncResultImpl.this, result);
							listener.resultReceived(are);
						});
					}
				}
			}
			evtResult.addListener(new EventListenerWrapper<Tresult>(listener));
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
		this.evtResult.removeListener(new EventListenerWrapper<Tresult>(listener));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.AsyncResult#signal()
	 */
	@Override
	public void signal() {
		synchronized (this) {
			if (this.hasResult) {
				if (this.exception != null) {
					final Throwable exception = this.exception;
					if (SwingUtilities.isEventDispatchThread()) {
						// Invoke holding no locks
						AsyncResultEvent<Throwable> are = new AsyncResultEvent<Throwable>(AsyncResultImpl.this,
								exception);
						evtResult.fireEvent(are);
					} else {

						SwingUtilities.invokeLater(() -> {
							// Invoke holding no locks
							AsyncResultEvent<Throwable> are = new AsyncResultEvent<Throwable>(AsyncResultImpl.this,
									exception);
							evtResult.fireEvent(are);
						});
					}

				} else {
					final Tresult result = this.result;
					if (SwingUtilities.isEventDispatchThread()) {
						// Invoke holding no locks
						AsyncResultEvent<Tresult> are = new AsyncResultEvent<Tresult>(AsyncResultImpl.this, result);
						evtResult.fireEvent(are);

					} else {

						SwingUtilities.invokeLater(() -> {
							// Invoke holding no locks
							AsyncResultEvent<Tresult> are = new AsyncResultEvent<Tresult>(AsyncResultImpl.this, result);
							evtResult.fireEvent(are);
						});
					}
				}
			}
		}
	}

	/**
	 * Sets the result.
	 *
	 * @param result
	 *            the new result
	 */
	public void setResult(final Tresult result) {
		synchronized (this) {
			this.result = result;
			this.hasResult = true;
			SwingUtilities.invokeLater(
					() -> evtResult.fireEvent(new AsyncResultEvent<Tresult>(AsyncResultImpl.this, result)));
		}
	}

	/**
	 * Sets the exception.
	 *
	 * @param exception
	 *            the new exception
	 */
	public void setException(final Throwable exception) {
		synchronized (this) {
			this.exception = exception;
			this.hasResult = true;
			if (SwingUtilities.isEventDispatchThread()) {
				evtResult.fireEvent(new AsyncResultEvent<Throwable>(AsyncResultImpl.this, exception));
			} else {

				SwingUtilities.invokeLater(
						() -> evtResult.fireEvent(new AsyncResultEvent<Throwable>(AsyncResultImpl.this, exception)));
			}
		}
	}

	/**
	 * The Class EventListenerWrapper.
	 *
	 * @param <TR>
	 *            the generic type
	 */
	private static class EventListenerWrapper<TR> implements GenericEventListener {

		/** The listener. */
		private final AsyncResultListener<TR> listener;

		/**
		 * Instantiates a new event listener wrapper.
		 *
		 * @param listener
		 *            the listener
		 */
		public EventListenerWrapper(AsyncResultListener<TR> listener) {
			super();
			this.listener = listener;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.loboevolution.util.GenericEventListener#processEvent(java.util.
		 * EventObject)
		 */
		@Override
		public void processEvent(EventObject event) {
			// Invoke holding no locks
			AsyncResultEvent are = (AsyncResultEvent) event;
			if (are.getResult() instanceof Exception) {
				this.listener.exceptionReceived(are);
			} else {
				this.listener.resultReceived(are);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object other) {
			if (!(other instanceof EventListenerWrapper)) {
				return false;
			}
			EventListenerWrapper elw = (EventListenerWrapper) other;
			return Objects.equals(elw.listener, this.listener);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return this.listener.hashCode();
		}
	}
}
