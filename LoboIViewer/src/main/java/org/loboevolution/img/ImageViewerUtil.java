/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
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
	public static void synchronizeViewers(ImageViewer first, ImageViewer... others) {
		Synchronizer mainSynchronizer = first.getSynchronizer();
		for (ImageViewer other : others) {
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
	public static void synchronizeViewers(Collection<ImageViewer> viewers) {
		Iterator<ImageViewer> iterator = viewers.iterator();
		if (!iterator.hasNext())
			return;

		Synchronizer mainSynchronizer = iterator.next().getSynchronizer();
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
	public static void synchronizePixelInfoStatusBars(ImageViewer... viewers) {
		PixelModel model = null;
		for (ImageViewer viewer : viewers) {
			StatusBar bar = viewer.getStatusBar();
			if (bar instanceof PixelInfoStatusBar) {
				if (model == null)
					model = ((PixelInfoStatusBar) bar).getModel();
				else
					((PixelInfoStatusBar) bar).setModel(model);
			}
		}
	}
}
