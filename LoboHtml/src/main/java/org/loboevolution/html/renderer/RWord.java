/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 17, 2005
 */
package org.loboevolution.html.renderer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

final class RWord extends BaseBoundableRenderable {
	public final int ascentPlusLeading;
	public final int descent;
	public final FontMetrics fontMetrics;
	final String shownWord;

	/**
	 * <p>Constructor for RWord.</p>
	 *
	 * @param me a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param word a {@link java.lang.String} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param fontMetrics a {@link java.awt.FontMetrics} object.
	 * @param descent a int.
	 * @param ascentPlusLeading a int.
	 * @param height a int.
	 * @param textTransform a int.
	 */
	public RWord(ModelNode me, String word, RenderableContainer container, FontMetrics fontMetrics, int descent,
			int ascentPlusLeading, int height, int textTransform) {
		super(container, me);
		final String renderedWord = textTransform == RenderState.TEXTTRANSFORM_NONE ? word
				: transformText(word, textTransform);
		this.shownWord = renderedWord;
		this.fontMetrics = fontMetrics;
		this.descent = descent;
		this.ascentPlusLeading = ascentPlusLeading;
		this.height = height;
		// TODO: In anti-aliasing, stringWidth is said not to be reliable.
		// Dimensions set when constructed.
		this.width = fontMetrics.stringWidth(renderedWord);
	}

	/** {@inheritDoc} */
	@Override
	public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
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
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		final RenderState rs = this.modelNode.getRenderState();
        if (rs != null && rs.getVisibility() != RenderState.VISIBILITY_VISIBLE) {
            return;
        }
		
        final String word = this.shownWord;
		final int width = this.width;
		final int ascentPlusLeading = this.ascentPlusLeading;
		final int height = this.height;
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

	/** {@inheritDoc} */
	@Override
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
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
			final int endPaint = width2 == -1 ? this.width : width2;
			g.setColor(SELECTION_COLOR);
			g.setXORMode(SELECTION_XOR);
			g.fillRect(startPaint, 0, endPaint - startPaint, this.height);
			g.setPaintMode();
			return width2 == -1;
		} else {
			if (inSelection) {
				g.setColor(SELECTION_COLOR);
				g.setXORMode(SELECTION_XOR);
				g.fillRect(0, 0, this.width, this.height);
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

	private String transformText(String word, int textTransform) {
		String string;
		switch (textTransform) {
		case RenderState.TEXTTRANSFORM_CAPITALIZE:
			string = Character.toTitleCase(word.charAt(0)) + word.substring(1).toLowerCase();
			break;
		case RenderState.TEXTTRANSFORM_LOWERCASE:
			string = word.toLowerCase();
			break;
		case RenderState.TEXTTRANSFORM_UPPERCASE:
			string = word.toUpperCase();
			break;
		default:
			string = word;
		}
		return string;
	}
}
