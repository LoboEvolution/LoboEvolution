/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

/**
 * A java.lang.SecurityManager subclass that provides access to
 * the current top-most script class on the execution stack. This can be used
 * to get the class loader or protection domain of the script that triggered
 * the current action. It is required for JavaAdapters to have the same
 * ProtectionDomain as the script code that created them.
 * Embeddings that implement their own SecurityManager can use this as base class.
 *
 * @author utente
 * @version $Id: $Id
 */
public class RhinoSecurityManager extends SecurityManager {

    /**
     * Get the class of the top-most stack element representing a script.
     *
     * @return The class of the top-most script in the current stack,
     *         or null if no script is currently running
     */
    protected Class<?> getCurrentScriptClass() {
        Class<?>[] context = getClassContext();
        for (Class<?> c : context) {
            if (c != InterpretedFunction.class && NativeFunction.class.isAssignableFrom(c) ||
                    PolicySecurityController.SecureCaller.class.isAssignableFrom(c)) {
                return c;
            }
        }
        return null;
    }

}
