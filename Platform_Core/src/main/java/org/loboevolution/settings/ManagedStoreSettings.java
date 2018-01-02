/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.settings;

import java.io.IOException;
import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.security.GenericLocalPermission;
import org.loboevolution.store.StorageManager;

/**
 * The Class ManagedStoreSettings.
 */
public class ManagedStoreSettings implements Serializable {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ManagedStoreSettings.class);

	/** The Constant instance. */
	private static final ManagedStoreSettings instance;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500000020705L;

	static {
		ManagedStoreSettings ins = null;
		try {
			ins = (ManagedStoreSettings) StorageManager.getInstance().retrieveSettings(
					ManagedStoreSettings.class.getSimpleName(), ManagedStoreSettings.class.getClassLoader());
		} catch (Exception err) {
			logger.error("getInstance(): Unable to retrieve settings.", err);
		}
		if (ins == null) {
			ins = new ManagedStoreSettings();
		}
		instance = ins;
	}

	/**
	 * Instantiates a new managed store settings.
	 */
	private ManagedStoreSettings() {
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static ManagedStoreSettings getInstance() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
		return instance;
	}

	/**
	 * Save.
	 */
	public void save() {
		try {
			StorageManager.getInstance().saveSettings(this.getClass().getSimpleName(), this);
		} catch (IOException ioe) {
			logger.error("Unable to save settings: " + this.getClass().getSimpleName(), ioe);
		}
	}
}
