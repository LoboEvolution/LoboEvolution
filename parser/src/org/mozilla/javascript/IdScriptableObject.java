/* -*- Mode: java; tab-width: 4; indent-tabs-mode: 1; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
Base class for native object implementation that uses IdFunctionObject 
to export its methods to script via class-name.prototype object.

Any descendant should implement at least the following methods:
    findInstanceIdInfo
    getInstanceIdName
    execIdCall
    methodArity

To define non-function properties, the descendant should override
    getInstanceIdValue
    setInstanceIdValue
to get/set property value and provide its default attributes.


To customize initialization of constructor and prototype objects, descendant
may override scopeInit or fillConstructorProperties methods.

*/
public abstract class IdScriptableObject extends ScriptableObject
    implements IdFunctionCall
{
    
    /** The prototype values. */
    private transient PrototypeValues prototypeValues;

    /**
     * The Class PrototypeValues.
     */
    private static final class PrototypeValues implements Serializable
    {
        
        /** The Constant serialVersionUID. */
        static final long serialVersionUID = 3038645279153854371L;

        /** The Constant NAME_SLOT. */
        private static final int NAME_SLOT = 1;
        
        /** The Constant SLOT_SPAN. */
        private static final int SLOT_SPAN = 2;

        /** The obj. */
        private IdScriptableObject obj;
        
        /** The max id. */
        private int maxId;
        
        /** The value array. */
        private Object[] valueArray;
        
        /** The attribute array. */
        private short[] attributeArray;

        // The following helps to avoid creation of valueArray during runtime
        // initialization for common case of "constructor" property
        /** The constructor id. */
        int constructorId;
        
        /** The constructor. */
        private IdFunctionObject constructor;
        
        /** The constructor attrs. */
        private short constructorAttrs;

        /**
         * Instantiates a new prototype values.
         *
         * @param obj the obj
         * @param maxId the max id
         */
        PrototypeValues(IdScriptableObject obj, int maxId)
        {
            if (obj == null) throw new IllegalArgumentException();
            if (maxId < 1) throw new IllegalArgumentException();
            this.obj = obj;
            this.maxId = maxId;
        }

        /**
         * Gets the max id.
         *
         * @return the max id
         */
        final int getMaxId()
        {
            return maxId;
        }

        /**
         * Inits the value.
         *
         * @param id the id
         * @param name the name
         * @param value the value
         * @param attributes the attributes
         */
        final void initValue(int id, String name, Object value, int attributes)
        {
            if (!(1 <= id && id <= maxId))
                throw new IllegalArgumentException();
            if (name == null)
                throw new IllegalArgumentException();
            if (value == NOT_FOUND)
                throw new IllegalArgumentException();
            ScriptableObject.checkValidAttributes(attributes);
            if (obj.findPrototypeId(name) != id)
                throw new IllegalArgumentException(name);

            if (id == constructorId) {
                if (!(value instanceof IdFunctionObject)) {
                    throw new IllegalArgumentException("consructor should be initialized with IdFunctionObject");
                }
                constructor = (IdFunctionObject)value;
                constructorAttrs = (short)attributes;
                return;
            }

            initSlot(id, name, value, attributes);
        }

        /**
         * Inits the slot.
         *
         * @param id the id
         * @param name the name
         * @param value the value
         * @param attributes the attributes
         */
        private void initSlot(int id, String name, Object value,
                              int attributes)
        {
            Object[] array = valueArray;
            if (array == null)
                throw new IllegalStateException();

            if (value == null) {
                value = UniqueTag.NULL_VALUE;
            }
            int index = (id - 1) * SLOT_SPAN;
            synchronized (this) {
                Object value2 = array[index];
                if (value2 == null) {
                    array[index] = value;
                    array[index + NAME_SLOT] = name;
                    attributeArray[id - 1] = (short)attributes;
                } else {
                    if (!name.equals(array[index + NAME_SLOT]))
                         throw new IllegalStateException();
                }
            }
        }

        /**
         * Creates the precached constructor.
         *
         * @return the id function object
         */
        final IdFunctionObject createPrecachedConstructor()
        {
            if (constructorId != 0) throw new IllegalStateException();
            constructorId = obj.findPrototypeId("constructor");
            if (constructorId == 0) {
                throw new IllegalStateException(
                    "No id for constructor property");
            }
            obj.initPrototypeId(constructorId);
            if (constructor == null) {
                throw new IllegalStateException(
                    obj.getClass().getName()+".initPrototypeId() did not "
                    +"initialize id="+constructorId);
            }
            constructor.initFunction(obj.getClassName(),
                                     ScriptableObject.getTopLevelScope(obj));
            constructor.markAsConstructor(obj);
            return constructor;
        }

        /**
         * Find id.
         *
         * @param name the name
         * @return the int
         */
        final int findId(String name)
        {
            return obj.findPrototypeId(name);
        }

        /**
         * Checks for.
         *
         * @param id the id
         * @return true, if successful
         */
        final boolean has(int id)
        {
            Object[] array = valueArray;
            if (array == null) {
                // Not yet initialized, assume all exists
                return true;
            }
            int valueSlot = (id  - 1) * SLOT_SPAN;
            Object value = array[valueSlot];
            if (value == null) {
                // The particular entry has not been yet initialized
                return true;
            }
            return value != NOT_FOUND;
        }

        /**
         * Gets the.
         *
         * @param id the id
         * @return the object
         */
        final Object get(int id)
        {
            Object value = ensureId(id);
            if (value == UniqueTag.NULL_VALUE) {
                value = null;
            }
            return value;
        }

        /**
         * Sets the.
         *
         * @param id the id
         * @param start the start
         * @param value the value
         */
        final void set(int id, Scriptable start, Object value)
        {
            if (value == NOT_FOUND) throw new IllegalArgumentException();
            ensureId(id);
            int attr = attributeArray[id - 1];
            if ((attr & READONLY) == 0) {
                if (start == obj) {
                    if (value == null) {
                        value = UniqueTag.NULL_VALUE;
                    }
                    int valueSlot = (id  - 1) * SLOT_SPAN;
                    synchronized (this) {
                        valueArray[valueSlot] = value;
                    }
                }
                else {
                    int nameSlot = (id  - 1) * SLOT_SPAN + NAME_SLOT;
                    String name = (String)valueArray[nameSlot];
                    start.put(name, start, value);
                }
            }
        }

        /**
         * Delete.
         *
         * @param id the id
         */
        final void delete(int id)
        {
            ensureId(id);
            int attr = attributeArray[id - 1];
            if ((attr & PERMANENT) == 0) {
                int valueSlot = (id  - 1) * SLOT_SPAN;
                synchronized (this) {
                    valueArray[valueSlot] = NOT_FOUND;
                    attributeArray[id - 1] = EMPTY;
                }
            }
        }

        /**
         * Gets the attributes.
         *
         * @param id the id
         * @return the attributes
         */
        final int getAttributes(int id)
        {
            ensureId(id);
            return attributeArray[id - 1];
        }

        /**
         * Sets the attributes.
         *
         * @param id the id
         * @param attributes the attributes
         */
        final void setAttributes(int id, int attributes)
        {
            ScriptableObject.checkValidAttributes(attributes);
            ensureId(id);
            synchronized (this) {
                attributeArray[id - 1] = (short)attributes;
            }
        }

        /**
         * Gets the names.
         *
         * @param getAll the get all
         * @param extraEntries the extra entries
         * @return the names
         */
        final Object[] getNames(boolean getAll, Object[] extraEntries)
        {
            Object[] names = null;
            int count = 0;
            for (int id = 1; id <= maxId; ++id) {
                Object value = ensureId(id);
                if (getAll || (attributeArray[id - 1] & DONTENUM) == 0) {
                    if (value != NOT_FOUND) {
                        int nameSlot = (id  - 1) * SLOT_SPAN + NAME_SLOT;
                        String name = (String)valueArray[nameSlot];
                        if (names == null) {
                            names = new Object[maxId];
                        }
                        names[count++] = name;
                    }
                }
            }
            if (count == 0) {
                return extraEntries;
            } else if (extraEntries == null || extraEntries.length == 0) {
                if (count != names.length) {
                    Object[] tmp = new Object[count];
                    System.arraycopy(names, 0, tmp, 0, count);
                    names = tmp;
                }
                return names;
            } else {
                int extra = extraEntries.length;
                Object[] tmp = new Object[extra + count];
                System.arraycopy(extraEntries, 0, tmp, 0, extra);
                System.arraycopy(names, 0, tmp, extra, count);
                return tmp;
            }
        }

        /**
         * Ensure id.
         *
         * @param id the id
         * @return the object
         */
        private Object ensureId(int id)
        {
            Object[] array = valueArray;
            if (array == null) {
                synchronized (this) {
                    array = valueArray;
                    if (array == null) {
                        array = new Object[maxId * SLOT_SPAN];
                        valueArray = array;
                        attributeArray = new short[maxId];
                    }
                }
            }
            int valueSlot = (id  - 1) * SLOT_SPAN;
            Object value = array[valueSlot];
            if (value == null) {
                if (id == constructorId) {
                    initSlot(constructorId, "constructor",
                             constructor, constructorAttrs);
                    constructor = null; // no need to refer it any longer
                } else {
                    obj.initPrototypeId(id);
                }
                value = array[valueSlot];
                if (value == null) {
                    throw new IllegalStateException(
                        obj.getClass().getName()+".initPrototypeId(int id) "
                        +"did not initialize id="+id);
                }
            }
            return value;
        }
    }

    /**
     * Instantiates a new id scriptable object.
     */
    public IdScriptableObject()
    {
    }

    /**
     * Instantiates a new id scriptable object.
     *
     * @param scope the scope
     * @param prototype the prototype
     */
    public IdScriptableObject(Scriptable scope, Scriptable prototype)
    {
        super(scope, prototype);
    }

    /**
     * Default has.
     *
     * @param name the name
     * @return true, if successful
     */
    protected final boolean defaultHas(String name)
    {
        return super.has(name, this);
    }

    /**
     * Default get.
     *
     * @param name the name
     * @return the object
     */
    protected final Object defaultGet(String name)
    {
        return super.get(name, this);
    }

    /**
     * Default put.
     *
     * @param name the name
     * @param value the value
     */
    protected final void defaultPut(String name, Object value)
    {
        super.put(name, this, value);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#has(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    @Override
    public boolean has(String name, Scriptable start)
    {
        int info = findInstanceIdInfo(name);
        if (info != 0) {
            int attr = (info >>> 16);
            if ((attr & PERMANENT) != 0) {
                return true;
            }
            int id = (info & 0xFFFF);
            return NOT_FOUND != getInstanceIdValue(id);
        }
        if (prototypeValues != null) {
            int id = prototypeValues.findId(name);
            if (id != 0) {
                return prototypeValues.has(id);
            }
        }
        return super.has(name, start);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#get(java.lang.String, org.mozilla.javascript.Scriptable)
     */
    @Override
    public Object get(String name, Scriptable start)
    {
        // Check for slot first for performance. This is a very hot code
        // path that should be further optimized.
        Object value = super.get(name, start);
        if (value != NOT_FOUND) {
            return value;
        }
        int info = findInstanceIdInfo(name);
        if (info != 0) {
            int id = (info & 0xFFFF);
            value = getInstanceIdValue(id);
            if (value != NOT_FOUND) return value;
        }
        if (prototypeValues != null) {
            int id = prototypeValues.findId(name);
            if (id != 0) {
                value = prototypeValues.get(id);
                if (value != NOT_FOUND) return value;
            }
        }
        return NOT_FOUND;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#put(java.lang.String, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    @Override
    public void put(String name, Scriptable start, Object value)
    {
        int info = findInstanceIdInfo(name);
        if (info != 0) {
            if (start == this && isSealed()) {
                throw Context.reportRuntimeError1("msg.modify.sealed",
                                                  name);
            }
            int attr = (info >>> 16);
            if ((attr & READONLY) == 0) {
                if (start == this) {
                    int id = (info & 0xFFFF);
                    setInstanceIdValue(id, value);
                }
                else {
                    start.put(name, start, value);
                }
            }
            return;
        }
        if (prototypeValues != null) {
            int id = prototypeValues.findId(name);
            if (id != 0) {
                if (start == this && isSealed()) {
                    throw Context.reportRuntimeError1("msg.modify.sealed",
                                                      name);
                }
                prototypeValues.set(id, start, value);
                return;
            }
        }
        super.put(name, start, value);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#delete(java.lang.String)
     */
    @Override
    public void delete(String name)
    {
        int info = findInstanceIdInfo(name);
        if (info != 0) {
            // Let the super class to throw exceptions for sealed objects
            if (!isSealed()) {
                int attr = (info >>> 16);
                if ((attr & PERMANENT) == 0) {
                    int id = (info & 0xFFFF);
                    setInstanceIdValue(id, NOT_FOUND);
                }
                return;
            }
        }
        if (prototypeValues != null) {
            int id = prototypeValues.findId(name);
            if (id != 0) {
                if (!isSealed()) {
                    prototypeValues.delete(id);
                }
                return;
            }
        }
        super.delete(name);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getAttributes(java.lang.String)
     */
    @Override
    public int getAttributes(String name)
    {
        int info = findInstanceIdInfo(name);
        if (info != 0) {
            int attr = (info >>> 16);
            return attr;
        }
        if (prototypeValues != null) {
            int id = prototypeValues.findId(name);
            if (id != 0) {
                return prototypeValues.getAttributes(id);
            }
        }
        return super.getAttributes(name);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#setAttributes(java.lang.String, int)
     */
    @Override
    public void setAttributes(String name, int attributes)
    {
        ScriptableObject.checkValidAttributes(attributes);
        int info = findInstanceIdInfo(name);
        if (info != 0) {
            int id = (info & 0xFFFF);
            int currentAttributes = (info >>> 16);
            if (attributes != currentAttributes) {
                setInstanceIdAttributes(id, attributes);
            }
            return;
        }
        if (prototypeValues != null) {
            int id = prototypeValues.findId(name);
            if (id != 0) {
                prototypeValues.setAttributes(id, attributes);
                return;
            }
        }
        super.setAttributes(name, attributes);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getIds(boolean)
     */
    @Override
    Object[] getIds(boolean getAll)
    {
        Object[] result = super.getIds(getAll);

        if (prototypeValues != null) {
            result = prototypeValues.getNames(getAll, result);
        }

        int maxInstanceId = getMaxInstanceId();
        if (maxInstanceId != 0) {
            Object[] ids = null;
            int count = 0;

            for (int id = maxInstanceId; id != 0; --id) {
                String name = getInstanceIdName(id);
                int info = findInstanceIdInfo(name);
                if (info != 0) {
                    int attr = (info >>> 16);
                    if ((attr & PERMANENT) == 0) {
                        if (NOT_FOUND == getInstanceIdValue(id)) {
                            continue;
                        }
                    }
                    if (getAll || (attr & DONTENUM) == 0) {
                        if (count == 0) {
                            // Need extra room for no more then [1..id] names
                            ids = new Object[id];
                        }
                        ids[count++] = name;
                    }
                }
            }
            if (count != 0) {
                if (result.length == 0 && ids.length == count) {
                    result = ids;
                }
                else {
                    Object[] tmp = new Object[result.length + count];
                    System.arraycopy(result, 0, tmp, 0, result.length);
                    System.arraycopy(ids, 0, tmp, result.length, count);
                    result = tmp;
                }
            }
        }
        return result;
    }

    /**
     * Get maximum id findInstanceIdInfo can generate.
     *
     * @return the max instance id
     */
    protected int getMaxInstanceId()
    {
        return 0;
    }

    /**
     * Instance id info.
     *
     * @param attributes the attributes
     * @param id the id
     * @return the int
     */
    protected static int instanceIdInfo(int attributes, int id)
    {
        return (attributes << 16) | id;
    }

    /**
     * Map name to id of instance property.
     * Should return 0 if not found or the result of
     * {@link #instanceIdInfo(int, int)}.
     *
     * @param name the name
     * @return the int
     */
    protected int findInstanceIdInfo(String name)
    {
        return 0;
    }

    /**
     *  Map id back to property name it defines.
     *
     * @param id the id
     * @return the instance id name
     */
    protected String getInstanceIdName(int id)
    {
        throw new IllegalArgumentException(String.valueOf(id));
    }

    /**
     *  Get id value.
     * * If id value is constant, descendant can call cacheIdValue to store
     * * value in the permanent cache.
     * * Default implementation creates IdFunctionObject instance for given id
     * * and cache its value
     *
     * @param id the id
     * @return the instance id value
     */
    protected Object getInstanceIdValue(int id)
    {
        throw new IllegalStateException(String.valueOf(id));
    }

    /**
     * Set or delete id value. If value == NOT_FOUND , the implementation
     * should make sure that the following getInstanceIdValue return NOT_FOUND.
     *
     * @param id the id
     * @param value the value
     */
    protected void setInstanceIdValue(int id, Object value)
    {
        throw new IllegalStateException(String.valueOf(id));
    }

    /**
     * Update the attributes of the given instance property. Classes which
     * want to support changing property attributes via Object.defineProperty
     * must override this method. The default implementation throws
     * InternalError.
     * @param id the instance property id
     * @param attr the new attribute bitset
     */
    protected void setInstanceIdAttributes(int id, int attr) {
        throw ScriptRuntime.constructError("InternalError",
                "Changing attributes not supported for " + getClassName()
                + " " + getInstanceIdName(id) + " property");
    }

    /**
     *  'thisObj' will be null if invoked as constructor, in which case
     * * instance of Scriptable should be returned.
     *
     * @param f the f
     * @param cx the cx
     * @param scope the scope
     * @param thisObj the this obj
     * @param args the args
     * @return the object
     */
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        throw f.unknown();
    }

    /**
     * Export as js class.
     *
     * @param maxPrototypeId the max prototype id
     * @param scope the scope
     * @param sealed the sealed
     * @return the id function object
     */
    public final IdFunctionObject exportAsJSClass(int maxPrototypeId,
                                                  Scriptable scope,
                                                  boolean sealed)
    {
        // Set scope and prototype unless this is top level scope itself
        if (scope != this && scope != null) {
            setParentScope(scope);
            setPrototype(getObjectPrototype(scope));
        }

        activatePrototypeMap(maxPrototypeId);
        IdFunctionObject ctor = prototypeValues.createPrecachedConstructor();
        if (sealed) {
            sealObject();
        }
        fillConstructorProperties(ctor);
        if (sealed) {
            ctor.sealObject();
        }
        ctor.exportAsScopeProperty();
        return ctor;
    }

    /**
     * Checks for prototype map.
     *
     * @return true, if successful
     */
    public final boolean hasPrototypeMap()
    {
        return prototypeValues != null;
    }

    /**
     * Activate prototype map.
     *
     * @param maxPrototypeId the max prototype id
     */
    public final void activatePrototypeMap(int maxPrototypeId)
    {
        PrototypeValues values = new PrototypeValues(this, maxPrototypeId);
        synchronized (this) {
            if (prototypeValues != null)
                throw new IllegalStateException();
            prototypeValues = values;
        }
    }

    /**
     * Inits the prototype method.
     *
     * @param tag the tag
     * @param id the id
     * @param name the name
     * @param arity the arity
     */
    public final void initPrototypeMethod(Object tag, int id, String name,
                                          int arity)
    {
        Scriptable scope = ScriptableObject.getTopLevelScope(this);
        IdFunctionObject f = newIdFunction(tag, id, name, arity, scope);
        prototypeValues.initValue(id, name, f, DONTENUM);
    }

    /**
     * Inits the prototype constructor.
     *
     * @param f the f
     */
    public final void initPrototypeConstructor(IdFunctionObject f)
    {
        int id = prototypeValues.constructorId;
        if (id == 0)
            throw new IllegalStateException();
        if (f.methodId() != id)
            throw new IllegalArgumentException();
        if (isSealed()) { f.sealObject(); }
        prototypeValues.initValue(id, "constructor", f, DONTENUM);
    }

    /**
     * Inits the prototype value.
     *
     * @param id the id
     * @param name the name
     * @param value the value
     * @param attributes the attributes
     */
    public final void initPrototypeValue(int id, String name, Object value,
                                         int attributes)
    {
        prototypeValues.initValue(id, name, value, attributes);
    }

    /**
     * Inits the prototype id.
     *
     * @param id the id
     */
    protected void initPrototypeId(int id)
    {
        throw new IllegalStateException(String.valueOf(id));
    }

    /**
     * Find prototype id.
     *
     * @param name the name
     * @return the int
     */
    protected int findPrototypeId(String name)
    {
        throw new IllegalStateException(name);
    }

    /**
     * Fill constructor properties.
     *
     * @param ctor the ctor
     */
    protected void fillConstructorProperties(IdFunctionObject ctor)
    {
    }

    /**
     * Adds the id function property.
     *
     * @param obj the obj
     * @param tag the tag
     * @param id the id
     * @param name the name
     * @param arity the arity
     */
    protected void addIdFunctionProperty(Scriptable obj, Object tag, int id,
                                         String name, int arity)
    {
        Scriptable scope = ScriptableObject.getTopLevelScope(obj);
        IdFunctionObject f = newIdFunction(tag, id, name, arity, scope);
        f.addAsProperty(obj);
    }

    /**
     * Utility method to construct type error to indicate incompatible call
     * when converting script thisObj to a particular type is not possible.
     * Possible usage would be to have a private function like realThis:
     * <pre>
     *  private static NativeSomething realThis(Scriptable thisObj,
     *                                          IdFunctionObject f)
     *  {
     *      if (!(thisObj instanceof NativeSomething))
     *          throw incompatibleCallError(f);
     *      return (NativeSomething)thisObj;
     * }
     * </pre>
     * Note that although such function can be implemented universally via
     * java.lang.Class.isInstance(), it would be much more slower.
     *
     * @param f function that is attempting to convert 'this'
     * object.
     * @return Scriptable object suitable for a check by the instanceof
     * operator.
     */
    protected static EcmaError incompatibleCallError(IdFunctionObject f)
    {
        throw ScriptRuntime.typeError1("msg.incompat.call",
                                       f.getFunctionName());
    }

    /**
     * New id function.
     *
     * @param tag the tag
     * @param id the id
     * @param name the name
     * @param arity the arity
     * @param scope the scope
     * @return the id function object
     */
    private IdFunctionObject newIdFunction(Object tag, int id, String name,
                                           int arity, Scriptable scope)
    {
        IdFunctionObject f = new IdFunctionObject(this, tag, id, name, arity,
                                                  scope);
        if (isSealed()) { f.sealObject(); }
        return f;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#defineOwnProperty(org.mozilla.javascript.Context, java.lang.Object, org.mozilla.javascript.ScriptableObject)
     */
    @Override
    public void defineOwnProperty(Context cx, Object key, ScriptableObject desc) {
      if (key instanceof String) {
        String name = (String) key;
        int info = findInstanceIdInfo(name);
        if (info != 0) {
            int id = (info & 0xFFFF);
            if (isAccessorDescriptor(desc)) {
              delete(id); // it will be replaced with a slot
            } else {
              checkPropertyDefinition(desc);
              ScriptableObject current = getOwnPropertyDescriptor(cx, key);
              checkPropertyChange(name, current, desc);
              int attr = (info >>> 16);
              Object value = getProperty(desc, "value");
              if (value != NOT_FOUND && (attr & READONLY) == 0) {
                Object currentValue = getInstanceIdValue(id);
                if (!sameValue(value, currentValue)) {
                  setInstanceIdValue(id, value);
                }
              }
              setAttributes(name, applyDescriptorToAttributeBitset(attr, desc));
              return;
            }
        }
        if (prototypeValues != null) {
            int id = prototypeValues.findId(name);
            if (id != 0) {
              if (isAccessorDescriptor(desc)) {
                prototypeValues.delete(id); // it will be replaced with a slot
              } else {
                checkPropertyDefinition(desc);
                ScriptableObject current = getOwnPropertyDescriptor(cx, key);
                checkPropertyChange(name, current, desc);
                int attr = prototypeValues.getAttributes(id);
                Object value = getProperty(desc, "value");
                if (value != NOT_FOUND && (attr & READONLY) == 0) {
                  Object currentValue = prototypeValues.get(id);
                  if (!sameValue(value, currentValue)) {
                    prototypeValues.set(id, this, value);
                  }
                }
                prototypeValues.setAttributes(id, applyDescriptorToAttributeBitset(attr, desc));
                return;
              }
            }
        }
      }
      super.defineOwnProperty(cx, key, desc);
    }


    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getOwnPropertyDescriptor(org.mozilla.javascript.Context, java.lang.Object)
     */
    @Override
    protected ScriptableObject getOwnPropertyDescriptor(Context cx, Object id) {
      ScriptableObject desc = super.getOwnPropertyDescriptor(cx, id);
      if (desc == null && id instanceof String) {
        desc = getBuiltInDescriptor((String) id);
      }
      return desc;
    }

    /**
     * Gets the built in descriptor.
     *
     * @param name the name
     * @return the built in descriptor
     */
    private ScriptableObject getBuiltInDescriptor(String name) {
      Object value = null;
      int attr = EMPTY;

      Scriptable scope = getParentScope();
      if (scope == null) {
        scope = this;
      }

      int info = findInstanceIdInfo(name);
      if (info != 0) {
        int id = (info & 0xFFFF);
        value = getInstanceIdValue(id);
        attr = (info >>> 16);
        return buildDataDescriptor(scope, value, attr);
      }
      if (prototypeValues != null) {
        int id = prototypeValues.findId(name);
        if (id != 0) {
          value = prototypeValues.get(id);
          attr = prototypeValues.getAttributes(id);
          return buildDataDescriptor(scope, value, attr);
        }
      }
      return null;
    }

    /**
     * Read object.
     *
     * @param stream the stream
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        int maxPrototypeId = stream.readInt();
        if (maxPrototypeId != 0) {
            activatePrototypeMap(maxPrototypeId);
        }
    }

    /**
     * Write object.
     *
     * @param stream the stream
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writeObject(ObjectOutputStream stream)
        throws IOException
    {
        stream.defaultWriteObject();
        int maxPrototypeId = 0;
        if (prototypeValues != null) {
            maxPrototypeId = prototypeValues.getMaxId();
        }
        stream.writeInt(maxPrototypeId);
    }

}

