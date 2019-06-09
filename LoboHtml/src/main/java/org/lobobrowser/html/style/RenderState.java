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
package org.lobobrowser.html.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * @author J. H. S.
 */
public interface RenderState {
	int DISPLAY_NONE = 0;
	int DISPLAY_INLINE = 1;
	int DISPLAY_BLOCK = 2;
	int DISPLAY_LIST_ITEM = 3;
	int DISPLAY_TABLE_ROW = 4;
	int DISPLAY_TABLE_CELL = 5;
	int DISPLAY_TABLE = 6;
	int DISPLAY_INLINE_BLOCK = 7;
	int DISPLAY_TABLE_ROW_GROUP = 8;
	int DISPLAY_TABLE_HEADER_GROUP = 9;
	int DISPLAY_TABLE_FOOTER_GROUP = 10;
	int DISPLAY_TABLE_COLUMN = 11;
	int DISPLAY_TABLE_COLUMN_GROUP = 12;
	int DISPLAY_TABLE_CAPTION = 13;
	int DISPLAY_INLINE_TABLE = 14;
	
	int FLOAT_LEFT = 1;
	int FLOAT_NONE = 0;
	int FLOAT_RIGHT = 2;
	
	int MASK_TEXTDECORATION_BLINK = 8;
	int MASK_TEXTDECORATION_LINE_THROUGH = 4;
	int MASK_TEXTDECORATION_OVERLINE = 2;
	int MASK_TEXTDECORATION_UNDERLINE = 1;

	int OVERFLOW_AUTO = 2;
	int OVERFLOW_HIDDEN = 3;
	int OVERFLOW_NONE = 0;
	int OVERFLOW_SCROLL = 1;
	int OVERFLOW_VISIBLE = 4;

	int POSITION_ABSOLUTE = 1;
	int POSITION_FIXED = 3;
	int POSITION_RELATIVE = 2;
	int POSITION_STATIC = 0;
	
	int TEXTTRANSFORM_CAPITALIZE = 1;
	int TEXTTRANSFORM_LOWERCASE = 4;
	int TEXTTRANSFORM_NONE = 0;
	int TEXTTRANSFORM_UPPERCASE = 2;
	
	int VISIBILITY_COLLAPSE = 2;
	int VISIBILITY_HIDDEN = 1;
	int VISIBILITY_VISIBLE = 0;

	int WS_NORMAL = 0;
	int WS_NOWRAP = 2;
	int WS_PRE = 1;

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
}
