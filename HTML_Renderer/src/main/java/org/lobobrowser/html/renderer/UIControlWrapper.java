/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import org.lobobrowser.html.HtmlObject;
import org.lobobrowser.html.control.RUIControl;

/**
 * The Class UIControlWrapper.
 */
public class UIControlWrapper implements UIControl {

	/** The component. */
	private final Component component;

	/** The html object. */
	private final HtmlObject htmlObject;

	/**
	 * Instantiates a new UI control wrapper.
	 *
	 * @param ho
	 *            the ho
	 */
	public UIControlWrapper(HtmlObject ho) {
		this.htmlObject = ho;
		Component c;
		if (ho == null) {
			c = new BrokenComponent();
		} else {
			c = ho.getComponent();
		}
		this.component = c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#reset(int, int)
	 */
	@Override
	public void reset(int availWidth, int availHeight) {
		this.htmlObject.reset(availWidth, availHeight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this.component;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#getVAlign()
	 */
	@Override
	public int getVAlign() {
		return RElement.VALIGN_BASELINE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#getBackgroundColor()
	 */
	@Override
	public Color getBackgroundColor() {
		return this.component.getBackground();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return this.component.getPreferredSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#invalidate()
	 */
	@Override
	public void invalidate() {
		// Calls its AWT parent's invalidate, but I guess that's OK.
		this.component.invalidate();
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
		// Does not paint selection
		return inSelection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#setBounds(int, int, int,
	 * int)
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.component.setBounds(x, y, width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.UIControl#setRUIControl(org.lobobrowser.
	 * html .control.RUIControl)
	 */
	@Override
	public void setRUIControl(RUIControl ruicontrol) {
		// Not doing anything with this.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		this.component.paint(g);
	}
}
