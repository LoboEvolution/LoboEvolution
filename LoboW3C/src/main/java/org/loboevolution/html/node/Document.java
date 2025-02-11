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

package org.loboevolution.html.node;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.*;
import org.loboevolution.events.Event;
import org.loboevolution.html.node.ranges.DocumentRange;
import org.loboevolution.html.node.ranges.Range;
import org.loboevolution.js.Location;
import org.loboevolution.js.Window;
import org.loboevolution.traversal.DocumentTraversal;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.html.xpath.XPathResult;
import org.loboevolution.type.DocumentReadyState;
import org.loboevolution.type.VisibilityState;

/**
 * Any web page loaded in the browser and serves as an entry point into the web
 * page's content, which is the DOM tree.
 */
public interface Document extends Node, NonElementParentNode, ParentNode, DocumentTraversal, DocumentRange {

	/** Constant <code>XMLNS_NAMESPACE_URI="<a href="http://www.w3.org/2000/xmlns/">...</a>"</code> */
	String XMLNS_NAMESPACE_URI = "http://www.w3.org/2000/xmlns/";

	/** Constant <code>XML_NAMESPACE_URI="<a href="http://www.w3.org/XML/1998/namespace">...</a>"</code> */
	String XML_NAMESPACE_URI = "http://www.w3.org/XML/1998/namespace";

	/** Constant <code>HTML_NAMESPACE_URI="<a href="http://www.w3.org/1999/xhtml">...</a>"</code> */
	String HTML_NAMESPACE_URI = "http://www.w3.org/1999/xhtml";

	/** Constant <code>NAMESPACE_SVG="<a href="http://www.w3.org/2000/svg">...</a>"</code> */
	String NAMESPACE_SVG = "http://www.w3.org/2000/svg";

	/**
	 * Sets or gets the URL for the current document.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getURL();

	/**
	 * Gets the object that has the focus when the parent document has focus.
	 *
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element getActiveElement();

	/**
	 * Returns a reference to the collection of elements contained by the object.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLAllCollection} object.
	 */
	@Deprecated
	HTMLAllCollection getall();

	/**
	 * Retrieves a collection of all a objects that have a name and/or id property.
	 * Objects in this collection are in HTML source order.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	@Deprecated
	HTMLCollection getAnchors();

	/**
	 * Retrieves a collection of all applet objects in the document.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	@Deprecated
	HTMLCollection getApplets();
	
	/**
	 * <p>getCommands.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getCommands();

	/**
	 * Deprecated. Sets or retrieves a value that indicates the background color
	 * behind the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getBgColor();

	/**
	 * <p>setBgColor.</p>
	 *
	 * @param bgColor a {@link java.lang.String} object.
	 */
	@Deprecated
	void setBgColor(String bgColor);

	/**
	 * Specifies the beginning and end of the document body.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
	 */
	HTMLElement getBody();

	/**
	 * <p>setBody.</p>
	 *
	 * @param body a {@link org.loboevolution.html.dom.HTMLElement} object.
	 */
	void setBody(HTMLElement body);

	/**
	 * Returns document's encoding.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCharacterSet();

	/**
	 * Gets or sets the character set used to encode the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCharset();

	/**
	 * Gets a value that indicates whether standards-compliant mode is switched on
	 * for the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCompatMode();

	/**
	 * Returns document's content type.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getContentType();

	/**
	 * Returns the HTTP cookies that apply to the Document. If there are no cookies
	 * or cookies can't be applied to this resource, the empty string will be
	 * returned.
	 * <p>
	 * Can be set, to add a new cookie to the element's set of HTTP cookies.
	 * <p>
	 * If the contents are sandboxed into a unique origin (e.g. in an iframe with
	 * the sandbox attribute), a "SecurityError" DOMException will be thrown on
	 * getting and setting.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCookie();

	/**
	 * <p>setCookie.</p>
	 *
	 * @param cookie a {@link java.lang.String} object.
	 */
	void setCookie(String cookie);

	/**
	 * Returns the script element, or the SVG script element, that is currently
	 * executing, as long as the element represents a classic script. In the case of
	 * reentrant script execution, returns the one that most recently started
	 * executing amongst those that have not yet finished executing.
	 * <p>
	 * Returns null if the Document is not currently executing a script or SVG
	 * script element (e.g., because the running script is an event handler, or a
	 * timeout), or if the currently executing script or SVG script element
	 * represents a module script.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLScriptElement} object.
	 */
	HTMLScriptElement getCurrentScript();

	/**
	 * <p>getDefaultView.</p>
	 *
	 * @return a {@link Window} object.
	 */
	Window getDefaultView();

	/**
	 * Sets or gets a value that indicates whether the document can be edited.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDesignMode();

	/**
	 * <p>setDesignMode.</p>
	 *
	 * @param designMode a {@link java.lang.String} object.
	 */
	void setDesignMode(String designMode);

	/**
	 * Sets or retrieves a value that indicates the reading order of the object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDir();

	/**
	 * <p>setDir.</p>
	 *
	 * @param dir a {@link java.lang.String} object.
	 */
	void setDir(String dir);

	/**
	 * Gets an object representing the document type declaration associated with the
	 * current document.
	 *
	 * @return a {@link org.loboevolution.html.node.DocumentType} object.
	 */
	DocumentType getDoctype();

	/**
	 * Gets a reference to the root node of the document.
	 *
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element getDocumentElement();

	/**
	 * Returns document's URL.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDocumentURI();

	/**
	 * Sets or gets the security domain of the document.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDomain();

	/**
	 * <p>setDomain.</p>
	 *
	 * @param domain a {@link java.lang.String} object.
	 */
	void setDomain(String domain);

	/**
	 * Retrieves a collection of all embed objects in the document.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getEmbeds();

	/**
	 * Sets or gets the foreground (text) color of the document.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	@Deprecated
	String getFgColor();

	/**
	 * <p>setFgColor.</p>
	 *
	 * @param fgColor a {@link java.lang.String} object.
	 */
	void setFgColor(String fgColor);

	/**
	 * Retrieves a collection, in source order, of all form objects in the document.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getForms();

	/**
	 * <p>isFullscreen.</p>
	 *
	 * @return a boolean.
	 */
	@Deprecated
	boolean isFullscreen();

	/**
	 * Returns true if document has the ability to display elements fullscreen and
	 * fullscreen is supported, or false otherwise.
	 *
	 * @return a boolean.
	 */
	boolean isFullscreenEnabled();

	/**
	 * Returns the head element.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLHeadElement} object.
	 */
	HTMLHeadElement getHead();

	/**
	 * <p>isHidden.</p>
	 *
	 * @return a boolean.
	 */
	boolean isHidden();

	/**
	 * Retrieves a collection, in source order, of img objects in the document.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getImages();

	/**
	 * Gets the implementation object of the current document.
	 *
	 * @return a {@link DOMImplementation} object.
	 */
	DOMImplementation getImplementation();

	/**
	 * Returns the character encoding used to create the webpage that is loaded into
	 * the document object.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getInputEncoding();

	/**
	 * Gets the date that the page was last modified, if the page supplies one.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getLastModified();

	
	/**
	 * Retrieves a collection of all a objects that specify the href property and
	 * all area objects in the document.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getLinks();

	/**
	 * Contains information about the current URL.
	 *
	 * @return a {@link Location} object.
	 */
	Location getLocation();

	/**
	 * <p>setLocation.</p>
	 *
	 * @param location a {@link Location} object.
	 */
	void setLocation(Location location);

	/**
	 * Returns document's origin.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getOrigin();

	/**
	 * Return an HTMLCollection of the embed elements in the Document.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getPlugins();

	/**
	 * Retrieves a value that indicates the current state of the object.
	 *
	 * @return a {@link org.loboevolution.type.DocumentReadyState} object.
	 */
	DocumentReadyState getReadyState();

	/**
	 * Gets the URL of the location that referred the user to the current page.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getReferrer();

	/**
	 * Retrieves a collection of all script objects in the document.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getScripts();

	/**
	 * <p>getScrollingElement.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element getScrollingElement();

	/**
	 * Contains the title of the document.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTitle();

	/**
	 * <p>setTitle.</p>
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	void setTitle(String title);

	/**
	 * <p>getVisibilityState.</p>
	 *
	 * @return a {@link org.loboevolution.type.VisibilityState} object.
	 */
	VisibilityState getVisibilityState();

	
	/**
	 * Moves node from another document and returns it.
	 * <p>
	 * If node is a document, throws a "NotSupportedError" DOMException or, if node
	 * is a shadow root, throws a "HierarchyRequestError" DOMException.
	 *
	 * @param source a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node adoptNode(Node source);

	/**
	 * <p>caretRangeFromPoint.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 * @return a {@link org.loboevolution.html.node.ranges.Range} object.
	 */
	@Deprecated
	Range caretRangeFromPoint(double x, double y);

	/**
	 * <p>clear.</p>
	 */
	@Deprecated
	void clear();

	/**
	 * Closes an output stream and forces the sent data to display.
	 */
	void close();

	/**
	 * Creates an attribute object with a specified name.
	 *
	 * @param localName String that sets the attribute object's name.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr createAttribute(String localName);

	/**
	 * <p>createAttributeNS.</p>
	 *
	 * @param namespace a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Attr} object.
	 */
	Attr createAttributeNS(String namespace, String qualifiedName);

	/**
	 * Returns a CDATASection node whose data is data.
	 *
	 * @param data a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.CDATASection} object.
	 */
	CDATASection createCDATASection(String data);

	/**
	 * Creates a comment object with the specified data.
	 *
	 * @param data Sets the comment object's data.
	 * @return a {@link org.loboevolution.html.node.Comment} object.
	 */
	Comment createComment(String data);

	/**
	 * Creates a new document.
	 *
	 * @return a {@link org.loboevolution.html.node.DocumentFragment} object.
	 */
	DocumentFragment createDocumentFragment();

	/**
	 * Creates an instance of the element for the specified tag.
	 *
	 * @param tagName The name of an element.
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element createElement(String tagName);

	/**
	 * Returns an element with namespace. Its namespace prefix will be
	 * everything before ":" (U+003E) in qualifiedName or null. Its local name will
	 * be everything after ":" (U+003E) in qualifiedName or qualifiedName.
	 * <p>
	 * If localName does not match the Name production an "InvalidCharacterError"
	 * DOMException will be thrown.
	 * <p>
	 * If one of the following conditions is true a "NamespaceError" DOMException
	 * will be thrown:
	 * <p>
	 * localName does not match the QName production. Namespace prefix is not null
	 * and namespace is the empty string. Namespace prefix is "xml" and namespace is
	 * not the XML namespace. qualifiedName or namespace prefix is "xmlns" and
	 * namespace is not the XMLNS namespace. namespace is the XMLNS namespace and
	 * neither qualifiedName nor namespace prefix is "xmlns".
	 * <p>
	 * When supplied, options's is can be used to create a customized built-in
	 * element.
	 *
	 * @param namespace a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @param options a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element createElementNS(String namespace, String qualifiedName, String options);

	/**
	 * <p>createElementNS.</p>
	 *
	 * @param namespace a {@link java.lang.String} object.
	 * @param qualifiedName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element createElementNS(String namespace, String qualifiedName);

	/**
	 * <p>createEvent.</p>
	 *
	 * @param eventInterface a {@link java.lang.String} object.
	 * @return a {@link Event} object.
	 */
	Event createEvent(String eventInterface);

	/**
	 * Returns a ProcessingInstruction node whose target is target and data is data.
	 * If target does not match the Name production an "InvalidCharacterError"
	 * DOMException will be thrown. If data contains "?&gt;" an
	 * "InvalidCharacterError" DOMException will be thrown.
	 *
	 * @param target a {@link java.lang.String} object.
	 * @param data a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.ProcessingInstruction} object.
	 */
	ProcessingInstruction createProcessingInstruction(String target, String data);

	/**
	 * Creates a text string from the specified value.
	 *
	 * @param data String that specifies the nodeValue property of the text node.
	 * @return a {@link org.loboevolution.html.node.Text} object.
	 */
	Text createTextNode(String data) throws DOMException;

	/**
	 * Returns the element for the specified x coordinate and the specified y
	 * coordinate.
	 *
	 * @param x The x-offset
	 * @param y The y-offset
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	Element elementFromPoint(double x, double y);

	/**
	 * Executes a command on the current document, current selection, or the given
	 * range.
	 *
	 * @param commandId String that specifies the command to execute. This command
	 *                  can be any of the command identifiers that can be executed
	 *                  in script.
	 * @param showUI    Display the user interface, defaults to false.
	 * @param value     Value to assign.
	 * @return a boolean.
	 */
	boolean execCommand(String commandId, boolean showUI, String value);

	/**
	 * <p>execCommand.</p>
	 *
	 * @param commandId a {@link java.lang.String} object.
	 * @param showUI a boolean.
	 * @return a boolean.
	 */
	boolean execCommand(String commandId, boolean showUI);

	/**
	 * <p>execCommand.</p>
	 *
	 * @param commandId a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	boolean execCommand(String commandId);

	/**
	 * {@inheritDoc}
	 *
	 * Returns a reference to the first object with the specified value of the ID or
	 * NAME attribute.
	 */
	Element getElementById(String elementId);

	/**
	 * Returns a HTMLCollection of the elements in the object on which the method
	 * was invoked (a document or an element) that have all the classes given by
	 * classNames. The classNames argument is interpreted as a space-separated list
	 * of classes.
	 *
	 * @param classNames a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getElementsByClassName(String classNames);

	/**
	 * Gets a collection of objects based on the value of the NAME or ID attribute.
	 *
	 * @param elementName Gets a collection of objects based on the value of the
	 *                    NAME or ID attribute.
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getElementsByName(String elementName);

	/**
	 * Retrieves a collection of objects based on the specified element name.
	 *
	 * @param qualifiedName Specifies the name of an element.
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getElementsByTagName(String qualifiedName);

	/**
	 * If namespace and localName are "*" returns a HTMLCollection of all descendant
	 * elements.
	 * <p>
	 * If only namespace is "*" returns a HTMLCollection of all descendant elements
	 * whose local name is localName.
	 * <p>
	 * If only localName is "*" returns a HTMLCollection of all descendant elements
	 * whose namespace is namespace.
	 * <p>
	 * Otherwise, returns a HTMLCollection of all descendant elements whose
	 * namespace is namespace and local name is localName.
	 *
	 * @param namespaceURI a {@link java.lang.String} object.
	 * @param localName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getElementsByTagNameNS(String namespaceURI, String localName);

	/**
	 * Returns an object representing the current selection of the document that is
	 * loaded into the object displaying a webpage.
	 *
	 * @return a {@link org.loboevolution.html.node.Selection} object.
	 */
	Selection getSelection();

	/**
	 * <p>getXmlEncoding.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getXmlEncoding();
	
	/**
	 * <p>getXmlVersion.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getXmlVersion();
	
	/**
	 * <p>getXmlStandalone.</p>
	 *
	 * @return a boolean.
	 */
	boolean getXmlStandalone();
	
	/**
	 * <p>getStrictErrorChecking.</p>
	 *
	 * @return a boolean.
	 */
	boolean getStrictErrorChecking();
	
	/**
	 * <p>getDomConfig.</p>
	 *
	 * @return a {@link DOMConfiguration} object.
	 */
	DOMConfiguration getDomConfig();

	/**
	 * <p>setDocumentURI.</p>
	 *
	 * @param documentURI a {@link java.lang.String} object.
	 */
	void setDocumentURI(String documentURI);
	
	/**
	 * <p>setStrictErrorChecking.</p>
	 *
	 * @param strictErrorChecking a boolean.
	 */
	void setStrictErrorChecking(boolean strictErrorChecking);
	
	/**
	 * <p>setXmlStandalone.</p>
	 *
	 * @param xmlStandalone a boolean.
	 */
	void setXmlStandalone(boolean xmlStandalone);
	
	/**
	 * <p>setXmlVersion.</p>
	 *
	 * @param xmlVersion a {@link java.lang.String} object.
	 */
	void setXmlVersion(String xmlVersion);

	/**
	 * Gets a value indicating whether the object currently has focus.
	 *
	 * @return a boolean.
	 */
	boolean hasFocus();


	/**
	 * Returns a copy of node. If deep is true, the copy also includes the node's
	 * descendants.
	 * <p>
	 * If node is a document or a shadow root, throws a "NotSupportedError"
	 * DOMException.
	 *
	 * @param importedNode a {@link org.loboevolution.html.node.Node} object.
	 * @param deep a boolean.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	Node importNode(Node importedNode, boolean deep);

	/**
	 * Opens a new window and loads a document specified by a given URL. Also, opens
	 * a new window that uses the url parameter and the name parameter to collect
	 * the output of the write method and the writeln method.
	 *
	 * @param url      Specifies a MIME type for the document.
	 * @param name     Specifies the name of the window. This name is used as the
	 *                 value for the TARGET attribute on a form or an anchor
	 *                 element.
	 * @param features Contains a list of items separated by commas. Each item
	 *                 consists of an option and a value, separated by an equals
	 *                 sign (for example, "fullscreen=yes, toolbar=yes"). The
	 *                 following values are supported.
	 * @param replace  Specifies whether the existing entry for the document is
	 *                 replaced in the history list.
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document open(String url, String name, String features, boolean replace);

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @param features a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document open(String url, String name, String features);

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document open(String url, String name);

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document open(String url);

	/**
	 * <p>open.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Document} object.
	 */
	Document open();

	/**
	 * Returns a Boolean value that indicates whether a specified command can be
	 * successfully executed using execCommand, given the current state of the
	 * document.
	 *
	 * @param commandId Specifies a command identifier.
	 * @return a boolean.
	 */
	boolean queryCommandEnabled(String commandId);

	/**
	 * Returns a Boolean value that indicates whether the specified command is in
	 * the indeterminate state.
	 *
	 * @param commandId String that specifies a command identifier.
	 * @return a boolean.
	 */
	boolean queryCommandIndeterm(String commandId);

	/**
	 * Returns a Boolean value that indicates the current state of the command.
	 *
	 * @param commandId String that specifies a command identifier.
	 * @return a boolean.
	 */
	boolean queryCommandState(String commandId);

	/**
	 * Returns a Boolean value that indicates whether the current command is
	 * supported on the current range.
	 *
	 * @param commandId Specifies a command identifier.
	 * @return a boolean.
	 */
	boolean queryCommandSupported(String commandId);

	/**
	 * Returns the current value of the document, range, or current selection for
	 * the given command.
	 *
	 * @param commandId String that specifies a command identifier.
	 * @return a {@link java.lang.String} object.
	 */
	String queryCommandValue(String commandId);

	/**
	 * <p>releaseEvents.</p>
	 */
	@Deprecated
	void releaseEvents();

	/**
	 * Writes one or more HTML expressions to a document in the specified window.
	 *
	 * @param text Specifies the text and HTML tags to write.
	 */
	void write(String text);

	/**
	 * Writes one or more HTML expressions, followed by a carriage return, to a
	 * document in the specified window.
	 *
	 * @param text The text and HTML tags to write.
	 */
	void writeln(String text);

	/**
	 * <p>normalizeDocument.</p>
	 */
	void normalizeDocument();
	
	/**
	 * <p>createExpression.</p>
	 *
	 * @return a {@link org.loboevolution.html.xpath.XPathExpression} object.
	 */
	XPathExpression createExpression();
	
	/**
	 * <p>createNSResolver.</p>
	 *
	 * @param nodeResolver a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.xpath.XPathNSResolver} object.
	 */
	XPathNSResolver createNSResolver(Node nodeResolver);
	
	/**
	 * <p>evaluate.</p>
	 *
	 * @param expression a {@link java.lang.String} object.
	 * @param contextNode a {@link org.loboevolution.html.node.Node} object.
	 * @param resolver a {@link org.loboevolution.html.xpath.XPathNSResolver} object.
	 * @param type a short.
	 * @param result a {@link java.lang.Object} object.
	 * @return a {@link org.loboevolution.html.xpath.XPathResult} object.
	 */
	XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result);

    EntityReference createEntityReference(String ent1);

	Node renameNode(Node node, String namespaceURI, String qualifiedName);

	boolean isXml();

	void setXml(boolean xml);


}
