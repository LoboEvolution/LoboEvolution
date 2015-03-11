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
 */
public abstract class XMLObject extends IdScriptableObject
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = 8455156490438576500L;
    
    /**
     * Instantiates a new XML object.
     */
    public XMLObject()
    {
    }

    /**
     * Instantiates a new XML object.
     *
     * @param scope the scope
     * @param prototype the prototype
     */
    public XMLObject(Scriptable scope, Scriptable prototype)
    {
        super(scope, prototype);
    }

    /**
     * Implementation of ECMAScript [[Has]].
     *
     * @param cx the cx
     * @param id the id
     * @return true, if successful
     */
    public abstract boolean has(Context cx, Object id);

    /**
     * Implementation of ECMAScript [[Get]].
     *
     * @param cx the cx
     * @param id the id
     * @return the object
     */
    public abstract Object get(Context cx, Object id);

    /**
     * Implementation of ECMAScript [[Put]].
     *
     * @param cx the cx
     * @param id the id
     * @param value the value
     */
    public abstract void put(Context cx, Object id, Object value);

    /**
     * Implementation of ECMAScript [[Delete]].
     *
     * @param cx the cx
     * @param id the id
     * @return true, if successful
     */
    public abstract boolean delete(Context cx, Object id);


    /**
     * Gets the function property.
     *
     * @param cx the cx
     * @param name the name
     * @return the function property
     */
    public abstract Object getFunctionProperty(Context cx, String name);

    /**
     * Gets the function property.
     *
     * @param cx the cx
     * @param id the id
     * @return the function property
     */
    public abstract Object getFunctionProperty(Context cx, int id);

    /**
     * Return an additional object to look for methods that runtime should
     * consider during method search. Return null if no such object available.
     *
     * @param cx the cx
     * @return the extra method source
     */
    public abstract Scriptable getExtraMethodSource(Context cx);

    /**
     * Generic reference to implement x.@y, x..y etc.
     *
     * @param cx the cx
     * @param elem the elem
     * @param memberTypeFlags the member type flags
     * @return the ref
     */
    public abstract Ref memberRef(Context cx, Object elem,
                                  int memberTypeFlags);

    /**
     * Generic reference to implement x::ns, x.@ns::y, x..@ns::y etc.
     *
     * @param cx the cx
     * @param namespace the namespace
     * @param elem the elem
     * @param memberTypeFlags the member type flags
     * @return the ref
     */
    public abstract Ref memberRef(Context cx, Object namespace, Object elem,
                                  int memberTypeFlags);

    /**
     * Wrap this object into NativeWith to implement the with statement.
     *
     * @param scope the scope
     * @return the native with
     */
    public abstract NativeWith enterWith(Scriptable scope);

    /**
     * Wrap this object into NativeWith to implement the .() query.
     *
     * @param scope the scope
     * @return the native with
     */
    public abstract NativeWith enterDotQuery(Scriptable scope);

    /**
     * Custom <tt>+</tt> operator.
     * Should return {@link Scriptable#NOT_FOUND} if this object does not have
     * custom addition operator for the given value,
     * or the result of the addition operation.
     * <p>
     * The default implementation returns {@link Scriptable#NOT_FOUND}
     * to indicate no custom addition operation.
     *
     * @param cx the Context object associated with the current thread.
     * @param thisIsLeft if true, the object should calculate this + value
     *                   if false, the object should calculate value + this.
     * @param value the second argument for addition operation.
     * @return the object
     */
    public Object addValues(Context cx, boolean thisIsLeft, Object value)
    {
        return Scriptable.NOT_FOUND;
    }

    /**
     * Gets the value returned by calling the typeof operator on this object.
     *
     * @return "xml" or "undefined" if {@link #avoidObjectDetection()} returns <code>true</code>
     * @see org.mozilla.javascript.ScriptableObject#getTypeOf()
     */
    @Override
    public String getTypeOf()
    {
    	return avoidObjectDetection() ? "undefined" : "xml";
    }
}
