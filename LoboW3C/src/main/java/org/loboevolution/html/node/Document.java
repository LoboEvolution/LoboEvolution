package org.loboevolution.html.node;

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLHeadElement;
import org.loboevolution.html.dom.HTMLScriptElement;
import org.loboevolution.html.node.CDATASection;
import org.loboevolution.html.node.Comment;
import org.loboevolution.html.node.DOMImplementation;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.DocumentType;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.js.Location;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.xpath.XPathExpression;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.html.xpath.XPathResult;
import org.loboevolution.jsenum.DocumentReadyState;
import org.loboevolution.jsenum.VisibilityState;

/**
 * Any web page loaded in the browser and serves as an entry point into the web
 * page's content, which is the DOM tree.
 */
public interface Document extends Node, NonElementParentNode, ParentNode {

	String NAMESPACE_XHTML = "http://www.w3.org/1999/xhtml";

	String NAMESPACE_SVG = "http://www.w3.org/2000/svg";

	/**
	 * Sets or gets the URL for the current document.
	 */

	String getURL();

	/**
	 * Gets the object that has the focus when the parent document has focus.
	 */

	Element getActiveElement();

	/**
	 * Sets or gets the color of all active links in the document.
	 */
	@Deprecated

	String getAlinkColor();

	@Deprecated

	void setAlinkColor(String alinkColor);

	/**
	 * Returns a reference to the collection of elements contained by the object.
	 */
	@Deprecated

	HTMLCollection getAll();

	/**
	 * Retrieves a collection of all a objects that have a name and/or id property.
	 * Objects in this collection are in HTML source order.
	 */
	@Deprecated
	HTMLCollection getAnchors();

	/**
	 * Retrieves a collection of all applet objects in the document.
	 */
	@Deprecated
	HTMLCollection getApplets();
	
	HTMLCollection getCommands();

	/**
	 * Deprecated. Sets or retrieves a value that indicates the background color
	 * behind the object.
	 */
	@Deprecated
	String getBgColor();

	@Deprecated
	void setBgColor(String bgColor);

	/**
	 * Specifies the beginning and end of the document body.
	 */

	HTMLElement getBody();

	void setBody(HTMLElement body);

	/**
	 * Returns document's encoding.
	 */

	String getCharacterSet();

	/**
	 * Gets or sets the character set used to encode the object.
	 */

	String getCharset();

	/**
	 * Gets a value that indicates whether standards-compliant mode is switched on
	 * for the object.
	 */

	String getCompatMode();

	/**
	 * Returns document's content type.
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
	 */

	String getCookie();

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
	 */

	HTMLScriptElement getCurrentScript();

	Window getDefaultView();

	/**
	 * Sets or gets a value that indicates whether the document can be edited.
	 */

	String getDesignMode();

	void setDesignMode(String designMode);

	/**
	 * Sets or retrieves a value that indicates the reading order of the object.
	 */

	String getDir();

	void setDir(String dir);

	/**
	 * Gets an object representing the document type declaration associated with the
	 * current document.
	 */

	DocumentType getDoctype();

	/**
	 * Gets a reference to the root node of the document.
	 */

	Element getDocumentElement();

	/**
	 * Returns document's URL.
	 */

	String getDocumentURI();

	/**
	 * Sets or gets the security domain of the document.
	 */

	String getDomain();

	void setDomain(String domain);

	/**
	 * Retrieves a collection of all embed objects in the document.
	 */
	HTMLCollection getEmbeds();

	/**
	 * Sets or gets the foreground (text) color of the document.
	 */
	@Deprecated

	String getFgColor();

	void setFgColor(String fgColor);

	/**
	 * Retrieves a collection, in source order, of all form objects in the document.
	 */
	HTMLCollection getForms();

	@Deprecated

	boolean isFullscreen();

	/**
	 * Returns true if document has the ability to display elements fullscreen and
	 * fullscreen is supported, or false otherwise.
	 */

	boolean isFullscreenEnabled();

	/**
	 * Returns the head element.
	 */

	HTMLHeadElement getHead();

	boolean isHidden();

	/**
	 * Retrieves a collection, in source order, of img objects in the document.
	 */
	HTMLCollection getImages();

	/**
	 * Gets the implementation object of the current document.
	 */

	DOMImplementation getImplementation();

	/**
	 * Returns the character encoding used to create the webpage that is loaded into
	 * the document object.
	 */

	String getInputEncoding();

	/**
	 * Gets the date that the page was last modified, if the page supplies one.
	 */

	String getLastModified();

	
	/**
	 * Retrieves a collection of all a objects that specify the href property and
	 * all area objects in the document.
	 */
	HTMLCollection getLinks();

	/**
	 * Contains information about the current URL.
	 */

	Location getLocation();

	void setLocation(Location location);

	/**
	 * Returns document's origin.
	 */

	String getOrigin();

	/**
	 * Return an HTMLCollection of the embed elements in the Document.
	 */
	HTMLCollection getPlugins();

	/**
	 * Retrieves a value that indicates the current state of the object.
	 */

	DocumentReadyState getReadyState();

	/**
	 * Gets the URL of the location that referred the user to the current page.
	 */

	String getReferrer();

	/**
	 * Retrieves a collection of all script objects in the document.
	 */
	HTMLCollection getScripts();

	Element getScrollingElement();

	/**
	 * Contains the title of the document.
	 */

	String getTitle();

	void setTitle(String title);

	VisibilityState getVisibilityState();

	
	/**
	 * Moves node from another document and returns it.
	 * <p>
	 * If node is a document, throws a "NotSupportedError" DOMException or, if node
	 * is a shadow root, throws a "HierarchyRequestError" DOMException.
	 */
	Node adoptNode(Node source);

	@Deprecated
	void captureEvents();

	@Deprecated
	Range caretRangeFromPoint(double x, double y);

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
	 */
	Attr createAttribute(String localName);

	Attr createAttributeNS(String namespace, String qualifiedName);

	/**
	 * Returns a CDATASection node whose data is data.
	 */
	CDATASection createCDATASection(String data);

	/**
	 * Creates a comment object with the specified data.
	 *
	 * @param data Sets the comment object's data.
	 */
	Comment createComment(String data);

	/**
	 * Creates a new document.
	 */
	DocumentFragment createDocumentFragment();

	/**
	 * Creates an instance of the element for the specified tag.
	 *
	 * @param tagName The name of an element.
	 */

	Element createElement(String tagName);

	/**
	 * Returns an element with namespace namespace. Its namespace prefix will be
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
	 */

	Element createElementNS(String namespace, String qualifiedName, String options);

	Element createElementNS(String namespace, String qualifiedName);

	Event createEvent(String eventInterface);

	/**
	 * Creates a NodeIterator object that you can use to traverse filtered lists of
	 * nodes or elements in a document.
	 *
	 * @param root The root element or node to start traversing on.
	 */
	NodeIterator createNodeIterator(Node root);

	/**
	 * Returns a ProcessingInstruction node whose target is target and data is data.
	 * If target does not match the Name production an "InvalidCharacterError"
	 * DOMException will be thrown. If data contains "?&gt;" an
	 * "InvalidCharacterError" DOMException will be thrown.
	 */
	ProcessingInstruction createProcessingInstruction(String target, String data);

	/**
	 * Returns an empty range object that has both of its boundary points positioned
	 * at the beginning of the document.
	 */
	Range createRange();

	/**
	 * Creates a text string from the specified value.
	 *
	 * @param data String that specifies the nodeValue property of the text node.
	 */
	Text createTextNode(String data);

	/**
	 * Creates a TreeWalker object that you can use to traverse filtered lists of
	 * nodes or elements in a document.
	 *
	 * @param root       The root element or node to start traversing on.
	 * @param whatToShow The type of nodes or elements to appear in the node list.
	 *                   For more information, see whatToShow.
	 * @param filter     A custom NodeFilter function to use.
	 */
	TreeWalker createTreeWalker(Node root);

	/**
	 * Returns the element for the specified x coordinate and the specified y
	 * coordinate.
	 *
	 * @param x The x-offset
	 * @param y The y-offset
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
	 */
	boolean execCommand(String commandId, boolean showUI, String value);

	boolean execCommand(String commandId, boolean showUI);

	boolean execCommand(String commandId);

	/**
	 * Returns a reference to the first object with the specified value of the ID or
	 * NAME attribute.
	 *
	 * @param elementId String that specifies the ID value. Case-insensitive.
	 */

	Element getElementById(String elementId);

	/**
	 * Returns a HTMLCollection of the elements in the object on which the method
	 * was invoked (a document or an element) that have all the classes given by
	 * classNames. The classNames argument is interpreted as a space-separated list
	 * of classes.
	 */
	HTMLCollection getElementsByClassName(String classNames);

	/**
	 * Gets a collection of objects based on the value of the NAME or ID attribute.
	 *
	 * @param elementName Gets a collection of objects based on the value of the
	 *                    NAME or ID attribute.
	 */
	HTMLCollection getElementsByName(String elementName);

	/**
	 * Retrieves a collection of objects based on the specified element name.
	 *
	 * @param qualifiedName Specifies the name of an element.
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
	 */
	HTMLCollection getElementsByTagNameNS(String namespaceURI, String localName);

	/**
	 * Returns an object representing the current selection of the document that is
	 * loaded into the object displaying a webpage.
	 */

	Selection getSelection();

	String getXmlEncoding();
	
	String getXmlVersion();
	
	boolean getXmlStandalone();
	
	boolean getStrictErrorChecking();
	
	DOMConfiguration getDomConfig();

	void setDocumentURI(String documentURI);
	
	void setStrictErrorChecking(boolean strictErrorChecking);
	
	void setXmlStandalone(boolean xmlStandalone);
	
	void setXmlVersion(String xmlVersion);

	/**
	 * Gets a value indicating whether the object currently has focus.
	 */
	boolean hasFocus();

	/**
	 * Returns a copy of node. If deep is true, the copy also includes the node's
	 * descendants.
	 * <p>
	 * If node is a document or a shadow root, throws a "NotSupportedError"
	 * DOMException.
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
	 */
	Document open(String url, String name, String features, boolean replace);

	Document open(String url, String name, String features);

	Document open(String url, String name);

	Document open(String url);

	Document open();

	/**
	 * Returns a Boolean value that indicates whether a specified command can be
	 * successfully executed using execCommand, given the current state of the
	 * document.
	 *
	 * @param commandId Specifies a command identifier.
	 */
	boolean queryCommandEnabled(String commandId);

	/**
	 * Returns a Boolean value that indicates whether the specified command is in
	 * the indeterminate state.
	 *
	 * @param commandId String that specifies a command identifier.
	 */
	boolean queryCommandIndeterm(String commandId);

	/**
	 * Returns a Boolean value that indicates the current state of the command.
	 *
	 * @param commandId String that specifies a command identifier.
	 */
	boolean queryCommandState(String commandId);

	/**
	 * Returns a Boolean value that indicates whether the current command is
	 * supported on the current range.
	 *
	 * @param commandId Specifies a command identifier.
	 */
	boolean queryCommandSupported(String commandId);

	/**
	 * Returns the current value of the document, range, or current selection for
	 * the given command.
	 *
	 * @param commandId String that specifies a command identifier.
	 */
	String queryCommandValue(String commandId);

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

	void normalizeDocument();
	
	XPathExpression createExpression();
	
	XPathNSResolver createNSResolver(Node nodeResolver);
	
	XPathResult evaluate(String expression, Node contextNode, XPathNSResolver resolver, short type, Object result);

}
