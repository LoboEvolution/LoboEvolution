/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.renderer;

import org.loboevolution.html.ListValues;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.renderer.info.RBlockInfo;
import org.loboevolution.html.renderer.info.RLayoutInfo;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.ListStyle;

import java.awt.*;

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
	 * @param info a {@link org.loboevolution.html.renderer.info.RBlockInfo} object.
	 */
	public RListItem(RBlockInfo info) {
		super(info);
	}

	/** {@inheritDoc} */
	@Override
	public void doLayout(RLayoutInfo info) {
		super.doLayout(info);
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
	public void paint(final Graphics g) {
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
						bulletType = parentListStyle == null ? ListValues.TYPE_DECIMAL : ListValues.get(parentListStyle.getType());
					} else {
						bulletType = ListValues.TYPE_DECIMAL;
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
						if (bulletNumber<10)	
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
