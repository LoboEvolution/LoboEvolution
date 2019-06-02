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
 * Created on Nov 5, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

abstract interface UIControl {
	Color getBackgroundColor();

	java.awt.Component getComponent();

	Dimension getPreferredSize();

	int getVAlign();

	void invalidate();

	// public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot
	// startPoint, RenderableSpot endPoint);
	void paint(Graphics g);

	/**
	 * Called as the control is layed out, either the first time HTML layout occurs
	 * or when the DOM changes. This method should reset its state assuming the
	 * element has changed, and change its preferred size if appropriate.
	 */
	void reset(int availWidth, int availHeight);

	void setBounds(int x, int y, int width, int height);

	void setRUIControl(RUIControl ruicontrol);
}
