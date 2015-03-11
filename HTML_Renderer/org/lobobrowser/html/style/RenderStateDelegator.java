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
package org.lobobrowser.html.style;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

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
	 * @param delegate the delegate
	 */
	public RenderStateDelegator(final RenderState delegate) {
		super();
		this.delegate = delegate;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getPreviousRenderState()
	 */
	public RenderState getPreviousRenderState() {
		return this.delegate;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getAlignXPercent()
	 */
	public int getAlignXPercent() {
		return delegate.getAlignXPercent();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getAlignYPercent()
	 */
	public int getAlignYPercent() {
		return delegate.getAlignYPercent();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getBlankWidth()
	 */
	public int getBlankWidth() {
		return delegate.getBlankWidth();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getColor()
	 */
	public Color getColor() {
		return delegate.getColor();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getFont()
	 */
	public Font getFont() {
		return delegate.getFont();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getFontBase()
	 */
	public int getFontBase() {
		return delegate.getFontBase();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getFontMetrics()
	 */
	public FontMetrics getFontMetrics() {
		return delegate.getFontMetrics();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getOverlayColor()
	 */
	public Color getOverlayColor() {
		return delegate.getOverlayColor();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getBackgroundColor()
	 */
	public Color getBackgroundColor() {
		return delegate.getBackgroundColor();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextDecorationMask()
	 */
	public int getTextDecorationMask() {
		return delegate.getTextDecorationMask();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextTransform()
	 */
	public int getTextTransform() {
		return delegate.getTextTransform();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getWordInfo(java.lang.String)
	 */
	public WordInfo getWordInfo(String word) {
		return delegate.getWordInfo(word);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#invalidate()
	 */
	public void invalidate() {
		delegate.invalidate();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#isHighlight()
	 */
	public boolean isHighlight() {
		return delegate.isHighlight();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#setHighlight(boolean)
	 */
	public void setHighlight(boolean highlight) {
		delegate.setHighlight(highlight);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getCount(java.lang.String, int)
	 */
	public int getCount(String counter, int nesting) {
		return this.delegate.getCount(counter, nesting);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#resetCount(java.lang.String, int, int)
	 */
	public void resetCount(String counter, int nesting, int value) {
		this.delegate.resetCount(counter, nesting, value);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#incrementCount(java.lang.String, int)
	 */
	public int incrementCount(String counter, int nesting) {
		return this.delegate.incrementCount(counter, nesting);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getBackgroundInfo()
	 */
	public BackgroundInfo getBackgroundInfo() {
		return this.delegate.getBackgroundInfo();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getDisplay()
	 */
	public int getDisplay() {
		return this.delegate.getDisplay();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextBackgroundColor()
	 */
	public Color getTextBackgroundColor() {
		return this.delegate.getTextBackgroundColor();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextIndent(int)
	 */
	public int getTextIndent(int availWidth) {
		return this.delegate.getTextIndent(availWidth);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getTextIndentText()
	 */
	public String getTextIndentText() {
		return this.delegate.getTextIndentText();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getWhiteSpace()
	 */
	public int getWhiteSpace() {
		return this.delegate.getWhiteSpace();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getMarginInsets()
	 */
	public HtmlInsets getMarginInsets() {
		return this.delegate.getMarginInsets();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getPaddingInsets()
	 */
	public HtmlInsets getPaddingInsets() {
		return this.delegate.getPaddingInsets();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getVisibility()
	 */
	public int getVisibility() {
		return this.delegate.getVisibility();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getPosition()
	 */
	public int getPosition() {
		return this.delegate.getPosition();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getFloat()
	 */
	public int getFloat() {
		return this.delegate.getFloat();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getOverflowX()
	 */
	public int getOverflowX() {
		return this.delegate.getOverflowX();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getOverflowY()
	 */
	public int getOverflowY() {
		return this.delegate.getOverflowY();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.renderstate.RenderState#getBorderInfo()
	 */
	public BorderInfo getBorderInfo() {
		return this.delegate.getBorderInfo();
	}
}
