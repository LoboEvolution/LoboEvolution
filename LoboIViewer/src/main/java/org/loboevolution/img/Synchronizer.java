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

import java.util.*;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Manages the synchronization of a collection of viewers.
 * <h2>Implementation notes</h2> This class is necessary mainly due to the
 * complicated operations that are performed for some property changes. When
 * e.g. a resize strategy is changed, that triggers a change in the component's
 * size, leading to an update to the scroll bars. But after that, the viewer
 * performs rescrolling to try to restore the original image center. These
 * multiple scroll operations do not work very well together when two
 * synchrnoized viewers have images of different sizes.
 * 
 * This scenario is managed in the following way. While the resize strategy
 * property is synchronized, all scroll synchronizations are disabled, so size
 * changes do not rewrite the scroll positions of other viewers. Rescrolling is
 * only allowed for the viewer in which the property change was initiated, and
 * after it finished rescrolling, this synchronizer matches the scroll positions
 * of the other viewers.
 * 
 * All this cannot be implemented using property change listeners, because then
 * by the time we'd learn that the resize strategy has changed, a bunch of
 * scrolls would have happened, and it is entirely possible that the rescroll
 * operation of the originating viewer is overwritten by a subsequent scroll
 * synchronization from one of the other viewers.
 * 
 * So the viewer needs a reference the the synchronizer. It announces to the
 * synchronizer that a complex operation begins, and asks permission to perform
 * rescrolling ({@link #resizeStrategyChangedCanIRescroll(ImageViewer)}). The
 * synchronizer uses this mechanism to make note of the viewer in which the
 * change originated, storing it in the variable {@link #leader}, and using it
 * to deny rescrolling to all the other viewers, and to disable scroll
 * synchronization. Then, when the originator finishes the rescroll, it notifies
 * the synchronizer that the operation is complete using the
 * {@link #doneRescrolling(ImageViewer)} function. At this point the
 * synchronizer adjusts the scroll panes of the other viewers to match the
 * originator.
 * 
 * Author Kazo Csaba
 */
class Synchronizer {
	private final WeakHashMap<ImageViewer, Void> viewers = new WeakHashMap<>(4);

	/*
	 * If there is currently a synchronization update taking place, the viewer which
	 * initiated the change is stored in this variable.
	 */
	private ImageViewer leader = null;

	private final ChangeListener scrollChangeListener = new ChangeListener() {
		boolean adjusting = false;

		@Override
		public void stateChanged(final ChangeEvent e) {
			if (leader != null) {
				// ignore every scrolling event during synchronization, it will all be adjusted
				// after the final rescroll
				return;
			}
			if (adjusting) {
				// also ignore changes that our adjustments cause
				return;
			}
			ImageViewer source = null;
			for (final Map.Entry<ImageViewer, Void> entry : viewers.entrySet()) {
				final ImageViewer viewer = entry.getKey();
				if (viewer.getScrollPane().getHorizontalScrollBar().getModel() == e.getSource()
						|| viewer.getScrollPane().getVerticalScrollBar().getModel() == e.getSource()) {
					source = viewer;
					break;
				}
			}
			if (source == null)
				throw new AssertionError("Couldn't find the source of the scroll bar change event");
			adjusting = true;
			for (final Map.Entry<ImageViewer, Void> entry : viewers.entrySet()) {
				final ImageViewer viewer = entry.getKey();
				updateScroll(viewer, source);
			}
			adjusting = false;
		}
	};

	/**
	 * <p>Constructor for Synchronizer.</p>
	 *
	 * @param viewer a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public Synchronizer(final ImageViewer viewer) {
		viewers.put(viewer, null);
		viewer.getScrollPane().getHorizontalScrollBar().getModel().addChangeListener(scrollChangeListener);
		viewer.getScrollPane().getVerticalScrollBar().getModel().addChangeListener(scrollChangeListener);
	}

	private void updateScroll(final ImageViewer viewer, final ImageViewer reference) {
		if (Objects.equals(reference, viewer))
			return;
		/*
		 * Note that this method may be called during a resize, before the viewport has
		 * had a chance to reshape itself so we cannot rely on the view rectangle.
		 */
		viewer.getScrollPane().getHorizontalScrollBar().getModel()
				.setValue(reference.getScrollPane().getHorizontalScrollBar().getModel().getValue());
		viewer.getScrollPane().getVerticalScrollBar().getModel()
				.setValue(reference.getScrollPane().getVerticalScrollBar().getModel().getValue());
	}

	/**
	 * <p>add.</p>
	 *
	 * @param viewer a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public void add(final ImageViewer viewer) {
		if (viewer.getSynchronizer() == this)
			return;
		final ImageViewer referenceViewer = viewers.keySet().iterator().next();

		final List<ImageViewer> otherViewers = new ArrayList<>(viewer.getSynchronizer().viewers.keySet());
		for (final ImageViewer otherViewer : otherViewers) {
			otherViewer.getSynchronizer().remove(otherViewer);
			otherViewer.setSynchronizer(this);
			viewers.put(otherViewer, null);
			otherViewer.setStatusBarVisible(referenceViewer.isStatusBarVisible());
			otherViewer.setResizeStrategy(referenceViewer.getResizeStrategy());
			otherViewer.setZoomFactor(referenceViewer.getZoomFactor());
			otherViewer.setPixelatedZoom(referenceViewer.isPixelatedZoom());
			otherViewer.setInterpolationType(referenceViewer.getInterpolationType());

			updateScroll(otherViewer, referenceViewer);
			otherViewer.getScrollPane().getHorizontalScrollBar().getModel().addChangeListener(scrollChangeListener);
			otherViewer.getScrollPane().getVerticalScrollBar().getModel().addChangeListener(scrollChangeListener);
		}
	}

	/**
	 * <p>remove.</p>
	 *
	 * @param viewer a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public void remove(final ImageViewer viewer) {
		viewers.remove(viewer);
		viewer.getScrollPane().getHorizontalScrollBar().getModel().removeChangeListener(scrollChangeListener);
		viewer.getScrollPane().getVerticalScrollBar().getModel().removeChangeListener(scrollChangeListener);
	}

	/**
	 * <p>resizeStrategyChangedCanIRescroll.</p>
	 *
	 * @param source a {@link org.loboevolution.img.ImageViewer} object.
	 * @return a boolean.
	 */
	public boolean resizeStrategyChangedCanIRescroll(final ImageViewer source) {
		if (leader != null) {
			// leader is leading an adjustment operation; wait for it to rescroll, and then
			// adjust everything else
			return false;
		}
		leader = source;
		for (final ImageViewer viewer : viewers.keySet())
			viewer.setResizeStrategy(source.getResizeStrategy());
		return true;
	}

	/**
	 * <p>zoomFactorChangedCanIRescroll.</p>
	 *
	 * @param source a {@link org.loboevolution.img.ImageViewer} object.
	 * @return a boolean.
	 */
	public boolean zoomFactorChangedCanIRescroll(final ImageViewer source) {
		if (leader != null) {
			// leader is leading an adjustment operation; wait for it to rescroll, and then
			// adjust everything else
			return false;
		}
		leader = source;
		for (final ImageViewer viewer : viewers.keySet())
			viewer.setZoomFactor(source.getZoomFactor());
		return true;
	}

	/**
	 * <p>doneRescrolling.</p>
	 *
	 * @param source a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public void doneRescrolling(final ImageViewer source) {
		if (!Objects.equals(leader, source))
			throw new AssertionError();
		for (final Map.Entry<ImageViewer, Void> entry : viewers.entrySet()) {
			final ImageViewer otherViewer = entry.getKey();
			if (!Objects.equals(otherViewer, leader)) {
				((JComponent) otherViewer.getScrollPane().getViewport().getView())
						.scrollRectToVisible(leader.getScrollPane().getViewport().getViewRect());
				updateScroll(otherViewer, leader);
			}
		}
		leader = null;
	}

	/**
	 * <p>interpolationTypeChanged.</p>
	 *
	 * @param source a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public void interpolationTypeChanged(final ImageViewer source) {
		for (final ImageViewer viewer : viewers.keySet())
			viewer.setInterpolationType(source.getInterpolationType());
	}

	/**
	 * <p>statusBarVisibilityChanged.</p>
	 *
	 * @param source a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public void statusBarVisibilityChanged(final ImageViewer source) {
		for (final ImageViewer viewer : viewers.keySet())
			viewer.setStatusBarVisible(source.isStatusBarVisible());
	}

	/**
	 * <p>pixelatedZoomChanged.</p>
	 *
	 * @param source a {@link org.loboevolution.img.ImageViewer} object.
	 */
	public void pixelatedZoomChanged(final ImageViewer source) {
		for (final ImageViewer viewer : viewers.keySet())
			viewer.setPixelatedZoom(source.isPixelatedZoom());
	}
}
