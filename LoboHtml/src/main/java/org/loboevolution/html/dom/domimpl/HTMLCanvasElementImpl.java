package org.loboevolution.html.dom.domimpl;

import java.awt.image.BufferedImage;

import org.loboevolution.html.dom.CanvasRenderingContext2D;
import org.loboevolution.html.dom.FileCallback;
import org.loboevolution.html.dom.HTMLCanvasElement;
import org.loboevolution.html.dom.canvas.CanvasRenderingImpl;
import org.loboevolution.html.style.HtmlValues;


/**
 * The Class HTMLCanvasElementImpl.
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLCanvasElementImpl extends HTMLAbstractUIElement implements HTMLCanvasElement {
	
	private BufferedImage image;

	/**
	 * <p>Constructor for HTMLCanvasElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLCanvasElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		String widthText = this.getAttribute("width");
		return HtmlValues.getPixelSize(widthText, null, 1);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(int width) {
		this.setAttribute("width", String.valueOf(width));
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		String heightText = this.getAttribute("height");
		return HtmlValues.getPixelSize(heightText, null, 1);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(int height) {
		this.setAttribute("height", String.valueOf(height));
	}

	/** {@inheritDoc} */
	@Override
	public String toDataURL() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String toDataURL(String type, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void toBlob(FileCallback callback) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void toBlob(FileCallback callback, String type, Object... args) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public CanvasRenderingContext2D getContext(String contextId) {
		CanvasRenderingImpl canvas = new CanvasRenderingImpl(this);
		image = canvas.getImage();
		return canvas;
	}
	
	/**
	 * <p>Getter for the field image.</p>
	 *
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
}
