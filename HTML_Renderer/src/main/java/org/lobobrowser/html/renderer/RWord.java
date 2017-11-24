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
/*
 * Created on Apr 17, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderstate.RenderState;

/**
 * The Class RWord.
 */
public final class RWord extends BaseBoundableRenderable {

	/** The shown word. */
	private String shownWord;

	/** The font metrics. */
	private FontMetrics fontMetrics;

	/** The descent. */
	private int descent;

	/** The ascent plus leading. */
	private int ascentPlusLeading;

	/**
	 * Instantiates a new r word.
	 *
	 * @param me
	 *            the me
	 * @param word
	 *            the word
	 * @param container
	 *            the container
	 * @param fontMetrics
	 *            the font metrics
	 * @param descent
	 *            the descent
	 * @param ascentPlusLeading
	 *            the ascent plus leading
	 * @param height
	 *            the height
	 * @param textTransform
	 *            the text transform
	 */
	public RWord(ModelNode me, String word, RenderableContainer container, FontMetrics fontMetrics, int descent,
			int ascentPlusLeading, int height, int textTransform) {
		super(container, me);
		String renderedWord = textTransform == RenderState.TEXTTRANSFORM_NONE ? word
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

	/**
	 * Transform text.
	 *
	 * @param word
	 *            the word
	 * @param textTransform
	 *            the text transform
	 * @return the string
	 */
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
			break;
		}
		return string;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.BaseBoundableRenderable#
	 * invalidateLayoutLocal()
	 */
	@Override
	protected void invalidateLayoutLocal() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.domimpl.markup.Renderable#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		RenderState rs = this.modelNode.getRenderState();
		String word = this.shownWord;
		int width = this.width;
		int ascentPlusLeading = this.ascentPlusLeading;
		int height = this.height;
		int textDecoration = rs.getTextDecorationMask();
		Color bkg = rs.getTextBackgroundColor();
		if (bkg != null) {
			Color oldColor = g.getColor();
			try {
				g.setColor(bkg);
				g.fillRect(0, 0, width, height);
			} finally {
				g.setColor(oldColor);
			}
		}
		g.drawString(word, 0, ascentPlusLeading);
		int td = textDecoration;
		if (td != 0) {
			if ((td & RenderState.MASK_TEXTDECORATION_UNDERLINE) != 0) {
				int lineOffset = ascentPlusLeading + 2;
				g.drawLine(0, lineOffset, width, lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_LINE_THROUGH) != 0) {
				FontMetrics fm = this.fontMetrics;
				int lineOffset = fm.getLeading() + (fm.getAscent() + fm.getDescent()) / 2;
				g.drawLine(0, lineOffset, width, lineOffset);
			}
			if ((td & RenderState.MASK_TEXTDECORATION_OVERLINE) != 0) {
				FontMetrics fm = this.fontMetrics;
				int lineOffset = fm.getLeading();
				g.drawLine(0, lineOffset, width, lineOffset);
			}
		}
		Color over = rs.getOverlayColor();
		if (over != null) {
			Color oldColor = g.getColor();
			try {
				g.setColor(over);
				g.fillRect(0, 0, width, height);
			} finally {
				g.setColor(oldColor);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#paintSelection(java.awt
	 * .Graphics, boolean, org.lobobrowser.html.renderer.RenderableSpot,
	 * org.lobobrowser.html.renderer.RenderableSpot)
	 */
	@Override
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		int startX = -1;
		int endX = -1;
		if (this == startPoint.getRenderable()) {
			startX = startPoint.getX();
		}
		if (this == endPoint.getRenderable()) {
			endX = endPoint.getX();
		}
		if (!inSelection && startX == -1 && endX == -1) {
			return false;
		}
		if (startX != -1 && endX != -1) {
			if (endX < startX) {
				int temp = startX;
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
		char[] wordChars = this.shownWord.toCharArray();
		if (startX != -1) {
			width1 = 0;
			FontMetrics fm = this.fontMetrics;
			for (int len = 0; len < wordChars.length; len++) {
				int w = fm.charsWidth(wordChars, 0, len);
				if (w > startX) {
					break;
				}
				width1 = w;
			}
		}
		if (endX != -1) {
			width2 = 0;
			FontMetrics fm = this.fontMetrics;
			for (int len = 0; len < wordChars.length; len++) {
				int w = fm.charsWidth(wordChars, 0, len);
				if (w > endX) {
					break;
				}
				width2 = w;
			}
		}
		if (width1 != -1 || width2 != -1) {
			int startPaint = width1 == -1 ? 0 : width1;
			int endPaint = width2 == -1 ? this.width : width2;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#extractSelectionText(
	 * java .lang.StringBuffer, boolean,
	 * org.lobobrowser.html.renderer.RenderableSpot,
	 * org.lobobrowser.html.renderer.RenderableSpot)
	 */
	@Override
	public boolean extractSelectionText(StringBuffer buffer, boolean inSelection, RenderableSpot startPoint,
			RenderableSpot endPoint) {
		int startX = -1;
		int endX = -1;
		if (this == startPoint.getRenderable()) {
			startX = startPoint.getX();
		}
		if (this == endPoint.getRenderable()) {
			endX = endPoint.getX();
		}
		if (!inSelection && startX == -1 && endX == -1) {
			return false;
		}
		if (startX != -1 && endX != -1) {
			if (endX < startX) {
				int temp = startX;
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
		char[] wordChars = this.shownWord.toCharArray();
		if (startX != -1) {
			index1 = 0;
			FontMetrics fm = this.fontMetrics;
			for (int len = 0; len < wordChars.length; len++) {
				int w = fm.charsWidth(wordChars, 0, len);
				if (w > startX) {
					break;
				}
				index1 = len;
			}
		}
		if (endX != -1) {
			index2 = 0;
			FontMetrics fm = this.fontMetrics;
			for (int len = 0; len < wordChars.length; len++) {
				int w = fm.charsWidth(wordChars, 0, len);
				if (w > endX) {
					break;
				}
				index2 = len;
			}
		}
		if (index1 != -1 || index2 != -1) {
			int startIndex = index1 == -1 ? 0 : index1;
			int endIndex = index2 == -1 ? wordChars.length : index2;
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

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#getLowestRenderableSpot
	 * (int, int)
	 */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		return new RenderableSpot(this, x, y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderer.BoundableRenderable#isContainedByNode()
	 */
	@Override
	public boolean isContainedByNode() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RWord[word=" + this.shownWord + "]";
	}

	/**
	 * Gets the shown word.
	 *
	 * @return the shown word
	 */
	public String getShownWord() {
		return shownWord;
	}

	/**
	 * Sets the shown word.
	 *
	 * @param shownWord
	 *            the new shown word
	 */
	public void setShownWord(String shownWord) {
		this.shownWord = shownWord;
	}

	/**
	 * Gets the font metrics.
	 *
	 * @return the font metrics
	 */
	public FontMetrics getFontMetrics() {
		return fontMetrics;
	}

	/**
	 * Sets the font metrics.
	 *
	 * @param fontMetrics
	 *            the new font metrics
	 */
	public void setFontMetrics(FontMetrics fontMetrics) {
		this.fontMetrics = fontMetrics;
	}

	/**
	 * Gets the descent.
	 *
	 * @return the descent
	 */
	public int getDescent() {
		return descent;
	}

	/**
	 * Sets the descent.
	 *
	 * @param descent
	 *            the new descent
	 */
	public void setDescent(int descent) {
		this.descent = descent;
	}

	/**
	 * Gets the ascent plus leading.
	 *
	 * @return the ascent plus leading
	 */
	public int getAscentPlusLeading() {
		return ascentPlusLeading;
	}

	/**
	 * Sets the ascent plus leading.
	 *
	 * @param ascentPlusLeading
	 *            the new ascent plus leading
	 */
	public void setAscentPlusLeading(int ascentPlusLeading) {
		this.ascentPlusLeading = ascentPlusLeading;
	}

}
