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
package org.loboevolution.html.gui;

import org.loboevolution.html.dombl.DocumentNotificationListener;
import org.loboevolution.html.domimpl.DOMNodeImpl;

/**
 * The listener interface for receiving localDocumentNotification events. The
 * class that is interested in processing a localDocumentNotification event
 * implements this interface, and the object created with that class is
 * registered with a component using the component's
 * <code>addLocalDocumentNotificationListener</code> method. When the
 * localDocumentNotification event occurs, that object's appropriate method is
 * invoked.
 *
 * @see LocalDocumentNotificationEvent
 */
public class LocalDocumentNotificationListener implements DocumentNotificationListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#allInvalidated()
	 */
	@Override
	public void allInvalidated() {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(DocumentNotification.GENERIC, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#invalidated(org.
	 * loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void invalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#lookInvalidated(
	 * org .loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void lookInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(DocumentNotification.LOOK, node));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DocumentNotificationListener#
	 * positionInvalidated (org.loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void positionInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(DocumentNotification.POSITION, node));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#sizeInvalidated(
	 * org .loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void sizeInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(DocumentNotification.SIZE, node));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DocumentNotificationListener#
	 * externalScriptLoading (org.loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void externalScriptLoading(DOMNodeImpl node) {
		// Ignorable here.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.dombl.DocumentNotificationListener#nodeLoaded(org.
	 * loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void nodeLoaded(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.DocumentNotificationListener#
	 * structureInvalidated (org.loboevolution.html.domimpl.DOMNodeImpl)
	 */
	@Override
	public void structureInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(DocumentNotification.GENERIC, node));
	}
}
