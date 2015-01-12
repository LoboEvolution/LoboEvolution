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
package org.lobobrowser.html.w3c;

import org.w3c.dom.Node;
import org.w3c.dom.ranges.Range;

public interface Selection {
	// Selection
	public Node getAnchorNode();

	public int getAnchorOffset();

	public Node getFocusNode();

	public int getFocusOffset();

	public boolean getIsCollapsed();

	public void collapse(Node parentNode, int offset);

	public void collapseToStart();

	public void collapseToEnd();

	public void selectAllChildren(Node parentNode);

	public void deleteFromDocument();

	public int getRangeCount();

	public Range getRangeAt(int index);

	public void addRange(Range range);

	public void removeRange(Range range);

	public void removeAllRanges();

	public String toString();
}
