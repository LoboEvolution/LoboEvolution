/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.store.CacheManager;
import org.lobobrowser.store.StorageManager;

/**
 * Browser cache settings. This is a singleton class with an instance obtained
 * by calling {@link #getInstance()}.
 */
public class CacheSettings implements Serializable {

    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(CacheSettings.class
            .getName());

    /** The Constant instance. */
    private static final CacheSettings instance;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 22574500900000604L;

    static {
        CacheSettings ins = null;
        try {
            ins = (CacheSettings) StorageManager.getInstance()
                    .retrieveSettings(CacheSettings.class.getSimpleName(),
                            CacheSettings.class.getClassLoader());
        } catch (Exception err) {
            logger.error(
                    "getInstance(): Unable to retrieve settings.", err);
        }
        if (ins == null) {
            ins = new CacheSettings();
        }
        instance = ins;
    }

    /** Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
    public static CacheSettings getInstance() {
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
                    + this.getClass().getSimpleName() + ".", ioe);
        }
    }

    /**
     * Instantiates a new cache settings.
     */
    private CacheSettings() {
        resetDefaults();
    }

    /**
     * Reset defaults.
     */
    public void resetDefaults() {
        this.setMaxRAMCacheSize(5 * 1024 * 1024);
        this.setDefaultCacheExpirationOffset(60);
    }

    /** Gets the max ram cache size.
	 *
	 * @return the max ram cache size
	 */
    public int getMaxRAMCacheSize() {
        return CacheManager.getInstance().getMaxTransientCacheSize();
    }

    /** Sets the max ram cache size.
	 *
	 * @param maxRAMCacheSize
	 *            the new max ram cache size
	 */
    public void setMaxRAMCacheSize(int maxRAMCacheSize) {
        CacheManager.getInstance().setMaxTransientCacheSize(maxRAMCacheSize);
    }

    /** The default cache expiration offset. */
    private int defaultCacheExpirationOffset;

    /** Gets the default cache expiration offset.
	 *
	 * @return the default cache expiration offset
	 */
    public int getDefaultCacheExpirationOffset() {
        return defaultCacheExpirationOffset;
    }

    /** Sets the default cache expiration offset.
	 *
	 * @param defaultCacheExpirationOffset
	 *            the new default cache expiration offset
	 */
    public void setDefaultCacheExpirationOffset(int defaultCacheExpirationOffset) {
        this.defaultCacheExpirationOffset = defaultCacheExpirationOffset;
    }
}
