package org.loboevolution.html.dom.canvas;

import org.loboevolution.html.dom.TextMetrics;

/**
 * <p>CanvasTextMetricsImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CanvasTextMetricsImpl implements TextMetrics {
	
	private final double width;
	
	private final double height;
	
	/**
	 * <p>Constructor for CanvasTextMetricsImpl.</p>
	 *
	 * @param width a double.
	 * @param height a double.
	 */
	public CanvasTextMetricsImpl(double width, double height) {
		this.width = width;
		this.height = height;
	}

	/** {@inheritDoc} */
	@Override
	public double getWidth() {
		return width;
	}

	/** {@inheritDoc} */
	@Override
	public double getHeight() {
		return height;
	}
}
