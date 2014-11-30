/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.js;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.GeneratedClassLoader;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.SecurityController;

public class SecurityControllerImpl extends SecurityController {
	private final java.net.URL url;
	private final java.security.Policy policy;
	private final CodeSource codesource;
	
	private static final Logger logger = Logger.getLogger(SecurityControllerImpl.class
			.getName());

	public SecurityControllerImpl(java.net.URL url, Policy policy) {
		this.url = url;
		this.policy = policy;
		this.codesource = new CodeSource(this.url,
				(java.security.cert.Certificate[]) null);
	}

	public Object callWithDomain(Object securityDomain, final Context ctx,
			final Callable callable, final Scriptable scope,
			final Scriptable thisObj, final Object[] args) {

		Object obj = null;
		try {
			if (securityDomain == null) {
				obj = callable.call(ctx, scope, thisObj, args);
			} else {
				PrivilegedAction<Object> action = new PrivilegedAction<Object>() {
					public Object run() {
						return callable.call(ctx, scope, thisObj, args);
					}
				};
				AccessControlContext acctx = new AccessControlContext(
						new ProtectionDomain[] { (ProtectionDomain) securityDomain });
				return AccessController.doPrivileged(action, acctx);
			}
		} catch (MissingResourceException err) {
			logger.log(Level.WARNING, "Missing Resource");
		}
		return obj;
	}

	public GeneratedClassLoader createClassLoader(ClassLoader parent,
			Object staticDomain) {
		return new LocalSecureClassLoader(parent,codesource);
	}

	public Object getDynamicSecurityDomain(Object securityDomain) {
		Policy policy = this.policy;
		if (policy == null) {
			return null;
		} else {
			PermissionCollection permissions = this.policy.getPermissions(codesource);
			return new ProtectionDomain(codesource, permissions);
		}
	}
}
