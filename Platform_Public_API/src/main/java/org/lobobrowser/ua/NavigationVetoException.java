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
package org.lobobrowser.ua;

/**
 * An exception thrown by {@link NavigationListener} methods in order to prevent
 * navigation from occurring.
 *
 * @see NavigationListener
 */
public class NavigationVetoException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new navigation veto exception.
	 */
	public NavigationVetoException() {
		super();
	}

	/**
	 * Instantiates a new navigation veto exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public NavigationVetoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new navigation veto exception.
	 *
	 * @param message
	 *            the message
	 */
	public NavigationVetoException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new navigation veto exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public NavigationVetoException(Throwable cause) {
		super(cause);
	}
}
