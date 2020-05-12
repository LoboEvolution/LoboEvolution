/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

/**
 * This class distinguishes between the three different supported stack trace formats.
 *
 * @author utente
 * @version $Id: $Id
 */
public enum StackStyle {
    /**
     * This is the default stack trace style in Rhino, which is like Java:
     *     at fileName:lineNumber (functionName)
     */
    RHINO,

    /**
     * This stack trace style comes from the old Mozilla code:
     * functionName()@fileName:lineNumber
     */
    MOZILLA,

    /**
     * This stack trace style matches that output from V8, either:
     *     at functionName (fileName:lineNumber:columnNumber)
     * or, for anonymous functions:
     *     at fileName:lineNumber:columnNumber
     */
    V8
}
