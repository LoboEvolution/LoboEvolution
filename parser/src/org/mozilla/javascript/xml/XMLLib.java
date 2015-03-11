/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.xml;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Ref;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;


/**
 * The Class XMLLib.
 */
public abstract class XMLLib
{
    
    /** The Constant XML_LIB_KEY. */
    private static final Object XML_LIB_KEY = new Object();

	/**
		An object which specifies an XMLLib implementation to be used at runtime.

		This interface should be considered experimental.  It may be better
		(and certainly more flexible) to write an interface that returns an
		XMLLib object rather than a class name, for example.  But that would
		cause many more ripple effects in the code, all the way back to
		{@link ScriptRuntime}.
	 */
	public static abstract class Factory {
		
		/**
		 * Creates the.
		 *
		 * @param className the class name
		 * @return the factory
		 */
		public static Factory create(final String className) {
			return new Factory() {
			    @Override
				public String getImplementationClassName() {
					return className;
				}
			};
		}

		/**
		 * Gets the implementation class name.
		 *
		 * @return the implementation class name
		 */
		public abstract String getImplementationClassName();
	}

    /**
     * Extract from scope or null.
     *
     * @param scope the scope
     * @return the XML lib
     */
    public static XMLLib extractFromScopeOrNull(Scriptable scope)
    {
        ScriptableObject so = ScriptRuntime.getLibraryScopeOrNull(scope);
        if (so == null) {
            // If library is not yet initialized, return null
            return null;
        }

        // Ensure lazily initialization of real XML library instance
        // which is done on first access to XML property
        ScriptableObject.getProperty(so, "XML");

        return (XMLLib)so.getAssociatedValue(XML_LIB_KEY);
    }

    /**
     * Extract from scope.
     *
     * @param scope the scope
     * @return the XML lib
     */
    public static XMLLib extractFromScope(Scriptable scope)
    {
        XMLLib lib = extractFromScopeOrNull(scope);
        if (lib != null) {
            return lib;
        }
        String msg = ScriptRuntime.getMessage0("msg.XML.not.available");
        throw Context.reportRuntimeError(msg);
    }

    /**
     * Bind to scope.
     *
     * @param scope the scope
     * @return the XML lib
     */
    protected final XMLLib bindToScope(Scriptable scope)
    {
        ScriptableObject so = ScriptRuntime.getLibraryScopeOrNull(scope);
        if (so == null) {
            // standard library should be initialized at this point
            throw new IllegalStateException();
        }
        return (XMLLib)so.associateValue(XML_LIB_KEY, this);
    }

    /**
     * Checks if is XML name.
     *
     * @param cx the cx
     * @param name the name
     * @return true, if is XML name
     */
    public abstract boolean isXMLName(Context cx, Object name);

    /**
     * Name ref.
     *
     * @param cx the cx
     * @param name the name
     * @param scope the scope
     * @param memberTypeFlags the member type flags
     * @return the ref
     */
    public abstract Ref nameRef(Context cx, Object name,
                                Scriptable scope, int memberTypeFlags);

    /**
     * Name ref.
     *
     * @param cx the cx
     * @param namespace the namespace
     * @param name the name
     * @param scope the scope
     * @param memberTypeFlags the member type flags
     * @return the ref
     */
    public abstract Ref nameRef(Context cx, Object namespace, Object name,
                                Scriptable scope, int memberTypeFlags);

    /**
     * Escapes the reserved characters in a value of an attribute.
     *
     * @param value Unescaped text
     * @return The escaped text
     */
    public abstract String escapeAttributeValue(Object value);

    /**
     * Escapes the reserved characters in a value of a text node.
     *
     * @param value Unescaped text
     * @return The escaped text
     */
    public abstract String escapeTextValue(Object value);


    /**
     * Construct namespace for default xml statement.
     *
     * @param cx the cx
     * @param uriValue the uri value
     * @return the object
     */
    public abstract Object toDefaultXmlNamespace(Context cx, Object uriValue);

    /**
     * Sets the ignore comments.
     *
     * @param b the new ignore comments
     */
    public void setIgnoreComments(boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the ignore whitespace.
     *
     * @param b the new ignore whitespace
     */
    public void setIgnoreWhitespace(boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the ignore processing instructions.
     *
     * @param b the new ignore processing instructions
     */
    public void setIgnoreProcessingInstructions(boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the pretty printing.
     *
     * @param b the new pretty printing
     */
    public void setPrettyPrinting(boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * Sets the pretty indent.
     *
     * @param i the new pretty indent
     */
    public void setPrettyIndent(int i) {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if is ignore comments.
     *
     * @return true, if is ignore comments
     */
    public boolean isIgnoreComments() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if is ignore processing instructions.
     *
     * @return true, if is ignore processing instructions
     */
    public boolean isIgnoreProcessingInstructions() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if is ignore whitespace.
     *
     * @return true, if is ignore whitespace
     */
    public boolean isIgnoreWhitespace() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if is pretty printing.
     *
     * @return true, if is pretty printing
     */
    public boolean isPrettyPrinting() {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets the pretty indent.
     *
     * @return the pretty indent
     */
    public int getPrettyIndent() {
        throw new UnsupportedOperationException();
    }
}
