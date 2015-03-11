/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;


/**
 * This class reflects a single Java constructor into the JavaScript
 * environment.  It satisfies a request for an overloaded constructor,
 * as introduced in LiveConnect 3.
 * All NativeJavaConstructors behave as JSRef `bound' methods, in that they
 * always construct the same NativeJavaClass regardless of any reparenting
 * that may occur.
 *
 * @author Frank Mitchell
 * @see NativeJavaMethod
 * @see NativeJavaPackage
 * @see NativeJavaClass
 */

public class NativeJavaConstructor extends BaseFunction
{
    
    /** The Constant serialVersionUID. */
    static final long serialVersionUID = -8149253217482668463L;

    /** The ctor. */
    MemberBox ctor;

    /**
     * Instantiates a new native java constructor.
     *
     * @param ctor the ctor
     */
    public NativeJavaConstructor(MemberBox ctor)
    {
        this.ctor = ctor;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.BaseFunction#call(org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    @Override
    public Object call(Context cx, Scriptable scope, Scriptable thisObj,
                       Object[] args)
    {
        return NativeJavaClass.constructSpecific(cx, scope, args, ctor);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.BaseFunction#getFunctionName()
     */
    @Override
    public String getFunctionName()
    {
        String sig = JavaMembers.liveConnectSignature(ctor.argTypes);
        return "<init>".concat(sig);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "[JavaConstructor " + ctor.getName() + "]";
    }
}

