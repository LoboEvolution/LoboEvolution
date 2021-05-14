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
/*
 * Created on Jan 15, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.TextAreaControl;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLTextAreaElement;
import org.loboevolution.html.dom.input.TextArea;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.NodeType;

/**
 * <p>HTMLTextAreaElementImpl class.</p>
 */
public class HTMLTextAreaElementImpl extends HTMLBasicInputElement implements HTMLTextAreaElement {
	
	private TextArea textArea;

	private String value;

	private boolean isSet = false;

	/**
	 * <p>Constructor for HTMLTextAreaElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTextAreaElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public int getCols() {	
		String cols = this.getAttribute("cols");
		if(Strings.isNotBlank(cols)  && Strings.isNumeric(cols)) {
			Float rowsInt = Float.parseFloat(cols);
			if(rowsInt > -1) {
				return rowsInt.intValue();
			} else {
				return 20;
			}
		}
		return 20;
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
	public String getName() {
		return getAttribute("name");
	}



	/** {@inheritDoc} */
	@Override
	public int getRows() {
		String rows = this.getAttribute("rows");
		if(Strings.isNotBlank(rows)  && Strings.isNumeric(rows)) {
			Float rowsInt = Float.parseFloat(rows);
			if(rowsInt > -1) {
				return rowsInt.intValue();
			} else {
				return 2;
			}
		}
		return 2;
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		return isSet ? this.value : getText();
	}

	/** {@inheritDoc} */
	@Override
	public void select() {
		if(textArea!= null) textArea.selectAll();
		
	}

	/** {@inheritDoc} */
	@Override
	public void setCols(int cols) {
		setAttribute("cols", String.valueOf(cols));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setRows(int rows) {
		setAttribute("rows", String.valueOf(rows));
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		this.value = Strings.isBlank(value) ? "" : value;
		this.isSet = true;
		setSelectionStart(Strings.isBlank(value) ? 0 : value.length());
        setSelectionEnd(Strings.isBlank(value) ? 0 : value.length());
	}

	/**
	 * <p>blur.</p>
	 */
	public void blur() {
		if(textArea!= null) {textArea.blur();} else {setFocusable(false);}
	}
	
	/**
	 * <p>focus.</p>
	 */
	public void focus() {
		if(textArea!= null) {textArea.focus();} else {setFocusable(true);}
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param ic a {@link org.loboevolution.html.control.TextAreaControl} object.
	 */
	public void draw(TextAreaControl ic) {
		textArea = new TextArea(this, ic);
	}
	
	
	private String getText() {
		StringBuilder text = new StringBuilder();
		if (hasChildNodes()) {
			NodeListImpl children = (NodeListImpl)getChildNodes();
			children.forEach(child -> {
				if (child.getNodeType() == NodeType.TEXT_NODE) {
					String nodeValue = child.getNodeValue();
					String childText;
					nodeValue = nodeValue.replace('\n', ' ');
					nodeValue = nodeValue.replace('\r', ' ');
					nodeValue = nodeValue.replace('\t', ' ');
					childText = nodeValue;
					text.append(childText).append(" ");
				}
			});
		}

		if (text.length() > 0) {
			return text.substring(0, text.length() - 1);
		} else {
			return text.toString();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getWrap() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setWrap(String wrap) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTextAreaElement]";
	}
}
