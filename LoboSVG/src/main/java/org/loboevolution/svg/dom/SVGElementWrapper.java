/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

package org.loboevolution.svg.dom;

import lombok.AllArgsConstructor;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.events.Event;
import org.loboevolution.html.dom.DOMTokenList;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.UserDataHandler;
import org.loboevolution.html.node.*;
import org.loboevolution.js.geom.DOMRect;
import org.loboevolution.js.geom.DOMRectList;
import org.loboevolution.traversal.NodeFilter;
import org.mozilla.javascript.Function;
import org.w3c.dom.events.EventException;

import java.net.URL;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class SVGElementWrapper implements HTMLElement {

    private HTMLElement element;

    @Override
    public String getAccessKey() {
        return element.getAccessKey();
    }

    @Override
    public void setAccessKey(String accessKey) {
        element.setAccessKey(accessKey);
    }

    @Override
    public String getAccessKeyLabel() {
        return element.getAccessKeyLabel();
    }

    @Override
    public String getAutocapitalize() {
        return element.getAutocapitalize();
    }

    @Override
    public void setAutocapitalize(String autocapitalize) {
        element.setAutocapitalize(autocapitalize);
    }

    @Override
    public String getDir() {
        return element.getDir();
    }

    @Override
    public void setDir(String dir) {
        element.setDir(dir);
    }

    @Override
    public String getContentEditable() {
        return element.getContentEditable();
    }

    @Override
    public void setContentEditable(String contenteditable) {
        element.setContentEditable(contenteditable);
    }

    @Override
    public DOMTokenList getClassList() {
        return element.getClassList();
    }

    @Override
    public String getClassName() {
        return element.getClassName();
    }

    @Override
    public void setClassName(String className) {
        element.setClassName(className);
    }

    @Override
    public int getClientHeight() {
        return element.getClientHeight();
    }

    @Override
    public int getClientLeft() {
        return element.getClientLeft();
    }

    @Override
    public int getClientTop() {
        return element.getClientTop();
    }

    @Override
    public Integer getClientWidth() {
        return element.getClientWidth();
    }

    @Override
    public String getId() {
        return element.getId();
    }

    @Override
    public void setId(String id) {
        element.setId(id);
    }

    @Override
    public String getLocalName() {
        return element.getLocalName();
    }

    @Override
    public String getTextContent() {
        return element.getTextContent();
    }

    @Override
    public void setTextContent(String textContent) {
        element.setTextContent(textContent);
    }

    @Override
    public Object getUserData(String key) {
        return element.getUserData(key);
    }

    @Override
    public Node getPreviousTo(Node node) {
        return element.getPreviousTo(node);
    }

    @Override
    public Node getNextTo(Node node) {
        return element.getNextTo(node);
    }

    @Override
    public void forgetRenderState() {
        element.forgetRenderState();
    }

    @Override
    public void appendChildrenToCollectionImpl(NodeFilter filter, Collection<Node> collection) {
        element.appendChildrenToCollectionImpl(filter,collection);
    }

    @Override
    public void extractDescendentsArrayImpl(NodeFilter filter, List<Node> al, boolean nestIntoMatchingNodes) {
        element.extractDescendentsArrayImpl(filter,al,nestIntoMatchingNodes);
    }

    @Override
    public UINode findUINode() {
        return element.findUINode();
    }

    @Override
    public void visit(NodeVisitor visitor) {
        element.visit(visitor);
    }

    @Override
    public Node cloneNode(boolean deep) {
        return element.cloneNode(deep);
    }

    @Override
    public Node cloneNode() {
        return element.cloneNode();
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        return element.setUserData(key, data, handler);
    }

    @Override
    public void setParentImpl(Node parent) {
        element.setParentImpl(parent);
    }

    @Override
    public void setNamespaceURI(String namespaceURI) {
        element.setNamespaceURI(namespaceURI);
    }

    @Override
    public boolean contains(Node other) {
        return element.contains(other);
    }

    @Override
    public boolean hasChildNodes() {
        return element.hasChildNodes();
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) {
        return element.insertBefore(newChild, refChild);
    }

    @Override
    public boolean isDefaultNamespace(String namespace) {
        return element.isDefaultNamespace(namespace);
    }

    @Override
    public boolean isEqualNode(Node otherNode) {
        return element.isEqualNode(otherNode);
    }

    @Override
    public boolean isSameNode(Node otherNode) {
        return element.isSameNode(otherNode);
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        return element.lookupNamespaceURI(prefix);
    }

    @Override
    public String lookupPrefix(String namespace) {
        return element.lookupPrefix(namespace);
    }

    @Override
    public void normalize() {
        element.normalize();
    }

    @Override
    public Node appendChild(Node newChild) {
        return element.appendChild(newChild);
    }

    @Override
    public Node prependChild(Node newChild) {
        return element.prependChild(newChild);
    }

    @Override
    public Node prepend(Node newChild) {
        return element.prepend(newChild);
    }

    @Override
    public Node removeChild(Node oldChild) {
        return element.removeChild(oldChild);
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) {
        return element.replaceChild(newChild, oldChild);
    }

    @Override
    public NamedNodeMap getAttributes() {
        return element.getAttributes();
    }

    @Override
    public short compareDocumentPosition(Node other) {
        return element.compareDocumentPosition(other);
    }

    @Override
    public boolean hasAttributes() {
        return element.hasAttributes();
    }

    @Override
    public boolean isSupported(String feature, String version) {
        return element.isSupported(feature, version);
    }

    @Override
    public Node getFeature(String feature, String version) {
        return element.getFeature(feature, version);
    }

    @Override
    public Document getDocumentNode() {
        return element.getDocumentNode();
    }

    @Override
    public String getNamespaceURI() {
        return element.getNamespaceURI();
    }

    @Override
    public Node getNextSibling() {
        return element.getNextSibling();
    }

    @Override
    public String getNodeName() {
        return element.getNodeName();
    }

    @Override
    public int getNodeType() {
        return element.getNodeType();
    }

    @Override
    public String getNodeValue() {
        return element.getNodeValue();
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
        element.setNodeValue(nodeValue);
    }

    @Override
    public Document getOwnerDocument() {
        return element.getOwnerDocument();
    }

    @Override
    public void setOwnerDocument(Document document) {
        element.setOwnerDocument(document);
    }

    @Override
    public void setOwnerDocument(Document value, boolean deep) {

    }

    @Override
    public HTMLElement getParentElement() {
        return element.getParentElement();
    }

    @Override
    public Node getParentNode() {
        return element.getParentNode();
    }

    @Override
    public Node getPreviousSibling() {
        return element.getPreviousSibling();
    }

    @Override
    public Node getRootNode() {
        return element.getRootNode();
    }

    @Override
    public String getOuterHTML() {
        return element.getOuterHTML();
    }

    @Override
    public void setOuterHTML(String outerHTML) {
        element.setOuterHTML(outerHTML);
    }

    @Override
    public String getInnerHTML() {
        return element.getInnerHTML();
    }

    @Override
    public void setInnerHTML(String innerHTML) {
        element.setInnerHTML(innerHTML);
    }

    @Override
    public String getInnerText() {
        return element.getInnerText();
    }

    @Override
    public void setInnerText(String innerText) {
        element.setInnerText(innerText);
    }

    @Override
    public DOMRect getBoundingClientRect() {
        return element.getBoundingClientRect();
    }

    @Override
    public DOMRectList getClientRects() {
        return element.getClientRects();
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return element.getSchemaTypeInfo();
    }

    @Override
    public String getBaseURI() {
        return element.getBaseURI();
    }

    @Override
    public String getPrefix() {
        return element.getPrefix();
    }

    @Override
    public void setPrefix(String prefix) {
        element.setPrefix(prefix);
    }

    @Override
    public NodeList getChildNodes() {
        return element.getChildNodes();
    }

    @Override
    public Node getFirstChild() {
        return element.getFirstChild();
    }

    @Override
    public boolean isIsConnected() {
        return element.isIsConnected();
    }

    @Override
    public Node getLastChild() {
        return element.getLastChild();
    }

    @Override
    public double getScrollHeight() {
        return element.getScrollHeight();
    }

    @Override
    public double getScrollLeft() {
        return element.getScrollLeft();
    }

    @Override
    public void setScrollLeft(double scrollLeft) {
        element.setScrollLeft(scrollLeft);
    }

    @Override
    public double getScrollTop() {
        return element.getScrollTop();
    }

    @Override
    public void setScrollTop(double scrollTop) {
        element.setScrollTop(scrollTop);
    }

    @Override
    public double getScrollWidth() {
        return element.getScrollWidth();
    }

    @Override
    public String getSlot() {
        return element.getSlot();
    }

    @Override
    public void setSlot(String slot) {
        element.setSlot(slot);
    }

    @Override
    public String getTagName() {
        return element.getTagName();
    }

    @Override
    public <E extends Element> E closest(String selector) {
        return element.closest(selector);
    }

    @Override
    public String getAttribute(String qualifiedName) {
        return element.getAttribute(qualifiedName);
    }

    @Override
    public String getAttributeNS(String namespace, String localName) {
        return element.getAttributeNS(namespace, localName);
    }

    @Override
    public String[] getAttributeNames() {
        return element.getAttributeNames();
    }

    @Override
    public Attr getAttributeNode(String name) {
        return element.getAttributeNode(name);
    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) {
        return element.getAttributeNodeNS(namespaceURI, localName);
    }

    @Override
    public HTMLCollection getElementsByClassName(String classNames) {
        return element.getElementsByClassName(classNames);
    }

    @Override
    public HTMLCollection getElementsByTagName(String qualifiedName) {
        return element.getElementsByTagName(qualifiedName);
    }

    @Override
    public HTMLCollection getElementsByTagNameNS(String namespaceURI, String localName) {
        return element.getElementsByTagNameNS(namespaceURI, localName);
    }

    @Override
    public boolean hasAttribute(String qualifiedName) {
        return element.hasAttribute(qualifiedName);
    }

    @Override
    public boolean hasAttributeNS(String namespace, String localName) {
        return element.hasAttributeNS(namespace, localName);
    }

    @Override
    public boolean hasPointerCapture(int pointerId) {
        return element.hasPointerCapture(pointerId);
    }

    @Override
    public boolean matches(String selectors) {
        return element.matches(selectors);
    }

    @Override
    public void releasePointerCapture(int pointerId) {
        element.releasePointerCapture(pointerId);
    }

    @Override
    public void removeAttribute(String qualifiedName) {
        element.removeAttribute(qualifiedName);
    }

    @Override
    public void removeAttributeNS(String removeAttributeNS, String localName) {
        element.removeAttributeNS(removeAttributeNS, localName);
    }

    @Override
    public Attr removeAttributeNode(Attr attr) {
        return element.removeAttributeNode(attr);
    }

    @Override
    public void requestPointerLock() {
        element.requestPointerLock();
    }

    @Override
    public void scroll(int x, int y) {
        element.scroll(x, y);
    }

    @Override
    public void scrollBy(int x, int y) {
        element.scrollBy(x, y);
    }

    @Override
    public void scrollIntoView(boolean arg) {
        element.scrollIntoView(arg);
    }

    @Override
    public void scrollIntoView() {
        element.scrollIntoView();
    }

    @Override
    public void scrollTo(int x, int y) {
        element.scrollTo(x, y);
    }

    @Override
    public void setAttribute(String qualifiedName, String value) {
        element.setAttribute(qualifiedName, value);
    }

    @Override
    public void setAttributeNS(String namespace, String qualifiedName, String value) {
        element.setAttributeNS(namespace, qualifiedName, value);
    }

    @Override
    public Attr setAttributeNode(Attr attr) {
        return element.setAttributeNode(attr);
    }

    @Override
    public Attr setAttributeNodeNS(Attr attr) {
        return element.setAttributeNodeNS(attr);
    }

    @Override
    public void setIdAttribute(String name, boolean isId) {
        element.setIdAttribute(name, isId);
    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) {
        element.setIdAttributeNode(idAttr, isId);
    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) {
        element.setIdAttributeNS(namespaceURI, localName, isId);
    }

    @Override
    public void setPointerCapture(int pointerId) {
        element.setPointerCapture(pointerId);
    }

    @Override
    public boolean toggleAttribute(String qualifiedName, boolean force) {
        return element.toggleAttribute(qualifiedName, force);
    }

    @Override
    public boolean toggleAttribute(String qualifiedName) {
        return element.toggleAttribute(qualifiedName);
    }

    @Override
    public Node insertAdjacentElement(String where, Node insertedElement) {
        return element.insertAdjacentElement(where, insertedElement);
    }

    @Override
    public void insertAdjacentHTML(String position, String text) {
        element.insertAdjacentHTML(position, text);
    }

    @Override
    public String getLang() {
        return element.getLang();
    }

    @Override
    public void setLang(String lang) {
        element.setLang(lang);
    }

    @Override
    public String getTitle() {
        return element.getTitle();
    }

    @Override
    public void setTitle(String title) {
        element.setTitle(title);
    }

    @Override
    public Integer getOffsetHeight() {
        return element.getOffsetHeight();
    }

    @Override
    public double getOffsetLeft() {
        return element.getOffsetLeft();
    }

    @Override
    public Element getOffsetParent() {
        return element.getOffsetParent();
    }

    @Override
    public int getOffsetTop() {
        return element.getOffsetTop();
    }

    @Override
    public Integer getOffsetWidth() {
        return element.getOffsetWidth();
    }

    @Override
    public CSSStyleDeclaration getStyle() {
        return element.getStyle();
    }

    @Override
    public boolean isSpellcheck() {
        return element.isSpellcheck();
    }

    @Override
    public void setSpellcheck(boolean spellcheck) {
        element.setSpellcheck(spellcheck);
    }

    @Override
    public boolean isDraggable() {
        return element.isDraggable();
    }

    @Override
    public void setDraggable(boolean draggable) {
        element.setDraggable(draggable);
    }

    @Override
    public boolean isHidden() {
        return element.isHidden();
    }

    @Override
    public void setHidden(boolean hidden) {
        element.setHidden(hidden);
    }

    @Override
    public boolean isTranslate() {
        return element.isTranslate();
    }

    @Override
    public void setTranslate(boolean translate) {
        element.setTranslate(translate);
    }

    @Override
    public void click() {
        element.click();
    }

    @Override
    public int getChildElementCount() {
        return element.getChildElementCount();
    }

    @Override
    public HTMLCollection getChildren() {
        return element.getChildren();
    }

    @Override
    public Element getFirstElementChild() {
        return element.getFirstElementChild();
    }

    @Override
    public Element getLastElementChild() {
        return element.getLastElementChild();
    }

    @Override
    public Element querySelector(String selectors) {
        return element.querySelector(selectors);
    }

    @Override
    public NodeList querySelectorAll(String selectors) {
        return element.querySelectorAll(selectors);
    }

    @Override
    public void addEventListener(String type, Function listener) {
        element.addEventListener(type, listener);
    }

    @Override
    public void addEventListener(String type, Function listener, boolean useCapture) {
        element.addEventListener(type, listener, useCapture);
    }

    @Override
    public void removeEventListener(String script, Function function) {
        element.removeEventListener(script, function);
    }

    @Override
    public void removeEventListener(String type, Function listener, boolean useCapture) {
        element.removeEventListener(type, listener, useCapture);
    }

    @Override
    public boolean dispatchEvent(Node element, Event evt) {
        return element.dispatchEvent(element, evt);
    }

    @Override
    public boolean dispatchEvent(Event evt) throws EventException {
        return element.dispatchEvent(evt);
    }

    @Override
    public Element getNextElementSibling() {
        return element.getNextElementSibling();
    }

    @Override
    public Element getPreviousElementSibling() {
        return element.getPreviousElementSibling();
    }

    @Override
    public Object getDocumentItem(String name) {
        return null;
    }

    @Override
    public URL getFullURL(String spec) throws Exception {
        return null;
    }

    @Override
    public ModelNode getParentModelNode() {
        return null;
    }

    @Override
    public boolean isEqualOrDescendentOf(ModelNode otherNode) {
        return false;
    }

    @Override
    public Object getRenderState() {
        return null;
    }

    @Override
    public void setDocumentItem(String name, Object value) {

    }

    @Override
    public void warn(String message, Throwable err) {

    }
}