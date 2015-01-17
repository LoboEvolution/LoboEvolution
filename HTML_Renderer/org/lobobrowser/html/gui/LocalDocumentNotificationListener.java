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

public class LocalDocumentNotificationListener implements
		DocumentNotificationListener {
	public void allInvalidated() {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.GENERIC, null));
	}

	public void invalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.GENERIC, node));
	}

	public void lookInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.LOOK, node));
	}

	public void positionInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.POSITION, node));
	}

	public void sizeInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.SIZE, node));
	}

	public void externalScriptLoading(DOMNodeImpl node) {
		// Ignorable here.
	}

	public void nodeLoaded(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.GENERIC, node));
	}

	public void structureInvalidated(DOMNodeImpl node) {
		HtmlPanel html = new HtmlPanel();
		html.addNotification(new DocumentNotification(
				DocumentNotification.GENERIC, node));
	}
}