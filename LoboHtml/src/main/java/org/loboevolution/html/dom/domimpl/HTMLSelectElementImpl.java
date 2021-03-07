/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.control.SelectControl;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLLabelElement;
import org.loboevolution.html.dom.HTMLOptGroupElement;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLOptionsCollection;
import org.loboevolution.html.dom.HTMLSelectElement;
import org.loboevolution.html.dom.input.SelectOption;

import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;

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
	public boolean isDisabled() {
		final String disabled = getAttribute("disabled");
		return disabled != null;
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
	public void setLength(int length) {
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
	public void setValue(String value) {
		setAttribute("value", String.valueOf(value));
		
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

	@Override
	public String getAccessKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAutocapitalize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSpellcheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutocapitalize(String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDraggable(boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslate(boolean translate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAutocomplete() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAutocomplete(String autocomplete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAutofocus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAutofocus(boolean autofocus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMultiple() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRequired(boolean required) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HTMLCollection getSelectedOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSize(int size) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWillValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(HTMLOptionElement element, HTMLElement before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptionElement element, int before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptionElement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptGroupElement element, HTMLElement before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptGroupElement element, int before) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(HTMLOptGroupElement element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Element item(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLOptionElement namedItem(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean reportValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void set(int name, Element value) {
		// TODO Auto-generated method stub
		
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLSelectElement]";
	}
}
