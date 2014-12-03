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
 * Copyright (c) 2003 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.html.w3c;

import org.w3c.dom.Element;

/**
 * All HTML element interfaces derive from this class. Elements that only expose
 * the HTML core attributes are represented by the base <code>HTMLElement</code>
 * interface. These elements are as follows: special: SUB, SUP, SPAN, BDOfont:
 * TT, I, B, U, S, STRIKE, BIG, SMALL phrase: EM, STRONG, DFN, CODE, SAMP, KBD,
 * VAR, CITE, ACRONYM, ABBRlist: DD, DTNOFRAMES, NOSCRIPTADDRESS, CENTERThe
 * <code>style</code> attribute of an HTML element is accessible through the
 * <code>ElementCSSInlineStyle</code> interface which is defined in the CSS
 * module [<a
 * href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>DOM Level 2
 * Style Sheets and CSS</a>].
 * <p>
 * See also the <a
 * 
 * Object Model (DOM) Level 2 HTML Specification</a>.
 */
public interface HTMLElement extends Element {
	/**
	 * The element's identifier. See the id attribute definition in HTML 4.01.
	 */
	public String getId();

	/**
	 * The element's identifier. See the id attribute definition in HTML 4.01.
	 */
	public void setId(String id);

	/**
	 * The element's advisory title. See the title attribute definition in HTML
	 * 4.01.
	 */
	public String getTitle();

	/**
	 * The element's advisory title. See the title attribute definition in HTML
	 * 4.01.
	 */
	public void setTitle(String title);

	/**
	 * Language code defined in RFC 1766. See the lang attribute definition in
	 * HTML 4.01.
	 */
	public String getLang();

	/**
	 * Language code defined in RFC 1766. See the lang attribute definition in
	 * HTML 4.01.
	 */
	public void setLang(String lang);

	/**
	 * Specifies the base direction of directionally neutral text and the
	 * directionality of tables. See the dir attribute definition in HTML 4.01.
	 */
	public String getDir();

	/**
	 * Specifies the base direction of directionally neutral text and the
	 * directionality of tables. See the dir attribute definition in HTML 4.01.
	 */
	public void setDir(String dir);

	/**
	 * The class attribute of the element. This attribute has been renamed due
	 * to conflicts with the "class" keyword exposed by many languages. See the
	 * class attribute definition in HTML 4.01.
	 */
	public String getClassName();

	/**
	 * The class attribute of the element. This attribute has been renamed due
	 * to conflicts with the "class" keyword exposed by many languages. See the
	 * class attribute definition in HTML 4.01.
	 */

	public String getInnerHTML();

	public void setInnerHTML(String innerHTML);

	public String getOuterHTML();

	public void setOuterHTML(String outerHTML);

	public void insertAdjacentHTML(String position, String text);

	public void setClassName(String className);

	public DOMTokenList getClassList();

	public DOMStringMap getDataset();

	public boolean getHidden();

	public void setHidden(boolean hidden);

	public void click();

	public void scrollIntoView();

	public void scrollIntoView(boolean top);

	public int getTabIndex();

	public void setTabIndex(int tabIndex);

	public void focus();

	public void blur();

	public String getAccessKey();

	public void setAccessKey(String accessKey);

	public String getAccessKeyLabel();

	public boolean getDraggable();

	public void setDraggable(boolean draggable);

	public String getContentEditable();

	public void setContentEditable(String contentEditable);

	public boolean getIsContentEditable();

	public HTMLMenuElement getContextMenu();

	public void setContextMenu(HTMLMenuElement contextMenu);

	public String getSpellcheck();

	public void setSpellcheck(String spellcheck);

	public String getCommandType();

	public String getLabel();

	public String getIcon();

	public boolean getDisabled();

	public boolean getChecked();

	public Element getOffsetParent();

	public int getOffsetTop();

	public int getOffsetLeft();

	public int getOffsetWidth();

	public int getOffsetHeight();

	public boolean getItemScope();

	public void setItemScope(boolean itemScope);

	public String getItemType();

	public void setItemType(String itemType);

	public String getItemId();

	public void setItemId(String itemId);

	public DOMSettableTokenList getItemRef();

	public void setItemRef(String itemRef);

	public DOMSettableTokenList getItemProp();

	public void setItemProp(String itemProp);

	public HTMLPropertiesCollection getProperties();

	public Object getItemValue();

	public void setItemValue(Object itemValue);

}
