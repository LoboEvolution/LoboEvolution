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
package org.lobobrowser.settings;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.store.StorageManager;
import org.lobobrowser.util.LRUCache;

/**
 * Settings associated with host names. This is a singleton class with an
 * instance obtained by calling {@link #getInstance()}.
 */
public class AssociatedSettings implements Serializable {
	private static final Logger logger = Logger
			.getLogger(AssociatedSettings.class.getName());
	private static final AssociatedSettings instance;
	private static final long serialVersionUID = 22574500005000804L;

	static {
		AssociatedSettings ins = null;
		try {
			ins = (AssociatedSettings) StorageManager.getInstance()
					.retrieveSettings(AssociatedSettings.class.getSimpleName(),
							AssociatedSettings.class.getClassLoader());
		} catch (Exception err) {
			logger.log(Level.WARNING, "Unable to retrieve settings.", err);
		}
		if (ins == null) {
			ins = new AssociatedSettings();
		}
		instance = ins;
	}

	private AssociatedSettings() {
	}

	/**
	 * Gets the class singleton.
	 */
	public static AssociatedSettings getInstance() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		return instance;
	}

	public void save() {
		try {
			StorageManager.getInstance().saveSettings(
					this.getClass().getSimpleName(), this);
		} catch (java.io.IOException ioe) {
			logger.log(Level.WARNING, "Unable to save settings: "
					+ this.getClass().getSimpleName(), ioe);
		}
	}

	private final LRUCache userNameByHost = new LRUCache(500);

	public String getUserNameForHost(String hostName) {
		synchronized (this) {
			return (String) this.userNameByHost.get(hostName);
		}
	}

	public void setUserNameForHost(String hostName, String userName) {
		synchronized (this) {
			this.userNameByHost.put(hostName, userName, 1);
		}
	}
}
