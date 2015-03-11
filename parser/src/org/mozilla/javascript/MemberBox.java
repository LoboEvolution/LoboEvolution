/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


/**
 * Wrappper class for Method and Constructor instances to cache
 * getParameterTypes() results, recover from IllegalAccessException
 * in some cases and provide serialization support.
 *
 * @author Igor Bukanov
 */

final class MemberBox implements Serializable
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = 6358550398665688245L;

    /** The member object. */
    private transient Member memberObject;
    
    /** The arg types. */
    transient Class<?>[] argTypes;
    
    /** The delegate to. */
    transient Object delegateTo;
    
    /** The vararg. */
    transient boolean vararg;


    /**
     * Instantiates a new member box.
     *
     * @param method the method
     */
    MemberBox(Method method)
    {
        init(method);
    }

    /**
     * Instantiates a new member box.
     *
     * @param constructor the constructor
     */
    MemberBox(Constructor<?> constructor)
    {
        init(constructor);
    }

    /**
     * Inits the.
     *
     * @param method the method
     */
    private void init(Method method)
    {
        this.memberObject = method;
        this.argTypes = method.getParameterTypes();
        this.vararg = VMBridge.instance.isVarArgs(method);
    }

    /**
     * Inits the.
     *
     * @param constructor the constructor
     */
    private void init(Constructor<?> constructor)
    {
        this.memberObject = constructor;
        this.argTypes = constructor.getParameterTypes();
        this.vararg = VMBridge.instance.isVarArgs(constructor);
    }

    /**
     * Method.
     *
     * @return the method
     */
    Method method()
    {
        return (Method)memberObject;
    }

    /**
     * Ctor.
     *
     * @return the constructor
     */
    Constructor<?> ctor()
    {
        return (Constructor<?>)memberObject;
    }

    /**
     * Member.
     *
     * @return the member
     */
    Member member()
    {
        return memberObject;
    }

    /**
     * Checks if is method.
     *
     * @return true, if is method
     */
    boolean isMethod()
    {
        return memberObject instanceof Method;
    }

    /**
     * Checks if is ctor.
     *
     * @return true, if is ctor
     */
    boolean isCtor()
    {
        return memberObject instanceof Constructor;
    }

    /**
     * Checks if is static.
     *
     * @return true, if is static
     */
    boolean isStatic()
    {
        return Modifier.isStatic(memberObject.getModifiers());
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    String getName()
    {
        return memberObject.getName();
    }

    /**
     * Gets the declaring class.
     *
     * @return the declaring class
     */
    Class<?> getDeclaringClass()
    {
        return memberObject.getDeclaringClass();
    }

    /**
     * To java declaration.
     *
     * @return the string
     */
    String toJavaDeclaration()
    {
        StringBuilder sb = new StringBuilder();
        if (isMethod()) {
            Method method = method();
            sb.append(method.getReturnType());
            sb.append(' ');
            sb.append(method.getName());
        } else {
            Constructor<?> ctor = ctor();
            String name = ctor.getDeclaringClass().getName();
            int lastDot = name.lastIndexOf('.');
            if (lastDot >= 0) {
                name = name.substring(lastDot + 1);
            }
            sb.append(name);
        }
        sb.append(JavaMembers.liveConnectSignature(argTypes));
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return memberObject.toString();
    }

    /**
     * Invoke.
     *
     * @param target the target
     * @param args the args
     * @return the object
     */
    Object invoke(Object target, Object[] args)
    {
        Method method = method();
        try {
            try {
                return method.invoke(target, args);
            } catch (IllegalAccessException ex) {
                Method accessible = searchAccessibleMethod(method, argTypes);
                if (accessible != null) {
                    memberObject = accessible;
                    method = accessible;
                } else {
                    if (!VMBridge.instance.tryToMakeAccessible(method)) {
                        throw Context.throwAsScriptRuntimeEx(ex);
                    }
                }
                // Retry after recovery
                return method.invoke(target, args);
            }
        } catch (InvocationTargetException ite) {
            // Must allow ContinuationPending exceptions to propagate unhindered
            Throwable e = ite;
            do {
                e = ((InvocationTargetException) e).getTargetException();
            } while ((e instanceof InvocationTargetException));
            if (e instanceof ContinuationPending)
                throw (ContinuationPending) e;
            throw Context.throwAsScriptRuntimeEx(e);
        } catch (Exception ex) {
            throw Context.throwAsScriptRuntimeEx(ex);
        }
    }

    /**
     * New instance.
     *
     * @param args the args
     * @return the object
     */
    Object newInstance(Object[] args)
    {
        Constructor<?> ctor = ctor();
        try {
            try {
                return ctor.newInstance(args);
            } catch (IllegalAccessException ex) {
                if (!VMBridge.instance.tryToMakeAccessible(ctor)) {
                    throw Context.throwAsScriptRuntimeEx(ex);
                }
            }
            return ctor.newInstance(args);
        } catch (Exception ex) {
            throw Context.throwAsScriptRuntimeEx(ex);
        }
    }

    /**
     * Search accessible method.
     *
     * @param method the method
     * @param params the params
     * @return the method
     */
    private static Method searchAccessibleMethod(Method method, Class<?>[] params)
    {
        int modifiers = method.getModifiers();
        if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers)) {
            Class<?> c = method.getDeclaringClass();
            if (!Modifier.isPublic(c.getModifiers())) {
                String name = method.getName();
                Class<?>[] intfs = c.getInterfaces();
                for (int i = 0, N = intfs.length; i != N; ++i) {
                    Class<?> intf = intfs[i];
                    if (Modifier.isPublic(intf.getModifiers())) {
                        try {
                            return intf.getMethod(name, params);
                        } catch (NoSuchMethodException ex) {
                        } catch (SecurityException ex) {  }
                    }
                }
                for (;;) {
                    c = c.getSuperclass();
                    if (c == null) { break; }
                    if (Modifier.isPublic(c.getModifiers())) {
                        try {
                            Method m = c.getMethod(name, params);
                            int mModifiers = m.getModifiers();
                            if (Modifier.isPublic(mModifiers)
                                && !Modifier.isStatic(mModifiers))
                            {
                                return m;
                            }
                        } catch (NoSuchMethodException ex) {
                        } catch (SecurityException ex) {  }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Read object.
     *
     * @param in the in
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        Member member = readMember(in);
        if (member instanceof Method) {
            init((Method)member);
        } else {
            init((Constructor<?>)member);
        }
    }

    /**
     * Write object.
     *
     * @param out the out
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writeObject(ObjectOutputStream out)
        throws IOException
    {
        out.defaultWriteObject();
        writeMember(out, memberObject);
    }

    /**
     * Writes a Constructor or Method object.
     * 
     * Methods and Constructors are not serializable, so we must serialize
     * information about the class, the name, and the parameters and
     * recreate upon deserialization.
     *
     * @param out the out
     * @param member the member
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void writeMember(ObjectOutputStream out, Member member)
        throws IOException
    {
        if (member == null) {
            out.writeBoolean(false);
            return;
        }
        out.writeBoolean(true);
        if (!(member instanceof Method || member instanceof Constructor))
            throw new IllegalArgumentException("not Method or Constructor");
        out.writeBoolean(member instanceof Method);
        out.writeObject(member.getName());
        out.writeObject(member.getDeclaringClass());
        if (member instanceof Method) {
            writeParameters(out, ((Method) member).getParameterTypes());
        } else {
            writeParameters(out, ((Constructor<?>) member).getParameterTypes());
        }
    }

    /**
     * Reads a Method or a Constructor from the stream.
     *
     * @param in the in
     * @return the member
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private static Member readMember(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        if (!in.readBoolean())
            return null;
        boolean isMethod = in.readBoolean();
        String name = (String) in.readObject();
        Class<?> declaring = (Class<?>) in.readObject();
        Class<?>[] parms = readParameters(in);
        try {
            if (isMethod) {
                return declaring.getMethod(name, parms);
            } else {
                return declaring.getConstructor(parms);
            }
        } catch (NoSuchMethodException e) {
            throw new IOException("Cannot find member: " + e);
        }
    }

    /** The Constant primitives. */
    private static final Class<?>[] primitives = {
        Boolean.TYPE,
        Byte.TYPE,
        Character.TYPE,
        Double.TYPE,
        Float.TYPE,
        Integer.TYPE,
        Long.TYPE,
        Short.TYPE,
        Void.TYPE
    };

    /**
     * Writes an array of parameter types to the stream.
     * 
     * Requires special handling because primitive types cannot be
     * found upon deserialization by the default Java implementation.
     *
     * @param out the out
     * @param parms the parms
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void writeParameters(ObjectOutputStream out, Class<?>[] parms)
        throws IOException
    {
        out.writeShort(parms.length);
    outer:
        for (int i=0; i < parms.length; i++) {
            Class<?> parm = parms[i];
            boolean primitive = parm.isPrimitive();
            out.writeBoolean(primitive);
            if (!primitive) {
                out.writeObject(parm);
                continue;
            }
            for (int j=0; j < primitives.length; j++) {
                if (parm.equals(primitives[j])) {
                    out.writeByte(j);
                    continue outer;
                }
            }
            throw new IllegalArgumentException("Primitive " + parm +
                                               " not found");
        }
    }

    /**
     * Reads an array of parameter types from the stream.
     *
     * @param in the in
     * @return the class[]
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private static Class<?>[] readParameters(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        Class<?>[] result = new Class[in.readShort()];
        for (int i=0; i < result.length; i++) {
            if (!in.readBoolean()) {
                result[i] = (Class<?>) in.readObject();
                continue;
            }
            result[i] = primitives[in.readByte()];
        }
        return result;
    }
}

