package org.lobo.img;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import javax.swing.JViewport;

/**
 * A custom layout manager for the image viewer scroll pane viewport. It is used
 * to set the size of the viewport component with respect to the resize
 * strategy.
 * 
 * @author Kaz Csaba
 */
class CustomViewportLayout implements LayoutManager {

	private final ImageViewer viewer;

	public CustomViewportLayout(ImageViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		BufferedImage image = viewer.getImage();
		if (image == null)
			return new Dimension();
		else
			return new Dimension(image.getWidth(), image.getHeight());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return new Dimension(4, 4);
	}

	@Override
	public void layoutContainer(Container parent) {
		JViewport vp = (JViewport) parent;
		Component view = vp.getView();

		if (view == null) {
			return;
		}

		Dimension vpSize = vp.getSize();
		Dimension viewSize = new Dimension(view.getPreferredSize());

		if (viewer.getResizeStrategy() == ResizeStrategy.SHRINK_TO_FIT
				|| viewer.getResizeStrategy() == ResizeStrategy.RESIZE_TO_FIT) {
			viewSize.width = vpSize.width;
			viewSize.height = vpSize.height;
		} else {
			viewSize.width = Math.max(viewSize.width, vpSize.width);
			viewSize.height = Math.max(viewSize.height, vpSize.height);
		}

		// vp.setViewPosition(new Point());
		vp.setViewSize(viewSize);
	}
}
