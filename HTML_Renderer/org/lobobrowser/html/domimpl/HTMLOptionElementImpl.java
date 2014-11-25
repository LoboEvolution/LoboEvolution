package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLFormElement;
import org.lobobrowser.html.w3c.HTMLOptionElement;
import org.lobobrowser.html.w3c.HTMLSelectElement;

public class HTMLOptionElementImpl extends HTMLElementImpl implements
		HTMLOptionElement {
	public HTMLOptionElementImpl(String name) {
		super(name, true);
	}

	public boolean getDefaultSelected() {
		return this.getAttributeAsBoolean(HtmlAttributeProperties.SELECTED);
	}

	public boolean getDisabled() {
		return false;
	}

	public HTMLFormElement getForm() {
		return this.getForm();
	}

	public int getIndex() {
		Object parent = this.getParentNode();
		if (parent instanceof HTMLSelectElement) {
			HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) ((HTMLSelectElement) parent)
					.getOptions();
			return options.indexOf(this);
		} else {
			return -1;
		}
	}

	public String getLabel() {
		return this.getAttribute(HtmlAttributeProperties.LABEL);
	}

	public boolean getSelected() {
		return this.selected;
	}

	public String getText() {
		return this.getRawInnerText(false);
	}

	public void setText(String value) {
		this.setTextContent(value);
	}

	public String getValue() {
		return this.getAttribute(HtmlAttributeProperties.VALUE);
	}

	public void setDefaultSelected(boolean defaultSelected) {
		this.setAttribute(HtmlAttributeProperties.SELECTED, defaultSelected ? HtmlAttributeProperties.SELECTED : null);
	}

	public void setDisabled(boolean disabled) {
		// TODO Unsupported
	}

	public void setLabel(String label) {
		this.setAttribute(HtmlAttributeProperties.LABEL, label);
	}

	private boolean selected;

	void setSelectedImpl(boolean selected) {
		this.selected = selected;
	}

	public void setSelected(boolean selected) {
		boolean changed = selected != this.selected;
		this.selected = selected;
		// Changing the option state changes the selected index.
		Object parent = this.getParentNode();
		if (parent instanceof HTMLSelectElementImpl) {
			HTMLSelectElementImpl parentSelect = ((HTMLSelectElementImpl) parent);
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

	public void setValue(String value) {
		this.setAttribute(HtmlAttributeProperties.VALUE, value);
	}

	public String toString() {
		return "HTMLOptionElementImpl[text=" + this.getText() + ",selected="
				+ this.getSelected() + "]";
	}
}
