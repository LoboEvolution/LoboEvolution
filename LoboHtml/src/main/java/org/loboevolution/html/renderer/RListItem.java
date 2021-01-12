/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.renderer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;

import org.loboevolution.html.ListValues;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.NodeImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.ListStyle;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

class RListItem extends BaseRListElement {
	private static final int BULLET_HEIGHT = 5;
	private static final int BULLET_RMARGIN = 5;
	private static final int BULLET_WIDTH = 5;
	private static final Integer UNSET = Integer.MIN_VALUE;

	private int count;

	private Integer value = null;

	/**
	 * <p>Constructor for RListItem.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 * @param listNesting a int.
	 * @param pcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param parentContainer a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	public RListItem(NodeImpl modelNode, int listNesting, UserAgentContext pcontext, HtmlRendererContext rcontext,
			FrameContext frameContext, RenderableContainer parentContainer) {
		super(modelNode, listNesting, pcontext, rcontext, frameContext, parentContainer);
	}

	/** {@inheritDoc} */
	@Override
	public void doLayout(int availWidth, int availHeight, boolean expandWidth, boolean expandHeight,
			FloatingBoundsSource floatBoundsSource, int defaultOverflowX, int defaultOverflowY, boolean sizeOnly) {
		super.doLayout(availWidth, availHeight, expandWidth, expandHeight, floatBoundsSource, defaultOverflowX,
				defaultOverflowY, sizeOnly);
		final RenderState renderState = this.modelNode.getRenderState();
		final Integer value = getValue();
		if (value == UNSET) {
			this.count = renderState.incrementCount(DEFAULT_COUNTER_NAME, this.listNesting);
		} else {
			final int newCount = value;
			this.count = newCount;
			renderState.resetCount(DEFAULT_COUNTER_NAME, this.listNesting, newCount + 1);
		}
	}

	private Integer getValue() {
		Integer value = this.value;
		if (value == null) {
			final HTMLElement node = (HTMLElement) this.modelNode;
			final String valueText = node == null ? null : node.getAttribute("value");
			if (valueText == null) {
				value = UNSET;
			} else {
				try {
					value = Integer.valueOf(valueText);
				} catch (final NumberFormatException nfe) {
					value = UNSET;
				}
			}
			this.value = value;
		}
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public int getViewportListNesting(int blockNesting) {
		return blockNesting + 1;
	}

	/** {@inheritDoc} */
	@Override
	public void invalidateLayoutLocal() {
		super.invalidateLayoutLocal();
		this.value = null;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		final RenderState rs = this.modelNode.getRenderState();
		final Insets marginInsets = this.marginInsets;
		final RBlockViewport layout = this.bodyLayout;
		if (layout != null) {
			final ListStyle listStyle = this.listStyle;
			
			ListValues bulletType = listStyle == null ? ListValues.TYPE_UNSET : ListValues.get(listStyle.getType());
			if (bulletType != ListValues.TYPE_NONE) {
				if (bulletType == ListValues.TYPE_UNSET) {
					RCollection parent = getOriginalOrCurrentParent();
					if (!(parent instanceof RList)) {
						parent = parent.getOriginalOrCurrentParent();
					}
					if (parent instanceof RList) {
						final ListStyle parentListStyle = ((RList) parent).listStyle;
						bulletType = parentListStyle == null ? ListValues.TYPE_DISC : ListValues.get(parentListStyle.getType());
					} else {
						bulletType = ListValues.TYPE_DISC;
					}
				}

				final Color prevColor = g.getColor();
				g.setColor(rs.getColor());
				try {
					final Insets insets = getInsets(this.hasHScrollBar, this.hasVScrollBar);
					final Insets paddingInsets = this.paddingInsets;
					final int baselineOffset = layout.getFirstBaselineOffset();
					final int bulletRight = (marginInsets == null ? 0 : marginInsets.left) - BULLET_RMARGIN;
					final int bulletBottom = insets.top + baselineOffset + (paddingInsets == null ? 0 : paddingInsets.top);
					final int bulletTop = bulletBottom - BULLET_HEIGHT;
					final int bulletLeft = bulletRight - BULLET_WIDTH;
					final int bulletNumber = this.count;
					String numberText = null;
										
					switch (bulletType) {
					case TYPE_DECIMAL:
						numberText = bulletNumber + ".";
						break;
					case TYPE_DECIMAL_LEADING_ZERO:
						if(bulletNumber<10)	
							numberText = "0" + bulletNumber + ".";
						else
							numberText = bulletNumber + ".";
						break;
					case TYPE_LOWER_ALPHA:
						numberText = ((char) ('a' + (bulletNumber - 1))) + ".";
						break;
					case TYPE_UPPER_ALPHA:
						numberText = ((char) ('A' + (bulletNumber - 1))) + ".";
						break;
					case TYPE_DISC:
						g.fillOval(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
						break;
					case TYPE_CIRCLE:
						g.drawOval(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
						break;
					case TYPE_SQUARE:
						g.fillRect(bulletLeft, bulletTop, BULLET_WIDTH, BULLET_HEIGHT);
						break;
					case TYPE_LOWER_ROMAN:
						numberText = ListStyle.getRomanNumerals(bulletNumber).toLowerCase() + ".";
                        break;
					case TYPE_UPPER_ROMAN:
						numberText = ListStyle.getRomanNumerals(bulletNumber).toUpperCase() + ".";
						break;
					case TYPE_URL:
						g.drawImage(listStyle.getImage(), bulletLeft, bulletTop, null);
						break;
					default:
						numberText = null;
						break;
					}
					if (numberText != null) {
						final FontMetrics fm = g.getFontMetrics();
						final int numberLeft = bulletRight - fm.stringWidth(numberText);
						final int numberY = bulletBottom;
						g.drawString(numberText, numberLeft, numberY);
					}
				} finally {
					g.setColor(prevColor);
				}
			}
		}
	}
}
