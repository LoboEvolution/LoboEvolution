/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;


/**
 * The Class IdFunctionObject.
 */
public class IdFunctionObject extends BaseFunction
{

    /** The Constant serialVersionUID. */
    static final long serialVersionUID = -5332312783643935019L;

    /**
     * Instantiates a new id function object.
     *
     * @param idcall the idcall
     * @param tag the tag
     * @param id the id
     * @param arity the arity
     */
    public IdFunctionObject(IdFunctionCall idcall, Object tag, int id, int arity)
    {
        if (arity < 0)
            throw new IllegalArgumentException();

        this.idcall = idcall;
        this.tag = tag;
        this.methodId = id;
        this.arity = arity;
        if (arity < 0) throw new IllegalArgumentException();
    }

    /**
     * Instantiates a new id function object.
     *
     * @param idcall the idcall
     * @param tag the tag
     * @param id the id
     * @param name the name
     * @param arity the arity
     * @param scope the scope
     */
    public IdFunctionObject(IdFunctionCall idcall, Object tag, int id,
                            String name, int arity, Scriptable scope)
    {
        super(scope, null);

        if (arity < 0)
            throw new IllegalArgumentException();
        if (name == null)
            throw new IllegalArgumentException();

        this.idcall = idcall;
        this.tag = tag;
        this.methodId = id;
        this.arity = arity;
        this.functionName = name;
    }

    /**
     * Inits the function.
     *
     * @param name the name
     * @param scope the scope
     */
    public void initFunction(String name, Scriptable scope)
    {
        if (name == null) throw new IllegalArgumentException();
        if (scope == null) throw new IllegalArgumentException();
        this.functionName = name;
        setParentScope(scope);
    }

    /**
     * Checks for tag.
     *
     * @param tag the tag
     * @return true, if successful
     */
    public final boolean hasTag(Object tag)
    {
        return tag == null ? this.tag == null : tag.equals(this.tag);
    }

    /**
     * Method id.
     *
     * @return the int
     */
    public final int methodId()
    {
        return methodId;
    }

    /**
     * Mark as constructor.
     *
     * @param prototypeProperty the prototype property
     */
    public final void markAsConstructor(Scriptable prototypeProperty)
    {
        useCallAsConstructor = true;
        setImmunePrototypeProperty(prototypeProperty);
    }

    /**
     * Adds the as property.
     *
     * @param target the target
     */
    public final void addAsProperty(Scriptable target)
    {
        ScriptableObject.defineProperty(target, functionName, this,
                                        ScriptableObject.DONTENUM);
    }

    /**
     * Export as scope property.
     */
    public void exportAsScopeProperty()
    {
        addAsProperty(getParentScope());
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getPrototype()
     */
    @Override
    public Scriptable getPrototype()
    {
        // Lazy initialization of prototype: for native functions this
        // may not be called at all
        Scriptable proto = super.getPrototype();
        if (proto == null) {
            proto = getFunctionPrototype(getParentScope());
            setPrototype(proto);
        }
        return proto;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.BaseFunction#call(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    @Override
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args)
    {
        return idcall.execIdCall(this, cx, scope, thisObj, args);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.BaseFunction#createObject(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable)
     */
    @Override
    public Scriptable createObject(Context cx, Scriptable scope)
    {
        if (useCallAsConstructor) {
            return null;
        }
        // Throw error if not explicitly coded to be used as constructor,
        // to satisfy ECMAScript standard (see bugzilla 202019).
        // To follow current (2003-05-01) SpiderMonkey behavior, change it to:
        // return super.createObject(cx, scope);
        throw ScriptRuntime.typeError1("msg.not.ctor", functionName);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.BaseFunction#decompile(int, int)
     */
    @Override
    String decompile(int indent, int flags)
    {
        StringBuilder sb = new StringBuilder();
        boolean justbody = (0 != (flags & Decompiler.ONLY_BODY_FLAG));
        if (!justbody) {
            sb.append("function ");
            sb.append(getFunctionName());
            sb.append("() { ");
        }
        sb.append("[native code for ");
        if (idcall instanceof Scriptable) {
            Scriptable sobj = (Scriptable)idcall;
            sb.append(sobj.getClassName());
            sb.append('.');
        }
        sb.append(getFunctionName());
        sb.append(", arity=");
        sb.append(getArity());
        sb.append(justbody ? "]\n" : "] }\n");
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.BaseFunction#getArity()
     */
    @Override
    public int getArity()
    {
        return arity;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.BaseFunction#getLength()
     */
    @Override
    public int getLength() { return getArity(); }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.BaseFunction#getFunctionName()
     */
    @Override
    public String getFunctionName()
    {
        return (functionName == null) ? "" : functionName;
    }

    /**
     * Unknown.
     *
     * @return the runtime exception
     */
    public final RuntimeException unknown()
    {
        // It is program error to call id-like methods for unknown function
        return new IllegalArgumentException(
            "BAD FUNCTION ID="+methodId+" MASTER="+idcall);
    }

    /** The idcall. */
    private final IdFunctionCall idcall;
    
    /** The tag. */
    private final Object tag;
    
    /** The method id. */
    private final int methodId;
    
    /** The arity. */
    private int arity;
    
    /** The use call as constructor. */
    private boolean useCallAsConstructor;
    
    /** The function name. */
    private String functionName;
}
