/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;

import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.InputContext;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLElement;
import org.lobobrowser.w3c.html.HTMLOptionsCollection;
import org.lobobrowser.w3c.html.HTMLSelectElement;
import org.mozilla.javascript.Function;
import org.w3c.dom.DOMException;

/**
 * The Class HTMLSelectElementImpl.
 */
public class HTMLSelectElementImpl extends HTMLBaseInputElement implements HTMLSelectElement {

	/**
	 * Instantiates a new HTML select element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLSelectElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLSelectElement#add(org.lobobrowser.w3c.html.
	 * HTMLElement, org.lobobrowser.w3c.html.HTMLElement)
	 */
	@Override
	public void add(HTMLElement element, HTMLElement before) throws DOMException {
		this.insertBefore(element, before);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#getLength()
	 */
	@Override
	public int getLength() {
		return this.getOptions().getLength();
	}

	/** The multiple state. */
	private Boolean multipleState = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#getMultiple()
	 */
	@Override
	public boolean getMultiple() {
		Boolean m = this.multipleState;
		if (m != null) {
			return m.booleanValue();
		}
		return this.getAttributeAsBoolean("multiple");
	}

	/** The options. */
	private HTMLOptionsCollection options;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#getOptions()
	 */
	@Override
	public HTMLOptionsCollection getOptions() {
		synchronized (this) {
			if (this.options == null) {
				this.options = new HTMLOptionsCollectionImpl(this);
			}
			return this.options;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#getSelectedIndex()
	 */
	@Override
	public int getSelectedIndex() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			return ic.getSelectedIndex();
		} else {
			return this.deferredSelectedIndex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#getSize()
	 */
	@Override
	public int getSize() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			return ic.getVisibleSize();
		} else {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#getType()
	 */
	@Override
	public String getType() {
		return this.getMultiple() ? "select-multiple" : "select-one";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#remove(int)
	 */
	@Override
	public void remove(int index) {
		try {
			this.removeChild(this.getOptions().item(index));
		} catch (DOMException de) {
			this.warn("remove(): Unable to remove option at index " + index + ".", de);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#setLength(int)
	 */
	@Override
	public void setLength(int length) throws DOMException {
		this.getOptions().setLength(length);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#setMultiple(boolean)
	 */
	@Override
	public void setMultiple(boolean multiple) {
		boolean prevMultiple = this.getMultiple();
		this.multipleState = Boolean.valueOf(multiple);
		if (prevMultiple != multiple) {
			this.informLayoutInvalid();
		}
	}

	/** The deferred selected index. */
	private int deferredSelectedIndex = -1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#setSelectedIndex(int)
	 */
	@Override
	public void setSelectedIndex(int selectedIndex) {
		this.setSelectedIndexImpl(selectedIndex);
		HTMLOptionsCollection options = this.getOptions();
		int length = options.getLength();
		for (int i = 0; i < length; i++) {
			HTMLOptionElementImpl option = (HTMLOptionElementImpl) options.item(i);
			option.setSelectedImpl(i == selectedIndex);
		}
	}

	/**
	 * Sets the selected index impl.
	 *
	 * @param selectedIndex
	 *            the new selected index impl
	 */
	void setSelectedIndexImpl(int selectedIndex) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setSelectedIndex(selectedIndex);
		} else {
			this.deferredSelectedIndex = selectedIndex;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#setSize(int)
	 */
	@Override
	public void setSize(int size) {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.setVisibleSize(size);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#getFormInputs()
	 */
	@Override
	protected FormInput[] getFormInputs() {
		// Needs to be overriden for forms to submit.
		InputContext ic = this.inputContext;
		String[] values = ic == null ? null : ic.getValues();
		if (values == null) {
			String value = this.getValue();
			values = value == null ? null : new String[] { value };
			if (values == null) {
				return null;
			}
		}
		String name = this.getName();
		if (name == null) {
			return null;
		}
		ArrayList<FormInput> formInputs = new ArrayList<FormInput>();
		for (int i = 0; i < values.length; i++) {
			formInputs.add(new FormInput(name, values[i]));
		}
		return formInputs.toArray(FormInput.EMPTY_ARRAY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLBaseInputElement#resetInput()
	 */
	@Override
	public void resetInput() {
		InputContext ic = this.inputContext;
		if (ic != null) {
			ic.resetInput();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.domimpl.HTMLBaseInputElement#setInputContext(org.
	 * lobobrowser .html.dombl.InputContext)
	 */
	@Override
	public void setInputContext(InputContext ic) {
		super.setInputContext(ic);
		if (ic != null) {
			ic.setSelectedIndex(this.deferredSelectedIndex);
		}
	}

	/** The onchange. */
	private Function onchange;

	/**
	 * Gets the onchange.
	 *
	 * @return the onchange
	 */
	@Override
	public Function getOnchange() {
		return this.getEventFunction(this.onchange, "onchange");
	}

	/**
	 * Sets the onchange.
	 *
	 * @param value
	 *            the new onchange
	 */
	@Override
	public void setOnchange(Function value) {
		this.onchange = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#getAutofocus()
	 */
	@Override
	public boolean getAutofocus() {
		String auto = this.getAttribute(HtmlAttributeProperties.AUTOFOCUS);
		return HtmlAttributeProperties.AUTOFOCUS.equalsIgnoreCase(auto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLSelectElement#setAutofocus(boolean)
	 */
	@Override
	public void setAutofocus(boolean autofocus) {
		this.setAttribute(HtmlAttributeProperties.AUTOFOCUS, autofocus ? HtmlAttributeProperties.AUTOFOCUS : null);

	}

	@Override
	public boolean getRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRequired(boolean required) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object item(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object namedItem(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(HTMLElement element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(HTMLElement element, int before) {
		// TODO Auto-generated method stub

	}

	@Override
	public HTMLCollection getSelectedOptions() {
		// TODO Auto-generated method stub
		return null;
	}
}
