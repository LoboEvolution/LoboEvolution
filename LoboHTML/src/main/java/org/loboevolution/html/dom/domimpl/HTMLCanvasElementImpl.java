/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.Getter;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.canvas.CanvasRenderingContext2D;
import org.loboevolution.html.dom.FileCallback;
import org.loboevolution.html.dom.HTMLCanvasElement;
import org.loboevolution.html.dom.canvas.CanvasRenderingImpl;
import org.loboevolution.html.style.HtmlValues;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;


/**
 * The Class HTMLCanvasElementImpl.
 */
@Getter
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
		final String widthText = this.getAttribute("width");
		if (Strings.isBlank(widthText)) {
			return getClientWidth();
		}
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
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
		final String heightText = this.getAttribute("height");
		if (Strings.isBlank(heightText)) {
			return getClientHeight();
		}
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return HtmlValues.getPixelSize(heightText, null, doc.getDefaultView(), 1);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(final int height) {
		this.setAttribute("height", String.valueOf(height));
	}

	/** {@inheritDoc} */
	@Override
	public String toDataURL() {
		return "data:image/png;base64," + Base64.getEncoder().encodeToString(toBytesCompressed(image));
	}

	/** {@inheritDoc} */
	@Override
	public String toDataURL(final String type, final Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void toBlob(final FileCallback callback) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void toBlob(final FileCallback callback, final String type, final Object... args) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public CanvasRenderingContext2D getContext(final String contextId) {
		final CanvasRenderingImpl canvas = new CanvasRenderingImpl(this);
		image = canvas.getImage();
		return canvas;
	}

	@Override
	public int getClientHeight() {
		final int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 150 : clientHeight;
	}

	@Override
	public Integer getClientWidth() {
		final int clientWidth = super.getClientWidth();
		return clientWidth == 0 ? 300 : clientWidth;
	}

	@Override
	public Integer getOffsetWidth() {
		return getClientWidth();
	}

	private byte[] toBytesCompressed(BufferedImage image) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, "png", out);
			return out.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLCanvasElement]";
	}
}
