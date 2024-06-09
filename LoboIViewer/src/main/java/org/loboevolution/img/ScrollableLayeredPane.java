/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.img;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.Serial;

import javax.swing.JLayeredPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;

/**
 * <p>ScrollableLayeredPane class.</p>
 *
 *
 *
 */
public class ScrollableLayeredPane extends JLayeredPane implements Scrollable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	private final transient LayeredImageView layeredImageView;
	

	/**
	 * <p>Constructor for ScrollableLayeredPane.</p>
	 *
	 * @param layeredImageView a {@link org.loboevolution.img.LayeredImageView} object.
	 */
	public ScrollableLayeredPane(final LayeredImageView layeredImageView) {
		this.layeredImageView = layeredImageView;
	}

	/** {@inheritDoc} */
	@Override
	public int getScrollableUnitIncrement(final Rectangle visibleRect, final int orientation, final int direction) {
		return 10;
	}

	/** {@inheritDoc} */
	@Override
	public int getScrollableBlockIncrement(final Rectangle visibleRect, final int orientation, final int direction) {
		return 50;
	}

	/*
	 * The getScrollableTracksViewportXxx functions below are used by
	 * javax.swing.ScrollPaneLayout to determine whether the scroll bars should be
	 * visible; so these need to be implemented.
	 */
	/** {@inheritDoc} */
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.SHRINK_TO_FIT
				|| layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.RESIZE_TO_FIT;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.SHRINK_TO_FIT
				|| layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.RESIZE_TO_FIT;
	}

	/*
	 * The getPreferredScrollableViewportSize does not seem to be used.
	 */
	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		if (layeredImageView.getTheImage().getResizeStrategy() == ResizeStrategy.NO_RESIZE)
			return getPreferredSize();
		else
			return javax.swing.SwingUtilities.getAncestorOfClass(JViewport.class, this).getSize();
	}
}
