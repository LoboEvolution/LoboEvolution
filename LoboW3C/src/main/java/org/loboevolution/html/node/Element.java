package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.NamedNodeMap;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventListener;

/**
 * Element is the most general base class from which all objects in a Document inherit. It only has methods and properties common to all kinds of elements. More specific classes inherit from Element.
 */
public interface Element extends Node, InnerHTML, NonDocumentTypeChildNode, ParentNode {

    NamedNodeMap getAttributes();

    /**
     * Allows for manipulation of element's class content attribute as a set of whitespace-separated tokens through a DOMTokenList object.
     */

    DOMTokenList getClassList();

    /**
     * Returns the value of element's class content attribute. Can be set to change it.
     */

    String getClassName();


    void setClassName(String className);


    int getClientHeight();


    int getClientLeft();


    int getClientTop();


    int getClientWidth();

    /**
     * Returns the value of element's id content attribute. Can be set to change it.
     */

    String getId();


    void setId(String id);

    /**
     * Returns the local name.
     */

    String getLocalName();

    /**
     * Returns the namespace.
     */


    String getNamespaceURI();


    EventListener<Event> getOnfullscreenchange();

    String getOuterHTML();

    void setOuterHTML(String outerHTML);

    /**
     * Returns the namespace prefix.
     */


    String getPrefix();


    double getScrollHeight();


    double getScrollLeft();


    void setScrollLeft(double scrollLeft);


    double getScrollTop();


    void setScrollTop(double scrollTop);


    double getScrollWidth();

    /**
     * Returns the value of element's slot content attribute. Can be set to change it.
     */

    String getSlot();


    void setSlot(String slot);

    /**
     * Returns the HTML-uppercased qualified name.
     */

    String getTagName();


    /**
     * Returns the first (starting at element) inclusive ancestor that matches selectors, and null otherwise.
     */
    <E extends Element> E closest(String selector);

    /**
     * Returns element's first attribute whose qualified name is qualifiedName, and null if there is no such attribute otherwise.
     */

    String getAttribute(String qualifiedName);

    /**
     * Returns element's attribute whose namespace is namespace and local name is localName, and null if there is no such attribute otherwise.
     */

    String getAttributeNS(String namespace, String localName);

    /**
     * Returns the qualified names of all element's attributes. Can contain duplicates.
     */
    String[] getAttributeNames();


    Attr getAttributeNode(String name);


    Attr getAttributeNodeNS(String namespaceURI, String localName);

    /**
     * Returns a HTMLCollection of the elements in the object on which the method was invoked (a document or an element) that have all the classes given by classNames. The classNames argument is interpreted as a space-separated list of classes.
     */
    HTMLCollection getElementsByClassName(String classNames);

    HTMLCollection getElementsByTagName(String qualifiedName);

    HTMLCollection getElementsByTagNameNS(String namespaceURI, String localName);

    /**
     * Returns true if element has an attribute whose qualified name is qualifiedName, and false otherwise.
     */
    boolean hasAttribute(String qualifiedName);

    /**
     * Returns true if element has an attribute whose namespace is namespace and local name is localName.
     */
    boolean hasAttributeNS(String namespace, String localName);

    /**
     * Returns true if element has attributes, and false otherwise.
     */
    boolean hasAttributes();

    boolean hasPointerCapture(int pointerId);

    /**
     * Returns true if matching selectors against element's root yields element, and false otherwise.
     */
    boolean matches(String selectors);

    void releasePointerCapture(int pointerId);

    /**
     * Removes element's first attribute whose qualified name is qualifiedName.
     */
    void removeAttribute(String qualifiedName);

    /**
     * Removes element's attribute whose namespace is namespace and local name is localName.
     */
    void removeAttributeNS(String namespace, String localName);

    Attr removeAttributeNode(Attr attr);

    void requestPointerLock();

    void scroll();

    void scroll(double x, double y);

    void scrollBy();

    void scrollBy(double x, double y);

    void scrollIntoView(boolean arg);

    void scrollIntoView();

    void scrollTo();

    void scrollTo(double x, double y);

    /**
     * Sets the value of element's first attribute whose qualified name is qualifiedName to value.
     */
    void setAttribute(String qualifiedName, String value);

    /**
     * Sets the value of element's attribute whose namespace is namespace and local name is localName to value.
     */
    void setAttributeNS(String namespace, String qualifiedName, String value);


    Attr setAttributeNode(Attr attr);

    Attr setAttributeNodeNS(Attr attr);
    
    void setIdAttribute(String name, boolean isId);
    
    void setIdAttributeNode(Attr idAttr, boolean isId);

    void setPointerCapture(int pointerId);

    /**
     * If force is not given, "toggles" qualifiedName, removing it if it is present and adding it if it is not present. If force is true, adds qualifiedName. If force is false, removes qualifiedName.
     * <p>
     * Returns true if qualifiedName is now present, and false otherwise.
     */
    boolean toggleAttribute(String qualifiedName, boolean force);

    boolean toggleAttribute(String qualifiedName);

}

