/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript.xml;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdScriptableObject;
import org.mozilla.javascript.NativeWith;
import org.mozilla.javascript.Ref;
import org.mozilla.javascript.Scriptable;

/**
 *  This Interface describes what all XML objects (XML, XMLList) should have in common.
 *
 *
 *
 */
public abstract class XMLObject extends IdScriptableObject
{

    private static final long serialVersionUID = 8455156490438576500L;

    /**
     * <p>Constructor for XMLObject.</p>
     */
    public XMLObject()
    {
    }

    /**
     * <p>Constructor for XMLObject.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param prototype a {@link org.mozilla.javascript.Scriptable} object.
     */
    public XMLObject(Scriptable scope, Scriptable prototype)
    {
        super(scope, prototype);
    }

    /**
     * Implementation of ECMAScript [[Has]].
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param id a {@link java.lang.Object} object.
     * @return a boolean.
     */
    public abstract boolean has(Context cx, Object id);

    /**
     * Implementation of ECMAScript [[Get]].
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param id a {@link java.lang.Object} object.
     * @return a {@link java.lang.Object} object.
     */
    public abstract Object get(Context cx, Object id);

    /**
     * {@inheritDoc}
     *
     * Implementation of ECMAScript [[Put]].
     */
    public abstract void put(Context cx, Object id, Object value);

    /**
     * Implementation of ECMAScript [[Delete]].
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param id a {@link java.lang.Object} object.
     * @return a boolean.
     */
    public abstract boolean delete(Context cx, Object id);


    /**
     * <p>getFunctionProperty.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param name a {@link java.lang.String} object.
     * @return a {@link java.lang.Object} object.
     */
    public abstract Object getFunctionProperty(Context cx, String name);

    /**
     * <p>getFunctionProperty.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param id a int.
     * @return a {@link java.lang.Object} object.
     */
    public abstract Object getFunctionProperty(Context cx, int id);

    /**
     * Return an additional object to look for methods that runtime should
     * consider during method search. Return null if no such object available.
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @return a {@link org.mozilla.javascript.Scriptable} object.
     */
    public abstract Scriptable getExtraMethodSource(Context cx);

    /**
     * Generic reference to implement x.@y, x..y etc.
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param elem a {@link java.lang.Object} object.
     * @param memberTypeFlags a int.
     * @return a {@link org.mozilla.javascript.Ref} object.
     */
    public abstract Ref memberRef(Context cx, Object elem,
                                  int memberTypeFlags);

    /**
     * Generic reference to implement x::ns, x.@ns::y, x..@ns::y etc.
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param namespace a {@link java.lang.Object} object.
     * @param elem a {@link java.lang.Object} object.
     * @param memberTypeFlags a int.
     * @return a {@link org.mozilla.javascript.Ref} object.
     */
    public abstract Ref memberRef(Context cx, Object namespace, Object elem,
                                  int memberTypeFlags);

    /**
     * Wrap this object into NativeWith to implement the with statement.
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link org.mozilla.javascript.NativeWith} object.
     */
    public abstract NativeWith enterWith(Scriptable scope);

    /**
     * Wrap this object into NativeWith to implement the .() query.
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link org.mozilla.javascript.NativeWith} object.
     */
    public abstract NativeWith enterDotQuery(Scriptable scope);

    /**
     * Custom <code>+</code> operator.
     * Should return {@link org.mozilla.javascript.Scriptable#NOT_FOUND} if this object does not have
     * custom addition operator for the given value,
     * or the result of the addition operation.
     * <p>
     * The default implementation returns {@link org.mozilla.javascript.Scriptable#NOT_FOUND}
     * to indicate no custom addition operation.
     *
     * @param cx the Context object associated with the current thread.
     * @param thisIsLeft if true, the object should calculate this + value
     *                   if false, the object should calculate value + this.
     * @param value the second argument for addition operation.
     * @return a {@link java.lang.Object} object.
     */
    public Object addValues(Context cx, boolean thisIsLeft, Object value)
    {
        return Scriptable.NOT_FOUND;
    }

    /**
     * {@inheritDoc}
     *
     * Gets the value returned by calling the typeof operator on this object.
     * @see org.mozilla.javascript.ScriptableObject#getTypeOf()
     */
    @Override
    public String getTypeOf()
    {
        return avoidObjectDetection() ? "undefined" : "xml";
    }
}
