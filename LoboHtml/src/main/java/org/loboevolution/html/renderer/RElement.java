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

import org.loboevolution.html.dom.domimpl.UINode;

/**
 * A renderer node for elements such as blocks, lists, tables, inputs, images,
 * etc.
 */
public interface RElement extends RCollection, UINode {
	int VALIGN_ABSBOTTOM = 4;

	int VALIGN_ABSMIDDLE = 3;

	int VALIGN_BASELINE = 5;
	int VALIGN_BOTTOM = 2;
	int VALIGN_MIDDLE = 1;
	int VALIGN_TOP = 0;

	int getCollapsibleMarginBottom();

	int getCollapsibleMarginTop();

	int getMarginBottom();

	int getMarginLeft();

	int getMarginRight();

	int getMarginTop();

	/**
	 * Vertical alignment for elements rendered in a line. Returns one of the
	 * constants defined in this class.
	 */
	int getVAlign();

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
