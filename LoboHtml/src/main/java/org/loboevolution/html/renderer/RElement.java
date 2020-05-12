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

package org.loboevolution.html.renderer;

import org.loboevolution.html.AlignValues;
import org.loboevolution.html.dom.domimpl.UINode;

/**
 * A renderer node for elements such as blocks, lists, tables, inputs, images,
 * etc.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface RElement extends RCollection, UINode {

	/**
	 * <p>getCollapsibleMarginBottom.</p>
	 *
	 * @return a int.
	 */
	int getCollapsibleMarginBottom();

	/**
	 * <p>getCollapsibleMarginTop.</p>
	 *
	 * @return a int.
	 */
	int getCollapsibleMarginTop();

	/**
	 * <p>getMarginBottom.</p>
	 *
	 * @return a int.
	 */
	int getMarginBottom();

	/**
	 * <p>getMarginLeft.</p>
	 *
	 * @return a int.
	 */
	int getMarginLeft();

	/**
	 * <p>getMarginRight.</p>
	 *
	 * @return a int.
	 */
	int getMarginRight();

	/**
	 * <p>getMarginTop.</p>
	 *
	 * @return a int.
	 */
	int getMarginTop();

	/**
	 * Vertical alignment for elements rendered in a line. Returns one of the
	 * constants defined in this class.
	 *
	 * @return a int.
	 */
	default int getVAlign() {
		return AlignValues.BASELINE.getValue();
	}

	/**
	 * Lays out the subtree below the RElement. The RElement is expected to set its
	 * own dimensions, but not its origin.
	 *
	 * @param availWidth  The available width from the parent's canvas.
	 * @param availHeight The available height from the parent's canvas.
	 * @param sizeOnly    Whether the layout is for sizing determination only.
	 */
	void layout(int availWidth, int availHeight, boolean sizeOnly);
}
