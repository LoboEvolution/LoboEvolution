/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.html.gui;

import org.lobobrowser.html.dombl.DocumentNotificationListener;
import org.lobobrowser.html.domimpl.DOMNodeImpl;


/**
 * The listener interface for receiving localDocumentNotification events.
 * The class that is interested in processing a localDocumentNotification
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLocalDocumentNotificationListener</code> method. When
 * the localDocumentNotification event occurs, that object's appropriate
 * method is invoked.
 */
public class LocalDocumentNotificationListener implements
		DocumentNotificationListener {
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.DocumentNotificationListener#allInvalidated()
	 */
	public void allInvalidated() {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.GENERIC, null));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.DocumentNotificationListener#invalidated(org.lobobrowser.html.domimpl.DOMNodeImpl)
	 */
	public void invalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.GENERIC, node));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.DocumentNotificationListener#lookInvalidated(org.lobobrowser.html.domimpl.DOMNodeImpl)
	 */
	public void lookInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.LOOK, node));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.DocumentNotificationListener#positionInvalidated(org.lobobrowser.html.domimpl.DOMNodeImpl)
	 */
	public void positionInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.POSITION, node));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.DocumentNotificationListener#sizeInvalidated(org.lobobrowser.html.domimpl.DOMNodeImpl)
	 */
	public void sizeInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.SIZE, node));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.DocumentNotificationListener#externalScriptLoading(org.lobobrowser.html.domimpl.DOMNodeImpl)
	 */
	public void externalScriptLoading(DOMNodeImpl node) {
		// Ignorable here.
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.DocumentNotificationListener#nodeLoaded(org.lobobrowser.html.domimpl.DOMNodeImpl)
	 */
	public void nodeLoaded(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.GENERIC, node));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.dombl.DocumentNotificationListener#structureInvalidated(org.lobobrowser.html.domimpl.DOMNodeImpl)
	 */
	public void structureInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.GENERIC, node));
	}
}