/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

/**
 * A proxy for the regexp package, so that the regexp package can be
 * loaded optionally.
 *
 * Author Norris Boyd
 *
 */
public interface RegExpProxy
{
    // Types of regexp actions
    /** Constant <code>RA_MATCH=1</code> */
    public static final int RA_MATCH   = 1;
    /** Constant <code>RA_REPLACE=2</code> */
    public static final int RA_REPLACE = 2;
    /** Constant <code>RA_SEARCH=3</code> */
    public static final int RA_SEARCH  = 3;

    /**
     * <p>isRegExp.</p>
     *
     * @param obj a {@link org.mozilla.javascript.Scriptable} object.
     * @return a boolean.
     */
    public boolean isRegExp(Scriptable obj);

    /**
     * <p>compileRegExp.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param source a {@link java.lang.String} object.
     * @param flags a {@link java.lang.String} object.
     * @return a {@link java.lang.Object} object.
     */
    public Object compileRegExp(Context cx, String source, String flags);

    /**
     * <p>wrapRegExp.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param compiled a {@link java.lang.Object} object.
     * @return a {@link org.mozilla.javascript.Scriptable} object.
     */
    public Scriptable wrapRegExp(Context cx, Scriptable scope,
                                 Object compiled);

    /**
     * <p>action.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param thisObj a {@link org.mozilla.javascript.Scriptable} object.
     * @param args an array of {@link java.lang.Object} objects.
     * @param actionType a int.
     * @return a {@link java.lang.Object} object.
     */
    public Object action(Context cx, Scriptable scope,
                         Scriptable thisObj, Object[] args,
                         int actionType);

    /**
     * <p>find_split.</p>
     *
     * @param cx a {@link org.mozilla.javascript.Context} object.
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param target a {@link java.lang.String} object.
     * @param separator a {@link java.lang.String} object.
     * @param re a {@link org.mozilla.javascript.Scriptable} object.
     * @param ip an array of {@link int} objects.
     * @param matchlen an array of {@link int} objects.
     * @param matched an array of {@link boolean} objects.
     * @param parensp an array of {@link java.lang.String} objects.
     * @return a int.
     */
    public int find_split(Context cx, Scriptable scope, String target,
                          String separator, Scriptable re,
                          int[] ip, int[] matchlen,
                          boolean[] matched, String[][] parensp);

    /**
     * <p>js_split.</p>
     *
     * @param _cx a {@link org.mozilla.javascript.Context} object.
     * @param _scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param thisString a {@link java.lang.String} object.
     * @param _args an array of {@link java.lang.Object} objects.
     * @return a {@link java.lang.Object} object.
     */
    public Object js_split(Context _cx, Scriptable _scope,
                           String thisString, Object[] _args);
}
