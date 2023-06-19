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
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.dom.HTMLSelectElement;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.renderstate.BlockRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HTMLOptionElementImpl class.</p>
 */
public class HTMLOptionElementImpl extends HTMLElementImpl implements HTMLOptionElement {

	private Object selected = null;
	
	private String text;

	/**
	 * <p>Constructor for HTMLOptionElementImpl.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 */
	public HTMLOptionElementImpl(final String text) {
		super(text);
	}

	/**
	 * <p>Constructor for HTMLOptionElementImpl.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @param value a {@link java.lang.String} object.
	 */
	public HTMLOptionElementImpl(final String text, final String value) {
		super(text);
		this.text = text;
		setValue(value);
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean isDefaultSelected() {
		return getAttributeAsBoolean("selected");
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDisabled() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLFormElement getForm() {
		Node parent = getParentNode();
		while (parent != null && !(parent instanceof HTMLFormElement)) {
			parent = parent.getParentNode();
		}
		return (HTMLFormElement) parent;
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
	public Boolean isSelected() {
		final Object parent = getParentNode();
		if (parent instanceof HTMLSelectElement) {
			final HTMLSelectElement selectElement = (HTMLSelectElement) parent;
			if (selectElement.isMultiple()) {
				return this.selected != null || getAttributeAsBoolean("selected");
			} else {
				if (selected == null) {
					return getAttributeAsBoolean("selected");
				} else {
					if (selected instanceof Boolean) {
						return (Boolean) this.selected;
					} else {
						return getAttributeAsBoolean("selected");
					}
				}
			}
		}
		return null;
	}


	/** {@inheritDoc} */
	@Override
	public String getText() {
		return Strings.isNotBlank(text) ? text : getRawInnerText(false);
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
	public void setSelected(Object selected) {
		if (selected instanceof Boolean) {
			this.selected = selected;
		} else{
			if (selected == null)
				this.selected = null;
			else this.selected = true;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>setText.</p>
	 */
	public void setText(String text) {
		this.text = text;
		setTextContent(text);
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		setAttribute("value", value);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new BlockRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLOptionElement]";
	}
}
