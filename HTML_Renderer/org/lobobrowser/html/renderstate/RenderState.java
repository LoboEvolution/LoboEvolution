/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
/*
 * Created on Apr 16, 2005
 */
package org.lobobrowser.html.renderstate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import org.lobobrowser.html.style.BackgroundInfo;
import org.lobobrowser.html.style.BorderInfo;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.WordInfo;


/**
 * The Interface RenderState.
 *
 * @author J. H. S.
 */
public interface RenderState {
	
	/** The Constant MASK_TEXTDECORATION_UNDERLINE. */
	public static final int MASK_TEXTDECORATION_UNDERLINE = 1;
	
	/** The Constant MASK_TEXTDECORATION_OVERLINE. */
	public static final int MASK_TEXTDECORATION_OVERLINE = 2;
	
	/** The Constant MASK_TEXTDECORATION_LINE_THROUGH. */
	public static final int MASK_TEXTDECORATION_LINE_THROUGH = 4;
	
	/** The Constant MASK_TEXTDECORATION_BLINK. */
	public static final int MASK_TEXTDECORATION_BLINK = 8;

	/** The Constant TEXTTRANSFORM_NONE. */
	public static final int TEXTTRANSFORM_NONE = 0;
	
	/** The Constant TEXTTRANSFORM_CAPITALIZE. */
	public static final int TEXTTRANSFORM_CAPITALIZE = 1;
	
	/** The Constant TEXTTRANSFORM_UPPERCASE. */
	public static final int TEXTTRANSFORM_UPPERCASE = 2;
	
	/** The Constant TEXTTRANSFORM_LOWERCASE. */
	public static final int TEXTTRANSFORM_LOWERCASE = 4;
	// TODO how to handle style cascading?
	// public static final int TEXTTRANSFORM_INHERIT = 8;

	/** The Constant DISPLAY_NONE. */
	public static final int DISPLAY_NONE = 0;
	
	/** The Constant DISPLAY_INLINE. */
	public static final int DISPLAY_INLINE = 1;
	
	/** The Constant DISPLAY_BLOCK. */
	public static final int DISPLAY_BLOCK = 2;
	
	/** The Constant DISPLAY_LIST_ITEM. */
	public static final int DISPLAY_LIST_ITEM = 3;
	
	/** The Constant DISPLAY_TABLE_ROW. */
	public static final int DISPLAY_TABLE_ROW = 4;
	
	/** The Constant DISPLAY_TABLE_CELL. */
	public static final int DISPLAY_TABLE_CELL = 5;
	
	/** The Constant DISPLAY_TABLE. */
	public static final int DISPLAY_TABLE = 6;
	
	/** The Constant DISPLAY_TABLE_CAPTION. */
	public static final int DISPLAY_TABLE_CAPTION = 7;
	
	/** The Constant DISPLAY_INLINE_BLOCK. */
	public static final int DISPLAY_INLINE_BLOCK = 8;

	/** The Constant WS_NORMAL. */
	public static final int WS_NORMAL = 0;
	
	/** The Constant WS_PRE. */
	public static final int WS_PRE = 1;
	
	/** The Constant WS_NOWRAP. */
	public static final int WS_NOWRAP = 2;

	/** The Constant VISIBILITY_VISIBLE. */
	public static final int VISIBILITY_VISIBLE = 0;
	
	/** The Constant VISIBILITY_HIDDEN. */
	public static final int VISIBILITY_HIDDEN = 1;
	
	/** The Constant VISIBILITY_COLLAPSE. */
	public static final int VISIBILITY_COLLAPSE = 2;

	/** The Constant POSITION_STATIC. */
	public static final int POSITION_STATIC = 0;
	
	/** The Constant POSITION_ABSOLUTE. */
	public static final int POSITION_ABSOLUTE = 1;
	
	/** The Constant POSITION_RELATIVE. */
	public static final int POSITION_RELATIVE = 2;
	
	/** The Constant POSITION_FIXED. */
	public static final int POSITION_FIXED = 3;

	/** The Constant FLOAT_NONE. */
	public static final int FLOAT_NONE = 0;
	
	/** The Constant FLOAT_LEFT. */
	public static final int FLOAT_LEFT = 1;
	
	/** The Constant FLOAT_RIGHT. */
	public static final int FLOAT_RIGHT = 2;

	/** The Constant OVERFLOW_NONE. */
	public static final int OVERFLOW_NONE = 0;
	
	/** The Constant OVERFLOW_SCROLL. */
	public static final int OVERFLOW_SCROLL = 1;
	
	/** The Constant OVERFLOW_AUTO. */
	public static final int OVERFLOW_AUTO = 2;
	
	/** The Constant OVERFLOW_HIDDEN. */
	public static final int OVERFLOW_HIDDEN = 3;
	
	/** The Constant OVERFLOW_VISIBLE. */
	public static final int OVERFLOW_VISIBLE = 4;

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public int getPosition();

	/**
	 * Gets the float.
	 *
	 * @return the float
	 */
	public int getFloat();

	/**
	 * Gets the visibility.
	 *
	 * @return the visibility
	 */
	public int getVisibility();

	/**
	 * Gets the font.
	 *
	 * @return the font
	 */
	public Font getFont();

	/**
	 * Gets the font base.
	 *
	 * @return the font base
	 */
	public int getFontBase();

	/**
	 * Gets the word info.
	 *
	 * @param word the word
	 * @return the word info
	 */
	public WordInfo getWordInfo(String word);

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor();

	/**
	 * Gets the background color.
	 *
	 * @return the background color
	 */
	public Color getBackgroundColor();

	/**
	 * Gets the text background color.
	 *
	 * @return the text background color
	 */
	public Color getTextBackgroundColor();

	/**
	 * Gets the background info.
	 *
	 * @return the background info
	 */
	public BackgroundInfo getBackgroundInfo();

	/**
	 * Gets the overlay color.
	 *
	 * @return the overlay color
	 */
	public Color getOverlayColor();

	/**
	 * Gets the text transform.
	 *
	 * @return the text transform
	 */
	public int getTextTransform();

	/**
	 * Gets the text decoration mask.
	 *
	 * @return the text decoration mask
	 */
	public int getTextDecorationMask();

	/**
	 * Gets the font metrics.
	 *
	 * @return the font metrics
	 */
	public FontMetrics getFontMetrics();

	/**
	 * Gets the blank width.
	 *
	 * @return the blank width
	 */
	public int getBlankWidth();

	/**
	 * Checks if is highlight.
	 *
	 * @return true, if is highlight
	 */
	public boolean isHighlight();

	/**
	 * Sets the highlight.
	 *
	 * @param highlight the new highlight
	 */
	public void setHighlight(boolean highlight);

	/**
	 * Gets the previous render state.
	 *
	 * @return the previous render state
	 */
	public RenderState getPreviousRenderState();

	/**
	 * Gets the align x percent.
	 *
	 * @return the align x percent
	 */
	public int getAlignXPercent();

	/**
	 * Gets the align y percent.
	 *
	 * @return the align y percent
	 */
	public int getAlignYPercent();

	/**
	 * Gets the count.
	 *
	 * @param counter the counter
	 * @param nesting the nesting
	 * @return the count
	 */
	public int getCount(String counter, int nesting);

	/**
	 * Gets the display.
	 *
	 * @return the display
	 */
	public int getDisplay();

	/**
	 * Reset count.
	 *
	 * @param counter the counter
	 * @param nesting the nesting
	 * @param value the value
	 */
	public void resetCount(String counter, int nesting, int value);

	/**
	 * Increment count.
	 *
	 * @param counter the counter
	 * @param nesting the nesting
	 * @return the int
	 */
	public int incrementCount(String counter, int nesting);

	/**
	 * Gets the text indent.
	 *
	 * @param availWidth the avail width
	 * @return the text indent
	 */
	public int getTextIndent(int availWidth);

	/**
	 * Gets the text indent text.
	 *
	 * @return the text indent text
	 */
	public String getTextIndentText();

	/**
	 * Gets the white space.
	 *
	 * @return the white space
	 */
	public int getWhiteSpace();

	/**
	 * Gets the margin insets.
	 *
	 * @return the margin insets
	 */
	public HtmlInsets getMarginInsets();

	/**
	 * Gets the padding insets.
	 *
	 * @return the padding insets
	 */
	public HtmlInsets getPaddingInsets();

	/**
	 * Gets the overflow x.
	 *
	 * @return the overflow x
	 */
	public int getOverflowX();

	/**
	 * Gets the overflow y.
	 *
	 * @return the overflow y
	 */
	public int getOverflowY();

	/**
	 * Invalidate.
	 */
	public void invalidate();

	/**
	 * Gets the border info.
	 *
	 * @return the border info
	 */
	public BorderInfo getBorderInfo();
}
