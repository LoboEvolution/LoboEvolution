package org.lobobrowser.html.js;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.lobobrowser.js.AbstractScriptableDelegate;


/**
 * The Class Screen.
 */
public class Screen extends AbstractScriptableDelegate {
	
	/** The graphics environment. */
	private final GraphicsEnvironment graphicsEnvironment;
	
	/** The graphics device. */
	private final GraphicsDevice graphicsDevice;

	/**
	 * Instantiates a new screen.
	 */
	Screen() {
		super();
		if (GraphicsEnvironment.isHeadless()) {
			this.graphicsEnvironment = null;
			this.graphicsDevice = null;
		} else {
			this.graphicsEnvironment = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			this.graphicsDevice = this.graphicsEnvironment
					.getDefaultScreenDevice();
		}
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		GraphicsDevice gd = this.graphicsDevice;
		return gd == null ? 0 : gd.getDisplayMode().getHeight();
	}

	/**
	 * Gets the pixel depth.
	 *
	 * @return the pixel depth
	 */
	public int getPixelDepth() {
		return this.getColorDepth();
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		return gd.getDisplayMode().getWidth();
	}

	/**
	 * Gets the avail height.
	 *
	 * @return the avail height
	 */
	public int getAvailHeight() {
		GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		return ge.getMaximumWindowBounds().height;
	}

	/**
	 * Gets the avail width.
	 *
	 * @return the avail width
	 */
	public int getAvailWidth() {
		GraphicsEnvironment ge = this.graphicsEnvironment;
		if (ge == null) {
			return 0;
		}
		return ge.getMaximumWindowBounds().width;
	}

	/**
	 * Gets the color depth.
	 *
	 * @return the color depth
	 */
	public int getColorDepth() {
		GraphicsDevice gd = this.graphicsDevice;
		if (gd == null) {
			return 0;
		}
		return gd.getDisplayMode().getBitDepth();
	}
}
