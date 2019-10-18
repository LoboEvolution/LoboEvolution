package org.loboevolution.html.js;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.loboevolution.js.AbstractScriptableDelegate;

public class Screen extends AbstractScriptableDelegate {
	private final GraphicsDevice graphicsDevice;
	private final GraphicsEnvironment graphicsEnvironment;

	/**
	 * @param context
	 */
	Screen() {
		super();
		if (GraphicsEnvironment.isHeadless()) {
			this.graphicsEnvironment = null;
			this.graphicsDevice = null;
		} else {
			this.graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			this.graphicsDevice = this.graphicsEnvironment.getDefaultScreenDevice();
		}
	}

	public int getAvailHeight() {
		final GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		return ge.getMaximumWindowBounds().height;
	}

	public int getAvailWidth() {
		final GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		return ge.getMaximumWindowBounds().width;
	}

	public int getColorDepth() {
		final GraphicsDevice gd = this.graphicsDevice;
		if (gd == null) {
			return 0;
		}
		return gd.getDisplayMode().getBitDepth();
	}

	public int getHeight() {
		final GraphicsDevice gd = this.graphicsDevice;
		return gd == null ? 0 : gd.getDisplayMode().getHeight();
	}

	public int getPixelDepth() {
		return getColorDepth();
	}

	public int getWidth() {
		final GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		final GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDisplayMode().getWidth();
	}
}
