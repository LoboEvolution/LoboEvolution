package org.loboevolution.html.dom.canvas;

import org.loboevolution.html.dom.CanvasPattern;
import org.loboevolution.html.dom.HTMLCanvasElement;
import org.loboevolution.html.dom.HTMLImageElement;

/**
 * <p>CanvasPatternImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CanvasPatternImpl implements CanvasPattern {
	
	private HTMLCanvasElement canvas;
	
	private HTMLImageElement image;
	
	private final String repetitionType;

	/**
	 * <p>Constructor for CanvasPatternImpl.</p>
	 *
	 * @param canvas a {@link org.loboevolution.html.dom.HTMLCanvasElement} object.
	 * @param repetitionType a {@link java.lang.String} object.
	 */
	public CanvasPatternImpl(HTMLCanvasElement canvas, String repetitionType) {
		this.canvas = canvas;
		this.repetitionType = repetitionType;
	}

	/**
	 * <p>Constructor for CanvasPatternImpl.</p>
	 *
	 * @param image a {@link org.loboevolution.html.dom.HTMLImageElement} object.
	 * @param repetitionType a {@link java.lang.String} object.
	 */
	public CanvasPatternImpl(HTMLImageElement image, String repetitionType) {
		this.image = image;
		this.repetitionType = repetitionType;
	}
}
