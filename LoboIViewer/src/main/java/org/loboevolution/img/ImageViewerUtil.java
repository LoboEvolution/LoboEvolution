/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import java.util.Collection;
import java.util.Iterator;

/**
 * Utility methods for image viewers.
 *
 * Author Kazo Csaba
 *
 */
public final class ImageViewerUtil {
	/** Private constructor. */
	private ImageViewerUtil() {
	}

	/**
	 * Synchronizes the view state of multiple image viewers with respect to scroll
	 * position and resize strategy, and other properties affecting display.
	 *
	 * @param first
	 *            the first viewer
	 * @param others
	 *            the other viewers
	 */
	public static void synchronizeViewers(final ImageViewer first, final ImageViewer... others) {
		final Synchronizer mainSynchronizer = first.getSynchronizer();
		for (final ImageViewer other : others) {
			mainSynchronizer.add(other);
		}
	}

	/**
	 * Synchronizes the view state of multiple image viewers with respect to scroll
	 * position and resize strategy, and other properties affecting display.
	 *
	 * @param viewers
	 *            the viewers to synchronize
	 */
	public static void synchronizeViewers(final Collection<ImageViewer> viewers) {
		final Iterator<ImageViewer> iterator = viewers.iterator();
		if (!iterator.hasNext())
			return;

		final Synchronizer mainSynchronizer = iterator.next().getSynchronizer();
		while (iterator.hasNext()) {
			mainSynchronizer.add(iterator.next());
		}
	}

	/**
	 * Synchronizes the {@link org.loboevolution.img.PixelInfoStatusBar}s associated with the viewers.
	 * Viewers with a different or {@code null} status bar are ignored. When this
	 * function returns, the {@code PixelInfoStatusBar}s among the viewer status
	 * bars will share the same {@code PixelModel}, and thus display the same pixel.
	 * <p>
	 * The default status bar of ImageViewer is a {@code PixelInfoStatusBar}, so
	 * this function can be used to synchronize the default status bars of viewers.
	 *
	 * @param viewers
	 *            the viewers
	 */
	public static void synchronizePixelInfoStatusBars(final ImageViewer... viewers) {
		PixelModel model = null;
		for (final ImageViewer viewer : viewers) {
			final StatusBar bar = viewer.getStatusBar();
			if (bar instanceof PixelInfoStatusBar) {
				if (model == null)
					model = ((PixelInfoStatusBar) bar).getModel();
				else
					((PixelInfoStatusBar) bar).setModel(model);
			}
		}
	}
}
