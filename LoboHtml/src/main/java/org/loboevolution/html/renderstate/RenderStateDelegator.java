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
 * <p>Abstract RenderStateDelegator class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class RenderStateDelegator implements RenderState {
	protected final RenderState delegate;

	/**
	 * <p>Constructor for RenderStateDelegator.</p>
	 *
	 * @param delegate a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	public RenderStateDelegator(final RenderState delegate) {
		super();
		this.delegate = delegate;
	}

	/** {@inheritDoc} */
	@Override
	public int getAlignXPercent() {
		return this.delegate.getAlignXPercent();
	}

	/** {@inheritDoc} */
	@Override
	public int getAlignYPercent() {
		return this.delegate.getAlignYPercent();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getAlignItems() {
		return this.delegate.getAlignItems();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getAlignContent() {
		return this.delegate.getAlignContent();
	}

	/** {@inheritDoc} */
	@Override
	public Color getBackgroundColor() {
		return this.delegate.getBackgroundColor();
	}

	/** {@inheritDoc} */
	@Override
	public BackgroundInfo getBackgroundInfo() {
		return this.delegate.getBackgroundInfo();
	}

	/** {@inheritDoc} */
	@Override
	public int getBlankWidth() {
		return this.delegate.getBlankWidth();
	}

	/** {@inheritDoc} */
	@Override
	public BorderInfo getBorderInfo() {
		return this.delegate.getBorderInfo();
	}

	/** {@inheritDoc} */
	@Override
	public Color getColor() {
		return this.delegate.getColor();
	}

	/** {@inheritDoc} */
	@Override
	public int getCount(String counter, int nesting) {
		return this.delegate.getCount(counter, nesting);
	}

	/** {@inheritDoc} */
	@Override
	public int getDisplay() {
		return this.delegate.getDisplay();
	}

	/** {@inheritDoc} */
	@Override
	public int getFloat() {
		return this.delegate.getFloat();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getFlexDirection() {
		return this.delegate.getFlexDirection();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getFlexWrap() {
		return this.delegate.getFlexWrap();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getFlexFlow() {
		return this.delegate.getFlexFlow();
	}
	
	/** {@inheritDoc} */
	@Override
	public String getJustifyContent() {
		return this.delegate.getJustifyContent();
	}

	/** {@inheritDoc} */
	@Override
	public Font getFont() {
		return this.delegate.getFont();
	}

	/** {@inheritDoc} */
	@Override
	public int getFontBase() {
		return this.delegate.getFontBase();
	}

	/** {@inheritDoc} */
	@Override
	public FontMetrics getFontMetrics() {
		return this.delegate.getFontMetrics();
	}

	/** {@inheritDoc} */
	@Override
	public HtmlInsets getMarginInsets() {
		return this.delegate.getMarginInsets();
	}

	/** {@inheritDoc} */
	@Override
	public int getOverflowX() {
		return this.delegate.getOverflowX();
	}

	/** {@inheritDoc} */
	@Override
	public int getOverflowY() {
		return this.delegate.getOverflowY();
	}

	/** {@inheritDoc} */
	@Override
	public Color getOverlayColor() {
		return this.delegate.getOverlayColor();
	}

	/** {@inheritDoc} */
	@Override
	public HtmlInsets getPaddingInsets() {
		return this.delegate.getPaddingInsets();
	}

	/** {@inheritDoc} */
	@Override
	public int getPosition() {
		return this.delegate.getPosition();
	}

	/** {@inheritDoc} */
	@Override
	public RenderState getPreviousRenderState() {
		return this.delegate;
	}

	/** {@inheritDoc} */
	@Override
	public Color getTextBackgroundColor() {
		return this.delegate.getTextBackgroundColor();
	}

	/** {@inheritDoc} */
	@Override
	public int getTextDecorationMask() {
		return this.delegate.getTextDecorationMask();
	}

	/** {@inheritDoc} */
	@Override
	public int getTextIndent(int availWidth) {
		return this.delegate.getTextIndent(availWidth);
	}

	/** {@inheritDoc} */
	@Override
	public String getTextIndentText() {
		return this.delegate.getTextIndentText();
	}

	/** {@inheritDoc} */
	@Override
	public int getTextTransform() {
		return this.delegate.getTextTransform();
	}

	/** {@inheritDoc} */
	@Override
	public int getVisibility() {
		return this.delegate.getVisibility();
	}

	/** {@inheritDoc} */
	@Override
	public int getWhiteSpace() {
		return this.delegate.getWhiteSpace();
	}

	/** {@inheritDoc} */
	@Override
	public WordInfo getWordInfo(String word) {
		return this.delegate.getWordInfo(word);
	}

	/** {@inheritDoc} */
	@Override
	public int incrementCount(String counter, int nesting) {
		return this.delegate.incrementCount(counter, nesting);
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		this.delegate.invalidate();
	}

	/** {@inheritDoc} */
	@Override
	public boolean isHighlight() {
		return this.delegate.isHighlight();
	}

	/** {@inheritDoc} */
	@Override
	public void resetCount(String counter, int nesting, int value) {
		this.delegate.resetCount(counter, nesting, value);
	}

	/** {@inheritDoc} */
	@Override
	public void setHighlight(boolean highlight) {
		this.delegate.setHighlight(highlight);
	}
	
    /** {@inheritDoc} */
    @Override
    public int getClear() {
        return this.delegate.getClear();
    }
    
    /** {@inheritDoc} */
    @Override
    public String getBoxSizing() {
        return this.delegate.getBoxSizing();
    }
   
    /** {@inheritDoc} */
    @Override
    public String getLeft() {
        return this.delegate.getLeft();
    }

    /** {@inheritDoc} */
    @Override
    public String getTop() {
        return this.delegate.getTop();
    }

    /** {@inheritDoc} */
    @Override
    public String getBottom() {
        return this.delegate.getBottom();
    }

	/** {@inheritDoc} */
	@Override
	public String getRight() {
		return this.delegate.getRight();
	}

	/** {@inheritDoc} */
	@Override
	public Optional<Cursor> getCursor() {
		return this.delegate.getCursor();
	}

	/** {@inheritDoc} */
	@Override
	public void setCursor(Optional<Cursor> cursor) {
		this.delegate.setCursor(cursor);
	}

	/** {@inheritDoc} */
	@Override
	public String getVerticalAlign() {
		return this.delegate.getVerticalAlign();
	}
}
