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

import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.info.WordInfo;
import org.loboevolution.html.style.HtmlInsets;

/**
 * @author J. H. S.
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
	
	int getAlignXPercent();

	int getAlignYPercent();

	Color getBackgroundColor();

	BackgroundInfo getBackgroundInfo();

	int getBlankWidth();

	BorderInfo getBorderInfo();

	Color getColor();

	int getCount(String counter, int nesting);

	int getDisplay();

	int getFloat();

	Font getFont();

	int getFontBase();

	FontMetrics getFontMetrics();

	HtmlInsets getMarginInsets();

	int getOverflowX();

	int getOverflowY();

	Color getOverlayColor();

	HtmlInsets getPaddingInsets();

	int getPosition();

	RenderState getPreviousRenderState();

	Color getTextBackgroundColor();

	int getTextDecorationMask();

	int getTextIndent(int availWidth);

	String getTextIndentText();

	int getTextTransform();

	int getVisibility();

	int getWhiteSpace();

	WordInfo getWordInfo(String word);

	int incrementCount(String counter, int nesting);

	void invalidate();

	boolean isHighlight();

	void resetCount(String counter, int nesting, int value);

	void setHighlight(boolean highlight);
	
    int getClear();
    
    String getBoxSizing();
    
    String getLeft();

    String getTop();

    String getRight();

    String getBottom();
    
    String getVerticalAlign();
    
    Optional<Cursor> getCursor();
    
    void setCursor(Optional<Cursor> cursor);
}
