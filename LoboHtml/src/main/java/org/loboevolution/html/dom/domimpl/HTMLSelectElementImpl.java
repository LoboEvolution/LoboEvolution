package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.control.SelectControl;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.HTMLSelectElement;
import org.loboevolution.html.dom.input.SelectOption;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class HTMLSelectElementImpl extends HTMLAbstractUIElement implements HTMLSelectElement {
	
	private SelectOption selectOption;
	
	private int selectedIndex = -1;
	
	public HTMLSelectElementImpl(String name) {
		super(name);
	}

	@Override
	public boolean getDisabled() {
		final String disabled = getAttribute("disabled");
		return disabled == null ? false : true;
	}

	@Override
	public HTMLFormElement getForm() {
		Node parent = this.getParentNode();
		while ((parent != null) && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
	}

	@Override
	public int getLength() {
		try {
			final String maxLength = getAttribute("length");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return Integer.MAX_VALUE;
		}
	}

	@Override
	public boolean getMultiple() {
		return this.getAttributeAsBoolean("multiple");
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}

	@Override
	public HTMLOptionsCollection getOptions() {
		return new HTMLOptionsCollectionImpl(this);
	}

	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}

	@Override
	public int getSize() {
		return getOptions().getLength();
	}

	@Override
	public int getTabIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		return this.getMultiple() ? "select-multiple" : "select-one";
	}

	@Override
	public String getValue() {
		return getAttribute("value");
	}

	@Override
	public void remove(int index) {
		removeChild(getOptions().item(index));
	}

	@Override
	public void setDisabled(boolean disabled) {
		setAttribute("disabled", String.valueOf(disabled));
		
	}

	@Override
	public void setLength(int length) throws DOMException {
		setAttribute("length", String.valueOf(length));
		
	}

	@Override
	public void setMultiple(boolean multiple) {
		setAttribute("multiple", String.valueOf(multiple));
		
	}

	@Override
	public void setName(String name) {
		setAttribute("name", String.valueOf(name));
		
	}

	@Override
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		
	}

	@Override
	public void setTabIndex(int tabIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValue(String value) {
		setAttribute("value", String.valueOf(value));
		
	}
	
	@Override
	public void add(HTMLElement element, HTMLElement before) throws DOMException {
		// TODO Auto-generated method stub
		
	}

	public void draw(SelectControl selectControl) {
		selectOption = new SelectOption(this, selectControl);
	}
	
	public void resetInput() {
		if (selectOption!= null) selectOption.resetInput();
	}
}
