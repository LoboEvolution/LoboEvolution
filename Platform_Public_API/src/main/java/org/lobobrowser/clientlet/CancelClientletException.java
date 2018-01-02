/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.clientlet;

/**
 * This exception may be thrown from the
 * {@link Clientlet#process(ClientletContext)} method of a clientlet to cancel
 * the request. Doing so will not result in an error page if the clientlet is
 * still processing the web response. Preferably, it should be thrown before
 * reading any input.
 * <p>
 * This exception may be thrown right after navigation is redirected to another
 * document.
 *
 * @author J. H. S.
 */
public class CancelClientletException extends ClientletException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new cancel clientlet exception.
	 *
	 * @param message
	 *            the message
	 */
	public CancelClientletException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new cancel clientlet exception.
	 */
	public CancelClientletException() {
		super("");
	}

	/**
	 * Instantiates a new cancel clientlet exception.
	 *
	 * @param message
	 *            the message
	 * @param rootCause
	 *            the root cause
	 */
	public CancelClientletException(String message, Throwable rootCause) {
		super(message, rootCause);
	}

	/**
	 * Instantiates a new cancel clientlet exception.
	 *
	 * @param rootCause
	 *            the root cause
	 */
	public CancelClientletException(Throwable rootCause) {
		super(rootCause);
	}
}
