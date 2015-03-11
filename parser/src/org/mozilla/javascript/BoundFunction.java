/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;


/**
 * The class for results of the Function.bind operation
 * EcmaScript 5 spec, 15.3.4.5
 * @author Raphael Speyer
 */
public class BoundFunction extends BaseFunction {
    
  /** The Constant serialVersionUID. */
  static final long serialVersionUID = 2118137342826470729L;
    
  /** The target function. */
  private final Callable targetFunction;
  
  /** The bound this. */
  private final Scriptable boundThis;
  
  /** The bound args. */
  private final Object[] boundArgs;
  
  /** The length. */
  private final int length;

  /**
   * Instantiates a new bound function.
   *
   * @param cx the cx
   * @param scope the scope
   * @param targetFunction the target function
   * @param boundThis the bound this
   * @param boundArgs the bound args
   */
  public BoundFunction(Context cx, Scriptable scope, Callable targetFunction, Scriptable boundThis,
                       Object[] boundArgs)
  {
    this.targetFunction = targetFunction;
    this.boundThis = boundThis;
    this.boundArgs = boundArgs;
    if (targetFunction instanceof BaseFunction) {
      length = Math.max(0, ((BaseFunction) targetFunction).getLength() - boundArgs.length);
    } else {
      length = 0;
    }

    ScriptRuntime.setFunctionProtoAndParent(this, scope);

    Function thrower = ScriptRuntime.typeErrorThrower(cx);
    NativeObject throwing = new NativeObject();
    throwing.put("get", throwing, thrower);
    throwing.put("set", throwing, thrower);
    throwing.put("enumerable", throwing, false);
    throwing.put("configurable", throwing, false);
    throwing.preventExtensions();

    this.defineOwnProperty(cx, "caller", throwing, false);
    this.defineOwnProperty(cx, "arguments", throwing, false);
  }

  /* (non-Javadoc)
   * @see org.mozilla.javascript.BaseFunction#call(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
   */
  @Override
  public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] extraArgs)
  {
    Scriptable callThis = boundThis != null ? boundThis : ScriptRuntime.getTopCallScope(cx);
    return targetFunction.call(cx, scope, callThis, concat(boundArgs, extraArgs));
  }

  /* (non-Javadoc)
   * @see org.mozilla.javascript.BaseFunction#construct(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, java.lang.Object[])
   */
  @Override
  public Scriptable construct(Context cx, Scriptable scope, Object[] extraArgs) {
    if (targetFunction instanceof Function) {
      return ((Function) targetFunction).construct(cx, scope, concat(boundArgs, extraArgs));
    }
    throw ScriptRuntime.typeError0("msg.not.ctor");
  }

  /* (non-Javadoc)
   * @see org.mozilla.javascript.BaseFunction#hasInstance(org.mozilla.javascript.Scriptable)
   */
  @Override
  public boolean hasInstance(Scriptable instance) {
    if (targetFunction instanceof Function) {
      return ((Function) targetFunction).hasInstance(instance);
    }
    throw ScriptRuntime.typeError0("msg.not.ctor");
  }

  /* (non-Javadoc)
   * @see org.mozilla.javascript.BaseFunction#getLength()
   */
  @Override
  public int getLength() {
    return length;
  }

  /**
   * Concat.
   *
   * @param first the first
   * @param second the second
   * @return the object[]
   */
  private Object[] concat(Object[] first, Object[] second) {
    Object[] args = new Object[first.length + second.length];
    System.arraycopy(first, 0, args, 0, first.length);
    System.arraycopy(second, 0, args, first.length, second.length);
    return args;
  }
}
