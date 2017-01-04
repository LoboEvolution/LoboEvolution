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
 * Created on Apr 16, 2005
 */
package org.lobobrowser.html.renderstate;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Optional;

import org.lobobrowser.html.info.BackgroundInfo;
import org.lobobrowser.html.info.BorderInfo;
import org.lobobrowser.html.info.WordInfo;
import org.lobobrowser.html.style.HtmlInsets;

/**
 * The Interface RenderState.
 */
public interface RenderState {
	
	/** The mask textdecoration none. */
	int MASK_TEXTDECORATION_NONE = 0;
	
	/** The mask textdecoration underline. */
	int MASK_TEXTDECORATION_UNDERLINE = 1;

	/** The mask textdecoration overline. */
	int MASK_TEXTDECORATION_OVERLINE = 2;

	/** The mask textdecoration line through. */
	int MASK_TEXTDECORATION_LINE_THROUGH = 4;

	/** The mask textdecoration blink. */
	int MASK_TEXTDECORATION_BLINK = 8;

	/** The texttransform none. */
	int TEXTTRANSFORM_NONE = 0;

	/** The texttransform capitalize. */
	int TEXTTRANSFORM_CAPITALIZE = 1;

	/** The texttransform uppercase. */
	int TEXTTRANSFORM_UPPERCASE = 2;

	/** The texttransform lowercase. */
	int TEXTTRANSFORM_LOWERCASE = 4;
	
	/** The display none. */
	int DISPLAY_NONE = 0;

	/** The display inline. */
	int DISPLAY_INLINE = 1;

	/** The display block. */
	int DISPLAY_BLOCK = 2;

	/** The display list item. */
	int DISPLAY_LIST_ITEM = 3;

	/** The display table row. */
	int DISPLAY_TABLE_ROW = 4;

	/** The display table cell. */
	int DISPLAY_TABLE_CELL = 5;

	/** The display table. */
	int DISPLAY_TABLE = 6;

	/** The display table caption. */
	int DISPLAY_TABLE_CAPTION = 7;

	/** The display inline block. */
	int DISPLAY_INLINE_BLOCK = 8;

	/** The ws normal. */
	int WS_NORMAL = 0;

	/** The ws pre. */
	int WS_PRE = 1;

	/** The ws nowrap. */
	int WS_NOWRAP = 2;

	/** The visibility visible. */
	int VISIBILITY_VISIBLE = 0;

	/** The visibility hidden. */
	int VISIBILITY_HIDDEN = 1;

	/** The visibility collapse. */
	int VISIBILITY_COLLAPSE = 2;

	/** The position static. */
	int POSITION_STATIC = 0;

	/** The position absolute. */
	int POSITION_ABSOLUTE = 1;

	/** The position relative. */
	int POSITION_RELATIVE = 2;

	/** The position fixed. */
	int POSITION_FIXED = 3;

	/** The float none. */
	int FLOAT_NONE = 0;

	/** The float left. */
	int FLOAT_LEFT = 1;

	/** The float right. */
	int FLOAT_RIGHT = 2;

	/** The overflow none. */
	int OVERFLOW_NONE = 0;

	/** The overflow scroll. */
	int OVERFLOW_SCROLL = 1;

	/** The overflow auto. */
	int OVERFLOW_AUTO = 2;

	/** The overflow hidden. */
	int OVERFLOW_HIDDEN = 3;

	/** The overflow visible. */
	int OVERFLOW_VISIBLE = 4;

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	int getPosition();

	/**
	 * Gets the float.
	 *
	 * @return the float
	 */
	int getFloat();

	/**
	 * Gets the visibility.
	 *
	 * @return the visibility
	 */
	int getVisibility();

	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	Font getFont();

	/**
	 * Gets the font base.
	 *
	 * @return the font base
	 */
	int getFontBase();

	/**
	 * Gets the word info.
	 *
	 * @param word
	 *            the word
	 * @return the word info
	 */
	WordInfo getWordInfo(String word);

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	Color getColor();

	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	Color getBackgroundColor();

	/**
	 * Gets the text background color.
	 *
	 * @return the text background color
	 */
	Color getTextBackgroundColor();

	/**
	 * Gets the background info.
	 *
	 * @return the background info
	 */
	BackgroundInfo getBackgroundInfo();

	/**
	 * Gets the overlay color.
	 *
	 * @return the overlay color
	 */
	Color getOverlayColor();

	/**
	 * Gets the text transform.
	 *
	 * @return the text transform
	 */
	int getTextTransform();

	/**
	 * Gets the text decoration mask.
	 *
	 * @return the text decoration mask
	 */
	int getTextDecorationMask();

	/**
	 * Gets the font metrics.
	 *
	 * @return the font metrics
	 */
	FontMetrics getFontMetrics();

	/**
	 * Gets the blank width.
	 *
	 * @return the blank width
	 */
	int getBlankWidth();

	/**
	 * Checks if is highlight.
	 *
	 * @return true, if is highlight
	 */
	boolean isHighlight();

	/**
	 * Sets the highlight.
	 *
	 * @param highlight
	 *            the new highlight
	 */
	void setHighlight(boolean highlight);

	/**
	 * Gets the previous render state.
	 *
	 * @return the previous render state
	 */
	RenderState getPreviousRenderState();

	/**
	 * Gets the align x percent.
	 *
	 * @return the align x percent
	 */
	int getAlignXPercent();

	/**
	 * Gets the align y percent.
	 *
	 * @return the align y percent
	 */
	int getAlignYPercent();

	/**
	 * Gets the count.
	 *
	 * @param counter
	 *            the counter
	 * @param nesting
	 *            the nesting
	 * @return the count
	 */
	int getCount(String counter, int nesting);

	/**
	 * Gets the display.
	 *
	 * @return the display
	 */
	int getDisplay();

	/**
	 * Reset count.
	 *
	 * @param counter
	 *            the counter
	 * @param nesting
	 *            the nesting
	 * @param value
	 *            the value
	 */
	void resetCount(String counter, int nesting, int value);

	/**
	 * Increment count.
	 *
	 * @param counter
	 *            the counter
	 * @param nesting
	 *            the nesting
	 * @return the int
	 */
	int incrementCount(String counter, int nesting);

	/**
	 * Gets the text indent.
	 *
	 * @param availWidth
	 *            the avail width
	 * @return the text indent
	 */
	int getTextIndent(int availWidth);

	/**
	 * Gets the text indent text.
	 *
	 * @return the text indent text
	 */
	String getTextIndentText();

	/**
	 * Gets the white space.
	 *
	 * @return the white space
	 */
	int getWhiteSpace();

	/**
	 * Gets the margin insets.
	 *
	 * @return the margin insets
	 */
	HtmlInsets getMarginInsets();

	/**
	 * Gets the padding insets.
	 *
	 * @return the padding insets
	 */
	HtmlInsets getPaddingInsets();

	/**
	 * Gets the overflow x.
	 *
	 * @return the overflow x
	 */
	int getOverflowX();

	/**
	 * Gets the overflow y.
	 *
	 * @return the overflow y
	 */
	int getOverflowY();

	/**
	 * Invalidate.
	 */
	void invalidate();

	/**
	 * Gets the border info.
	 *
	 * @return the border info
	 */
	BorderInfo getBorderInfo();

	/**
	 * Gets the cursor.
	 *
	 * @return the cursor
	 */
	Optional<Cursor> getCursor();

	/**
	 * Sets the cursor.
	 *
	 * @param cursor
	 *            the new cursor
	 */
	void setCursor(Optional<Cursor> cursor);

}
