/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.ListStyle;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.w3c.html.HTMLElement;

/**
 * The Class RListItem.
 */
public class RListItem extends BaseRListElement {

	/** The Constant BULLET_WIDTH. */
	private static final int BULLET_WIDTH = 5;

	/** The Constant BULLET_HEIGHT. */
	private static final int BULLET_HEIGHT = 5;

	/** The Constant BULLET_RMARGIN. */
	private static final int BULLET_RMARGIN = 5;

	/**
	 * Instantiates a new r list item.
	 *
	 * @param modelNode
	 *            the model node
	 * @param listNesting
	 *            the list nesting
	 * @param pcontext
	 *            the pcontext
	 * @param rcontext
	 *            the rcontext
	 * @param frameContext
	 *            the frame context
	 * @param parentContainer
	 *            the parent container
	 * @param parent
	 *            the parent
	 */
	public RListItem(DOMNodeImpl modelNode, int listNesting, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer parentContainer, RCollection parent) {
		super(modelNode, listNesting, pcontext, rcontext, frameContext, parentContainer);
		// this.defaultMarginInsets = new java.awt.Insets(0, BULLET_SPACE_WIDTH,
		// 0, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RBlock#getViewportListNesting(int)
	 */
	@Override
	public int getViewportListNesting(int blockNesting) {
		return blockNesting + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RBlock#invalidateLayoutLocal()
	 */
	@Override
	public void invalidateLayoutLocal() {
		super.invalidateLayoutLocal();
		this.value = null;
	}

	/** The Constant UNSET. */
	private static final Integer UNSET = Integer.valueOf(Integer.MIN_VALUE);

	/** The value. */
	private Integer value = null;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	private Integer getValue() {
		Integer value = this.value;
		if (value == null) {
			HTMLElement node = (HTMLElement) this.modelNode;
			String valueText = node == null ? null : node.getAttribute(HtmlAttributeProperties.VALUE);
			if (valueText == null) {
				value = UNSET;
			} else {
				try {
					value = Integer.valueOf(valueText);
				} catch (NumberFormatException nfe) {
					value = UNSET;
				}
			}
			this.value = value;
		}
		return value;
	}

	/** The count. */
	private int count;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RBlock#doLayout(int, int, boolean,
	 * boolean, org.lobobrowser.html.renderer.FloatingBoundsSource, int, int,
	 * boolean)
	 */
	@Override
	public void doLayout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		super.doLayout(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, defaultOverflowX,
				defaultOverflowY, sizeOnly);
		// Note: Count must be calculated even if layout is valid.
		RenderState renderState = this.modelNode.getRenderState();
		Integer value = this.getValue();
		if (value == UNSET) {
			this.count = renderState.incrementCount(DEFAULT_COUNTER_NAME, this.listNesting);
		} else {
			int newCount = value.intValue();
			this.count = newCount;
			renderState.resetCount(DEFAULT_COUNTER_NAME, this.listNesting, newCount + 1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.RBlock#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		RenderState rs = this.modelNode.getRenderState();
		Insets marginInsets = this.marginInsets;
		RBlockViewport layout = this.bodyLayout;
		if (layout != null) {
			ListStyle listStyle = this.listStyle;
			int bulletType = listStyle == null ? ListStyle.TYPE_UNSET : listStyle.type;
			if (bulletType != ListStyle.TYPE_NONE) {
				if (bulletType == ListStyle.TYPE_UNSET) {
					RCollection parent = this.getOriginalOrCurrentParent();
					if (!(parent instanceof RList)) {
						parent = parent.getOriginalOrCurrentParent();
					}
					if (parent instanceof RList) {
						ListStyle parentListStyle = ((RList) parent).listStyle;
						bulletType = parentListStyle == null ? ListStyle.TYPE_DISC : parentListStyle.type;
					} else {
						bulletType = ListStyle.TYPE_DISC;
					}
				}
				// Paint bullets
				Color prevColor = g.getColor();
				g.setColor(rs.getColor());
				try {
					Insets insets = this.getInsets(this.hasHScrollBar, this.hasVScrollBar);
					Insets paddingInsets = this.paddingInsets;
					int baselineOffset = layout.getFirstBaselineOffset();
					int bulletRight = (marginInsets == null ? 0 : marginInsets.left) - BULLET_RMARGIN;
					int bulletBottom = insets.top + baselineOffset + (paddingInsets == null ? 0 : paddingInsets.top);
					int bulletTop = bulletBottom - BULLET_HEIGHT;
					int bulletLeft = bulletRight - BULLET_WIDTH;
					int bulletNumber = this.count;
					String numberText = null;
					switch (bulletType) {
					case ListStyle.TYPE_DECIMAL:
						numberText = bulletNumber + ".";
						break;
					case ListStyle.TYPE_DECIMAL_LEADING_ZERO:
						if (bulletNumber < 10) {
							numberText = "0" + bulletNumber + ".";
						} else {
							numberText = bulletNumber + ".";
						}
						break;
					case ListStyle.TYPE_LOWER_ALPHA:
						numberText = ((char) ('a' + (bulletNumber - 1))) + ".";
						break;
					case ListStyle.TYPE_UPPER_ALPHA:
						numberText = ((char) ('A' + (bulletNumber - 1))) + ".";
						break;
					case ListStyle.TYPE_LOWER_ROMAN:
						numberText = ListStyle.getRomanNumerals(bulletNumber).toLowerCase() + ".";
						break;
					case ListStyle.TYPE_UPPER_ROMAN:
						numberText = ListStyle.getRomanNumerals(bulletNumber).toUpperCase() + ".";
						break;
					case ListStyle.TYPE_DISC:
						g.fillOval(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
						break;
					case ListStyle.TYPE_CIRCLE:
						g.drawOval(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
						break;
					case ListStyle.TYPE_SQUARE:
						g.fillRect(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
						break;
					}

					if (numberText != null) {
						FontMetrics fm = g.getFontMetrics();
						int numberLeft = bulletRight - fm.stringWidth(numberText);
						int numberY = bulletBottom;
						g.drawString(numberText, numberLeft, numberY);
					}
				} finally {
					g.setColor(prevColor);
				}
			}
		}
	}
}
