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
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.style.RenderState;

class HrControl extends BaseControl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int availWidth;

	public HrControl(HTMLElementImpl modelNode) {
		super(modelNode);
	}

	@Override
	public Dimension getPreferredSize() {
		final RenderState rs = this.controlElement.getRenderState();
		final FontMetrics fm = rs.getFontMetrics();
		return new Dimension(this.availWidth, fm.getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		final Dimension size = this.getSize();
		final int offset = 8;
		final int x = offset;
		final int y = size.height / 2 - 1;
		final int width = size.width - offset * 2;
		g.setColor(Color.black);
		g.drawRect(x, y, width, 0);
	}

	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		return inSelection;
	}

	@Override
	public void reset(int availWidth, int availHeight) {
		this.availWidth = availWidth;
	}
}
