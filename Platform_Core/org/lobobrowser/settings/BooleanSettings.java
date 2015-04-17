/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.settings;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.store.StorageManager;

/**
 * Miscellaneous settings in the form of boolean values. This is a singleton
 * class with an instance obtained by calling {@link #getInstance()}.
 */
public class BooleanSettings implements Serializable {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(BooleanSettings.class
            .getName());

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
            ins = (BooleanSettings) StorageManager.getInstance()
                    .retrieveSettings(BooleanSettings.class.getSimpleName(),
                            BooleanSettings.class.getClassLoader());
        } catch (Exception err) {
            logger.log(Level.WARNING, "Unable to retrieve settings.", err);
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
     * Gets the class singleton.
     *
     * @return single instance of BooleanSettings
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
            StorageManager.getInstance().saveSettings(
                    this.getClass().getSimpleName(), this);
        } catch (IOException ioe) {
            logger.log(Level.WARNING, "Unable to save settings: "
                    + this.getClass().getSimpleName(), ioe);
        }
    }

    /**
     * Checks if is http use chunked encoding post.
     *
     * @return true, if is http use chunked encoding post
     */
    public boolean isHttpUseChunkedEncodingPOST() {
        return httpUseChunkedEncodingPOST;
    }

    /**
     * Sets the http use chunked encoding post.
     *
     * @param httpUseChunkedEncodingPOST
     *            the new http use chunked encoding post
     */
    public void setHttpUseChunkedEncodingPOST(boolean httpUseChunkedEncodingPOST) {
        this.httpUseChunkedEncodingPOST = httpUseChunkedEncodingPOST;
    }
}
