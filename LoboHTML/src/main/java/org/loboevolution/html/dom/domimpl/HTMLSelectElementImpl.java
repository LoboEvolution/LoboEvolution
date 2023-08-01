/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.mozilla.javascript.Undefined;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>HTMLSelectElementImpl class.</p>
 */
public class HTMLSelectElementImpl extends HTMLBasicInputElement implements HTMLSelectElement {
	
	private SelectOption selectOption;
	
	private HTMLOptionsCollection options;
	
	private Integer selectedIndex = null;

	/**
	 * <p>Constructor for HTMLSelectElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLSelectElementImpl(final String name) {
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
		final String name = getAttribute("name");
		return name == null ? "" : name;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLOptionsCollection getOptions() {
		if (options == null ) {
			options = new HTMLOptionsCollectionImpl(this, new OptionFilter());
		}
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
		String value = getAttribute("value");
		if (value != null) {
			return value;
		} else {
			AtomicReference<String> x = new AtomicReference<>();
			HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) getOptions();
			options.forEach(node -> {
				HTMLOptionElement op = (HTMLOptionElement) node;
				if (op.isSelected()) {
					x.set(op.getValue());
				}
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
				if (d > -1 && d.intValue() < getOptions().getLength()) {
					getOptions().remove(d.intValue());
				}
			} else {
				getOptions().remove(0);
			}
			if (getOptions().getLength() == 1 && !isMultiple()) {
				HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) getOptions();
				options.stream().findFirst().ifPresent(option -> ((HTMLOptionElement) option).setSelected(true));
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
			this.selectedIndex = null;
		} else {
			this.selectedIndex = selectedIndex;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) getOptions();
		Optional<Node> optional =
		options.stream().
				filter(op -> (op.getTextContent() != null &&  op.getTextContent().trim().equalsIgnoreCase(value)) ||
						(op.getNodeValue() != null &&  op.getNodeValue().equalsIgnoreCase(value))).
			    findFirst();

		setAttribute("value", optional.isPresent() ? value : "");
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
	public boolean isMultiple() {
		return this.getAttributeAsBoolean("multiple");
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
	public void add(Object element, Object before) {
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
			options.stream().findFirst().ifPresent(option -> ((HTMLOptionElement) option).setSelected(true));
		}

		if (selectOption != null) selectOption.resetItemList(this);
	}

	/** {@inheritDoc} */
	@Override
	public void add(Object element) {
		getOptions().add((HTMLOptionElementImpl)element);
		if (selectOption!= null) selectOption.resetItemList(this);
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

	@Override
	public void setItem(Integer index, Node node) {
		getOptions().setItem(index, node);
	}

	@Override
	public int getClientHeight() {
		int clientHeight = super.getClientHeight();
		return clientHeight == 0 ? 17 : clientHeight;
	}

	@Override
	public Integer getClientWidth() {
		int clientWidth = super.getClientWidth();
		return clientWidth == 0 ? 22 : clientWidth;
	}

	@Override
	public Integer getOffsetWidth() {
		return getClientWidth() + 2;
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_INLINE_BLOCK);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLSelectElement]";
	}
}
