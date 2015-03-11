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
 * @author Norris Boyd
 */
public interface RegExpProxy
{
    // Types of regexp actions
    /** The Constant RA_MATCH. */
    public static final int RA_MATCH   = 1;
    
    /** The Constant RA_REPLACE. */
    public static final int RA_REPLACE = 2;
    
    /** The Constant RA_SEARCH. */
    public static final int RA_SEARCH  = 3;

    /**
     * Checks if is reg exp.
     *
     * @param obj the obj
     * @return true, if is reg exp
     */
    public boolean isRegExp(Scriptable obj);

    /**
     * Compile reg exp.
     *
     * @param cx the cx
     * @param source the source
     * @param flags the flags
     * @return the object
     */
    public Object compileRegExp(Context cx, String source, String flags);

    /**
     * Wrap reg exp.
     *
     * @param cx the cx
     * @param scope the scope
     * @param compiled the compiled
     * @return the scriptable
     */
    public Scriptable wrapRegExp(Context cx, Scriptable scope,
                                 Object compiled);

    /**
     * Action.
     *
     * @param cx the cx
     * @param scope the scope
     * @param thisObj the this obj
     * @param args the args
     * @param actionType the action type
     * @return the object
     */
    public Object action(Context cx, Scriptable scope,
                         Scriptable thisObj, Object[] args,
                         int actionType);

    /**
     * Find_split.
     *
     * @param cx the cx
     * @param scope the scope
     * @param target the target
     * @param separator the separator
     * @param re the re
     * @param ip the ip
     * @param matchlen the matchlen
     * @param matched the matched
     * @param parensp the parensp
     * @return the int
     */
    public int find_split(Context cx, Scriptable scope, String target,
                          String separator, Scriptable re,
                          int[] ip, int[] matchlen,
                          boolean[] matched, String[][] parensp);

    /**
     * Js_split.
     *
     * @param _cx the _cx
     * @param _scope the _scope
     * @param thisString the this string
     * @param _args the _args
     * @return the object
     */
    public Object js_split(Context _cx, Scriptable _scope,
                           String thisString, Object[] _args);
}
