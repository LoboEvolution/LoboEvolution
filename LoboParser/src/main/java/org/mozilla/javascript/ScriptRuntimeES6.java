/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

/**
 * <p>ScriptRuntimeES6 class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class ScriptRuntimeES6 {

    /**
     * <p>requireObjectCoercible.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param val a {@link org.mozilla.javascript.Scriptable} object.
     * @param idFuncObj a {@link org.mozilla.javascript.IdFunctionObject} object.
     * @return a {@link org.mozilla.javascript.Scriptable} object.
     */
    public static Scriptable requireObjectCoercible(Context cx, Scriptable val, IdFunctionObject idFuncObj) {
        if (val == null || Undefined.isUndefined(val)) {
            throw ScriptRuntime.typeError2("msg.called.null.or.undefined", idFuncObj.getTag(), idFuncObj.getFunctionName());
        }
        return val;
    }
}
