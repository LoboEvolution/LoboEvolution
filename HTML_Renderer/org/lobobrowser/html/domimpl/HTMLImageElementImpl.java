/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 19, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.ImageEvent;
import org.lobobrowser.html.dombl.ImageListener;
import org.lobobrowser.html.dombl.UINode;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.renderstate.ImageRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.w3c.HTMLImageElement;
import org.mozilla.javascript.Function;

public class HTMLImageElementImpl extends HTMLAbstractUIElement implements
		HTMLImageElement {
	public HTMLImageElementImpl() {
		super(HtmlProperties.IMG);
	}

	public HTMLImageElementImpl(String name) {
		super(name);
	}

	public String getName() {
		return this.getAttribute(HtmlAttributeProperties.NAME);
	}

	public void setName(String name) {
		this.setAttribute(HtmlAttributeProperties.NAME, name);
	}

	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	public String getAlt() {
		return this.getAttribute(HtmlAttributeProperties.ALT);
	}

	public void setAlt(String alt) {
		this.setAttribute(HtmlAttributeProperties.ALT, alt);
	}

	public String getBorder() {
		return this.getAttribute(HtmlAttributeProperties.BORDER);
	}

	public void setBorder(String border) {
		this.setAttribute(HtmlAttributeProperties.BORDER, border);
	}

	public int getHeight() {
		UINode r = this.uiNode;
		return r == null ? 0 : r.getBounds().height;
	}

	public void setHeight(int height) {
		this.setAttribute(HtmlAttributeProperties.HEIGHT, String.valueOf(height));
	}

	public int getHspace() {
		return this.getAttributeAsInt(HtmlAttributeProperties.HSPACE, 0);
	}

	public void setHspace(int hspace) {
		this.setAttribute(HtmlAttributeProperties.HSPACE, String.valueOf(hspace));
	}

	public boolean getIsMap() {
		return this.getAttributeAsBoolean(HtmlAttributeProperties.ISMAP);
	}

	public void setIsMap(boolean isMap) {
		this.setAttribute(HtmlAttributeProperties.ISMAP, isMap ? HtmlAttributeProperties.ISMAP : null);
	}

	public String getLongDesc() {
		return this.getAttribute(HtmlAttributeProperties.LONGDESC);
	}

	public void setLongDesc(String longDesc) {
		this.setAttribute(HtmlAttributeProperties.LONGDESC, longDesc);
	}

	public String getSrc() {
		return this.getAttribute(HtmlAttributeProperties.SRC);
	}

	/**
	 * Sets the image URI and starts to load the image. Note that an
	 * HtmlRendererContext should be available to the HTML document for images
	 * to be loaded.
	 */
	public void setSrc(String src) {
		this.setAttribute(HtmlAttributeProperties.SRC, src);
	}

	public String getUseMap() {
		return this.getAttribute(HtmlAttributeProperties.USEMAP);
	}

	public void setUseMap(String useMap) {
		this.setAttribute(HtmlAttributeProperties.USEMAP, useMap);
	}

	public int getVspace() {
		return this.getAttributeAsInt(HtmlAttributeProperties.VSPACE, 0);
	}

	public void setVspace(int vspace) {
		this.setAttribute(HtmlAttributeProperties.VSPACE, String.valueOf(vspace));
	}

	public int getWidth() {
		UINode r = this.uiNode;
		return r == null ? 0 : r.getBounds().width;
	}

	public void setWidth(int width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, String.valueOf(width));
	}

	protected void assignAttributeField(String normalName, String value) {
		super.assignAttributeField(normalName, value);
		if (HtmlAttributeProperties.SRC.equals(normalName)) {
			this.loadImage(value);
		}
	}

	private Function onload;

	public Function getOnload() {
		return this.getEventFunction(this.onload, "onload");
	}

	public void setOnload(Function onload) {
		this.onload = onload;
	}

	private java.awt.Image image = null;
	private String imageSrc;

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

	public final java.awt.Image getImage() {
		synchronized (this.listeners) {
			return this.image;
		}
	}

	private final ArrayList<ImageListener> listeners = new ArrayList<ImageListener>(
			1);

	/**
	 * Adds a listener of image loading events. The listener gets called right
	 * away if there's already an image.
	 * 
	 * @param listener
	 */
	public void addImageListener(ImageListener listener) {
		ArrayList<ImageListener> l = this.listeners;
		java.awt.Image currentImage;
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

	public void removeImageListener(ImageListener listener) {
		ArrayList<ImageListener> l = this.listeners;
		synchronized (l) {
			l.remove(l);
		}
	}

	private void dispatchEvent(String expectedImgSrc, ImageEvent event) {
		ArrayList<ImageListener> l = this.listeners;
		ImageListener[] listenerArray;
		synchronized (l) {
			if (!expectedImgSrc.equals(this.imageSrc)) {
				return;
			}
			this.image = event.image;
			// Get array of listeners while holding lock.
			listenerArray = (ImageListener[]) l
					.toArray(ImageListener.EMPTY_ARRAY);
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

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ImageRenderState(prevRenderState, this);
	}

	private class LocalImageListener implements ImageListener {
		private final String expectedImgSrc;

		public LocalImageListener(String imgSrc) {
			this.expectedImgSrc = imgSrc;
		}

		public void imageLoaded(ImageEvent event) {
			dispatchEvent(this.expectedImgSrc, event);
		}
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
}
