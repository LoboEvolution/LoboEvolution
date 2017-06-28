/*
 * Copyright (c) 1999 World Wide Web Consortium
 * (Massachusetts Institute of Technology, Institut National de Recherche
 *  en Informatique et en Automatique, Keio University).
 * All Rights Reserved. http://www.w3.org/Consortium/Legal/
 *
 * The original version of this interface comes from SAX :
 * http://www.megginson.com/SAX/
 *
 * $Id: Parser.java 477010 2006-11-20 02:54:38Z mrglavas $
 */
package org.w3c.css.sac;

import java.io.IOException;
import java.util.Locale;

/**
 * Basic interface for CSS (Simple API for CSS) parsers.
 * 
 * <p>
 * All CSS parsers must implement this basic interface: it allows applications
 * to register handlers for different types of events and to initiate a parse
 * from a URI, or a character stream.
 * </p>
 * 
 * <p>
 * All CSS parsers must also implement a zero-argument constructor (though other
 * constructors are also allowed).
 * </p>
 * 
 * <p>
 * CSS parsers are reusable but not re-entrant: the application may reuse a
 * parser object (possibly with a different input source) once the first parse
 * has completed successfully, but it may not invoke the parse() methods
 * recursively within a parse.
 * </p>
 *
 * @author Philippe Le Hegaret
 * @version $Revision: 477010 $
 * @see DocumentHandler
 * @see ErrorHandler
 * @see InputSource
 */
public interface Parser {

	/**
	 * Allow an application to request a locale for errors and warnings.
	 * 
	 * <p>
	 * CSS parsers are not required to provide localisation for errors and
	 * warnings; if they cannot support the requested locale, however, they must
	 * throw a CSS exception. Applications may not request a locale change in
	 * the middle of a parse.
	 * </p>
	 *
	 * @param locale
	 *            A Java Locale object.
	 * @see CSSException
	 * @see CSSParseException
	 * @exception CSSException
	 *                Throws an exception (using the previous or default locale)
	 *                if the requested locale is not supported.
	 */
	public void setLocale(Locale locale) throws CSSException;

	/**
	 * Allow an application to register a document event handler.
	 *
	 * <p>
	 * If the application does not register a document handler, all document
	 * events reported by the CSS parser will be silently ignored (this is the
	 * default behaviour implemented by HandlerBase).
	 * </p>
	 *
	 * <p>
	 * Applications may register a new or different handler in the middle of a
	 * parse, and the CSS parser must begin using the new handler immediately.
	 * </p>
	 *
	 * @param handler
	 *            The document handler.
	 * @see DocumentHandler
	 */
	public void setDocumentHandler(DocumentHandler handler);

	/**
	 * Sets the selector factory.
	 *
	 * @param selectorFactory
	 *            the new selector factory
	 */
	public void setSelectorFactory(SelectorFactory selectorFactory);

	/**
	 * Sets the condition factory.
	 *
	 * @param conditionFactory
	 *            the new condition factory
	 */
	public void setConditionFactory(ConditionFactory conditionFactory);

	/**
	 * Allow an application to register an error event handler.
	 *
	 * <p>
	 * If the application does not register an error event handler, all error
	 * events reported by the CSS parser will be silently ignored, except for
	 * fatalError, which will throw a CSSException (this is the default
	 * behaviour implemented by HandlerBase).
	 * </p>
	 *
	 * <p>
	 * Applications may register a new or different handler in the middle of a
	 * parse, and the CSS parser must begin using the new handler immediately.
	 * </p>
	 *
	 * @param handler
	 *            The error handler.
	 * @see ErrorHandler
	 * @see CSSException
	 */
	public void setErrorHandler(ErrorHandler handler);

	/**
	 * Parse a CSS document.
	 * 
	 * <p>
	 * The application can use this method to instruct the CSS parser to begin
	 * parsing an CSS document from any valid input source (a character stream,
	 * a byte stream, or a URI).
	 * </p>
	 * 
	 * <p>
	 * Applications may not invoke this method while a parse is in progress
	 * (they should create a new Parser instead for each additional CSS
	 * document). Once a parse is complete, an application may reuse the same
	 * Parser object, possibly with a different input source.
	 * </p>
	 *
	 * @param source
	 *            The input source for the top-level of the CSS document.
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see InputSource
	 * @see #parseStyleSheet(java.lang.String)
	 * @see #setDocumentHandler
	 * @see #setErrorHandler
	 */
	public void parseStyleSheet(InputSource source) throws CSSException, IOException;

	/**
	 * Parse a CSS document from a URI.
	 * 
	 * <p>
	 * This method is a shortcut for the common case of reading a document from
	 * a URI. It is the exact equivalent of the following:
	 * </p>
	 * 
	 * <pre>
	 * parse(new InputSource(uri));
	 * </pre>
	 * 
	 * <p>
	 * The URI must be fully resolved by the application before it is passed to
	 * the parser.
	 * </p>
	 *
	 * @param uri
	 *            The URI.
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @see #parseStyleSheet(InputSource)
	 */
	public void parseStyleSheet(String uri) throws CSSException, IOException;

	/**
	 * Parse a CSS style declaration (without '{' and '}').
	 *
	 * @param source
	 *            The declaration.
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void parseStyleDeclaration(InputSource source) throws CSSException, IOException;

	/**
	 * Parse a CSS rule.
	 *
	 * @param source
	 *            the source
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void parseRule(InputSource source) throws CSSException, IOException;

	/**
	 * Returns a string about which CSS language is supported by this parser.
	 * For CSS Level 1, it returns "http://www.w3.org/TR/REC-CSS1", for CSS
	 * Level 2, it returns "http://www.w3.org/TR/REC-CSS2". Note that a "CSSx"
	 * parser can return lexical unit other than those allowed by CSS Level x
	 * but this usage is not recommended.
	 *
	 * @return the parser version
	 */
	public String getParserVersion();

	/**
	 * Parse a comma separated list of selectors.
	 *
	 * @param source
	 *            the source
	 * @return the selector list
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public SelectorList parseSelectors(InputSource source) throws CSSException, IOException;

	/**
	 * Parse a CSS property value.
	 *
	 * @param source
	 *            the source
	 * @return the lexical unit
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public LexicalUnit parsePropertyValue(InputSource source) throws CSSException, IOException;

	/**
	 * Parse a CSS priority value (e.g. "!important").
	 *
	 * @param source
	 *            the source
	 * @return true, if successful
	 * @exception CSSException
	 *                Any CSS exception, possibly wrapping another exception.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public boolean parsePriority(InputSource source) throws CSSException, IOException;
}
