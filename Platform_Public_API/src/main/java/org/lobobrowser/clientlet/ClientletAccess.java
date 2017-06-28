/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
 * Provides utility methods to access the current clientlet context.
 */
public class ClientletAccess {

	/** The Constant currentClientletContext. */
	private static final ThreadLocal<ClientletContext> currentClientletContext = new ThreadLocal<ClientletContext>();

	/**
	 * Instantiates a new clientlet access.
	 */
	private ClientletAccess() {
	}

	/**
	 * Gets the Constant currentClientletContext.
	 *
	 * @return the Constant currentClientletContext
	 */
	public static ClientletContext getCurrentClientletContext() {
		ClientletContext ctx = currentClientletContext.get();
		if (ctx != null) {
			return ctx;
		} else {
			ThreadGroup td = Thread.currentThread().getThreadGroup();
			if (td instanceof ClientletThreadGroup) {
				return ((ClientletThreadGroup) td).getClientletContext();
			} else {
				return null;
			}
		}
	}

	/**
	 * Sets the Constant currentClientletContext.
	 *
	 * @param context
	 *            the new Constant currentClientletContext
	 */
	public static void setCurrentClientletContext(ClientletContext context) {
		currentClientletContext.set(context);
	}
}
