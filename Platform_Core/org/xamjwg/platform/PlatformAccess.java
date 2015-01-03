/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Aug 14, 2005
 */
package org.xamjwg.platform;

import java.net.MalformedURLException;
import java.security.Permission;

import org.lobobrowser.clientlet.ClientletSelector;
import org.lobobrowser.context.ClientletFactory;
import org.lobobrowser.main.EntryPoint;
import org.lobobrowser.main.PlatformInit;
import org.lobobrowser.security.StoreHostPermission;

/**
 * Platform access utilities. This class is a singleton. Instantiate by calling
 * <code>PlatformAccess.getInstance()</code>.
 * 
 * @see #getInstance()
 * @since 0.93.2
 * @deprecated See the new Lobo Browser API.
 */
public class PlatformAccess {
	private PlatformAccess() {
	}

	private static PlatformAccess instance;

	/**
	 * Gets the <code>PlatformAccess</code> singleton.
	 */
	public static PlatformAccess getInstance() {
		if (instance == null) {
			synchronized (PlatformAccess.class) {
				if (instance == null) {
					instance = new PlatformAccess();
				}
			}
		}
		return instance;
	}

	/**
	 * Launches the default browser application. It tries to reuse any existing
	 * platform JVM. The method <code>initPlatform</code> should <em>not</em> be
	 * invoked when this method is used.
	 * 
	 * @see #launchLocal
	 */
	public void launch() {
		EntryPoint.main(new String[0]);
	}

	/**
	 * Launches the URLs given. Documents are normally opened in a browser
	 * frame, unless they include a <code>document-disposition</code> of
	 * <code>auto</code> or <code>window</code>. This method attempts to reuse
	 * any existing platform JVMs. The method <code>initPlatform</code> should
	 * <em>not</em> be invoked when this method is used.
	 * 
	 * @param urls
	 *            An array of URLs to open.
	 * @see #launchLocal
	 */
	public void launch(String[] urls) {
		EntryPoint.main(urls);
	}

	/**
	 * Sets up a security policy, default Look & Feel, and standard output
	 * redirection to console. Implementations that do not care about security
	 * do not need to call this before calling <code>launchLocal</code>. This is
	 * implicitly called if using one of the <code>launch</code> methods,
	 * however.
	 * 
	 * @see #launchLocal
	 */
	public void initPlatform() throws Exception {
		PlatformInit.getInstance().init(new String[0], true);
	}

	/**
	 * Launches the platform without setting up security or a default look &
	 * feel, and without reusing any existing platform JVM. Documents are
	 * normally opened in a browser frame, unless they include a
	 * <code>document-disposition</code> of <code>auto</code> or
	 * <code>window</code>.
	 * 
	 * @param urls
	 *            An array of URLs to open.
	 * @see #initPlatform()
	 * @see #createFrame()
	 */
	public void launchLocal(String[] urls) throws MalformedURLException {
		PlatformInit.getInstance().start(urls);
	}

	/**
	 * Systems that set their own security policy (not invoking
	 * <code>initPlatform</code>) should use this method to create a permission
	 * needed to access managed stores.
	 * 
	 * @param hostName
	 *            The name of the host the code was loaded from.
	 * @return A Permission object which may be used in a security Policy.
	 * @see #initPlatform
	 */
	public Permission createHostPermission(String hostName) {
		return StoreHostPermission.forHost(hostName);
	}

	/**
	 * Systems that set their own security policy (not invoking
	 * <code>initPlatform</code>) should use this method to create a permission
	 * needed in the custom policy to allow access to managed stores.
	 * 
	 * @param url
	 *            The URL the code was loaded from.
	 * @return A Permission object which may be used in a security Policy.
	 * @see #initPlatform()
	 */
	public Permission createHostPermission(java.net.URL url) {
		return StoreHostPermission.forURL(url);
	}

	/**
	 * Adds a <code>ClientletSelector</code> which allows the platform to
	 * process additional content types. The platform itself registers one or
	 * more selectors internally in order to handle XAMJ and other content
	 * types. These cannot be overridden.
	 * 
	 * @param selector
	 *            A <code>ClientletSelector</code> instance.
	 * @see org.lobobrowser.clientlet.ClientletSelector
	 */
	public void addClientletSelector(ClientletSelector selector) {
		ClientletFactory.getInstance().addClientletSelector(selector);
	}

	// TODO: Methods to allow putting a web browser on any GUI.
}
