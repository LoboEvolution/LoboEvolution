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
package org.lobobrowser.gui;

import java.awt.Component;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.NavigatorProgressEvent;

/**
 * A interface that receives calls during requests.
 */
public interface WindowCallback {

	/**
	 * Gets the component.
	 *
	 * @return the component
	 */
	Component getComponent();

	/**
	 * Called when a document has been accessed, but has not yet rendered.
	 * Processing could be cancelled.
	 *
	 * @param frame
	 *            the frame
	 * @param response
	 *            the response
	 */
	void handleDocumentAccess(NavigatorFrame frame, ClientletResponse response);

	/**
	 * Called when the document has either rendered or is beginning to be
	 * incrementally rendered.
	 *
	 * @param frame
	 *            the frame
	 * @param response
	 *            the response
	 * @param content
	 *            the content
	 */
	void handleDocumentRendering(NavigatorFrame frame, ClientletResponse response, ComponentContent content);

	/**
	 * Updates request progress.
	 *
	 * @param event
	 *            the event
	 */
	void updateProgress(NavigatorProgressEvent event);

	/**
	 * Invoked when there's a request error.
	 *
	 * @param frame
	 *            the frame
	 * @param response
	 *            the response
	 * @param exception
	 *            the exception
	 */
	void handleError(NavigatorFrame frame, ClientletResponse response, Throwable exception);

	/**
	 * Called to set a status message.
	 *
	 * @param frame
	 *            the frame
	 * @param status
	 *            the status
	 */
	void setStatus(NavigatorFrame frame, String status);

	/**
	 * Called to set a default status message.
	 *
	 * @param frame
	 *            the frame
	 * @param defaultStatus
	 *            the default status
	 */
	void setDefaultStatus(NavigatorFrame frame, String defaultStatus);

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	String getStatus();

	/**
	 * Gets the default status.
	 *
	 * @return the default status
	 */
	String getDefaultStatus();
}
