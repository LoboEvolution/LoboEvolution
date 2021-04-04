/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.SelectControl;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.filter.OptionFilter;
import org.loboevolution.html.dom.input.SelectOption;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.node.ValidityState;
import org.mozilla.javascript.Undefined;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>HTMLSelectElementImpl class.</p>
 *
 *
 *
 */
public class HTMLSelectElementImpl extends HTMLElementImpl implements HTMLSelectElement {
	
	private SelectOption selectOption;
	
	private HTMLOptionsCollection options;
	
	private Integer selectedIndex = null;

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
		return getOptions().getLength();
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLOptionsCollection getOptions() {
		if(options == null ) options = new HTMLOptionsCollectionImpl(this, new OptionFilter());
		return options;
	}

	/** {@inheritDoc} */
	@Override
	public int getSelectedIndex() {
		if (selectedIndex != null) return this.selectedIndex;
		return getOptions().getSelectedIndex();
	}

	/** {@inheritDoc} */
	@Override
	public int getSize() {
		String size = getAttribute("size");
		if (!Strings.isNumeric(size)) {
			return 0;
		} else if (Strings.isNotBlank(size)) {
			return Integer.parseInt(size);
		} else {
			return getOptions().getLength();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return this.isMultiple() ? "select-multiple" : "select-one";
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		AtomicReference<String> x = new AtomicReference<>();
		String value = getAttribute("value");
		if (Strings.isNotBlank(value)) {
			return value;
		} else {
			HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) getOptions();
			options.forEach(node -> {
				HTMLOptionElement op = (HTMLOptionElement) node;
				if(Strings.isBlank(x.get())) x.set(op.getValue());
			});
			return x.get();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void remove(Object element) {
		if (getOptions().getLength() > 0) {
			if (element instanceof Double) {
				Double d = (Double) element;
				if (d.intValue() < getOptions().getLength()) {
					getOptions().remove(d.intValue());
				}
			} else {
				getOptions().remove(0);
			}
			if (getOptions().getLength() == 1 && !isMultiple()) {
				HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) getOptions();
				options.stream().findFirst().ifPresent(option -> { ((HTMLOptionElement) option).setSelected(true); });
			}
			if (selectOption != null) selectOption.resetItemList(this);
		}
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
		if (getOptions().getLength() <= selectedIndex || selectedIndex < 0) {
			this.selectedIndex = -1;
		} else {
			this.selectedIndex = selectedIndex;
		}
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

	/** {@inheritDoc} */
	@Override
	public String getAccessKey() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getAutocapitalize() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSpellcheck() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setAutocapitalize(String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setDraggable(boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setTranslate(boolean translate) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String getAutocomplete() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setAutocomplete(String autocomplete) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean isAutofocus() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setAutofocus(boolean autofocus) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMultiple() {
		return this.getAttributeAsBoolean("multiple");
	}

	/** {@inheritDoc} */
	@Override
	public boolean isRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setRequired(boolean required) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getSelectedOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setSize(int size) {
		setAttribute("size", String.valueOf(size));
	}

	/** {@inheritDoc} */
	@Override
	public String getValidationMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public ValidityState getValidity() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isWillValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void add(Object element, Object before) {
		try {
			if (before == null || before instanceof Undefined) {
				getOptions().add((HTMLOptionElement) element);
			}

			if (element instanceof HTMLOptionElementImpl && before instanceof Double) {
				double d = (double) before;
				getOptions().add(element, d);
			}

			if (element instanceof HTMLOptionElementImpl && before instanceof HTMLOptionElementImpl) {
				HTMLOptionElementImpl d = (HTMLOptionElementImpl) before;
				getOptions().add(element, d);
			}

			if (getOptions().getLength() == 1 && !isMultiple()) {
				HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) getOptions();
				options.stream().findFirst().ifPresent(option -> { ((HTMLOptionElement) option).setSelected(true); });
			}

			if (selectOption != null) selectOption.resetItemList(this);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void add(Object element) {
		getOptions().add((HTMLOptionElementImpl)element);
		if (selectOption!= null) selectOption.resetItemList(this);
	}

	/** {@inheritDoc} */
	@Override
	public boolean checkValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Element item(int index) {
		return (Element)getOptions().item(index);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLOptionElement namedItem(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void remove() {
		getOptions().remove(getOptions().getLength() - 1);
		if (selectOption!= null) selectOption.resetItemList(this);
		
	}

	/** {@inheritDoc} */
	@Override
	public boolean reportValidity() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void setCustomValidity(String error) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
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
