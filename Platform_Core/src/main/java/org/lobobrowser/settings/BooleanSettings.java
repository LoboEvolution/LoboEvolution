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
package org.lobobrowser.settings;

import java.io.IOException;
import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.store.StorageManager;

/**
 * Miscellaneous settings in the form of boolean values. This is a singleton
 * class with an instance obtained by calling {@link #getInstance()}.
 */
public class BooleanSettings implements Serializable {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(BooleanSettings.class.getName());

	/** The Constant instance. */
	private static final BooleanSettings instance;

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500900000604L;

	/**
	 * Whether chunked encoding is used in POSTs. Note that some servers (e.g.
	 * Wikimedia) apparently expect a content length.
	 */
	private boolean httpUseChunkedEncodingPOST;

	static {
		BooleanSettings ins = null;
		try {
			ins = (BooleanSettings) StorageManager.getInstance().retrieveSettings(BooleanSettings.class.getSimpleName(),
					BooleanSettings.class.getClassLoader());
		} catch (Exception err) {
			logger.error("Unable to retrieve settings.", err);
		}
		if (ins == null) {
			ins = new BooleanSettings();
		}
		instance = ins;
	}

	/**
	 * Instantiates a new boolean settings.
	 */
	private BooleanSettings() {
		this.resetDefaults();
	}

	/**
	 * Reset defaults.
	 */
	private void resetDefaults() {
		this.httpUseChunkedEncodingPOST = false;
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static BooleanSettings getInstance() {
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

	/**
	 * Checks if is whether chunked encoding is used in POSTs.
	 *
	 * @return the whether chunked encoding is used in POSTs
	 */
	public boolean isHttpUseChunkedEncodingPOST() {
		return httpUseChunkedEncodingPOST;
	}

	/**
	 * Sets the whether chunked encoding is used in POSTs.
	 *
	 * @param httpUseChunkedEncodingPOST
	 *            the new whether chunked encoding is used in POSTs
	 */
	public void setHttpUseChunkedEncodingPOST(boolean httpUseChunkedEncodingPOST) {
		this.httpUseChunkedEncodingPOST = httpUseChunkedEncodingPOST;
	}
}
