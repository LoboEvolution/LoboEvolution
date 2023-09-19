/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
