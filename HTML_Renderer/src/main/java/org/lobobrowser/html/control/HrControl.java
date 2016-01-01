/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.renderer.RenderableSpot;
import org.lobobrowser.html.renderstate.RenderState;

/**
 * The Class HrControl.
 */
public class HrControl extends BaseControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new hr control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public HrControl(HTMLElementImpl modelNode) {
		super(modelNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		int offset = 8;
		int x = offset;
		int y = (size.height / 2) - 1;
		int width = size.width - (offset * 2);
		g.setColor(Color.black);
		g.drawRect(x, y, width, 0);
	}

	/**
	 * Paint selection.
	 *
	 * @param g
	 *            the g
	 * @param inSelection
	 *            the in selection
	 * @param startPoint
	 *            the start point
	 * @param endPoint
	 *            the end point
	 * @return true, if successful
	 */
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		return inSelection;
	}

	/** The avail width. */
	private int availWidth;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.control.BaseControl#reset(int, int)
	 */
	@Override
	public void reset(int availWidth, int availHeight) {
		this.availWidth = availWidth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		RenderState rs = this.controlElement.getRenderState();
		FontMetrics fm = rs.getFontMetrics();
		return new Dimension(this.availWidth, fm.getHeight());
	}
}
