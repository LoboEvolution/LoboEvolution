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
package org.lobobrowser.html.renderstate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import org.lobo.info.BackgroundInfo;
import org.lobo.info.BorderInfo;
import org.lobo.info.WordInfo;
import org.lobobrowser.html.style.HtmlInsets;

public abstract class RenderStateDelegator implements RenderState {
	protected final RenderState delegate;

	public RenderStateDelegator(final RenderState delegate) {
		super();
		this.delegate = delegate;
	}

	@Override
	public int getAlignXPercent() {
		return this.delegate.getAlignXPercent();
	}

	@Override
	public int getAlignYPercent() {
		return this.delegate.getAlignYPercent();
	}

	@Override
	public Color getBackgroundColor() {
		return this.delegate.getBackgroundColor();
	}

	@Override
	public BackgroundInfo getBackgroundInfo() {
		return this.delegate.getBackgroundInfo();
	}

	@Override
	public int getBlankWidth() {
		return this.delegate.getBlankWidth();
	}

	@Override
	public BorderInfo getBorderInfo() {
		return this.delegate.getBorderInfo();
	}

	@Override
	public Color getColor() {
		return this.delegate.getColor();
	}

	@Override
	public int getCount(String counter, int nesting) {
		return this.delegate.getCount(counter, nesting);
	}

	@Override
	public int getDisplay() {
		return this.delegate.getDisplay();
	}

	@Override
	public int getFloat() {
		return this.delegate.getFloat();
	}

	@Override
	public Font getFont() {
		return this.delegate.getFont();
	}

	@Override
	public int getFontBase() {
		return this.delegate.getFontBase();
	}

	@Override
	public FontMetrics getFontMetrics() {
		return this.delegate.getFontMetrics();
	}

	@Override
	public HtmlInsets getMarginInsets() {
		return this.delegate.getMarginInsets();
	}

	@Override
	public int getOverflowX() {
		return this.delegate.getOverflowX();
	}

	@Override
	public int getOverflowY() {
		return this.delegate.getOverflowY();
	}

	@Override
	public Color getOverlayColor() {
		return this.delegate.getOverlayColor();
	}

	@Override
	public HtmlInsets getPaddingInsets() {
		return this.delegate.getPaddingInsets();
	}

	@Override
	public int getPosition() {
		return this.delegate.getPosition();
	}

	@Override
	public RenderState getPreviousRenderState() {
		return this.delegate;
	}

	@Override
	public Color getTextBackgroundColor() {
		return this.delegate.getTextBackgroundColor();
	}

	@Override
	public int getTextDecorationMask() {
		return this.delegate.getTextDecorationMask();
	}

	@Override
	public int getTextIndent(int availWidth) {
		return this.delegate.getTextIndent(availWidth);
	}

	@Override
	public String getTextIndentText() {
		return this.delegate.getTextIndentText();
	}

	@Override
	public int getTextTransform() {
		return this.delegate.getTextTransform();
	}

	@Override
	public int getVisibility() {
		return this.delegate.getVisibility();
	}

	@Override
	public int getWhiteSpace() {
		return this.delegate.getWhiteSpace();
	}

	@Override
	public WordInfo getWordInfo(String word) {
		return this.delegate.getWordInfo(word);
	}

	@Override
	public int incrementCount(String counter, int nesting) {
		return this.delegate.incrementCount(counter, nesting);
	}

	@Override
	public void invalidate() {
		this.delegate.invalidate();
	}

	@Override
	public boolean isHighlight() {
		return this.delegate.isHighlight();
	}

	@Override
	public void resetCount(String counter, int nesting, int value) {
		this.delegate.resetCount(counter, nesting, value);
	}

	@Override
	public void setHighlight(boolean highlight) {
		this.delegate.setHighlight(highlight);
	}
	
    @Override
    public int getClear() {
        return this.delegate.getClear();
    }
    
    @Override
    public String getBoxSizing() {
        return this.delegate.getBoxSizing();
    }
   
    @Override
    public String getLeft() {
        return this.delegate.getLeft();
    }

    @Override
    public String getTop() {
        return this.delegate.getTop();
    }

    @Override
    public String getBottom() {
        return this.delegate.getBottom();
    }

    @Override
    public String getRight() {
        return this.delegate.getRight();
    }
}
