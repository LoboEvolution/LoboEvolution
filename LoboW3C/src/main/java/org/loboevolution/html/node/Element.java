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

package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLCollection;
import org.mozilla.javascript.Function;

/**
 * Element is the most general base class from which all objects in a Document inherit.
 * It only has methods and properties common to all kinds of elements.
 * More specific classes inherit from Element.
 */
public interface Element extends Node, InnerHTML, NonDocumentTypeChildNode, ParentNode {

    /**
     * <p>getAttributes.</p>
     *
     * @return a {@link org.loboevolution.html.node.NamedNodeMap} object.
     */
    NamedNodeMap getAttributes();

    /**
     * Allows for manipulation of element's class content attribute as a set of whitespace-separated tokens through a DOMTokenList object.
     *
     * @return a {@link org.loboevolution.html.node.DOMTokenList} object.
     */
    DOMTokenList getClassList();

    /**
     * Returns the value of element's class content attribute. Can be set to change it.
     *
     * @return a {@link java.lang.String} object.
     */
    String getClassName();


    /**
     * <p>setClassName.</p>
     *
     * @param className a {@link java.lang.String} object.
     */
    void setClassName(String className);


    /**
     * <p>getClientHeight.</p>
     *
     * @return a int.
     */
    int getClientHeight();


    /**
     * <p>getClientLeft.</p>
     *
     * @return a int.
     */
    int getClientLeft();


    /**
     * <p>getClientTop.</p>
     *
     * @return a int.
     */
    int getClientTop();


    /**
     * <p>getClientWidth.</p>
     *
     * @return a int.
     */
    int getClientWidth();

    /**
     * Returns the value of element's id content attribute. Can be set to change it.
     *
     * @return a {@link java.lang.String} object.
     */
    String getId();


    /**
     * <p>setId.</p>
     *
     * @param id a {@link java.lang.String} object.
     */
    void setId(String id);

    /**
     * Returns the local name.
     *
     * @return a {@link java.lang.String} object.
     */
    String getLocalName();

    /**
     * Returns the namespace.
     *
     * @return a {@link java.lang.String} object.
     */
    String getNamespaceURI();


    /**
     * <p>getOnfullscreenchange.</p>
     *
     * @return a {@link org.loboevolution.html.node.events.EventListener} object.
     */
    EventListener<Event> getOnfullscreenchange();

    /**
     * <p>getOuterHTML.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getOuterHTML();

    /**
     * <p>setOuterHTML.</p>
     *
     * @param outerHTML a {@link java.lang.String} object.
     */
    void setOuterHTML(String outerHTML);

    /**
     * Returns the namespace prefix.
     *
     * @return a {@link java.lang.String} object.
     */
    String getPrefix();


    /**
     * <p>getScrollHeight.</p>
     *
     * @return a double.
     */
    double getScrollHeight();


    /**
     * <p>getScrollLeft.</p>
     *
     * @return a double.
     */
    double getScrollLeft();


    /**
     * <p>setScrollLeft.</p>
     *
     * @param scrollLeft a int.
     */
    void setScrollLeft(int scrollLeft);


    /**
     * <p>getScrollTop.</p>
     *
     * @return a double.
     */
    double getScrollTop();


    /**
     * <p>setScrollTop.</p>
     *
     * @param scrollTop a int.
     */
    void setScrollTop(int scrollTop);


    /**
     * <p>getScrollWidth.</p>
     *
     * @return a double.
     */
    double getScrollWidth();

    /**
     * Returns the value of element's slot content attribute. Can be set to change it.
     *
     * @return a {@link java.lang.String} object.
     */
    String getSlot();


    /**
     * <p>setSlot.</p>
     *
     * @param slot a {@link java.lang.String} object.
     */
    void setSlot(String slot);

    /**
     * Returns the HTML-uppercased qualified name.
     *
     * @return a {@link java.lang.String} object.
     */
    String getTagName();


    /**
     * Returns the first (starting at element) inclusive ancestor that matches selectors, and null otherwise.
     *
     * @param selector a {@link java.lang.String} object.
     * @param <E>      a E object.
     * @return a E object.
     */
    <E extends Element> E closest(String selector);

    /**
     * Returns element's first attribute whose qualified name is qualifiedName,
     * and null if there is no such attribute otherwise.
     *
     * @param qualifiedName a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String getAttribute(String qualifiedName);

    /**
     * Returns element's attribute whose namespace is namespace and local name is localName, and null if there is no such attribute otherwise.
     *
     * @param namespace a {@link java.lang.String} object.
     * @param localName a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String getAttributeNS(String namespace, String localName);

    /**
     * Returns the qualified names of all element's attributes. Can contain duplicates.
     *
     * @return an array of {@link java.lang.String} objects.
     */
    String[] getAttributeNames();


    /**
     * <p>getAttributeNode.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.Attr} object.
     */
    Attr getAttributeNode(String name);


    /**
     * <p>getAttributeNodeNS.</p>
     *
     * @param namespaceURI a {@link java.lang.String} object.
     * @param localName    a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.node.Attr} object.
     */
    Attr getAttributeNodeNS(String namespaceURI, String localName);

    /**
     * Returns a HTMLCollection of the elements in the object on which the method was invoked (a document or an element) that have all the classes given by classNames. The classNames argument is interpreted as a space-separated list of classes.
     *
     * @param classNames a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getElementsByClassName(String classNames);

    /**
     * <p>getElementsByTagName.</p>
     *
     * @param qualifiedName a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getElementsByTagName(String qualifiedName);

    /**
     * <p>getElementsByTagNameNS.</p>
     *
     * @param namespaceURI a {@link java.lang.String} object.
     * @param localName    a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getElementsByTagNameNS(String namespaceURI, String localName);

    /**
     * Returns true if element has an attribute whose qualified name is qualifiedName, and false otherwise.
     *
     * @param qualifiedName a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean hasAttribute(String qualifiedName);

    /**
     * Returns true if element has an attribute whose namespace is namespace and local name is localName.
     *
     * @param namespace a {@link java.lang.String} object.
     * @param localName a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean hasAttributeNS(String namespace, String localName);

    /**
     * Returns true if element has attributes, and false otherwise.
     *
     * @return a boolean.
     */
    boolean hasAttributes();

    /**
     * <p>hasPointerCapture.</p>
     *
     * @param pointerId a int.
     * @return a boolean.
     */
    boolean hasPointerCapture(int pointerId);

    /**
     * Returns true if matching selectors against element's root yields element, and false otherwise.
     *
     * @param selectors a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean matches(String selectors);

    /**
     * <p>releasePointerCapture.</p>
     *
     * @param pointerId a int.
     */
    void releasePointerCapture(int pointerId);

    /**
     * Removes element's first attribute whose qualified name is qualifiedName.
     *
     * @param qualifiedName a {@link java.lang.String} object.
     */
    void removeAttribute(String qualifiedName);

    /**
     * Removes element's attribute whose namespace is namespace and local name is localName.
     *
     * @param namespace a {@link java.lang.String} object.
     * @param localName a {@link java.lang.String} object.
     */
    void removeAttributeNS(String namespace, String localName);

    /**
     * <p>removeAttributeNode.</p>
     *
     * @param attr a {@link org.loboevolution.html.node.Attr} object.
     * @return a {@link org.loboevolution.html.node.Attr} object.
     */
    Attr removeAttributeNode(Attr attr);

    /**
     * <p>requestPointerLock.</p>
     */
    void requestPointerLock();

    /**
     * <p>scroll.</p>
     *
     * @param x a double.
     * @param y a double.
     */
    void scroll(int x, int y);

    /**
     * <p>scrollBy.</p>
     *
     * @param x a double.
     * @param y a double.
     */
    void scrollBy(int x, int y);

    /**
     * <p>scrollIntoView.</p>
     *
     * @param arg a boolean.
     */
    void scrollIntoView(boolean arg);

    /**
     * <p>scrollIntoView.</p>
     */
    void scrollIntoView();

    /**
     * <p>scrollTo.</p>
     *
     * @param x a double.
     * @param y a double.
     */
    void scrollTo(int x, int y);

    /**
     * Sets the value of element's first attribute whose qualified name is qualifiedName to value.
     *
     * @param qualifiedName a {@link java.lang.String} object.
     * @param value         a {@link java.lang.String} object.
     */
    void setAttribute(String qualifiedName, String value);

    /**
     * Sets the value of element's attribute whose namespace is namespace and local name is localName to value.
     *
     * @param namespace     a {@link java.lang.String} object.
     * @param qualifiedName a {@link java.lang.String} object.
     * @param value         a {@link java.lang.String} object.
     */
    void setAttributeNS(String namespace, String qualifiedName, String value);


    /**
     * <p>setAttributeNode.</p>
     *
     * @param attr a {@link org.loboevolution.html.node.Attr} object.
     * @return a {@link org.loboevolution.html.node.Attr} object.
     */
    Attr setAttributeNode(Attr attr);

    /**
     * <p>setAttributeNodeNS.</p>
     *
     * @param attr a {@link org.loboevolution.html.node.Attr} object.
     * @return a {@link org.loboevolution.html.node.Attr} object.
     */
    Attr setAttributeNodeNS(Attr attr);

    /**
     * <p>setIdAttribute.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param isId a boolean.
     */
    void setIdAttribute(String name, boolean isId);

    /**
     * <p>setIdAttributeNode.</p>
     *
     * @param idAttr a {@link org.loboevolution.html.node.Attr} object.
     * @param isId   a boolean.
     */
    void setIdAttributeNode(Attr idAttr, boolean isId);

    /**
     * <p>setPointerCapture.</p>
     *
     * @param pointerId a int.
     */
    void setPointerCapture(int pointerId);

    /**
     * If force is not given, "toggles" qualifiedName, removing it if it is present and adding it if it is not present. If force is true, adds qualifiedName. If force is false, removes qualifiedName.
     * <p>
     * Returns true if qualifiedName is now present, and false otherwise.
     *
     * @param qualifiedName a {@link java.lang.String} object.
     * @param force         a boolean.
     * @return a boolean.
     */
    boolean toggleAttribute(String qualifiedName, boolean force);

    /**
     * <p>toggleAttribute.</p>
     *
     * @param qualifiedName a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean toggleAttribute(String qualifiedName);

}

