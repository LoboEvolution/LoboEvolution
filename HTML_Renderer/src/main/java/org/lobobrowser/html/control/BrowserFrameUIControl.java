/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import org.lobobrowser.html.BrowserFrame;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderer.RElement;
import org.lobobrowser.html.renderer.RenderableSpot;
import org.lobobrowser.html.renderer.UIControl;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLElement;

/**
 * The Class BrowserFrameUIControl.
 */
public class BrowserFrameUIControl implements UIControl, HtmlAttributeProperties {
	
	/** The component. */
	private final Component component;

	/** The element. */
	private final HTMLElement element;

	/** The browser frame. */
	private final BrowserFrame browserFrame;

	/** The rui control. */
	private RUIControl ruiControl;
	
	/** The avail width. */
	private int availWidth;

	/** The avail height. */
	private int availHeight;

	/**
	 * Instantiates a new browser frame ui control.
	 *
	 * @param element
	 *            the element
	 * @param browserFrame
	 *            the browser frame
	 */
	public BrowserFrameUIControl(HTMLElement element, BrowserFrame browserFrame) {
		this.component = browserFrame.getComponent();
		this.browserFrame = browserFrame;
		this.element = element;
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

	/**
	 * Gets the alignment y.
	 *
	 * @return the alignment y
	 */
	public float getAlignmentY() {
		return 0;
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
	 * @see org.lobobrowser.html.renderer.UIControl#getComponent()
	 */
	@Override
	public Component getComponent() {
		return this.component;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#reset(int, int)
	 */
	@Override
	public void reset(int availWidth, int availHeight) {
		this.availWidth = availWidth;
		this.availHeight = availHeight;
		RUIControl ruiControl = this.ruiControl;
		if (ruiControl != null) {
			ModelNode node = ruiControl.getModelNode();
			HTMLElement element = (HTMLElement) node;
			RenderState renderState = node.getRenderState();
			HtmlInsets insets = null;
			String marginwidth = element.getAttribute(MARGINWIDTH);
			String marginheight = element.getAttribute(MARGINHEIGHT);
			if (marginwidth != null && marginwidth.length() != 0) {
				if (insets == null) {
					insets = new HtmlInsets();
				}
				marginwidth = marginwidth.trim();
				if (marginwidth.endsWith("%")) {
					int value;
					try {
						value = Integer.parseInt(marginwidth.substring(0, marginwidth.length() - 1));
					} catch (NumberFormatException nfe) {
						value = 0;
					}
					insets.left = value;
					insets.right = value;
					insets.leftType = HtmlInsets.TYPE_PERCENT;
					insets.rightType = HtmlInsets.TYPE_PERCENT;
				} else {
					int value;
					try {
						value = Integer.parseInt(marginwidth);
					} catch (NumberFormatException nfe) {
						value = 0;
					}
					insets.left = value;
					insets.right = value;
					insets.leftType = HtmlInsets.TYPE_PIXELS;
					insets.rightType = HtmlInsets.TYPE_PIXELS;
				}
			}
			if (marginheight != null && marginheight.length() != 0) {
				if (insets == null) {
					insets = new HtmlInsets();
				}
				marginheight = marginheight.trim();
				if (marginheight.endsWith("%")) {
					int value;
					try {
						value = Integer.parseInt(marginheight.substring(0, marginheight.length() - 1));
					} catch (NumberFormatException nfe) {
						value = 0;
					}
					insets.top = value;
					insets.bottom = value;
					insets.topType = HtmlInsets.TYPE_PERCENT;
					insets.bottomType = HtmlInsets.TYPE_PERCENT;
				} else {
					int value;
					try {
						value = Integer.parseInt(marginheight);
					} catch (NumberFormatException nfe) {
						value = 0;
					}
					insets.top = value;
					insets.bottom = value;
					insets.topType = HtmlInsets.TYPE_PIXELS;
					insets.bottomType = HtmlInsets.TYPE_PIXELS;
				}
			}
			Insets awtMarginInsets = insets == null ? null : insets.getSimpleAWTInsets(availWidth, availHeight);
			int overflowX = renderState.getOverflowX();
			int overflowY = renderState.getOverflowY();
			if (awtMarginInsets != null) {
				this.browserFrame.setDefaultMarginInsets(awtMarginInsets);
			}
			if (overflowX != RenderState.OVERFLOW_NONE) {
				this.browserFrame.setDefaultOverflowX(overflowX);
			}
			if (overflowY != RenderState.OVERFLOW_NONE) {
				this.browserFrame.setDefaultOverflowY(overflowY);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {

		String w = element.getAttribute(WIDTH);
		String h = element.getAttribute(HEIGHT);
		int width = HtmlValues.getPixelSize(w, null, 100, this.availWidth);
		int height = HtmlValues.getPixelSize(h, null, 100, this.availHeight);
		return new Dimension(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#invalidate()
	 */
	@Override
	public void invalidate() {
		this.component.invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.UIControl#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// We actually have to paint it.
		this.component.paint(g);
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
		// Selection does not cross in here?
		return false;
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
		this.ruiControl = ruicontrol;
	}
}
