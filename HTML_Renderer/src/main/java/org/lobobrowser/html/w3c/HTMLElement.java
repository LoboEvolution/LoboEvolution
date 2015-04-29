/*
 * GNU LESSER GENERAL LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Copyright (c) 2003 World Wide Web Consortium, (Massachusetts Institute of
 * Technology, Institut National de Recherche en Informatique et en Automatique,
 * Keio University). All Rights Reserved. This program is distributed under the
 * W3C's Software Intellectual Property License. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
 * W3C License http://www.w3.org/Consortium/Legal/ for more details.
 */

package org.lobobrowser.html.w3c;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * All HTML element public interfaces derive from this class. Elements that only expose
 * the HTML core attributes are represented by the base <code>HTMLElement</code>
 * public interface. These elements are as follows: special: SUB, SUP, SPAN, BDOfont:
 * TT, I, B, U, S, STRIKE, BIG, SMALL phrase: EM, STRONG, DFN, CODE, SAMP, KBD,
 * VAR, CITE, ACRONYM, ABBRlist: DD, DTNOFRAMES, NOSCRIPTADDRESS, CENTERThe
 * <code>style</code> attribute of an HTML element is accessible through the
 * <code>ElementCSSInlineStyle</code> public interface which is defined in the CSS
 * module [<a
 * href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>DOM Level 2
 * Style Sheets and CSS</a>].
 * <p>
 * See also the Object Model (DOM) Level 2 HTML Specification
 * </p>
 * .
 */
public interface HTMLElement extends Element {

    /**
     * The element's identifier. See the id attribute definition in HTML 4.01.
     *
     * @return the id
     */
    String getId();

    /**
     * The element's identifier. See the id attribute definition in HTML 4.01.
     *
     * @param id
     *            the new id
     */
    void setId(String id);

    /**
     * The element's advisory title. See the title attribute definition in HTML
     * 4.01.
     *
     * @return the title
     */
    String getTitle();

    /**
     * The element's advisory title. See the title attribute definition in HTML
     * 4.01.
     *
     * @param title
     *            the new title
     */
    void setTitle(String title);

    /**
     * Language code defined in RFC 1766. See the lang attribute definition in
     * HTML 4.01.
     *
     * @return the lang
     */
    String getLang();

    /**
     * Language code defined in RFC 1766. See the lang attribute definition in
     * HTML 4.01.
     *
     * @param lang
     *            the new lang
     */
    void setLang(String lang);

    /**
     * Specifies the base direction of directionally neutral text and the
     * directionality of tables. See the dir attribute definition in HTML 4.01.
     *
     * @return the dir
     */
    String getDir();

    /**
     * Specifies the base direction of directionally neutral text and the
     * directionality of tables. See the dir attribute definition in HTML 4.01.
     *
     * @param dir
     *            the new dir
     */
    void setDir(String dir);

    /**
     * The class attribute of the element. This attribute has been renamed due
     * to conflicts with the "class" keyword exposed by many languages. See the
     * class attribute definition in HTML 4.01.
     *
     * @return the class name
     */
    String getClassName();

    /**
     * The class attribute of the element. This attribute has been renamed due
     * to conflicts with the "class" keyword exposed by many languages. See the
     * class attribute definition in HTML 4.01.
     *
     * @return the inner html
     */

    String getInnerHTML();

    /**
     * Sets the inner html.
     *
     * @param innerHTML
     *            the new inner html
     */
    void setInnerHTML(String innerHTML);

    /**
     * Gets the outer html.
     *
     * @return the outer html
     */
    String getOuterHTML();

    /**
     * Sets the outer html.
     *
     * @param outerHTML
     *            the new outer html
     */
    void setOuterHTML(String outerHTML);

    /**
     * Insert adjacent html.
     *
     * @param position
     *            the position
     * @param text
     *            the text
     */
    void insertAdjacentHTML(String position, String text);

    /**
     * Sets the class name.
     *
     * @param className
     *            the new class name
     */
    void setClassName(String className);

    /**
     * Gets the class list.
     *
     * @return the class list
     */
    DOMTokenList getClassList();

    /**
     * Gets the dataset.
     *
     * @return the dataset
     */
    DOMStringMap getDataset();

    /**
     * Gets the hidden.
     *
     * @return the hidden
     */
    boolean getHidden();

    /**
     * Click.
     */
    void click();

    /**
     * Scroll into view.
     *
     * @param top
     *            the top
     */
    void scrollIntoView(boolean top);

    /**
     * Gets the tab index.
     *
     * @return the tab index
     */
    int getTabIndex();

    /**
     * Sets the tab index.
     *
     * @param tabIndex
     *            the new tab index
     */
    void setTabIndex(int tabIndex);

    /**
     * Focus.
     */
    void focus();

    /**
     * Blur.
     */
    void blur();

    /**
     * Gets the access key.
     *
     * @return the access key
     */
    String getAccessKey();

    /**
     * Sets the access key.
     *
     * @param accessKey
     *            the new access key
     */
    void setAccessKey(String accessKey);

    /**
     * Gets the access key label.
     *
     * @return the access key label
     */
    String getAccessKeyLabel();

    /**
     * Gets the draggable.
     *
     * @return the draggable
     */
    boolean getDraggable();

    /**
     * Sets the draggable.
     *
     * @param draggable
     *            the new draggable
     */
    void setDraggable(boolean draggable);

    /**
     * Gets the content editable.
     *
     * @return the content editable
     */
    String getContentEditable();

    /**
     * Sets the content editable.
     *
     * @param contentEditable
     *            the new content editable
     */
    void setContentEditable(String contentEditable);

    /**
     * Gets the checks if is content editable.
     *
     * @return the checks if is content editable
     */
    boolean getIsContentEditable();

    /**
     * Gets the context menu.
     *
     * @return the context menu
     */
    HTMLMenuElement getContextMenu();

    /**
     * Sets the context menu.
     *
     * @param contextMenu
     *            the new context menu
     */
    void setContextMenu(HTMLMenuElement contextMenu);

    /**
     * Gets the spellcheck.
     *
     * @return the spellcheck
     */
    String getSpellcheck();

    /**
     * Sets the spellcheck.
     *
     * @param spellcheck
     *            the new spellcheck
     */
    void setSpellcheck(String spellcheck);

    /**
     * Gets the command type.
     *
     * @return the command type
     */
    String getCommandType();

    /**
     * Gets the label.
     *
     * @return the label
     */
    String getLabel();

    /**
     * Gets the icon.
     *
     * @return the icon
     */
    String getIcon();

    /**
     * Gets the disabled.
     *
     * @return the disabled
     */
    boolean getDisabled();

    /**
     * Gets the checked.
     *
     * @return the checked
     */
    boolean getChecked();

    /**
     * Gets the offset parent.
     *
     * @return the offset parent
     */
    Element getOffsetParent();

    /**
     * Gets the offset top.
     *
     * @return the offset top
     */
    int getOffsetTop();

    /**
     * Gets the offset left.
     *
     * @return the offset left
     */
    int getOffsetLeft();

    /**
     * Gets the offset width.
     *
     * @return the offset width
     */
    int getOffsetWidth();

    /**
     * Gets the offset height.
     *
     * @return the offset height
     */
    int getOffsetHeight();

    /**
     * Gets the item scope.
     *
     * @return the item scope
     */
    boolean getItemScope();

    /**
     * Sets the item scope.
     *
     * @param itemScope
     *            the new item scope
     */
    void setItemScope(boolean itemScope);

    /**
     * Gets the item type.
     *
     * @return the item type
     */
    String getItemType();

    /**
     * Sets the item type.
     *
     * @param itemType
     *            the new item type
     */
    void setItemType(String itemType);

    /**
     * Gets the item id.
     *
     * @return the item id
     */
    String getItemId();

    /**
     * Sets the item id.
     *
     * @param itemId
     *            the new item id
     */
    void setItemId(String itemId);

    /**
     * Gets the item ref.
     *
     * @return the item ref
     */
    DOMSettableTokenList getItemRef();

    /**
     * Sets the item ref.
     *
     * @param itemRef
     *            the new item ref
     */
    void setItemRef(String itemRef);

    /**
     * Gets the item prop.
     *
     * @return the item prop
     */
    DOMSettableTokenList getItemProp();

    /**
     * Sets the item prop.
     *
     * @param itemProp
     *            the new item prop
     */
    void setItemProp(String itemProp);

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    HTMLPropertiesCollection getProperties();

    /**
     * Gets the item value.
     *
     * @return the item value
     */
    Object getItemValue();

    /**
     * Sets the item value.
     *
     * @param itemValue
     *            the new item value
     */
    void setItemValue(Object itemValue);

    /**
     * Query selector.
     *
     * @param selectors
     *            the selectors
     * @return the element
     */
    Element querySelector(String selectors);

    /**
     * Query selector all.
     *
     * @param selectors
     *            the selectors
     * @return the node list
     */
    NodeList querySelectorAll(String selectors);

}
