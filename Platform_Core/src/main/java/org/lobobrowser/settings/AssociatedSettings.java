/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.http.LRUCache;
import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.store.StorageManager;

/**
 * Settings associated with host names. This is a singleton class with an
 * instance obtained by calling {@link #getInstance()}.
 */
public class AssociatedSettings implements Serializable {

    /** The Constant logger. */
    private static final Logger logger = LogManager
            .getLogger(AssociatedSettings.class);

    /** The Constant instance. */
    private static final AssociatedSettings instance;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 22574500005000804L;

    static {
        AssociatedSettings ins = null;
        try {
            ins = (AssociatedSettings) StorageManager.getInstance()
                    .retrieveSettings(AssociatedSettings.class.getSimpleName(),
                            AssociatedSettings.class.getClassLoader());
        } catch (Exception err) {
            logger.error("Unable to retrieve settings.", err);
        }
        if (ins == null) {
            ins = new AssociatedSettings();
        }
        instance = ins;
    }

    /**
     * Instantiates a new associated settings.
     */
    private AssociatedSettings() {
    }

    /** Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
    public static AssociatedSettings getInstance() {
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
            StorageManager.getInstance().saveSettings(
                    this.getClass().getSimpleName(), this);
        } catch (IOException ioe) {
            logger.error("Unable to save settings: "
                    + this.getClass().getSimpleName(), ioe);
        }
    }

    /** The user name by host. */
    private final LRUCache userNameByHost = new LRUCache(500);

    /**
     * Gets the user name for host.
     *
     * @param hostName
     *            the host name
     * @return the user name for host
     */
    public String getUserNameForHost(String hostName) {
        synchronized (this) {
            return (String) this.userNameByHost.get(hostName);
        }
    }

    /**
     * Sets the user name for host.
     *
     * @param hostName
     *            the host name
     * @param userName
     *            the user name
     */
    public void setUserNameForHost(String hostName, String userName) {
        synchronized (this) {
            this.userNameByHost.put(hostName, userName, 1);
        }
    }
}
