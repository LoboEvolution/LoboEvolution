/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.js;

import java.net.URL;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.MissingResourceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.GeneratedClassLoader;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.SecurityController;

/**
 * The Class SecurityControllerImpl.
 */
public class SecurityControllerImpl extends SecurityController {

	/** The url. */
	private final URL url;

	/** The policy. */
	private final Policy policy;

	/** The codesource. */
	private final CodeSource codesource;

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(SecurityControllerImpl.class.getName());

	/**
	 * Instantiates a new security controller impl.
	 *
	 * @param url
	 *            the url
	 * @param policy
	 *            the policy
	 */
	public SecurityControllerImpl(URL url, Policy policy) {
		this.url = url;
		this.policy = policy;
		Certificate[] certs = new Certificate[] {};
		this.codesource = new CodeSource(this.url, certs);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mozilla.javascript.SecurityController#callWithDomain(java.lang.
	 * Object, org.mozilla.javascript.Context, org.mozilla.javascript.Callable,
	 * org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable,
	 * java.lang.Object[])
	 */
	@Override
	public Object callWithDomain(Object securityDomain, final Context ctx, final Callable callable,
			final Scriptable scope, final Scriptable thisObj, final Object[] args) {

		Object obj = null;
		try {
			if (securityDomain == null) {
				obj = callable.call(ctx, scope, thisObj, args);
			} else {
				PrivilegedAction<Object> action = () -> callable.call(ctx, scope, thisObj, args);
				AccessControlContext acctx = new AccessControlContext(
						new ProtectionDomain[] { (ProtectionDomain) securityDomain });
				return AccessController.doPrivileged(action, acctx);
			}
		} catch (MissingResourceException err) {
			logger.error("Missing Resource");
		}
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mozilla.javascript.SecurityController#createClassLoader(java.lang.
	 * ClassLoader , java.lang.Object)
	 */
	@Override
	public GeneratedClassLoader createClassLoader(ClassLoader parent, Object staticDomain) {
		return new LocalSecureClassLoader(parent, codesource);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mozilla.javascript.SecurityController#getDynamicSecurityDomain(java.
	 * lang .Object)
	 */
	@Override
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
