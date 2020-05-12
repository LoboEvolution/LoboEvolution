/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;

/**
 * <p>IdFunctionObject class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class IdFunctionObject extends BaseFunction
{

    private static final long serialVersionUID = -5332312783643935019L;

    /**
     * <p>Constructor for IdFunctionObject.</p>
     *
     * @param idcall a {@link org.mozilla.javascript.IdFunctionCall} object.
     * @param tag a {@link java.lang.Object} object.
     * @param id a int.
     * @param arity a int.
     */
    public IdFunctionObject(IdFunctionCall idcall, Object tag, int id, int arity)
    {
        if (arity < 0)
            throw new IllegalArgumentException();

        this.idcall = idcall;
        this.tag = tag;
        this.methodId = id;
        this.arity = arity;
    }

    /**
     * <p>Constructor for IdFunctionObject.</p>
     *
     * @param idcall a {@link org.mozilla.javascript.IdFunctionCall} object.
     * @param tag a {@link java.lang.Object} object.
     * @param id a int.
     * @param name a {@link java.lang.String} object.
     * @param arity a int.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
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
     * <p>initFunction.</p>
     *
     * @param name a {@link java.lang.String} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     */
    public void initFunction(String name, Scriptable scope)
    {
        if (name == null) throw new IllegalArgumentException();
        if (scope == null) throw new IllegalArgumentException();
        this.functionName = name;
        setParentScope(scope);
    }

    /**
     * <p>hasTag.</p>
     *
     * @param tag a {@link java.lang.Object} object.
     * @return a boolean.
     */
    public final boolean hasTag(Object tag)
    {
        return tag == null ? this.tag == null : tag.equals(this.tag);
    }

    /**
     * <p>Getter for the field tag.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    public Object getTag() {
        return tag;
    }

    /**
     * <p>methodId.</p>
     *
     * @return a int.
     */
    public final int methodId()
    {
        return methodId;
    }

    /**
     * <p>markAsConstructor.</p>
     *
     * @param prototypeProperty a {@link org.mozilla.javascript.Scriptable} object.
     */
    public final void markAsConstructor(Scriptable prototypeProperty)
    {
        useCallAsConstructor = true;
        setImmunePrototypeProperty(prototypeProperty);
    }

    /**
     * <p>addAsProperty.</p>
     *
     * @param target a {@link org.mozilla.javascript.Scriptable} object.
     */
    public final void addAsProperty(Scriptable target)
    {
        ScriptableObject.defineProperty(target, functionName, this,
                                        ScriptableObject.DONTENUM);
    }

    /**
     * <p>exportAsScopeProperty.</p>
     */
    public void exportAsScopeProperty()
    {
        addAsProperty(getParentScope());
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args)
    {
        return idcall.execIdCall(this, cx, scope, thisObj, args);
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public int getArity()
    {
        return arity;
    }

    /** {@inheritDoc} */
    @Override
    public int getLength() { return getArity(); }

    /** {@inheritDoc} */
    @Override
    public String getFunctionName()
    {
        return (functionName == null) ? "" : functionName;
    }

    /**
     * <p>unknown.</p>
     *
     * @return a {@link java.lang.RuntimeException} object.
     */
    public final RuntimeException unknown()
    {
        // It is program error to call id-like methods for unknown function
        return new IllegalArgumentException(
            "BAD FUNCTION ID="+methodId+" MASTER="+idcall);
    }

    static boolean equalObjectGraphs(IdFunctionObject f1, IdFunctionObject f2, EqualObjectGraphs eq) {
        return f1.methodId == f2.methodId && f1.hasTag(f2.tag) && eq.equalGraphs(f1.idcall, f2.idcall);
    }

    private final IdFunctionCall idcall;
    private final Object tag;
    private final int methodId;
    private int arity;
    private boolean useCallAsConstructor;
    private String functionName;
}
