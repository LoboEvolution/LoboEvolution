/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.domimpl;

import java.awt.Image;
import java.util.ArrayList;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.ImageEvent;
import org.lobobrowser.html.dombl.ImageListener;
import org.lobobrowser.html.dombl.UINode;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.renderstate.ImageRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLImageElement;
import org.mozilla.javascript.Function;

/**
 * The Class HTMLImageElementImpl.
 */
public class HTMLImageElementImpl extends HTMLAbstractUIElement implements HTMLImageElement {

	/** The image. */
	private Image image = null;

	/** The image src. */
	private String imageSrc;

	/** The onload. */
	private Function onload;

	/**
	 * Instantiates a new HTML image element impl.
	 */
	public HTMLImageElementImpl() {
		super(HtmlProperties.IMG);
	}

	/**
	 * Instantiates a new HTML image element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLImageElementImpl(String name) {
		super(name);
	}

	@Override
	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	@Override
	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	@Override
	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	@Override
	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	@Override
	public String getAlt() {
		return this.getAttribute(HtmlAttributeProperties.ALT);
	}

	@Override
	public void setAlt(String alt) {
		this.setAttribute(HtmlAttributeProperties.ALT, alt);
	}

	@Override
	public String getBorder() {
		return this.getAttribute(HtmlAttributeProperties.BORDER);
	}

	@Override
	public void setBorder(String border) {
		this.setAttribute(HtmlAttributeProperties.BORDER, border);
	}

	@Override
	public int getHeight() {
		String height = this.getAttribute(HtmlAttributeProperties.HEIGHT);
		UINode r = this.uiNode;

		if ((height != null) && (height.length() > 0)) {
			return HtmlValues.getPixelSize(height, null, 1); 
		}

		return r == null ? 0 : r.getBounds().height;
	}

	@Override
	public void setHeight(int height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, String.valueOf(height));
	}

	@Override
	public int getHspace() {
		return this.getAttributeAsInt(HtmlAttributeProperties.HSPACE, 0);
	}

	@Override
	public void setHspace(int hspace) {
		this.setAttribute(HtmlAttributeProperties.HSPACE, String.valueOf(hspace));
	}

	@Override
	public boolean getIsMap() {
		return this.getAttributeAsBoolean(HtmlAttributeProperties.ISMAP);
	}

	@Override
	public void setIsMap(boolean isMap) {
		this.setAttribute(HtmlAttributeProperties.ISMAP, isMap ? HtmlAttributeProperties.ISMAP : null);
	}

	@Override
	public String getLongDesc() {
		return this.getAttribute(HtmlAttributeProperties.LONGDESC);
	}

	@Override
	public void setLongDesc(String longDesc) {
		this.setAttribute(HtmlAttributeProperties.LONGDESC, longDesc);
	}

	@Override
	public String getSrc() {
		return this.getAttribute(HtmlAttributeProperties.SRC);
	}

	/**
	 * Sets the image URI and starts to load the image. Note that an
	 * HtmlRendererContext should be available to the HTML document for images
	 * to be loaded.
	 *
	 * @param src
	 *            the new src
	 */
	@Override
	public void setSrc(String src) {
		this.setAttribute(HtmlAttributeProperties.SRC, src);
	}

	@Override
	public String getUseMap() {
		return this.getAttribute(HtmlAttributeProperties.USEMAP);
	}

	@Override
	public void setUseMap(String useMap) {
		this.setAttribute(HtmlAttributeProperties.USEMAP, useMap);
	}

	@Override
	public int getVspace() {
		return this.getAttributeAsInt(HtmlAttributeProperties.VSPACE, 0);
	}

	@Override
	public void setVspace(int vspace) {
		this.setAttribute(HtmlAttributeProperties.VSPACE, String.valueOf(vspace));
	}

	@Override
	public int getWidth() {

		String width = this.getAttribute(HtmlAttributeProperties.WIDTH);
		UINode r = this.uiNode;

		if ((width != null) && (width.length() > 0)) {
			return HtmlValues.getPixelSize(width, null, 1); 
		}

		return r == null ? 0 : r.getBounds().width;
	}

	@Override
	public void setWidth(int width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, String.valueOf(width));
	}

	@Override
	protected void assignAttributeField(String normalName, String value) {
		super.assignAttributeField(normalName, value);
		if (HtmlAttributeProperties.SRC.equals(normalName)) {
			this.loadImage(value);
		}
	}

	@Override
	public Function getOnload() {
		return this.getEventFunction(this.onload, "onload");
	}

	@Override
	public void setOnload(Function onload) {
		this.onload = onload;
	}

	/**
	 * Load image.
	 *
	 * @param src
	 *            the src
	 */
	private void loadImage(String src) {
		HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document != null) {
			synchronized (this.listeners) {
				this.imageSrc = src;
				this.image = null;
			}
			if (src != null) {
				document.loadImage(src, new LocalImageListener(src));
			}
		}
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public final Image getImage() {
		synchronized (this.listeners) {
			return this.image;
		}
	}

	/** The listeners. */
	private final ArrayList<ImageListener> listeners = new ArrayList<ImageListener>(1);

	/**
	 * Adds a listener of image loading events. The listener gets called right
	 * away if there's already an image.
	 *
	 * @param listener
	 *            the listener
	 */
	public void addImageListener(ImageListener listener) {
		ArrayList<ImageListener> l = this.listeners;
		Image currentImage;
		synchronized (l) {
			currentImage = this.image;
			l.add(listener);
		}
		if (currentImage != null) {
			// Call listener right away if there's already an
			// image; holding no locks.
			listener.imageLoaded(new ImageEvent(this, currentImage));
			// Should not call onload handler here. That's taken
			// care of otherwise.
		}
	}

	/**
	 * Removes the image listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void removeImageListener(ImageListener listener) {
		ArrayList<ImageListener> l = this.listeners;
		synchronized (l) {
			l.remove(l);
		}
	}

	/**
	 * Dispatch event.
	 *
	 * @param expectedImgSrc
	 *            the expected img src
	 * @param event
	 *            the event
	 */
	private void dispatchEvent(String expectedImgSrc, ImageEvent event) {
		ArrayList<ImageListener> l = this.listeners;
		ImageListener[] listenerArray;
		synchronized (l) {
			if (!expectedImgSrc.equals(this.imageSrc)) {
				return;
			}
			this.image = event.image;
			// Get array of listeners while holding lock.
			listenerArray = l.toArray(ImageListener.EMPTY_ARRAY);
		}
		int llength = listenerArray.length;
		for (int i = 0; i < llength; i++) {
			// Inform listener, holding no lock.
			listenerArray[i].imageLoaded(event);
		}
		Function onload = this.getOnload();
		if (onload != null) {
			// TODO: onload event object?
			Executor.executeFunction(HTMLImageElementImpl.this, onload, null);
		}
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ImageRenderState(prevRenderState, this);
	}

	@Override
	public int getNaturalWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNaturalHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * The listener interface for receiving localImage events. The class that is
	 * interested in processing a localImage event implements this interface,
	 * and the object created with that class is registered with a component
	 * using the component's <code>addLocalImageListener</code> method. When the
	 * localImage event occurs, that object's appropriate method is invoked.
	 *
	 * @see LocalImageEvent
	 */
	private class LocalImageListener implements ImageListener {

		/** The expected img src. */
		private final String expectedImgSrc;

		/**
		 * Instantiates a new local image listener.
		 *
		 * @param imgSrc
		 *            the img src
		 */
		public LocalImageListener(String imgSrc) {
			this.expectedImgSrc = imgSrc;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.html.dombl.ImageListener#imageLoaded(org.lobobrowser.
		 * html .dombl.ImageEvent)
		 */
		@Override
		public void imageLoaded(ImageEvent event) {
			dispatchEvent(this.expectedImgSrc, event);
		}
	}

	@Override
	public String getCrossOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCrossOrigin(String crossOrigin) {
		// TODO Auto-generated method stub

	}
}
