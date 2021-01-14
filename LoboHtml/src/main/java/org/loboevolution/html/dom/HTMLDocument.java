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

package org.loboevolution.html.dom;

import org.loboevolution.html.dom.domimpl.HTMLHeadElementImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * An HTMLDocument is the root of the HTML hierarchy and holds the
 * entire content. Besides providing access to the hierarchy, it also provides
 * some convenience methods for accessing certain sets of information from the
 * document.
 * <p>
 * The following properties have been deprecated in favor of the corresponding
 * ones for the BODY element:alinkColorbackground
 * bgColorfgColorlinkColorvlinkColorIn DOM Level 2, the method
 * getElementById is inherited from the Document
 * interface where it was moved to.
 * <p>
 * See also the
 * <a href='http://www.w3.org/TR/2003/REC-DOM-Level-2-HTML-20030109'>Document
 * Object Model (DOM) Level 2 HTML Specification</a>.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface HTMLDocument extends Document {
	/**
	 * Closes a document stream opened by open() and forces rendering.
	 */
	void close();

	/**
	 * A collection of all the anchor (A) elements in a document with a
	 * value for the name attribute. For reasons of backward
	 * compatibility, the returned set of anchors only contains those anchors
	 * created with the name attribute, not those created with the
	 * id attribute. Note that in
	 * [<a href='http://www.w3.org/TR/2002/REC-xhtml1-20020801'>XHTML 1.0</a>], the
	 * name attribute (see section 4.10) has no semantics and is only
	 * present for legacy user agents: the id attribute is used
	 * instead. Users should prefer the iterator mechanisms provided by [<a href=
	 * 'http://www.w3.org/TR/2000/REC-DOM-Level-2-Traversal-Range-20001113'>DOM
	 * Level 2 Traversal</a>] instead.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getAnchors();
	

	/**
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getAll();

	/**
	 * The element that contains the content for the document. In documents with
	 * BODY contents, returns the BODY element. In
	 * frameset documents, this returns the outermost FRAMESET element.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
	 */
	HTMLElement getBody();
	
	/**
	 * The element that contains the content for the document. In documents with
	 * HEAD contents, returns the HEAD element.
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.HTMLHeadElementImpl} object.
	 */
	HTMLHeadElementImpl getHead();


	/**
	 * This mutable string attribute denotes persistent state information that (1)
	 * is associated with the current frame or document and (2) is composed of
	 * information described by the cookies non-terminal of
	 * [<a href='http://www.ietf.org/rfc/rfc2965.txt'>IETF RFC 2965</a>], Section
	 * 4.2.2. <br>
	 * If no persistent state information is available for the current frame or
	 * document document, then this property's value is an empty string. <br>
	 * When this attribute is read, all cookies are returned as a single string,
	 * with each cookie's name-value pair concatenated into a list of name-value
	 * pairs, each list item being separated by a ';' (semicolon). <br>
	 * When this attribute is set, the value it is set to should be a string that
	 * adheres to the cookie non-terminal of
	 * [<a href='http://www.ietf.org/rfc/rfc2965.txt'>IETF RFC 2965</a>]; that is,
	 * it should be a single name-value pair followed by zero or more cookie
	 * attribute values. If no domain attribute is specified, then the domain
	 * attribute for the new value defaults to the host portion of an absolute URI
	 * [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of the
	 * current frame or document. If no path attribute is specified, then the path
	 * attribute for the new value defaults to the absolute path portion of the URI
	 * [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of the
	 * current frame or document. If no max-age attribute is specified, then the
	 * max-age attribute for the new value defaults to a user agent defined value.
	 * If a cookie with the specified name is already associated with the current
	 * frame or document, then the new value as well as the new attributes replace
	 * the old value and attributes. If a max-age attribute of 0 is specified for
	 * the new value, then any existing cookies of the specified name are removed
	 * from the cookie storage. See
	 * [<a href='http://www.ietf.org/rfc/rfc2965.txt'>IETF RFC 2965</a>] for the
	 * semantics of persistent state item attribute value pairs. The precise nature
	 * of a user agent session is not defined by this specification.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCookie();

	/**
	 * The domain name of the server that served the document, or null
	 * if the server cannot be identified by a domain name.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDomain();

	/**
	 * With [<a href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML 4.01</a>]
	 * documents, this method returns the (possibly empty) collection of elements
	 * whose name value is given by elementName. In
	 * [<a href='http://www.w3.org/TR/2002/REC-xhtml1-20020801'>XHTML 1.0</a>]
	 * documents, this methods only return the (possibly empty) collection of form
	 * controls with matching name. This method is case sensitive.
	 *
	 * @param elementName The name attribute value for an element.
	 * @return The matching elements.
	 */
	NodeList getElementsByName(String elementName);

	/**
	 * A collection of all the forms of a document.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getForms();

	/**
	 * A collection of all the IMG elements in a document. The behavior
	 * is limited to IMG elements for backwards compatibility. As
	 * suggested by [<a href='http://www.w3.org/TR/1999/REC-html401-19991224'>HTML
	 * 4.01</a>], to include images, authors may use the OBJECT element
	 * or the IMG element. Therefore, it is recommended not to use this
	 * attribute to find the images in the document but
	 * getElementsByTagName with HTML 4.01 or
	 * getElementsByTagNameNS with XHTML 1.0.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getImages();

	/**
	 * A collection of all AREA elements and anchor ( A)
	 * elements in a document with a value for the href attribute.
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	HTMLCollection getLinks();
	
    /*
     * The embeds attribute must return an HTMLCollection rooted at the Document
     * node, whose filter matches only embed elements.
     */
    /**
     * <p>getEmbeds.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getEmbeds();

    /*
     * The plugins attribute must return the same object as that returned by the
     * embeds attribute.
     */
    /**
     * <p>getPlugins.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getPlugins();

    /*
     * The scripts attribute must return an HTMLCollection rooted at the Document
     * node, whose filter matches only script elements.
     */
    /**
     * <p>getScripts.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getScripts();

    /*
     * The commands attribute of the document's HTMLDocument interface must return
     * an HTMLCollection rooted at the Document node, whose filter matches only
     * elements that define commands and have IDs.
     */
    /**
     * <p>getCommands.</p>
     *
     * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
     */
    HTMLCollection getCommands();

	/**
	 * Returns the URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>] of the page that linked to this page. The value is an empty string
	 * if the user navigated to the page directly (not through a link, but, for
	 * example, via a bookmark).
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getReferrer();

	/**
	 * The title of a document as specified by the TITLE element in the
	 * head of the document.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getTitle();

	/**
	 * The absolute URI [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC
	 * 2396</a>] of the document.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getURL();

	/**
	 * Open a document stream for writing. If a document exists in the target, this
	 * method clears it. This method and the ones following allow a user to add to
	 * or replace the structure model of a document using strings of unparsed HTML.
	 * At the time of writing alternate methods for providing similar functionality
	 * for both HTML and XML documents were being considered (see
	 * [<a href='http://www.w3.org/TR/2002/WD-DOM-Level-3-LS-20020725'>DOM Level 3
	 * Load and Save</a>]).
	 */
	void open();

	/**
	 * The element that contains the content for the document. In documents with
	 * BODY contents, returns the BODY element. In
	 * frameset documents, this returns the outermost FRAMESET element.
	 *
	 * @param body a {@link org.loboevolution.html.dom.HTMLElement} object.
	 */
	void setBody(HTMLElement body);

	/**
	 * This mutable string attribute denotes persistent state information that (1)
	 * is associated with the current frame or document and (2) is composed of
	 * information described by the cookies non-terminal of
	 * [<a href='http://www.ietf.org/rfc/rfc2965.txt'>IETF RFC 2965</a>], Section
	 * 4.2.2. <br>
	 * If no persistent state information is available for the current frame or
	 * document document, then this property's value is an empty string. <br>
	 * When this attribute is read, all cookies are returned as a single string,
	 * with each cookie's name-value pair concatenated into a list of name-value
	 * pairs, each list item being separated by a ';' (semicolon). <br>
	 * When this attribute is set, the value it is set to should be a string that
	 * adheres to the cookie non-terminal of
	 * [<a href='http://www.ietf.org/rfc/rfc2965.txt'>IETF RFC 2965</a>]; that is,
	 * it should be a single name-value pair followed by zero or more cookie
	 * attribute values. If no domain attribute is specified, then the domain
	 * attribute for the new value defaults to the host portion of an absolute URI
	 * [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of the
	 * current frame or document. If no path attribute is specified, then the path
	 * attribute for the new value defaults to the absolute path portion of the URI
	 * [<a href='http://www.ietf.org/rfc/rfc2396.txt'>IETF RFC 2396</a>] of the
	 * current frame or document. If no max-age attribute is specified, then the
	 * max-age attribute for the new value defaults to a user agent defined value.
	 * If a cookie with the specified name is already associated with the current
	 * frame or document, then the new value as well as the new attributes replace
	 * the old value and attributes. If a max-age attribute of 0 is specified for
	 * the new value, then any existing cookies of the specified name are removed
	 * from the cookie storage. See
	 * [<a href='http://www.ietf.org/rfc/rfc2965.txt'>IETF RFC 2965</a>] for the
	 * semantics of persistent state item attribute value pairs. The precise nature
	 * of a user agent session is not defined by this specification.
	 *
	 * @exception DOMException SYNTAX_ERR: If the new value does not adhere to the
	 *                         cookie syntax specified by
	 *                         [<a href='http://www.ietf.org/rfc/rfc2965.txt'>IETF
	 *                         RFC 2965</a>].
	 * @param cookie a {@link java.lang.String} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	void setCookie(String cookie) throws DOMException;

	/**
	 * The title of a document as specified by the TITLE element in the
	 * head of the document.
	 *
	 * @param title a {@link java.lang.String} object.
	 */
	void setTitle(String title);

	/**
	 * Write a string of text to a document stream opened by open() .
	 * Note that the function will produce a document which is not necessarily
	 * driven by a DTD and therefore might be produce an invalid result in the
	 * context of the document.
	 *
	 * @param text The string to be parsed into some structure in the document
	 *             structure model.
	 */
	void write(String text);

	/**
	 * Write a string of text followed by a newline character to a document stream
	 * opened by open(). Note that the function will produce a document
	 * which is not necessarily driven by a DTD and therefore might be produce an
	 * invalid result in the context of the document
	 *
	 * @param text The string to be parsed into some structure in the document
	 *             structure model.
	 */
	void writeln(String text);

	String getCharacterSet();
	
	String getCharset();
	

}
