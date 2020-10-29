/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Jan 15, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.html.control.TextAreaControl;
import org.loboevolution.html.dom.HTMLFormElement;
import org.loboevolution.html.dom.HTMLTextAreaElement;
import org.loboevolution.html.dom.input.TextArea;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>HTMLTextAreaElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLTextAreaElementImpl extends HTMLAbstractUIElement implements HTMLTextAreaElement {
	
	private TextArea text;

	/**
	 * <p>Constructor for HTMLTextAreaElementImpl.</p>
	 */
	public HTMLTextAreaElementImpl() {
		super("TEXTAREA");
	}

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
	public String getAccessKey() {
		return getAttribute("accessKey");
	}

	/** {@inheritDoc} */
	@Override
	public int getCols() {
		String cols = this.getAttribute("cols");
		return Strings.isNotBlank(cols) ? Integer.parseInt(cols) : -1;
	}

	/** {@inheritDoc} */
	@Override
	public String getDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getDisabled() {
		final String disabled = getAttribute("disabled");
		return disabled != null;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLFormElement getForm() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return getAttribute("name");
	}

	/** {@inheritDoc} */
	@Override
	public boolean getReadOnly() {
		final String readonly = getAttribute("readonly");
		return readonly != null;
	}

	/** {@inheritDoc} */
	@Override
	public int getRows() {
		String rows = this.getAttribute("rows");
		return Strings.isNotBlank(rows) ? Integer.parseInt(rows) : -1;
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
		return getAttribute("type");
	}

	/** {@inheritDoc} */
	@Override
	public String getValue() {
		return getText();
	}

	/** {@inheritDoc} */
	@Override
	public void select() {
		if(text!= null) text.selectAll();
		
	}

	/** {@inheritDoc} */
	@Override
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setCols(int cols) {
		setAttribute("cols", String.valueOf(cols));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setDefaultValue(String defaultValue) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setDisabled(boolean disabled) {
		setAttribute("disabled", String.valueOf(disabled));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setName(String name) {
		setAttribute("name", name);
		
	}

	/** {@inheritDoc} */
	@Override
	public void setReadOnly(boolean readOnly) {
		setAttribute("readonly", String.valueOf(readOnly));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setRows(int rows) {
		setAttribute("rows", String.valueOf(rows));
		
	}

	/** {@inheritDoc} */
	@Override
	public void setTabIndex(int tabIndex) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		
	}
	
	/** {@inheritDoc} */
	@Override
	public int getMaxLength() {
		try {
			final String maxLength = getAttribute("maxlength");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return Integer.MAX_VALUE;
		}
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param ic a {@link org.loboevolution.html.control.TextAreaControl} object.
	 */
	public void draw(TextAreaControl ic) {
		text = new TextArea(this, ic);
	}
	
	
	private String getText() {
		StringBuilder text = new StringBuilder();
		if (hasChildNodes()) {
			NodeList children = getChildNodes();
			for (Node child : Nodes.iterable(children)) {
				if (child.getNodeType() == Node.TEXT_NODE) {
					String nodeValue = child.getNodeValue();
					String childText = "";
					nodeValue = nodeValue.replace('\n', ' ');
					nodeValue = nodeValue.replace('\r', ' ');
					nodeValue = nodeValue.replace('\t', ' ');
					childText = nodeValue;
					text.append(childText).append(" ");
				}
			}
		}

		if (text.length() > 0) {
			return text.substring(0, text.length() - 1);
		} else {
			return text.toString();
		}
	}
}
