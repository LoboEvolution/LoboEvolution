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
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLOptionElement;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

/**
 * <p>HTMLOptionElementImpl class.</p>
 *
 *
 *
 */
public class HTMLOptionElementImpl extends HTMLElementImpl implements HTMLOptionElement {

	private Boolean selected = null;
	
	private String text;

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
		/*final Object parent = getParentNode();
		if (parent instanceof HTMLSelectElement) {
			final HTMLOptionsCollectionImpl options = (HTMLOptionsCollectionImpl) ((HTMLSelectElement) parent).getOptions();
			return options.indexOf(this);
		} else {
			return -1;
		}*/
		
		return -1; //TODO
	}

	/** {@inheritDoc} */
	@Override
	public String getLabel() {
		return getAttribute("label");
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSelected() {
		if (this.selected == null) {
			return isDefaultSelected();
		} else {
			return this.selected;
		}
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
	public void setSelected(boolean selected) {
		this.selected = selected;
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
	public String toString() {
		return "[object HTMLOptionElement]";
	}
}
