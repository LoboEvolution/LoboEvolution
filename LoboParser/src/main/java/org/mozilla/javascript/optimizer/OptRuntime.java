/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */


package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.ArrowFunction;
import org.mozilla.javascript.Callable;
import org.mozilla.javascript.ConsString;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.ES6Generator;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.NativeGenerator;
import org.mozilla.javascript.NativeIterator;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

/**
 * <p>OptRuntime class.</p>
 *
 *
 *
 */
public final class OptRuntime extends ScriptRuntime
{
    /** Constant <code>oneObj</code> */
    public static final Double oneObj = Double.valueOf(1.0);
    /** Constant <code>minusOneObj</code> */
    public static final Double minusOneObj = Double.valueOf(-1.0);

    /**
     * Implement ....() call shrinking optimizer code.
     *
     * @param fun a {@link org.mozilla.javascript.Callable} object.
     * @param thisObj a {@link org.mozilla.javascript.Scriptable} object.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object call0(Callable fun, Scriptable thisObj,
                               Context cx, Scriptable scope)
    {
        return fun.call(cx, scope, thisObj, ScriptRuntime.emptyArgs);
    }

    /**
     * Implement ....(arg) call shrinking optimizer code.
     *
     * @param fun a {@link org.mozilla.javascript.Callable} object.
     * @param thisObj a {@link org.mozilla.javascript.Scriptable} object.
     * @param arg0 a {@link java.lang.Object} object.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object call1(Callable fun, Scriptable thisObj, Object arg0,
                               Context cx, Scriptable scope)
    {
        return fun.call(cx, scope, thisObj, new Object[] { arg0 } );
    }

    /**
     * Implement ....(arg0, arg1) call shrinking optimizer code.
     *
     * @param fun a {@link org.mozilla.javascript.Callable} object.
     * @param thisObj a {@link org.mozilla.javascript.Scriptable} object.
     * @param arg0 a {@link java.lang.Object} object.
     * @param arg1 a {@link java.lang.Object} object.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object call2(Callable fun, Scriptable thisObj,
                               Object arg0, Object arg1,
                               Context cx, Scriptable scope)
    {
        return fun.call(cx, scope, thisObj, new Object[] { arg0, arg1 });
    }

    /**
     * Implement ....(arg0, arg1, ...) call shrinking optimizer code.
     *
     * @param fun a {@link org.mozilla.javascript.Callable} object.
     * @param thisObj a {@link org.mozilla.javascript.Scriptable} object.
     * @param args an array of {@link java.lang.Object} objects.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object callN(Callable fun, Scriptable thisObj,
                               Object[] args,
                               Context cx, Scriptable scope)
    {
        return fun.call(cx, scope, thisObj, args);
    }

    /**
     * Implement name(args) call shrinking optimizer code.
     *
     * @param args an array of {@link java.lang.Object} objects.
     * @param name a {@link java.lang.String} object.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object callName(Object[] args, String name,
                                  Context cx, Scriptable scope)
    {
        Callable f = getNameFunctionAndThis(name, cx, scope);
        Scriptable thisObj = lastStoredScriptable(cx);
        return f.call(cx, scope, thisObj, args);
    }

    /**
     * Implement name() call shrinking optimizer code.
     *
     * @param name a {@link java.lang.String} object.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object callName0(String name,
                                   Context cx, Scriptable scope)
    {
        Callable f = getNameFunctionAndThis(name, cx, scope);
        Scriptable thisObj = lastStoredScriptable(cx);
        return f.call(cx, scope, thisObj, ScriptRuntime.emptyArgs);
    }

    /**
     * Implement x.property() call shrinking optimizer code.
     *
     * @param value a {@link java.lang.Object} object.
     * @param property a {@link java.lang.String} object.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object callProp0(Object value, String property,
                                   Context cx, Scriptable scope)
    {
        Callable f = getPropFunctionAndThis(value, property, cx, scope);
        Scriptable thisObj = lastStoredScriptable(cx);
        return f.call(cx, scope, thisObj, ScriptRuntime.emptyArgs);
    }

    /**
     * <p>add.</p>
     *
     * @param val1 a {@link java.lang.Object} object.
     * @param val2 a double.
     * @return a {@link java.lang.Object} object.
     */
    public static Object add(Object val1, double val2)
    {
        if (val1 instanceof Scriptable)
            val1 = ((Scriptable) val1).getDefaultValue(null);
        if (!(val1 instanceof CharSequence))
            return wrapDouble(toNumber(val1) + val2);
        return new ConsString((CharSequence)val1, toString(val2));
    }

    /** {@inheritDoc} */
    public static Object add(double val1, Object val2)
    {
        if (val2 instanceof Scriptable)
            val2 = ((Scriptable) val2).getDefaultValue(null);
        if (!(val2 instanceof CharSequence))
            return wrapDouble(toNumber(val2) + val1);
        return new ConsString(toString(val1), (CharSequence)val2);
    }

    /** {@inheritDoc} */
    @Deprecated
    public static Object elemIncrDecr(Object obj, double index,
                                      Context cx, int incrDecrMask)
    {
        return elemIncrDecr(obj, index, cx, getTopCallScope(cx), incrDecrMask);
    }

    /** {@inheritDoc} */
    public static Object elemIncrDecr(Object obj, double index,
                                      Context cx, Scriptable scope,
                                      int incrDecrMask)
    {
        return ScriptRuntime.elemIncrDecr(obj, Double.valueOf(index), cx, scope,
                                          incrDecrMask);
    }

    /**
     * <p>padStart.</p>
     *
     * @param currentArgs an array of {@link java.lang.Object} objects.
     * @param count a int.
     * @return an array of {@link java.lang.Object} objects.
     */
    public static Object[] padStart(Object[] currentArgs, int count) {
        Object[] result = new Object[currentArgs.length + count];
        System.arraycopy(currentArgs, 0, result, count, currentArgs.length);
        return result;
    }

    /**
     * <p>initFunction.</p>
     *
     * @param fn a {@link org.mozilla.javascript.NativeFunction} object.
     * @param functionType a int.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     */
    public static void initFunction(NativeFunction fn, int functionType,
                                    Scriptable scope, Context cx)
    {
        ScriptRuntime.initFunction(cx, scope, fn, functionType, false);
    }

    /**
     * <p>bindThis.</p>
     *
     * @param fn a {@link org.mozilla.javascript.NativeFunction} object.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param thisObj a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link org.mozilla.javascript.Function} object.
     */
    public static Function bindThis(NativeFunction fn, Context cx, Scriptable scope, Scriptable thisObj)
    {
        return new ArrowFunction(cx, scope, fn, thisObj);
    }

    /** {@inheritDoc} */
    public static Object callSpecial(Context cx, Callable fun,
                                     Scriptable thisObj, Object[] args,
                                     Scriptable scope,
                                     Scriptable callerThis, int callType,
                                     String fileName, int lineNumber)
    {
        return ScriptRuntime.callSpecial(cx, fun, thisObj, args, scope,
                                         callerThis, callType,
                                         fileName, lineNumber);
    }

    /**
     * <p>newObjectSpecial.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param fun a {@link java.lang.Object} object.
     * @param args an array of {@link java.lang.Object} objects.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param callerThis a {@link org.mozilla.javascript.Scriptable} object.
     * @param callType a int.
     * @return a {@link java.lang.Object} object.
     */
    public static Object newObjectSpecial(Context cx, Object fun,
                                          Object[] args, Scriptable scope,
                                          Scriptable callerThis, int callType)
    {
        return ScriptRuntime.newSpecial(cx, fun, args, scope, callType);
    }

    /**
     * <p>wrapDouble.</p>
     *
     * @param num a double.
     * @return a {@link java.lang.Double} object.
     */
    public static Double wrapDouble(double num)
    {
        if (num == 0.0) {
            if (1 / num > 0) {
                // +0.0
                return zeroObj;
            }
        } else if (num == 1.0) {
            return oneObj;
        } else if (num == -1.0) {
            return minusOneObj;
        } else if (Double.isNaN(num)) {
            return NaNobj;
        }
        return Double.valueOf(num);
    }

    static String encodeIntArray(int[] array)
    {
        // XXX: this extremely inefficient for small integers
        if (array == null) { return null; }
        int n = array.length;
        char[] buffer = new char[1 + n * 2];
        buffer[0] = 1;
        for (int i = 0; i != n; ++i) {
            int value = array[i];
            int shift = 1 + i * 2;
            buffer[shift] = (char)(value >>> 16);
            buffer[shift + 1] = (char)value;
        }
        return new String(buffer);
    }

    private static int[] decodeIntArray(String str, int arraySize)
    {
        // XXX: this extremely inefficient for small integers
        if (arraySize == 0) {
            if (str != null) throw new IllegalArgumentException();
            return null;
        }
        if (str.length() != 1 + arraySize * 2 && str.charAt(0) != 1) {
            throw new IllegalArgumentException();
        }
        int[] array = new int[arraySize];
        for (int i = 0; i != arraySize; ++i) {
            int shift = 1 + i * 2;
            array[i] = (str.charAt(shift) << 16) | str.charAt(shift + 1);
        }
        return array;
    }

    /**
     * <p>newArrayLiteral.</p>
     *
     * @param objects an array of {@link java.lang.Object} objects.
     * @param encodedInts a {@link java.lang.String} object.
     * @param skipCount a int.
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @return a {@link org.mozilla.javascript.Scriptable} object.
     */
    public static Scriptable newArrayLiteral(Object[] objects,
                                             String encodedInts,
                                             int skipCount,
                                             Context cx,
                                             Scriptable scope)
    {
        int[] skipIndexces = decodeIntArray(encodedInts, skipCount);
        return newArrayLiteral(objects, skipIndexces, cx, scope);
    }

    /**
     * <p>main.</p>
     *
     * @param script a {@link org.mozilla.javascript.Script} object.
     * @param args an array of {@link java.lang.String} objects.
     */
    public static void main(final Script script, final String[] args)
    {
        ContextFactory.getGlobal().call(cx -> {
            ScriptableObject global = getGlobal(cx);

            // get the command line arguments and define "arguments"
            // array in the top-level object
            Object[] argsCopy = new Object[args.length];
            System.arraycopy(args, 0, argsCopy, 0, args.length);
            Scriptable argsObj = cx.newArray(global, argsCopy);
            global.defineProperty("arguments", argsObj,
                                  ScriptableObject.DONTENUM);
            script.exec(cx, global);
            return null;
        });
    }

    /**
     * <p>throwStopIteration.</p>
     *
     * @param scope a {@link java.lang.Object} object.
     * @param genState a {@link java.lang.Object} object.
     */
    public static void throwStopIteration(Object scope, Object genState) {
        Object value = getGeneratorReturnValue(genState);
        Object si =
            (value == Undefined.instance) ?
              NativeIterator.getStopIterationObject((Scriptable)scope) :
              new NativeIterator.StopIteration(value);
        throw new JavaScriptException(si, "", 0);
    }

    /**
     * <p>createNativeGenerator.</p>
     *
     * @param funObj a {@link org.mozilla.javascript.NativeFunction} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param thisObj a {@link org.mozilla.javascript.Scriptable} object.
     * @param maxLocals a int.
     * @param maxStack a int.
     * @return a {@link org.mozilla.javascript.Scriptable} object.
     */
    public static Scriptable createNativeGenerator(NativeFunction funObj,
                                                   Scriptable scope,
                                                   Scriptable thisObj,
                                                   int maxLocals,
                                                   int maxStack)
    {
        GeneratorState gs = new GeneratorState(thisObj, maxLocals, maxStack);
        if (Context.getCurrentContext().getLanguageVersion() >= Context.VERSION_ES6) {
            return new ES6Generator(scope, funObj, gs);
        } else {
            return new NativeGenerator(scope, funObj, gs);
        }
    }

    /**
     * <p>getGeneratorStackState.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return an array of {@link java.lang.Object} objects.
     */
    public static Object[] getGeneratorStackState(Object obj) {
        GeneratorState rgs = (GeneratorState) obj;
        if (rgs.stackState == null)
            rgs.stackState = new Object[rgs.maxStack];
        return rgs.stackState;
    }

    /**
     * <p>getGeneratorLocalsState.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return an array of {@link java.lang.Object} objects.
     */
    public static Object[] getGeneratorLocalsState(Object obj) {
        GeneratorState rgs = (GeneratorState) obj;
        if (rgs.localsState == null)
            rgs.localsState = new Object[rgs.maxLocals];
        return rgs.localsState;
    }

    /**
     * <p>setGeneratorReturnValue.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @param val a {@link java.lang.Object} object.
     */
    public static void setGeneratorReturnValue(Object obj, Object val) {
        GeneratorState rgs = (GeneratorState) obj;
        rgs.returnValue = val;
    }

    /**
     * <p>getGeneratorReturnValue.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return a {@link java.lang.Object} object.
     */
    public static Object getGeneratorReturnValue(Object obj) {
        GeneratorState rgs = (GeneratorState) obj;
        return (rgs.returnValue == null ? Undefined.instance : rgs.returnValue);
    }

    public static class GeneratorState {
        static final String CLASS_NAME =
            "org/mozilla/javascript/optimizer/OptRuntime$GeneratorState";

        @SuppressWarnings("unused")
        public int resumptionPoint;
        static final String resumptionPoint_NAME = "resumptionPoint";
        static final String resumptionPoint_TYPE = "I";

        @SuppressWarnings("unused")
        public Scriptable thisObj;
        static final String thisObj_NAME = "thisObj";
        static final String thisObj_TYPE =
            "Lorg/mozilla/javascript/Scriptable;";

        Object[] stackState;
        Object[] localsState;
        int maxLocals;
        int maxStack;
        Object returnValue;

        GeneratorState(Scriptable thisObj, int maxLocals, int maxStack) {
            this.thisObj = thisObj;
            this.maxLocals = maxLocals;
            this.maxStack = maxStack;
        }
    }
}
