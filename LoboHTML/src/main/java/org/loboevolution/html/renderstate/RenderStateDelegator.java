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
package org.loboevolution.html.renderstate;

import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.info.WordInfo;

import java.awt.*;

/**
 * <p>Abstract RenderStateDelegator class.</p>
 */
public abstract class RenderStateDelegator implements RenderState {
	protected final RenderState delegate;

	/**
	 * <p>Constructor for RenderStateDelegator.</p>
	 *
	 * @param delegate a {@link RenderState} object.
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
	public BackgroundInfo getBackgroundImageInfo(final int width, final int height) {
		return this.delegate.getBackgroundImageInfo(width, height);
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
	public int getCount(final String counter, final int nesting) {
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
	public int getTextIndent(final int availWidth) {
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
	public WordInfo getWordInfo(final String word) {
		return this.delegate.getWordInfo(word);
	}

	/** {@inheritDoc} */
	@Override
	public int incrementCount(final String counter, final int nesting) {
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
	public void resetCount(final String counter, final int nesting, final int value) {
		this.delegate.resetCount(counter, nesting, value);
	}

	/** {@inheritDoc} */
	@Override
	public void setHighlight(final boolean highlight) {
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
	public Cursor getCursor() {
		return this.delegate.getCursor();
	}

	/** {@inheritDoc} */
	@Override
	public void setCursor(final Cursor cursor) {
		this.delegate.setCursor(cursor);
	}

	/** {@inheritDoc} */
	@Override
	public String getVerticalAlign() {
		return this.delegate.getVerticalAlign();
	}
}
