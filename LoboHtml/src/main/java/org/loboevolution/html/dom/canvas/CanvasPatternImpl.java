package org.loboevolution.html.dom.canvas;

import org.loboevolution.html.dom.CanvasPattern;
import org.loboevolution.html.dom.HTMLCanvasElement;
import org.loboevolution.html.dom.HTMLImageElement;

public class CanvasPatternImpl implements CanvasPattern {
	
	private HTMLCanvasElement canvas;
	
	private HTMLImageElement image;
	
	private String repetitionType;

	public CanvasPatternImpl(HTMLCanvasElement canvas, String repetitionType) {
		this.canvas = canvas;
		this.repetitionType = repetitionType;
	}

	public CanvasPatternImpl(HTMLImageElement image, String repetitionType) {
		this.image = image;
		this.repetitionType = repetitionType;
	}
}
