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

package org.loboevolution.html.control;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import org.loboevolution.html.dom.domimpl.HTMLCanvasElementImpl;

/**
 * The Class CanvasControl.
 *
 * @author utente
 * @version $Id: $Id
 */
public class CanvasControl extends BaseControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The canvas. */
	private final HTMLCanvasElementImpl canvas;

	/**
	 * Instantiates a new canvas control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public CanvasControl(HTMLCanvasElementImpl modelNode) {
		super(modelNode);
		canvas = modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawRect(0, 0, canvas.getWidth(), canvas.getHeight());
		g2d.drawImage(canvas.getImage(), new AffineTransform(), null);
	}
}
