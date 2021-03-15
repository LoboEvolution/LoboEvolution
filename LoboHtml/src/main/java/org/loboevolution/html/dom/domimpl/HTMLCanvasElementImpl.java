/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

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
public class HTMLCanvasElementImpl extends HTMLElementImpl implements HTMLCanvasElement {
	
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
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLCanvasElement]";
	}
}
