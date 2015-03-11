package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.w3c.HTMLFormElement;
import org.lobobrowser.html.w3c.HTMLOptionElement;
import org.lobobrowser.html.w3c.HTMLSelectElement;


/**
 * The Class HTMLOptionElementImpl.
 */
public class HTMLOptionElementImpl extends HTMLElementImpl implements
		HTMLOptionElement {
	
	/**
	 * Instantiates a new HTML option element impl.
	 *
	 * @param name the name
	 */
	public HTMLOptionElementImpl(String name) {
		super(name, true);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#getDefaultSelected()
	 */
	public boolean getDefaultSelected() {
		return this.getAttributeAsBoolean(HtmlAttributeProperties.SELECTED);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMElementImpl#getDisabled()
	 */
	public boolean getDisabled() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#getForm()
	 */
	public HTMLFormElement getForm() {
		return this.getForm();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#getIndex()
	 */
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

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#getLabel()
	 */
	public String getLabel() {
		return this.getAttribute(HtmlAttributeProperties.LABEL);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#getSelected()
	 */
	public boolean getSelected() {
		return this.selected;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#getText()
	 */
	public String getText() {
		return this.getRawInnerText(false);
	}

	/**
	 * Sets the text.
	 *
	 * @param value the new text
	 */
	public void setText(String value) {
		this.setTextContent(value);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#getValue()
	 */
	public String getValue() {
		return this.getAttribute(HtmlAttributeProperties.VALUE);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#setDefaultSelected(boolean)
	 */
	public void setDefaultSelected(boolean defaultSelected) {
		this.setAttribute(HtmlAttributeProperties.SELECTED, defaultSelected ? HtmlAttributeProperties.SELECTED : null);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#setDisabled(boolean)
	 */
	public void setDisabled(boolean disabled) {
		// TODO Unsupported
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#setLabel(java.lang.String)
	 */
	public void setLabel(String label) {
		this.setAttribute(HtmlAttributeProperties.LABEL, label);
	}

	/** The selected. */
	private boolean selected;

	/**
	 * Sets the selected impl.
	 *
	 * @param selected the new selected impl
	 */
	void setSelectedImpl(boolean selected) {
		this.selected = selected;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#setSelected(boolean)
	 */
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

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLOptionElement#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		this.setAttribute(HtmlAttributeProperties.VALUE, value);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#toString()
	 */
	public String toString() {
		return "HTMLOptionElementImpl[text=" + this.getText() + ",selected="
				+ this.getSelected() + "]";
	}
}
