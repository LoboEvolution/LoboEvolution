/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.CanvasRenderingContext2D;
import org.loboevolution.html.dom.FileCallback;
import org.loboevolution.html.dom.HTMLCanvasElement;
import org.loboevolution.html.dom.canvas.CanvasRenderingImpl;
import org.loboevolution.html.style.HtmlValues;

import java.awt.image.BufferedImage;


/**
 * The Class HTMLCanvasElementImpl.
 */
public class HTMLCanvasElementImpl extends HTMLElementImpl implements HTMLCanvasElement {
	
	private BufferedImage image;

	/**
	 * <p>Constructor for HTMLCanvasElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLCanvasElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public int getWidth() {
		String widthText = this.getAttribute("width");
		if (Strings.isBlank(widthText)) {
			return getClientWidth();
		}
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return HtmlValues.getPixelSize(widthText, null, doc.getDefaultView(), 1);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(final int width) {
		this.setAttribute("width", String.valueOf(width));
	}

	/** {@inheritDoc} */
	@Override
	public int getHeight() {
		String heightText = this.getAttribute("height");
		if (Strings.isBlank(heightText)) {
			return getClientHeight();
		}
		HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		return HtmlValues.getPixelSize(heightText, null, doc.getDefaultView(), 1);
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

	@Override
	public int getClientHeight() {
		int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 150 : clientHeight;
	}

	@Override
	public Integer getClientWidth() {
		int clientWidth = super.getClientWidth();
		return clientWidth == 0 ? 300 : clientWidth;
	}

	@Override
	public Integer getOffsetWidth() {
		return getClientWidth();
	}

	/**
	 * <p>Getter for the field image.</p>
	 *
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLCanvasElement]";
	}
}
