/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Copyright (c) 2003 World Wide Web Consortium, (Massachusetts Institute of
 * Technology, Institut National de Recherche en Informatique et en Automatique,
 * Keio University). All Rights Reserved. This program is distributed under the
 * W3C's Software Intellectual Property License. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.w3c.html;

import org.mozilla.javascript.Function;

/**
 * The public interface MessagePort.
 */
public interface MessagePort {
	// MessagePort
	/**
	 * Post message.
	 *
	 * @param message
	 *            the message
	 */
	void postMessage(Object message);

	/**
	 * Post message.
	 *
	 * @param message
	 *            the message
	 * @param ports
	 *            the ports
	 */
	void postMessage(Object message, MessagePort[] ports);

	/**
	 * Start.
	 */
	void start();

	/**
	 * Close.
	 */
	void close();

	/**
	 * Gets the onmessage.
	 *
	 * @return the onmessage
	 */
	Function getOnmessage();

	/**
	 * Sets the onmessage.
	 *
	 * @param onmessage
	 *            the new onmessage
	 */
	void setOnmessage(Function onmessage);
}
