/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.style;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Optional;

import org.lobobrowser.html.renderstate.RenderState;

/**
 * The Class RenderStateDelegator.
 */
public abstract class RenderStateDelegator implements RenderState {

    /** The delegate. */
    protected final RenderState delegate;

    /**
     * Instantiates a new render state delegator.
     *
     * @param delegate
     *            the delegate
     */
    public RenderStateDelegator(final RenderState delegate) {
        super();
        this.delegate = delegate;
    }

    @Override
    public RenderState getPreviousRenderState() {
        return this.delegate;
    }

    @Override
    public int getAlignXPercent() {
        return delegate.getAlignXPercent();
    }


    @Override
    public int getAlignYPercent() {
        return delegate.getAlignYPercent();
    }

    @Override
    public int getBlankWidth() {
        return delegate.getBlankWidth();
    }

    @Override
    public Color getColor() {
        return delegate.getColor();
    }

    @Override
    public Font getFont() {
        return delegate.getFont();
    }

    @Override
    public int getFontBase() {
        return delegate.getFontBase();
    }

    @Override
    public FontMetrics getFontMetrics() {
        return delegate.getFontMetrics();
    }

    @Override
    public Color getOverlayColor() {
        return delegate.getOverlayColor();
    }

    @Override
    public Color getBackgroundColor() {
        return delegate.getBackgroundColor();
    }

    @Override
    public int getTextDecorationMask() {
        return delegate.getTextDecorationMask();
    }

    @Override
    public int getTextTransform() {
        return delegate.getTextTransform();
    }

    @Override
    public WordInfo getWordInfo(String word) {
        return delegate.getWordInfo(word);
    }

    @Override
    public void invalidate() {
        delegate.invalidate();
    }

    @Override
    public boolean isHighlight() {
        return delegate.isHighlight();
    }

    @Override
    public void setHighlight(boolean highlight) {
        delegate.setHighlight(highlight);
    }

    @Override
    public int getCount(String counter, int nesting) {
        return this.delegate.getCount(counter, nesting);
    }

    @Override
    public void resetCount(String counter, int nesting, int value) {
        this.delegate.resetCount(counter, nesting, value);
    }

    @Override
    public int incrementCount(String counter, int nesting) {
        return this.delegate.incrementCount(counter, nesting);
    }

    @Override
    public BackgroundInfo getBackgroundInfo() {
        return this.delegate.getBackgroundInfo();
    }

    @Override
    public int getDisplay() {
        return this.delegate.getDisplay();
    }

    @Override
    public Color getTextBackgroundColor() {
        return this.delegate.getTextBackgroundColor();
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
    public int getWhiteSpace() {
        return this.delegate.getWhiteSpace();
    }

    @Override
    public HtmlInsets getMarginInsets() {
        return this.delegate.getMarginInsets();
    }

    @Override
    public HtmlInsets getPaddingInsets() {
        return this.delegate.getPaddingInsets();
    }

    @Override
    public int getVisibility() {
        return this.delegate.getVisibility();
    }

    @Override
    public int getPosition() {
        return this.delegate.getPosition();
    }

    @Override
    public int getFloat() {
        return this.delegate.getFloat();
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
    public BorderInfo getBorderInfo() {
        return this.delegate.getBorderInfo();
    }
    
    @Override
    public Optional<Cursor> getCursor() {
        return this.delegate.getCursor();
      }
}
