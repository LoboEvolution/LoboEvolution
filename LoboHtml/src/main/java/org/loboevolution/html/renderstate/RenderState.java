/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
package org.loboevolution.html.renderstate;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Optional;

import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.info.WordInfo;

/**
 * <p>RenderState interface.</p>
 *
 * @author J. H. S.
 * @version $Id: $Id
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
	
	/** The display inline table. */
	int DISPLAY_INLINE_TABLE = 9;
	
	/** The display table row group. */
	int DISPLAY_TABLE_ROW_GROUP = 10;
	
	/** The display table header group. */
	int DISPLAY_TABLE_HEADER_GROUP = 11;
	
	/** The display table footer group. */
	int DISPLAY_TABLE_FOOTER_GROUP = 12;
	
	/** The display table column. */
	int DISPLAY_TABLE_COLUMN = 13;
	
	/** The display table column group. */
	int DISPLAY_TABLE_COLUMN_GROUP = 14;

	/** The display flex box. */
	int DISPLAY_FLEX_BOX = 15;
	
	/** The display flex child. */
	int DISPLAY_FLEX_CHILD = 16;

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
	 * <p>getAlignXPercent.</p>
	 *
	 * @return a int.
	 */
	int getAlignXPercent();

	/**
	 * <p>getAlignYPercent.</p>
	 *
	 * @return a int.
	 */
	int getAlignYPercent();
	
	/**
	 * <p>getAlignItems.</p>
	 *
	 * @return a String.
	 */
	String getAlignItems();
	
	/**
	 * <p>getAlignContent.</p>
	 *
	 * @return a String.
	 */
	String getAlignContent();

	/**
	 * <p>getBackgroundColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getBackgroundColor();

	/**
	 * <p>getBackgroundInfo.</p>
	 *
	 * @return a {@link org.loboevolution.info.BackgroundInfo} object.
	 */
	BackgroundInfo getBackgroundInfo();

	/**
	 * <p>getBlankWidth.</p>
	 *
	 * @return a int.
	 */
	int getBlankWidth();

	/**
	 * <p>getBorderInfo.</p>
	 *
	 * @return a {@link org.loboevolution.info.BorderInfo} object.
	 */
	BorderInfo getBorderInfo();

	/**
	 * <p>getColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getColor();

	/**
	 * <p>getCount.</p>
	 *
	 * @param counter a {@link java.lang.String} object.
	 * @param nesting a int.
	 * @return a int.
	 */
	int getCount(String counter, int nesting);

	/**
	 * <p>getDisplay.</p>
	 *
	 * @return a int.
	 */
	int getDisplay();

	/**
	 * <p>getFloat.</p>
	 *
	 * @return a int.
	 */
	int getFloat();
	
	/**
	 * <p>getFlexDirection.</p>
	 *
	 * @return a String.
	 */
	String getFlexDirection();
	
	/**
	 * <p>getFlexWrap.</p>
	 *
	 * @return a String.
	 */
	String getFlexWrap();
	
	/**
	 * <p>getFlexFlow.</p>
	 *
	 * @return a String.
	 */
	String getFlexFlow();
	
	/**
	 * <p>getJustifyContent.</p>
	 *
	 * @return a String.
	 */
	String getJustifyContent();

	/**
	 * <p>getFont.</p>
	 *
	 * @return a {@link java.awt.Font} object.
	 */
	Font getFont();

	/**
	 * <p>getFontBase.</p>
	 *
	 * @return a int.
	 */
	int getFontBase();

	/**
	 * <p>getFontMetrics.</p>
	 *
	 * @return a {@link java.awt.FontMetrics} object.
	 */
	FontMetrics getFontMetrics();

	/**
	 * <p>getMarginInsets.</p>
	 *
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	HtmlInsets getMarginInsets();

	/**
	 * <p>getOverflowX.</p>
	 *
	 * @return a int.
	 */
	int getOverflowX();

	/**
	 * <p>getOverflowY.</p>
	 *
	 * @return a int.
	 */
	int getOverflowY();

	/**
	 * <p>getOverlayColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getOverlayColor();

	/**
	 * <p>getPaddingInsets.</p>
	 *
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	HtmlInsets getPaddingInsets();

	/**
	 * <p>getPosition.</p>
	 *
	 * @return a int.
	 */
	int getPosition();

	/**
	 * <p>getPreviousRenderState.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	RenderState getPreviousRenderState();

	/**
	 * <p>getTextBackgroundColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getTextBackgroundColor();

	/**
	 * <p>getTextDecorationMask.</p>
	 *
	 * @return a int.
	 */
	int getTextDecorationMask();

	/**
	 * <p>getTextIndent.</p>
	 *
	 * @param availWidth a int.
	 * @return a int.
	 */
	int getTextIndent(int availWidth);

	/**
	 * <p>getTextIndentText.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTextIndentText();

	/**
	 * <p>getTextTransform.</p>
	 *
	 * @return a int.
	 */
	int getTextTransform();

	/**
	 * <p>getVisibility.</p>
	 *
	 * @return a int.
	 */
	int getVisibility();

	/**
	 * <p>getWhiteSpace.</p>
	 *
	 * @return a int.
	 */
	int getWhiteSpace();

	/**
	 * <p>getWordInfo.</p>
	 *
	 * @param word a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.info.WordInfo} object.
	 */
	WordInfo getWordInfo(String word);

	/**
	 * <p>incrementCount.</p>
	 *
	 * @param counter a {@link java.lang.String} object.
	 * @param nesting a int.
	 * @return a int.
	 */
	int incrementCount(String counter, int nesting);

	/**
	 * <p>invalidate.</p>
	 */
	void invalidate();

	/**
	 * <p>isHighlight.</p>
	 *
	 * @return a boolean.
	 */
	boolean isHighlight();

	/**
	 * <p>resetCount.</p>
	 *
	 * @param counter a {@link java.lang.String} object.
	 * @param nesting a int.
	 * @param value a int.
	 */
	void resetCount(String counter, int nesting, int value);

	/**
	 * <p>setHighlight.</p>
	 *
	 * @param highlight a boolean.
	 */
	void setHighlight(boolean highlight);
	
    /**
     * <p>getClear.</p>
     *
     * @return a int.
     */
    int getClear();
    
    /**
     * <p>getBoxSizing.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getBoxSizing();
    
    /**
     * <p>getLeft.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getLeft();

    /**
     * <p>getTop.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getTop();

    /**
     * <p>getRight.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getRight();

    /**
     * <p>getBottom.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getBottom();
    
    /**
     * <p>getVerticalAlign.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getVerticalAlign();
    
    /**
     * <p>getCursor.</p>
     *
     * @return a {@link java.util.Optional} object.
     */
    Optional<Cursor> getCursor();
    
    /**
     * <p>setCursor.</p>
     *
     * @param cursor a {@link java.util.Optional} object.
     */
    void setCursor(Optional<Cursor> cursor);
}
