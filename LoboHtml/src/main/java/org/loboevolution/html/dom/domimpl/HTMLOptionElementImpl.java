package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLSelectElement;

public class HTMLOptionElementImpl extends HTMLElementImpl implements HTMLOptionElement {
	private boolean selected;

	public HTMLOptionElementImpl(String name) {
		super(name, true);
	}

	@Override
	public boolean getDefaultSelected() {
		return getAttributeAsBoolean("selected");
	}

	@Override
	public boolean getDisabled() {
		return false;
	}

	@Override
	public HTMLFormElement getForm() {
		return getForm();
	}

	@Override
	public int getIndex() {
		final Object parent = getParentNode();
		if (parent instanceof HTMLSelectElement) {
			final HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) ((HTMLSelectElement) parent)
					.getOptions();
			return options.indexOf(this);
		} else {
			return -1;
		}
	}

	@Override
	public String getLabel() {
		return getAttribute("label");
	}

	@Override
	public boolean getSelected() {
		return this.selected;
	}

	@Override
	public String getText() {
		return getRawInnerText(false);
	}

	@Override
	public String getValue() {
		return getAttribute("value");
	}

	@Override
	public void setDefaultSelected(boolean defaultSelected) {
		setAttribute("selected", defaultSelected ? "selected" : null);
	}

	@Override
	public void setDisabled(boolean disabled) {
		// TODO Unsupported
	}

	@Override
	public void setLabel(String label) {
		setAttribute("label", label);
	}

	@Override
	public void setSelected(boolean selected) {
		final boolean changed = selected != this.selected;
		this.selected = selected;
		// Changing the option state changes the selected index.
		final Object parent = getParentNode();
		if (parent instanceof HTMLSelectElementImpl) {
			final HTMLSelectElementImpl parentSelect = (HTMLSelectElementImpl) parent;
			if (changed || parentSelect.getSelectedIndex() == -1) {
				/*if (selected) {
					parentSelect.setSelectedIndexImpl(getIndex());
				} else {
					final int currentIndex = parentSelect.getSelectedIndex();
					if (currentIndex != -1 && currentIndex == getIndex()) {
						parentSelect.setSelectedIndexImpl(-1);
					}
				}*/ //TODO
			}
		}
	}

	void setSelectedImpl(boolean selected) {
		this.selected = selected;
	}

	public void setText(String value) {
		setTextContent(value);
	}

	@Override
	public void setValue(String value) {
		setAttribute("value", value);
	}

	@Override
	public String toString() {
		return "HTMLOptionElementImpl[text=" + getText() + ",selected=" + getSelected() + "]";
	}
}
