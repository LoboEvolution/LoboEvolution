/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
import org.w3c.dom.NodeList;


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
 * See also the
 * Object Model (DOM) Level 2 HTML Specification</p>.
 */
public interface HTMLElement extends Element {
	
	/**
	 * The element's identifier. See the id attribute definition in HTML 4.01.
	 *
	 * @return the id
	 */
	public String getId();

	/**
	 * The element's identifier. See the id attribute definition in HTML 4.01.
	 *
	 * @param id the new id
	 */
	public void setId(String id);

	/**
	 * The element's advisory title. See the title attribute definition in HTML
	 * 4.01.
	 *
	 * @return the title
	 */
	public String getTitle();

	/**
	 * The element's advisory title. See the title attribute definition in HTML
	 * 4.01.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title);

	/**
	 * Language code defined in RFC 1766. See the lang attribute definition in
	 * HTML 4.01.
	 *
	 * @return the lang
	 */
	public String getLang();

	/**
	 * Language code defined in RFC 1766. See the lang attribute definition in
	 * HTML 4.01.
	 *
	 * @param lang the new lang
	 */
	public void setLang(String lang);

	/**
	 * Specifies the base direction of directionally neutral text and the
	 * directionality of tables. See the dir attribute definition in HTML 4.01.
	 *
	 * @return the dir
	 */
	public String getDir();

	/**
	 * Specifies the base direction of directionally neutral text and the
	 * directionality of tables. See the dir attribute definition in HTML 4.01.
	 *
	 * @param dir the new dir
	 */
	public void setDir(String dir);

	/**
	 * The class attribute of the element. This attribute has been renamed due
	 * to conflicts with the "class" keyword exposed by many languages. See the
	 * class attribute definition in HTML 4.01.
	 *
	 * @return the class name
	 */
	public String getClassName();

	/**
	 * The class attribute of the element. This attribute has been renamed due
	 * to conflicts with the "class" keyword exposed by many languages. See the
	 * class attribute definition in HTML 4.01.
	 *
	 * @return the inner html
	 */

	public String getInnerHTML();

	/**
	 * Sets the inner html.
	 *
	 * @param innerHTML the new inner html
	 */
	public void setInnerHTML(String innerHTML);

	/**
	 * Gets the outer html.
	 *
	 * @return the outer html
	 */
	public String getOuterHTML();

	/**
	 * Sets the outer html.
	 *
	 * @param outerHTML the new outer html
	 */
	public void setOuterHTML(String outerHTML);

	/**
	 * Insert adjacent html.
	 *
	 * @param position the position
	 * @param text the text
	 */
	public void insertAdjacentHTML(String position, String text);

	/**
	 * Sets the class name.
	 *
	 * @param className the new class name
	 */
	public void setClassName(String className);

	/**
	 * Gets the class list.
	 *
	 * @return the class list
	 */
	public DOMTokenList getClassList();

	/**
	 * Gets the dataset.
	 *
	 * @return the dataset
	 */
	public DOMStringMap getDataset();

	/**
	 * Gets the hidden.
	 *
	 * @return the hidden
	 */
	public boolean getHidden();

	/**
	 * Click.
	 */
	public void click();
	
	/**
	 * Scroll into view.
	 *
	 * @param top the top
	 */
	public void scrollIntoView(boolean top);

	/**
	 * Gets the tab index.
	 *
	 * @return the tab index
	 */
	public int getTabIndex();

	/**
	 * Sets the tab index.
	 *
	 * @param tabIndex the new tab index
	 */
	public void setTabIndex(int tabIndex);

	/**
	 * Focus.
	 */
	public void focus();

	/**
	 * Blur.
	 */
	public void blur();

	/**
	 * Gets the access key.
	 *
	 * @return the access key
	 */
	public String getAccessKey();

	/**
	 * Sets the access key.
	 *
	 * @param accessKey the new access key
	 */
	public void setAccessKey(String accessKey);

	/**
	 * Gets the access key label.
	 *
	 * @return the access key label
	 */
	public String getAccessKeyLabel();

	/**
	 * Gets the draggable.
	 *
	 * @return the draggable
	 */
	public boolean getDraggable();

	/**
	 * Sets the draggable.
	 *
	 * @param draggable the new draggable
	 */
	public void setDraggable(boolean draggable);

	/**
	 * Gets the content editable.
	 *
	 * @return the content editable
	 */
	public String getContentEditable();

	/**
	 * Sets the content editable.
	 *
	 * @param contentEditable the new content editable
	 */
	public void setContentEditable(String contentEditable);

	/**
	 * Gets the checks if is content editable.
	 *
	 * @return the checks if is content editable
	 */
	public boolean getIsContentEditable();

	/**
	 * Gets the context menu.
	 *
	 * @return the context menu
	 */
	public HTMLMenuElement getContextMenu();

	/**
	 * Sets the context menu.
	 *
	 * @param contextMenu the new context menu
	 */
	public void setContextMenu(HTMLMenuElement contextMenu);

	/**
	 * Gets the spellcheck.
	 *
	 * @return the spellcheck
	 */
	public String getSpellcheck();

	/**
	 * Sets the spellcheck.
	 *
	 * @param spellcheck the new spellcheck
	 */
	public void setSpellcheck(String spellcheck);

	/**
	 * Gets the command type.
	 *
	 * @return the command type
	 */
	public String getCommandType();

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel();

	/**
	 * Gets the icon.
	 *
	 * @return the icon
	 */
	public String getIcon();

	/**
	 * Gets the disabled.
	 *
	 * @return the disabled
	 */
	public boolean getDisabled();

	/**
	 * Gets the checked.
	 *
	 * @return the checked
	 */
	public boolean getChecked();

	/**
	 * Gets the offset parent.
	 *
	 * @return the offset parent
	 */
	public Element getOffsetParent();

	/**
	 * Gets the offset top.
	 *
	 * @return the offset top
	 */
	public int getOffsetTop();

	/**
	 * Gets the offset left.
	 *
	 * @return the offset left
	 */
	public int getOffsetLeft();

	/**
	 * Gets the offset width.
	 *
	 * @return the offset width
	 */
	public int getOffsetWidth();

	/**
	 * Gets the offset height.
	 *
	 * @return the offset height
	 */
	public int getOffsetHeight();

	/**
	 * Gets the item scope.
	 *
	 * @return the item scope
	 */
	public boolean getItemScope();

	/**
	 * Sets the item scope.
	 *
	 * @param itemScope the new item scope
	 */
	public void setItemScope(boolean itemScope);

	/**
	 * Gets the item type.
	 *
	 * @return the item type
	 */
	public String getItemType();

	/**
	 * Sets the item type.
	 *
	 * @param itemType the new item type
	 */
	public void setItemType(String itemType);

	/**
	 * Gets the item id.
	 *
	 * @return the item id
	 */
	public String getItemId();

	/**
	 * Sets the item id.
	 *
	 * @param itemId the new item id
	 */
	public void setItemId(String itemId);

	/**
	 * Gets the item ref.
	 *
	 * @return the item ref
	 */
	public DOMSettableTokenList getItemRef();

	/**
	 * Sets the item ref.
	 *
	 * @param itemRef the new item ref
	 */
	public void setItemRef(String itemRef);

	/**
	 * Gets the item prop.
	 *
	 * @return the item prop
	 */
	public DOMSettableTokenList getItemProp();

	/**
	 * Sets the item prop.
	 *
	 * @param itemProp the new item prop
	 */
	public void setItemProp(String itemProp);

	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public HTMLPropertiesCollection getProperties();

	/**
	 * Gets the item value.
	 *
	 * @return the item value
	 */
	public Object getItemValue();

	/**
	 * Sets the item value.
	 *
	 * @param itemValue the new item value
	 */
	public void setItemValue(Object itemValue);
	
	/**
	 * Adds the event listener.
	 *
	 * @param script the script
	 * @param function the function
	 */
	public void addEventListener(String script, String function);
	
	/**
	 * Removes the event listener.
	 *
	 * @param script the script
	 * @param function the function
	 */
	public void removeEventListener(String script, String function);
	
	/**
	 * Query selector.
	 *
	 * @param selectors the selectors
	 * @return the element
	 */
	public Element querySelector(String selectors);
	
	/**
	 * Query selector all.
	 *
	 * @param selectors the selectors
	 * @return the node list
	 */
	public NodeList querySelectorAll(String selectors);

}
