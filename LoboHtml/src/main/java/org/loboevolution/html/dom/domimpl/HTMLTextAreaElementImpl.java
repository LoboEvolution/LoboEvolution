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
	
	private TextArea textArea;
	
	private String value;
	
	private boolean isSet = false;
	
	private boolean isMaxSet = false;
	
	private boolean isMinSet = false;
	
	private int maxlength = 0;
	
	private int minlength = 0;
	
	private int selectionStart = 0;
	
	private int selectionEnd = 0;
	
	private boolean focusable = false;

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
	public boolean getReadOnly() {
		final String readonly = getAttribute("readonly");
		return "true".equals(readonly) || "readonly".equals(readonly);
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
		return isSet ? this.value : getText();
	}

	/** {@inheritDoc} */
	@Override
	public void select() {
		if(textArea!= null) textArea.selectAll();
		
	}

	/** {@inheritDoc} */
	@Override
	public void setAccessKey(String accessKey) {
		setAttribute("accessKey", accessKey);
	}

	/** {@inheritDoc} */
	@Override
	public void setCols(Object cols) {
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
	public void setRows(Object rows) {
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
		this.value = Strings.isBlank(value) ? "" : value;
		this.isSet = true;
		setSelectionStart(Strings.isBlank(value) ? 0 : value.length());
        setSelectionEnd(Strings.isBlank(value) ? 0 : value.length());
	}
	
	/** {@inheritDoc} */
	@Override
	public int getMaxLength() {		
		if(isMaxSet) {
			return this.maxlength;
		}
		
		try {
			final String maxLength = getAttribute("maxlength");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return Integer.MAX_VALUE;
		}
	}
	
	/** {@inheritDoc} */
	public void setMaxLength(int value) {
		this.maxlength = value;
		this.isMaxSet = true;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public int getMinLength() {		
		if(isMinSet) {
			return this.minlength;
		}
		
		try {
			final String maxLength = getAttribute("minlength");
			return Integer.parseInt(maxLength.trim());
		} catch (Exception e) {
			return -1;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public void setMinLength(int value) {
		this.minlength = value;
		this.isMinSet = true;
	}

	public int getTextLength() {
		return getValue().length();
	}
	
	
	/** {@inheritDoc} */
	@Override
    public int getSelectionStart() {
		final int textLenght = getTextLength();
        return (selectionStart > textLenght || selectionStart < 0) ? textLenght : selectionStart;
    }

    /** {@inheritDoc} */
	@Override
    public void setSelectionStart(int start) {
       this.selectionStart = start;
    }
	
	/** {@inheritDoc} */
	@Override
    public int getSelectionEnd() {
		final int textLenght = getTextLength();
        return (selectionEnd > textLenght || selectionEnd < 0) ? textLenght : selectionEnd;
    }

	/** {@inheritDoc} */
	@Override
    public void setSelectionEnd(int end) {
        this.selectionEnd = end;
    }

	/** {@inheritDoc} */
	@Override
    public void setSelectionRange(int start, int end) {
        setSelectionStart(start);
        setSelectionEnd(end);
    }
	
	/** {@inheritDoc} */
	@Override
	public void blur() {
		if(textArea!= null) {textArea.blur();} else {focusable = false;}
	}
	
	/** {@inheritDoc} */
	@Override
	public void focus() {
		if(textArea!= null) {textArea.focus();} else {focusable = true;}
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
	
	/**
	 * @return the focusable
	 */
	public boolean isFocusable() {
		return focusable;
	}

	/**
	 * @param focusable the focusable to set
	 */
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTextAreaElement]";
	}
}
