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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.w3c.html.HTMLFormElement;
import org.lobobrowser.w3c.html.HTMLOptionElement;
import org.lobobrowser.w3c.html.HTMLSelectElement;
import org.w3c.dom.Node;

/**
 * The Class HTMLOptionElementImpl.
 */
public class HTMLOptionElementImpl extends HTMLElementImpl implements HTMLOptionElement {

	/**
	 * Instantiates a new HTML option element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLOptionElementImpl(String name) {
		super(name, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLOptionElement#getDefaultSelected()
	 */
	@Override
	public boolean getDefaultSelected() {
		return this.getAttributeAsBoolean(HtmlAttributeProperties.SELECTED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.DOMElementImpl#getDisabled()
	 */
	@Override
	public boolean getDisabled() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLOptionElement#getForm()
	 */
	@Override
	public HTMLFormElement getForm() {
		Node parent = this.getParentNode();
		while (parent != null && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLOptionElement#getIndex()
	 */
	@Override
	public int getIndex() {
		Object parent = this.getParentNode();
		if (parent instanceof HTMLSelectElement) {
			HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) ((HTMLSelectElement) parent).getOptions();
			return options.indexOf(this);
		} else {
			return -1;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#getLabel()
	 */
	@Override
	public String getLabel() {
		return this.getAttribute(HtmlAttributeProperties.LABEL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLOptionElement#getSelected()
	 */
	@Override
	public boolean getSelected() {
		return this.selected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLOptionElement#getText()
	 */
	@Override
	public String getText() {
		return this.getRawInnerText(false);
	}

	/**
	 * Sets the text.
	 *
	 * @param value
	 *            the new text
	 */
	@Override
	public void setText(String value) {
		this.setTextContent(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLOptionElement#getValue()
	 */
	@Override
	public String getValue() {
		return this.getAttribute(HtmlAttributeProperties.VALUE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLOptionElement#setDefaultSelected(boolean)
	 */
	@Override
	public void setDefaultSelected(boolean defaultSelected) {
		this.setAttribute(HtmlAttributeProperties.SELECTED, defaultSelected ? HtmlAttributeProperties.SELECTED : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLOptionElement#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean disabled) {
		// TODO Unsupported
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLOptionElement#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String label) {
		this.setAttribute(HtmlAttributeProperties.LABEL, label);
	}

	/** The selected. */
	private boolean selected;

	/**
	 * Sets the selected impl.
	 *
	 * @param selected
	 *            the new selected impl
	 */
	public void setSelectedImpl(boolean selected) {
		this.selected = selected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLOptionElement#setSelected(boolean)
	 */
	@Override
	public void setSelected(boolean selected) {
		boolean changed = selected != this.selected;
		this.selected = selected;
		// Changing the option state changes the selected index.
		Object parent = this.getParentNode();
		if (parent instanceof HTMLSelectElementImpl) {
			HTMLSelectElementImpl parentSelect = (HTMLSelectElementImpl) parent;
			if (changed || parentSelect.getSelectedIndex() == -1) {
				if (selected) {
					parentSelect.setSelectedIndexImpl(this.getIndex());
				} else {
					int currentIndex = parentSelect.getSelectedIndex();
					if (currentIndex != -1 && currentIndex == this.getIndex()) {
						parentSelect.setSelectedIndexImpl(-1);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLOptionElement#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.setAttribute(HtmlAttributeProperties.VALUE, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#toString()
	 */
	@Override
	public String toString() {
		return "HTMLOptionElementImpl[text=" + this.getText() + ",selected=" + this.getSelected() + "]";
	}
}
