package org.loboevolution.html.dom.domimpl;

import java.awt.image.BufferedImage;

import org.loboevolution.html.dom.CanvasRenderingContext2D;
import org.loboevolution.html.dom.FileCallback;
import org.loboevolution.html.dom.HTMLCanvasElement;
import org.loboevolution.html.dom.canvas.CanvasRenderingImpl;
import org.loboevolution.html.style.HtmlValues;


/**
 * The Class HTMLCanvasElementImpl.
 */
public class HTMLCanvasElementImpl extends HTMLAbstractUIElement implements HTMLCanvasElement {
	
	private BufferedImage image;

	public HTMLCanvasElementImpl(String name) {
		super(name);
	}

	@Override
	public int getWidth() {
		String widthText = this.getAttribute("width");
		return HtmlValues.getPixelSize(widthText, null, 1);
	}

	@Override
	public void setWidth(int width) {
		this.setAttribute("width", String.valueOf(width));
	}

	@Override
	public int getHeight() {
		String heightText = this.getAttribute("height");
		return HtmlValues.getPixelSize(heightText, null, 1);
	}

	@Override
	public void setHeight(int height) {
		this.setAttribute("height", String.valueOf(height));
	}

	@Override
	public String toDataURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toDataURL(String type, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void toBlob(FileCallback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBlob(FileCallback callback, String type, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CanvasRenderingContext2D getContext(String contextId) {
		CanvasRenderingImpl canvas = new CanvasRenderingImpl(this);
		image = canvas.getImage();
		return canvas;
	}
	
	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
}