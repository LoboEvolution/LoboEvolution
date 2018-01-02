/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * 
 */
package org.loboevolution.html.domimpl;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import org.loboevolution.html.FormInput;
import org.loboevolution.html.dombl.ImageEvent;
import org.loboevolution.html.dombl.ImageListener;
import org.loboevolution.html.dombl.InputContext;
import org.loboevolution.html.js.Executor;
import org.loboevolution.w3c.html.HTMLElement;
import org.loboevolution.w3c.html.HTMLFormElement;
import org.loboevolution.w3c.html.HTMLOptionElement;
import org.loboevolution.w3c.html.ValidityState;
import org.mozilla.javascript.Function;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class HTMLBaseInputElement.
 */
public abstract class HTMLBaseInputElement extends HTMLAbstractUIElement {

	/** The input context. */
	protected InputContext inputContext;

	/** The deferred value. */
	protected String deferredValue;

	/** The deferred checked. */
	protected Boolean deferredChecked;

	/** The deferred readonly. */
	protected Boolean deferredReadonly;

	/** The deferred disabled. */
	protected Boolean deferredDisabled;
	
	/** The onload. */
	private Function onload;
	
	/** The image. */
	private Image image = null;

	/** The image src. */
	private String imageSrc;
	
	/** The image listeners. */
	private final ArrayList<ImageListener> imageListeners = new ArrayList<ImageListener>(1);

	/**
	 * Instantiates a new HTML base input element.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLBaseInputElement(String name) {
		super(name);
	}

	/**
	 * Sets the input context.
	 *
	 * @param ic
	 *            the new input context
	 */
	public void setInputContext(InputContext ic) {
		String dv = null;
		Boolean defDisabled = null;
		Boolean defReadonly = null;
		Boolean defChecked = null;
		synchronized (this) {
			this.inputContext = ic;
			if (ic != null) {
				dv = this.deferredValue;
				defDisabled = this.deferredDisabled;
				defReadonly = this.deferredReadonly;
				defChecked = this.deferredChecked;
			}
		}
		if (dv != null) {
			ic.setValue(dv);
		}
		if (defDisabled != null) {
			ic.setDisabled(defDisabled.booleanValue());
		}
		if (defReadonly != null) {
			ic.setDisabled(defReadonly.booleanValue());
		}
		if (defChecked != null) {
			ic.setDisabled(defChecked.booleanValue());
		}
	}

	/**
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public String getDefaultValue() {
		return this.getAttribute(DEFAULTVALUE);
	}

	/**
	 * Sets the default value.
	 *
	 * @param defaultValue
	 *            the new default value
	 */
	public void setDefaultValue(String defaultValue) {
		this.setAttribute(DEFAULTVALUE, defaultValue);
	}

	/**
	 * Gets the form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm() {
		Node parent = this.getParentNode();
		while (parent != null && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	/**
	 * Submit form.
	 *
	 * @param extraFormInputs
	 *            the extra form inputs
	 */
	public void submitForm(FormInput[] extraFormInputs) {
		HTMLFormElementImpl form = (HTMLFormElementImpl) this.getForm();
		if (form != null) {
			form.submit(extraFormInputs);
		}
	}

	/**
	 * Reset form.
	 */
	public void resetForm() {
		HTMLFormElement form = this.getForm();
		if (form != null) {
			form.reset();
		}
	}

	/**
	 * Gets the accept.
	 *
	 * @return the accept
	 */
	public String getAccept() {
		return this.getAttribute(ACCEPT);
	}

	/**
	 * Sets the accept.
	 *
	 * @param accept
	 *            the new accept
	 */
	public void setAccept(String accept) {
		this.setAttribute(ACCEPT, accept);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMElementImpl#getAccessKey()
	 */

	@Override
	public String getAccessKey() {
		return this.getAttribute(ACCESSKEY);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMElementImpl#setAccessKey(java.lang.
	 * String)
	 */

	@Override
	public void setAccessKey(String accessKey) {
		this.setAttribute(ACCESSKEY, accessKey);
	}

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	public String getAlign() {
		return this.getAttribute(ALIGN);
	}

	/**
	 * Sets the align.
	 *
	 * @param align
	 *            the new align
	 */
	public void setAlign(String align) {
		this.setAttribute(ALIGN, align);
	}

	/**
	 * Gets the alt.
	 *
	 * @return the alt
	 */
	public String getAlt() {
		return this.getAttribute(ALT);
	}

	/**
	 * Sets the alt.
	 *
	 * @param alt
	 *            the new alt
	 */
	public void setAlt(String alt) {
		this.setAttribute(ALT, alt);
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		// TODO: Should this return value of "id"?
		return this.getAttribute(NAME);
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.setAttribute(NAME, name);
	}

	/**
	 * Gets the placeholder.
	 *
	 * @return the placeholder
	 */
	public String getPlaceholder() {
		return this.getAttribute(PLACEHOLDER);
	}

	/**
	 * Sets the placeholder.
	 *
	 * @param placeholder
	 *            the new placeholder
	 */
	public void setPlaceholder(String placeholder) {
		this.setAttribute(PLACEHOLDER, placeholder);

	}

	/**
	 * Gets the src.
	 *
	 * @return the src
	 */
	public String getSrc() {
		return this.getAttribute(SRC);
	}

	/**
	 * Sets the src.
	 *
	 * @param src
	 *            the new src
	 */
	public void setSrc(String src) {
		this.setAttribute(SRC, src);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMElementImpl#getDisabled()
	 */

	@Override
	public boolean getDisabled() {
		InputContext ic = this.inputContext;
		if (ic == null) {
			Boolean db = this.deferredDisabled;
			return db == null ? false : db.booleanValue();
		} else {
			return ic.getDisabled();
		}
	}

	/**
	 * Sets the disabled.
	 *
	 * @param disabled
	 *            the new disabled
	 */
	public void setDisabled(boolean disabled) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setDisabled(disabled);
		} else {
			this.deferredDisabled = Boolean.valueOf(disabled);
		}
	}

	/**
	 * Gets the read only.
	 *
	 * @return the read only
	 */
	public boolean getReadOnly() {
		InputContext ic = this.inputContext;
		if (ic == null) {
			Boolean db = this.deferredReadonly;
			return db == null ? false : db.booleanValue();
		} else {
			return ic.getReadOnly();
		}
	}

	/**
	 * Sets the read only.
	 *
	 * @param readOnly
	 *            the new read only
	 */
	public void setReadOnly(boolean readOnly) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setReadOnly(readOnly);
		} else {
			this.deferredReadonly = Boolean.valueOf(readOnly);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMElementImpl#getChecked()
	 */

	@Override
	public boolean getChecked() {
		InputContext ic = this.inputContext;
		if (ic == null) {
			Boolean db = this.deferredChecked;
			return db == null ? false : db.booleanValue();
		} else {
			return ic.getChecked();
		}
	}

	/**
	 * Sets the checked.
	 *
	 * @param value
	 *            the new checked
	 */
	public void setChecked(boolean value) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setChecked(value);
		} else {
			this.deferredChecked = Boolean.valueOf(value);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMElementImpl#getTabIndex()
	 */

	@Override
	public int getTabIndex() {
		InputContext ic = this.inputContext;
		return ic == null ? 0 : ic.getTabIndex();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.DOMElementImpl#setTabIndex(int)
	 */

	@Override
	public void setTabIndex(int tabIndex) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setTabIndex(tabIndex);
		}
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			// Note: Per HTML Spec, setValue does not set attribute.
			return ic.getValue();
		} else {
			String dv = this.deferredValue;
			if (dv != null) {
				return dv;
			} else {
				String val = this.getAttribute(VALUE);
				return val == null ? "" : val;
			}
		}
	}

	/**
	 * Gets the file value.
	 *
	 * @return the file value
	 */
	protected File[] getFileValue() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			return ic.getFileValue();
		} else {
			return null;
		}
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.HTMLAbstractUIElement#blur()
	 */

	@Override
	public void blur() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.blur();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.HTMLAbstractUIElement#focus()
	 */

	@Override
	public void focus() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.focus();
		}
	}

	/**
	 * Select.
	 */
	public void select() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.select();
		}
	}

	/**
	 * Sets the autocomplete.
	 *
	 * @param autocomplete
	 *            the new autocomplete
	 */
	public void setAutocomplete(String autocomplete) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the indeterminate.
	 *
	 * @return the indeterminate
	 */
	public boolean getIndeterminate() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Sets the indeterminate.
	 *
	 * @param indeterminate
	 *            the new indeterminate
	 */
	public void setIndeterminate(boolean indeterminate) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public HTMLElement getList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the value as date.
	 *
	 * @return the value as date
	 */
	public long getValueAsDate() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Sets the value as date.
	 *
	 * @param valueAsDate
	 *            the new value as date
	 */
	public void setValueAsDate(long valueAsDate) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the value as number.
	 *
	 * @return the value as number
	 */
	public double getValueAsNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Sets the value as number.
	 *
	 * @param valueAsNumber
	 *            the new value as number
	 */
	public void setValueAsNumber(double valueAsNumber) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the selected option.
	 *
	 * @return the selected option
	 */
	public HTMLOptionElement getSelectedOption() {
		// TODO Auto-generated method stub
		return null;
	}

	public void stepUp() {
		// TODO Auto-generated method stub

	}

	public void stepUp(int n) {
		// TODO Auto-generated method stub

	}

	public void stepDown() {
		// TODO Auto-generated method stub

	}

	public void stepDown(int n) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the dir name.
	 *
	 * @return the dir name
	 */
	public String getDirName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the dir name.
	 *
	 * @param dirName
	 *            the new dir name
	 */
	public void setDirName(String dirName) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the pattern.
	 *
	 * @return the pattern
	 */
	public String getPattern() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the pattern.
	 *
	 * @param pattern
	 *            the new pattern
	 */
	public void setPattern(String pattern) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the will validate.
	 *
	 * @return the will validate
	 */
	public boolean getWillValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Gets the validity.
	 *
	 * @return the validity
	 */
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the validation message.
	 *
	 * @return the validation message
	 */
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Sets the custom validity.
	 *
	 * @param error
	 *            the new custom validity
	 */
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the selection start.
	 *
	 * @return the selection start
	 */
	public int getSelectionStart() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Sets the selection start.
	 *
	 * @param selectionStart
	 *            the new selection start
	 */
	public void setSelectionStart(int selectionStart) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the selection end.
	 *
	 * @return the selection end
	 */
	public int getSelectionEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Sets the selection end.
	 *
	 * @param selectionEnd
	 *            the new selection end
	 */
	public void setSelectionEnd(int selectionEnd) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the selection direction.
	 *
	 * @return the selection direction
	 */
	public String getSelectionDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the selection direction.
	 *
	 * @param selectionDirection
	 *            the new selection direction
	 */
	public void setSelectionDirection(String selectionDirection) {
		// TODO Auto-generated method stub

	}

	public void setSelectionRange(int start, int end) {
		// TODO Auto-generated method stub

	}

	public void setSelectionRange(int start, int end, String direction) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.loboevolution.html.dombl.HTMLElementImpl#assignAttributeField(java.lang
	 * .String, java.lang.String)
	 */

	@Override
	public void assignAttributeField(String normalName, String value) {
		if (VALUE.equals(normalName)) {
			this.setValue(value);
		} else if ("checked".equals(normalName)) {
			this.setChecked(value != null);
		} else if ("disabled".equals(normalName)) {
			this.setDisabled(value != null);
		} else if ("readonly".equals(normalName)) {
			this.setReadOnly(value != null);
		} else if (SRC.equals(normalName)) {
			this.loadImage(value);
		} else {
			super.assignAttributeField(normalName, value);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.loboevolution.html.domimpl.HTMLAbstractUIElement#getOnload()
	 */

	@Override
	public Function getOnload() {
		return this.getEventFunction(this.onload, "onload");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.loboevolution.html.domimpl.HTMLAbstractUIElement#setOnload(org.mozilla.
	 * javascript.Function)
	 */

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
			synchronized (this.imageListeners) {
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
		synchronized (this.imageListeners) {
			return this.image;
		}
	}

	/**
	 * Adds a listener of image loading events. The listener gets called right
	 * away if there's already an image.
	 *
	 * @param listener
	 *            the listener
	 */
	public void addImageListener(ImageListener listener) {
		ArrayList<ImageListener> l = this.imageListeners;
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
		ArrayList<ImageListener> l = this.imageListeners;
		synchronized (l) {
			l.remove(l);
		}
	}

	/**
	 * Reset input.
	 */
	public void resetInput() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.resetInput();
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
		ArrayList<ImageListener> l = this.imageListeners;
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
			Executor.executeFunction(HTMLBaseInputElement.this, onload, null);
		}
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
		 * org.loboevolution.html.dombl.ImageListener#imageLoaded(org.loboevolution.
		 * html .dombl.ImageEvent)
		 */

		@Override
		public void imageLoaded(ImageEvent event) {
			dispatchEvent(this.expectedImgSrc, event);
		}
	}

}
