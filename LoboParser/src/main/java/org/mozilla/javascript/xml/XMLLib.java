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
 * <p>Abstract XMLLib class.</p>
 *
 *
 *
 */
public abstract class XMLLib
{
    private static final Object XML_LIB_KEY = new Object();

  /**
   * An object which specifies an XMLLib implementation to be used at runtime.
   *
   * This interface should be considered experimental.  It may be better
   * (and certainly more flexible) to write an interface that returns an
   * XMLLib object rather than a class name, for example.  But that would
   * cause many more ripple effects in the code, all the way back to
   * {@link ScriptRuntime}.
   */
  public static abstract class Factory {

    public static Factory create(final String className) {
      return new Factory() {
        @Override
        public String getImplementationClassName() {
          return className;
        }
      };
    }

    public abstract String getImplementationClassName();
  }

    /**
     * <p>extractFromScopeOrNull.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link org.mozilla.javascript.xml.XMLLib} object.
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
     * <p>extractFromScope.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link org.mozilla.javascript.xml.XMLLib} object.
     */
    public static XMLLib extractFromScope(Scriptable scope)
    {
        XMLLib lib = extractFromScopeOrNull(scope);
        if (lib != null) {
            return lib;
        }
        String msg = ScriptRuntime.getMessageById("msg.XML.not.available");
        throw Context.reportRuntimeError(msg);
    }

    /**
     * <p>bindToScope.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link org.mozilla.javascript.xml.XMLLib} object.
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
     * <p>isXMLName.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param name a {@link java.lang.Object} object.
     * @return a boolean.
     */
    public abstract boolean isXMLName(Context cx, Object name);

    /**
     * <p>nameRef.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param name a {@link java.lang.Object} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param memberTypeFlags a int.
     * @return a {@link org.mozilla.javascript.Ref} object.
     */
    public abstract Ref nameRef(Context cx, Object name,
                                Scriptable scope, int memberTypeFlags);

    /**
     * <p>nameRef.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param namespace a {@link java.lang.Object} object.
     * @param name a {@link java.lang.Object} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param memberTypeFlags a int.
     * @return a {@link org.mozilla.javascript.Ref} object.
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
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param uriValue a {@link java.lang.Object} object.
     * @return a {@link java.lang.Object} object.
     */
    public abstract Object toDefaultXmlNamespace(Context cx, Object uriValue);

    /**
     * <p>setIgnoreComments.</p>
     *
     * @param b a boolean.
     */
    public void setIgnoreComments(boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>setIgnoreWhitespace.</p>
     *
     * @param b a boolean.
     */
    public void setIgnoreWhitespace(boolean b) {
      throw new UnsupportedOperationException();
    }

    /**
     * <p>setIgnoreProcessingInstructions.</p>
     *
     * @param b a boolean.
     */
    public void setIgnoreProcessingInstructions(boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>setPrettyPrinting.</p>
     *
     * @param b a boolean.
     */
    public void setPrettyPrinting(boolean b) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>setPrettyIndent.</p>
     *
     * @param i a int.
     */
    public void setPrettyIndent(int i) {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>isIgnoreComments.</p>
     *
     * @return a boolean.
     */
    public boolean isIgnoreComments() {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>isIgnoreProcessingInstructions.</p>
     *
     * @return a boolean.
     */
    public boolean isIgnoreProcessingInstructions() {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>isIgnoreWhitespace.</p>
     *
     * @return a boolean.
     */
    public boolean isIgnoreWhitespace() {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>isPrettyPrinting.</p>
     *
     * @return a boolean.
     */
    public boolean isPrettyPrinting() {
        throw new UnsupportedOperationException();
    }

    /**
     * <p>getPrettyIndent.</p>
     *
     * @return a int.
     */
    public int getPrettyIndent() {
        throw new UnsupportedOperationException();
    }
}
