package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLSelectElement;

/**
 * <p>HTMLOptionElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLOptionElementImpl extends HTMLElementImpl implements HTMLOptionElement {
	private boolean selected;

	/**
	 * <p>Constructor for HTMLOptionElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLOptionElementImpl(String name) {
		super(name, true);
	}

	/** {@inheritDoc} */
	@Override
	public boolean getDefaultSelected() {
		return getAttributeAsBoolean("selected");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getDisabled() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLFormElement getForm() {
		return getForm();
	}

	/** {@inheritDoc} */
	@Override
	public int getIndex() {
		final Object parent = getParentNode();
		if (parent instanceof HTMLSelectElement) {
			final HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) ((HTMLSelectElement) parent).getOptions();
			return options.indexOf(this);
		} else {
			return -1;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getLabel() {
		return getAttribute("label");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getSelected() {
		return this.selected;
	}

	/** {@inheritDoc} */
	@Override
	public String getText() {
		return getRawInnerText(false);
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		return getAttribute("value");
	}

	/** {@inheritDoc} */
	@Override
	public void setDefaultSelected(boolean defaultSelected) {
		setAttribute("selected", defaultSelected ? "selected" : null);
	}

	/** {@inheritDoc} */
	@Override
	public void setDisabled(boolean disabled) {
		// TODO Unsupported
	}

	/** {@inheritDoc} */
	@Override
	public void setLabel(String label) {
		setAttribute("label", label);
	}

	/** {@inheritDoc} */
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

	/**
	 * <p>setText.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setText(String value) {
		setTextContent(value);
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		setAttribute("value", value);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "HTMLOptionElementImpl[text=" + getText() + ",selected=" + getSelected() + "]";
	}
}
