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
 * Created on Jan 15, 2006
 */
package org.loboevolution.html.dom.domimpl;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import org.loboevolution.html.FormInput;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.js.Executor;
import org.mozilla.javascript.Function;
import org.w3c.dom.Node;

public class HTMLBaseInputElement extends HTMLAbstractUIElement {

	protected String deferredValue;
	private Image image = null;

	private final ArrayList<ImageListener> imageListeners = new ArrayList<ImageListener>(1);

	private String imageSrc;

	protected InputContext inputContext;

	private Function onload;

	public HTMLBaseInputElement(String name) {
		super(name);
	}

	/**
	 * Adds a listener of image loading events. The listener gets called right away
	 * if there's already an image.
	 * 
	 * @param listener
	 */
	public void addImageListener(ImageListener listener) {
		final ArrayList<ImageListener> l = this.imageListeners;
		java.awt.Image currentImage;
		synchronized (l) {
			currentImage = this.image;
			l.add(listener);
		}
		if (currentImage != null) {
			listener.imageLoaded(new ImageEvent(this, currentImage));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.domimpl.HTMLElementImpl#assignAttributeField(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	protected void assignAttributeField(String normalName, String value) {
		if ("value".equals(normalName)) {
			final InputContext ic = this.inputContext;
			if (ic != null) {
				ic.setValue(value);
			}
		} else if ("src".equals(normalName)) {
			loadImage(value);
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	@Override
	public void blur() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.blur();
		}
	}

	private void dispatchEvent(String expectedImgSrc, ImageEvent event) {
		final ArrayList<ImageListener> l = this.imageListeners;
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
			Executor.executeFunction(HTMLBaseInputElement.this, onload, null);
		}
	}

	@Override
	public void focus() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.focus();
		}
	}
	

	public String getType() {
		final String type = getAttribute("type");
		return type == null ? null : type.toLowerCase();
	}

	public String getAccept() {
		return getAttribute("accept");
	}

	public String getAccessKey() {
		return getAttribute("accessKey");
	}

	public String getAlign() {
		return getAttribute("align");
	}

	public String getAlt() {
		return getAttribute("alit");
	}

	public String getDefaultValue() {
		return getAttribute("defaultValue");
	}

	public String getPlaceholder() {
		return this.getAttribute("placeholder");
	}

	public void setPlaceholder(String placeholder) {
		this.setAttribute("placeholder", placeholder);
	}

	public boolean getDisabled() {
		final InputContext ic = this.inputContext;
		return ic == null ? false : ic.getDisabled();
	}

	public boolean getChecked() {
		final InputContext ic = this.inputContext;
		return ic == null ? false : ic.getChecked();
	}
	
	public boolean getAutocomplete() {
		String autocomplete = this.getAttribute("autocomplete");
		return "on".equalsIgnoreCase(autocomplete);
	}

	protected File getFileValue() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			return ic.getFileValue();
		} else {
			return null;
		}
	}

	public HTMLFormElement getForm() {
		Node parent = getParentNode();
		while (parent != null && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	public final Image getImage() {
		synchronized (this.imageListeners) {
			return this.image;
		}
	}

	public String getName() {
		return getAttribute("name");
	}

	public Function getOnload() {
		return getEventFunction(this.onload, "onload");
	}

	public boolean getReadOnly() {
		final InputContext ic = this.inputContext;
		return ic == null ? false : ic.getReadOnly();
	}

	public int getTabIndex() {
		final InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getTabIndex();
	}

	public String getValue() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			// Note: Per HTML Spec, setValue does not set attribute.
			return ic.getValue();
		} else {
			final String dv = this.deferredValue;
			if (dv != null) {
				return dv;
			} else {
				final String val = getAttribute("value");
				return val == null ? "" : val;
			}
		}
	}

	private void loadImage(String src) {
		final HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document != null) {
			synchronized (this.imageListeners) {
				this.imageSrc = src;
				this.image = null;
			}
			if (src != null) {
				document.loadImage(src, new LocalImageListener(src));
			}
		}
	}

	public void removeImageListener(ImageListener listener) {
		final ArrayList<ImageListener> l = this.imageListeners;
		synchronized (l) {
			l.remove(l);
		}
	}

	public void resetForm() {
		final HTMLFormElement form = getForm();
		if (form != null) {
			form.reset();
		}
	}

	void resetInput() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.resetInput();
		}
	}

	public void select() {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.select();
		}
	}

	public void setAccept(String accept) {
		setAttribute("accept", accept);
	}

	public void setAccessKey(String accessKey) {
		setAttribute("accessKey", accessKey);
	}

	public void setAlign(String align) {
		setAttribute("align", align);
	}

	public void setAlt(String alt) {
		setAttribute("alt", alt);
	}

	public void setDefaultValue(String defaultValue) {
		setAttribute("defaultValue", defaultValue);
	}

	public void setDisabled(boolean disabled) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setDisabled(disabled);
		}
	}

	public void setInputContext(InputContext ic) {
		String dv = null;
		synchronized (this) {
			this.inputContext = ic;
			if (ic != null) {
				dv = this.deferredValue;
			}
		}
		if (dv != null) {
			ic.setValue(dv);
		}
	}

	public void setName(String name) {
		setAttribute("name", name);
	}

	public void setOnload(Function onload) {
		this.onload = onload;
	}

	public void setReadOnly(boolean readOnly) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setReadOnly(readOnly);
		}
	}

	public void setTabIndex(int tabIndex) {
		final InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setTabIndex(tabIndex);
		}
	}

	public void setValue(String value) {
		InputContext ic = null;
		synchronized (this) {
			ic = this.inputContext;
			if (ic == null) {
				this.deferredValue = value;
			}
		}
		if (ic != null) {
			ic.setValue(value);
		}
	}

	public void submitForm(FormInput[] extraFormInputs) {
		final HTMLFormElementImpl form = (HTMLFormElementImpl) getForm();
		if (form != null) {
			form.submit(extraFormInputs);
		}
	}

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

}
