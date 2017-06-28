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
package org.lobobrowser.ua;

import java.util.EventListener;

/**
 * A listener of navigator window events. Listener methods are invoked by the
 * user agent in the GUI dispatch thread.
 *
 * @see NavigatorWindowEvent
 */
public interface NavigatorWindowListener extends EventListener {

	/**
	 * Invoked after a connection request to a URL succeeds and the document
	 * starts loading. There is no guarantee that the document will render. For
	 * example, a download may be started instead.
	 * <p>
	 *
	 * @param event
	 *            the event
	 */
	void documentAccessed(NavigatorWindowEvent event);

	/**
	 * Invoked after a clientlet has set the document content. This could be
	 * when the document has rendered or is being rendered. This method is also
	 * called when a page error occurs.
	 * <p>
	 * This method is meant to be used by the primary extension in order to
	 * update the address bar.
	 *
	 * @param event
	 *            the event
	 */
	void documentRendering(NavigatorWindowEvent event);

	/**
	 * Invoked as a document loads in order to inform the listener of progress.
	 * <p>
	 * This method is meant to be used by the primary extension in order to
	 * update its progress bar. It is invoked outside the GUI dispatch thread.
	 *
	 * @param event
	 *            the event
	 */
	void progressUpdated(NavigatorProgressEvent event);

	/**
	 * Invoked when the status message is updated.
	 *
	 * @param event
	 *            the event
	 */
	void statusUpdated(NavigatorWindowEvent event);

	/**
	 * Invoked when the default status message is updated.
	 *
	 * @param event
	 *            the event
	 */
	void defaultStatusUpdated(NavigatorWindowEvent event);
}
