/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

// API class

package org.mozilla.javascript;


/**
 * Class ImporterTopLevel
 *
 * This class defines a ScriptableObject that can be instantiated
 * as a top-level ("global") object to provide functionality similar
 * to Java's "import" statement.
 * <p>
 * This class can be used to create a top-level scope using the following code:
 * <pre>
 *  Scriptable scope = new ImporterTopLevel(cx);
 * </pre>
 * Then JavaScript code will have access to the following methods:
 * <ul>
 * <li>importClass - will "import" a class by making its unqualified name
 *                   available as a property of the top-level scope
 * <li>importPackage - will "import" all the classes of the package by
 *                     searching for unqualified names as classes qualified
 *                     by the given package.
 * </ul>
 * The following code from the shell illustrates this use:
 * <pre>
 * importClass(java.io.File)
 * f = new File('help.txt')
 * help.txt
 * importPackage(java.util)
 *  v = new Vector()
 * []
 * </pre>
 * @author Norris Boyd
 */
public class ImporterTopLevel extends TopLevel {
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = -9095380847465315412L;

    /** The Constant IMPORTER_TAG. */
    private static final Object IMPORTER_TAG = "Importer";

    /**
     * Instantiates a new importer top level.
     */
    public ImporterTopLevel() { }

    /**
     * Instantiates a new importer top level.
     *
     * @param cx the cx
     */
    public ImporterTopLevel(Context cx) {
        this(cx, false);
    }

    /**
     * Instantiates a new importer top level.
     *
     * @param cx the cx
     * @param sealed the sealed
     */
    public ImporterTopLevel(Context cx, boolean sealed)
    {
        initStandardObjects(cx, sealed);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.TopLevel#getClassName()
     */
    @Override
    public String getClassName()
    {
        return (topScopeFlag) ? "global" : "JavaImporter";
    }

    /**
     * Inits the.
     *
     * @param cx the cx
     * @param scope the scope
     * @param sealed the sealed
     */
    public static void init(Context cx, Scriptable scope, boolean sealed)
    {
        ImporterTopLevel obj = new ImporterTopLevel();
        obj.exportAsJSClass(MAX_PROTOTYPE_ID, scope, sealed);
    }

    /**
     * Inits the standard objects.
     *
     * @param cx the cx
     * @param sealed the sealed
     */
    public void initStandardObjects(Context cx, boolean sealed)
    {
        // Assume that Context.initStandardObjects initialize JavaImporter
        // property lazily so the above init call is not yet called
        cx.initStandardObjects(this, sealed);
        topScopeFlag = true;
        // If seal is true then exportAsJSClass(cx, seal) would seal
        // this obj. Since this is scope as well, it would not allow
        // to add variables.
        IdFunctionObject ctor = exportAsJSClass(MAX_PROTOTYPE_ID, this, false);
        if (sealed) {
            ctor.sealObject();
        }
        // delete "constructor" defined by exportAsJSClass so "constructor"
        // name would refer to Object.constructor
        // and not to JavaImporter.prototype.constructor.
        delete("constructor");
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#has(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    @Override
    public boolean has(String name, Scriptable start) {
        return super.has(name, start)
               || getPackageProperty(name, start) != NOT_FOUND;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#get(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    @Override
    public Object get(String name, Scriptable start) {
        Object result = super.get(name, start);
        if (result != NOT_FOUND)
            return result;
        result = getPackageProperty(name, start);
        return result;
    }

    /**
     * Gets the package property.
     *
     * @param name the name
     * @param start the start
     * @return the package property
     */
    private Object getPackageProperty(String name, Scriptable start) {
        Object result = NOT_FOUND;
        Object[] elements;
        synchronized (importedPackages) {
            elements = importedPackages.toArray();
        }
        for (int i=0; i < elements.length; i++) {
            NativeJavaPackage p = (NativeJavaPackage) elements[i];
            Object v = p.getPkgProperty(name, start, false);
            if (v != null && !(v instanceof NativeJavaPackage)) {
                if (result == NOT_FOUND) {
                    result = v;
                } else {
                    throw Context.reportRuntimeError2(
                        "msg.ambig.import", result.toString(), v.toString());
                }
            }
        }
        return result;
    }

    /**
     * Import package.
     *
     * @param cx the cx
     * @param thisObj the this obj
     * @param args the args
     * @param funObj the fun obj
     * @deprecated Kept only for compatibility.
     */
    public void importPackage(Context cx, Scriptable thisObj, Object[] args,
                              Function funObj)
    {
        js_importPackage(args);
    }

    /**
     * Js_construct.
     *
     * @param scope the scope
     * @param args the args
     * @return the object
     */
    private Object js_construct(Scriptable scope, Object[] args)
    {
        ImporterTopLevel result = new ImporterTopLevel();
        for (int i = 0; i != args.length; ++i) {
            Object arg = args[i];
            if (arg instanceof NativeJavaClass) {
                result.importClass((NativeJavaClass)arg);
            } else if (arg instanceof NativeJavaPackage) {
                result.importPackage((NativeJavaPackage)arg);
            } else {
                throw Context.reportRuntimeError1(
                    "msg.not.class.not.pkg", Context.toString(arg));
            }
        }
        // set explicitly prototype and scope
        // as otherwise in top scope mode BaseFunction.construct
        // would keep them set to null. It also allow to use
        // JavaImporter without new and still get properly
        // initialized object.
        result.setParentScope(scope);
        result.setPrototype(this);
        return result;
    }

    /**
     * Js_import class.
     *
     * @param args the args
     * @return the object
     */
    private Object js_importClass(Object[] args)
    {
        for (int i = 0; i != args.length; i++) {
            Object arg = args[i];
            if (!(arg instanceof NativeJavaClass)) {
                throw Context.reportRuntimeError1(
                    "msg.not.class", Context.toString(arg));
            }
            importClass((NativeJavaClass)arg);
        }
        return Undefined.instance;
    }

    /**
     * Js_import package.
     *
     * @param args the args
     * @return the object
     */
    private Object js_importPackage(Object[] args)
    {
        for (int i = 0; i != args.length; i++) {
            Object arg = args[i];
            if (!(arg instanceof NativeJavaPackage)) {
                throw Context.reportRuntimeError1(
                    "msg.not.pkg", Context.toString(arg));
            }
            importPackage((NativeJavaPackage)arg);
        }
        return Undefined.instance;
    }

    /**
     * Import package.
     *
     * @param pkg the pkg
     */
    private void importPackage(NativeJavaPackage pkg)
    {
        if(pkg == null) {
            return;
        }
        synchronized (importedPackages) {
            for (int j = 0; j != importedPackages.size(); j++) {
                if (pkg.equals(importedPackages.get(j))) {
                    return;
                }
            }
            importedPackages.add(pkg);
        }
    }

    /**
     * Import class.
     *
     * @param cl the cl
     */
    private void importClass(NativeJavaClass cl)
    {
        String s = cl.getClassObject().getName();
        String n = s.substring(s.lastIndexOf('.')+1);
        Object val = get(n, this);
        if (val != NOT_FOUND && val != cl) {
            throw Context.reportRuntimeError1("msg.prop.defined", n);
        }
        //defineProperty(n, cl, DONTENUM);
        put(n, this, cl);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#initPrototypeId(int)
     */
    @Override
    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
          case Id_constructor:   arity=0; s="constructor";   break;
          case Id_importClass:   arity=1; s="importClass";   break;
          case Id_importPackage: arity=1; s="importPackage"; break;
          default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(IMPORTER_TAG, id, s, arity);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(IMPORTER_TAG)) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        switch (id) {
          case Id_constructor:
            return js_construct(scope, args);

          case Id_importClass:
            return realThis(thisObj, f).js_importClass(args);

          case Id_importPackage:
            return realThis(thisObj, f).js_importPackage(args);
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    /**
     * Real this.
     *
     * @param thisObj the this obj
     * @param f the f
     * @return the importer top level
     */
    private ImporterTopLevel realThis(Scriptable thisObj, IdFunctionObject f)
    {
        if (topScopeFlag) {
            // when used as top scope importPackage and importClass are global
            // function that ignore thisObj
            return this;
        }
        if (!(thisObj instanceof ImporterTopLevel))
            throw incompatibleCallError(f);
        return (ImporterTopLevel)thisObj;
    }

// #string_id_map#

    /* (non-Javadoc)
 * @see org.mozilla.javascript.IdScriptableObject#findPrototypeId(java.lang.String)
 */
@Override
    protected int findPrototypeId(String s)
    {
        int id;
// #generated# Last update: 2007-05-09 08:15:24 EDT
        L0: { id = 0; String X = null; int c;
            int s_length = s.length();
            if (s_length==11) {
                c=s.charAt(0);
                if (c=='c') { X="constructor";id=Id_constructor; }
                else if (c=='i') { X="importClass";id=Id_importClass; }
            }
            else if (s_length==13) { X="importPackage";id=Id_importPackage; }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
            break L0;
        }
// #/generated#
        return id;
    }

    /** The Constant MAX_PROTOTYPE_ID. */
    private static final int
        Id_constructor          = 1,
        Id_importClass          = 2,
        Id_importPackage        = 3,
        MAX_PROTOTYPE_ID        = 3;

// #/string_id_map#

    /** The imported packages. */
private ObjArray importedPackages = new ObjArray();
    
    /** The top scope flag. */
    private boolean topScopeFlag;
}
