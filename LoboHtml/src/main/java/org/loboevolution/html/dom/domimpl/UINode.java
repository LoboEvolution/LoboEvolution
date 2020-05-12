/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Jan 14, 2006
 */
package org.loboevolution.html.dom.domimpl;

import java.awt.Rectangle;

/**
 * A UI node abstraction that is used to send notifications back to the UI and
 * to obtain information the DOM needs from the UI (such as image dimensions).
 *
 * @author utente
 * @version $Id: $Id
 */
public interface UINode {
	/**
	 * <p>blur.</p>
	 */
	void blur();

	/**
	 * <p>focus.</p>
	 */
	void focus();

	/**
	 * <p>getBounds.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	Rectangle getBounds();

	/**
	 * <p>getBoundsRelativeToBlock.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	Rectangle getBoundsRelativeToBlock();

	/**
	 * Called
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 */
	void repaint(ModelNode modelNode);
}
