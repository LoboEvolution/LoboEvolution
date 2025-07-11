/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
/*
 * Created on Apr 17, 2005
 */
package org.loboevolution.html.renderer;

import org.loboevolution.html.node.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;

final class RWord extends BaseBoundableRenderable {
	public final int ascentPlusLeading;
	public final int descent;
	public final FontMetrics fontMetrics;
	final String shownWord;

	/**
	 * <p>Constructor for RWord.</p>
	 *
	 * @param me a {@link org.loboevolution.html.node.ModelNode} object.
	 * @param word a {@link java.lang.String} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param fontMetrics a {@link java.awt.FontMetrics} object.
	 * @param descent a {@link java.lang.Integer} object.
	 * @param ascentPlusLeading a {@link java.lang.Integer} object.
	 * @param height a {@link java.lang.Integer} object.
	 * @param textTransform a {@link java.lang.Integer} object.
	 */
	public RWord(final ModelNode me, final String word, final RenderableContainer container, final FontMetrics fontMetrics, final int descent,
				 final int ascentPlusLeading, final int height, final int textTransform) {
		super(container, me);
		final String renderedWord = textTransform == RenderState.TEXTTRANSFORM_NONE ? word
				: transformText(word, textTransform);
		this.shownWord = renderedWord;
		this.fontMetrics = fontMetrics;
		this.descent = descent;
		this.ascentPlusLeading = ascentPlusLeading;
		this.setHeight(height);
		this.setWidth(fontMetrics.stringWidth(renderedWord));
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(final StringBuilder buffer, final boolean inSelection, final RenderableSpot startPoint,
										final RenderableSpot endPoint) {
		int startX = -1;
		int endX = -1;
		if (this == startPoint.renderable) {
			startX = startPoint.x;
		}
		if (this == endPoint.renderable) {
			endX = endPoint.x;
		}
		if (!inSelection && startX == -1 && endX == -1) {
			return false;
		}
		if (startX != -1 && endX != -1) {
			if (endX < startX) {
				final int temp = startX;
				startX = endX;
				endX = temp;
			}
		} else if (startX != -1 && endX == -1 && inSelection) {
			endX = startX;
			startX = -1;
		} else if (startX == -1 && endX != -1 && !inSelection) {
			startX = endX;
			endX = -1;
		}
		int index1 = -1;
		int index2 = -1;
		final char[] wordChars = this.shownWord.toCharArray();
		if (startX != -1) {
			index1 = 0;
			final FontMetrics fm = this.fontMetrics;
			for (int len = 0; len < wordChars.length; len++) {
				final int w = fm.charsWidth(wordChars, 0, len);
				if (w > startX) {
					break;
				}
				index1 = len;
			}
		}
		if (endX != -1) {
			index2 = 0;
			final FontMetrics fm = this.fontMetrics;
			for (int len = 0; len < wordChars.length; len++) {
				final int w = fm.charsWidth(wordChars, 0, len);
				if (w > endX) {
					break;
				}
				index2 = len;
			}
		}
		if (index1 != -1 || index2 != -1) {
			final int startIndex = index1 == -1 ? 0 : index1;
			final int endIndex = index2 == -1 ? wordChars.length : index2;
			buffer.append(wordChars, startIndex, endIndex - startIndex);
		} else {
			if (inSelection) {
				buffer.append(wordChars);
				return true;
			}
		}
		if (index1 != -1 && index2 != -1) {
			return false;
		} else {
			return !inSelection;
		}
	}

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		return new RenderableSpot(this, x, y);
	}

	/** {@inheritDoc} */
	@Override
	protected void invalidateLayoutLocal() {
	}

	/** {@inheritDoc} */
	@Override
	public boolean isContainedByNode() {
		return true;
	}
	
	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		final RenderState rs = (RenderState)this.modelNode.getRenderState();
		if (rs != null && rs.getVisibility() == RenderState.VISIBILITY_VISIBLE) {

			final String word = this.shownWord;
			final int width = this.getWidth();
			final int ascentPlusLeading = this.ascentPlusLeading;
			final int height = this.getHeight();
			final int textDecoration = rs.getTextDecorationMask();
			final Color bkg = rs.getTextBackgroundColor();
			if (bkg != null) {
				final Color oldColor = g.getColor();
				try {
					g.setColor(bkg);
					g.fillRect(0, 0, width, height);
				} finally {
					g.setColor(oldColor);
				}
			}
			g.drawString(word, 0, ascentPlusLeading);
			final int td = textDecoration;
			if (td != 0) {
				if ((td & RenderState.MASK_TEXTDECORATION_UNDERLINE) != 0) {
					final int lineOffset = ascentPlusLeading + 2;
					g.drawLine(0, lineOffset, width, lineOffset);
				}
				if ((td & RenderState.MASK_TEXTDECORATION_LINE_THROUGH) != 0) {
					final FontMetrics fm = this.fontMetrics;
					final int lineOffset = fm.getLeading() + (fm.getAscent() + fm.getDescent()) / 2;
					g.drawLine(0, lineOffset, width, lineOffset);
				}
				if ((td & RenderState.MASK_TEXTDECORATION_OVERLINE) != 0) {
					final FontMetrics fm = this.fontMetrics;
					final int lineOffset = fm.getLeading();
					g.drawLine(0, lineOffset, width, lineOffset);
				}
				if ((td & RenderState.MASK_TEXTDECORATION_BLINK) != 0) {
					// TODO
				}
			}
			final Color over = rs.getOverlayColor();
			if (over != null) {
				final Color oldColor = g.getColor();
				try {
					g.setColor(over);
					g.fillRect(0, 0, width, height);
				} finally {
					g.setColor(oldColor);
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(final Graphics g, final boolean inSelection, final RenderableSpot startPoint, final RenderableSpot endPoint) {
		int startX = -1;
		int endX = -1;
		if (this == startPoint.renderable) {
			startX = startPoint.x;
		}
		if (this == endPoint.renderable) {
			endX = endPoint.x;
		}
		if (!inSelection && startX == -1 && endX == -1) {
			return false;
		}
		if (startX != -1 && endX != -1) {
			if (endX < startX) {
				final int temp = startX;
				startX = endX;
				endX = temp;
			}
		} else if (startX != -1 && endX == -1 && inSelection) {
			endX = startX;
			startX = -1;
		} else if (startX == -1 && endX != -1 && !inSelection) {
			startX = endX;
			endX = -1;
		}
		int width1 = -1;
		int width2 = -1;
		final char[] wordChars = this.shownWord.toCharArray();
		if (startX != -1) {
			width1 = 0;
			final FontMetrics fm = this.fontMetrics;
			for (int len = 0; len < wordChars.length; len++) {
				final int w = fm.charsWidth(wordChars, 0, len);
				if (w > startX) {
					break;
				}
				width1 = w;
			}
		}
		if (endX != -1) {
			width2 = 0;
			final FontMetrics fm = this.fontMetrics;
			for (int len = 0; len < wordChars.length; len++) {
				final int w = fm.charsWidth(wordChars, 0, len);
				if (w > endX) {
					break;
				}
				width2 = w;
			}
		}
		if (width1 != -1 || width2 != -1) {
			final int startPaint = width1 == -1 ? 0 : width1;
			final int endPaint = width2 == -1 ? this.getWidth() : width2;
			g.setColor(SELECTION_COLOR);
			g.setXORMode(SELECTION_XOR);
			g.fillRect(startPaint, 0, endPaint - startPaint, this.getHeight());
			g.setPaintMode();
			return width2 == -1;
		} else {
			if (inSelection) {
				g.setColor(SELECTION_COLOR);
				g.setXORMode(SELECTION_XOR);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setPaintMode();
			}
			return inSelection;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "RWord[word=" + this.shownWord + "]";
	}

	private String transformText(final String word, final int textTransform) {
		final String string = switch (textTransform) {
            case RenderState.TEXTTRANSFORM_CAPITALIZE ->
                    Character.toTitleCase(word.charAt(0)) + word.substring(1).toLowerCase();
            case RenderState.TEXTTRANSFORM_LOWERCASE -> word.toLowerCase();
            case RenderState.TEXTTRANSFORM_UPPERCASE -> word.toUpperCase();
            default -> word;
        };
        return string;
	}
}
