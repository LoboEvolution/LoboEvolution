/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jun 6, 2005
 */
package org.lobobrowser.primary.ext;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.store.StorageManager;

/**
 * History of navigation locations. Not thread safe.
 */
public class NavigationHistory extends BaseHistory<Object>implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2257845000600200100L;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(NavigationHistory.class.getName());

	/** The Constant instance. */
	private static final NavigationHistory instance;

	static {
		NavigationHistory ins = null;
		try {
			ins = (NavigationHistory) StorageManager.getInstance().retrieveSettings(
					NavigationHistory.class.getSimpleName(), NavigationHistory.class.getClassLoader());
		} catch (Exception err) {
			logger.log(Level.WARNING, "Unable to retrieve settings.", err);
		}
		if (ins == null) {
			ins = new NavigationHistory();
		}
		instance = ins;
	}

	/**
	 * Instantiates a new navigation history.
	 */
	private NavigationHistory() {
	}

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static NavigationHistory getInstance() {
		return instance;
	}

	/**
	 * Save.
	 */
	public void save() {
		synchronized (this) {
			try {
				StorageManager.getInstance().saveSettings(this.getClass().getSimpleName(), this);
			} catch (IOException ioe) {
				logger.log(Level.WARNING, "Unable to save settings: " + this.getClass().getSimpleName(), ioe);
			}
		}
	}
}
