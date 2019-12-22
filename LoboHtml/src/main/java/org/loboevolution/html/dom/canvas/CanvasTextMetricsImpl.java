package org.loboevolution.html.dom.canvas;

import org.loboevolution.html.dom.TextMetrics;

public class CanvasTextMetricsImpl implements TextMetrics {
	
	private double width;
	
	private double height;
	
	public CanvasTextMetricsImpl(double width, double height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}
}