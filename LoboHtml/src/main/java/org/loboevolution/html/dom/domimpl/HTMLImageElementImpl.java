/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
package org.loboevolution.html.dom.domimpl;

import java.awt.Image;
import java.util.ArrayList;

import org.loboevolution.html.dom.HTMLImageElement;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.renderstate.ImageRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.mozilla.javascript.Function;

public class HTMLImageElementImpl extends HTMLAbstractUIElement implements HTMLImageElement {
	private class LocalImageListener implements ImageListener {
		private final String expectedImgSrc;

		public LocalImageListener(String imgSrc) {
			this.expectedImgSrc = imgSrc;
		}

		@Override
		public void imageLoaded(ImageEvent event) {
			dispatchEvent(this.expectedImgSrc, event);
		}
	}

	private Image image = null;

	private String imageSrc;

	private final ArrayList<ImageListener> listeners = new ArrayList<ImageListener>(1);

	private Function onload;

	public HTMLImageElementImpl() {
		super("IMG");
	}

	public HTMLImageElementImpl(String name) {
		super(name);
	}

	/**
	 * Adds a listener of image loading events. The listener gets called right away
	 * if there's already an image.
	 * 
	 * @param listener
	 */
	public void addImageListener(ImageListener listener) {
		final ArrayList<ImageListener> l = this.listeners;
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

	@Override
	protected void assignAttributeField(String normalName, String value) {
		super.assignAttributeField(normalName, value);
		if ("src".equals(normalName)) {
			loadImage(value);
		}
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ImageRenderState(prevRenderState, this);
	}

	private void dispatchEvent(String expectedImgSrc, ImageEvent event) {
		final ArrayList<ImageListener> l = this.listeners;
		ImageListener[] listenerArray;
		synchronized (l) {
			if (!expectedImgSrc.equals(this.imageSrc)) {
				return;
			}
			this.image = event.image;
			// Get array of listeners while holding lock.
			listenerArray = (ImageListener[]) l.toArray(ImageListener.EMPTY_ARRAY);
		}
		final int llength = listenerArray.length;
		for (int i = 0; i < llength; i++) {
			// Inform listener, holding no lock.
			listenerArray[i].imageLoaded(event);
		}
		final Function onload = getOnload();
		if (onload != null) {
			// TODO: onload event object?
			Executor.executeFunction(HTMLImageElementImpl.this, onload, null);
		}
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public String getAlt() {
		return getAttribute("alt");
	}

	@Override
	public String getBorder() {
		return getAttribute("border");
	}

	@Override
	public int getHeight() {
		final UINode r = this.uiNode;
		return r == null ? 0 : r.getBounds().height;
	}

	@Override
	public int getHspace() {
		return getAttributeAsInt("hspace", 0);
	}

	public final java.awt.Image getImage() {
		synchronized (this.listeners) {
			return this.image;
		}
	}

	@Override
	public boolean getIsMap() {
		return getAttributeAsBoolean("isMap");
	}

	@Override
	public String getLongDesc() {
		return getAttribute("longDesc");
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}

	public Function getOnload() {
		return getEventFunction(this.onload, "onload");
	}

	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	@Override
	public String getUseMap() {
		return getAttribute("useMap");
	}

	@Override
	public int getVspace() {
		return getAttributeAsInt("vspace", 0);
	}

	@Override
	public int getWidth() {
		final UINode r = this.uiNode;
		return r == null ? 0 : r.getBounds().width;
	}

	private void loadImage(String src) {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
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

	public void removeImageListener(ImageListener listener) {
		final ArrayList<ImageListener> l = this.listeners;
		synchronized (l) {
			l.remove(l);
		}
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	@Override
	public void setBorder(String border) {
		setAttribute("border", border);
	}

	@Override
	public void setHeight(int height) {
		setAttribute("height", String.valueOf(height));
	}

	@Override
	public void setHspace(int hspace) {
		setAttribute("hspace", String.valueOf("hspace"));
	}

	@Override
	public void setIsMap(boolean isMap) {
		setAttribute("isMap", isMap ? "isMap" : null);
	}

	@Override
	public void setLongDesc(String longDesc) {
		setAttribute("longDesc", longDesc);
	}

	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	public void setOnload(Function onload) {
		this.onload = onload;
	}

	/**
	 * Sets the image URI and starts to load the image. Note that an
	 * HtmlRendererContext should be available to the HTML document for images to be
	 * loaded.
	 */
	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}

	@Override
	public void setUseMap(String useMap) {
		setAttribute("useMap", useMap);
	}

	@Override
	public void setVspace(int vspace) {
		setAttribute("vspace", String.valueOf(vspace));
	}

	@Override
	public void setWidth(int width) {
		setAttribute("width", String.valueOf(width));
	}
}
