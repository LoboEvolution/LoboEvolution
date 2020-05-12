package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.control.SelectControl;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.HTMLSelectElement;
import org.loboevolution.html.dom.input.SelectOption;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * <p>HTMLSelectElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLSelectElementImpl extends HTMLAbstractUIElement implements HTMLSelectElement {
	
	private SelectOption selectOption;
	
	private int selectedIndex = -1;
	
	/**
	 * <p>Constructor for HTMLSelectElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLSelectElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public boolean getDisabled() {
		final String disabled = getAttribute("disabled");
		return disabled == null ? false : true;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLFormElement getForm() {
		Node parent = this.getParentNode();
		while ((parent != null) && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		try {
			final String maxLength = getAttribute("length");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return Integer.MAX_VALUE;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean getMultiple() {
		return this.getAttributeAsBoolean("multiple");
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLOptionsCollection getOptions() {
		return new HTMLOptionsCollectionImpl(this);
	}

	/** {@inheritDoc} */
	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		return getOptions().getLength();
	}

	/** {@inheritDoc} */
	@Override
	public int getTabIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return this.getMultiple() ? "select-multiple" : "select-one";
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		return getAttribute("value");
	}

	/** {@inheritDoc} */
	@Override
	public void remove(int index) {
		removeChild(getOptions().item(index));
	}

	/** {@inheritDoc} */
	@Override
	public void setDisabled(boolean disabled) {
		setAttribute("disabled", String.valueOf(disabled));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setLength(int length) throws DOMException {
		setAttribute("length", String.valueOf(length));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setMultiple(boolean multiple) {
		setAttribute("multiple", String.valueOf(multiple));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", String.valueOf(name));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		
	}

	/** {@inheritDoc} */
	@Override
	public void setTabIndex(int tabIndex) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		setAttribute("value", String.valueOf(value));
		
	}
	
	/** {@inheritDoc} */
	@Override
	public void add(HTMLElement element, HTMLElement before) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param selectControl a {@link org.loboevolution.html.control.SelectControl} object.
	 */
	public void draw(SelectControl selectControl) {
		selectOption = new SelectOption(this, selectControl);
	}
	
	/**
	 * <p>resetInput.</p>
	 */
	public void resetInput() {
		if (selectOption!= null) selectOption.resetInput();
	}
}
