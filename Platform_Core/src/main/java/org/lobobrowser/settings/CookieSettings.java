/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.settings;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.store.StorageManager;

/**
 * The Class CookieSettings.
 */
public class CookieSettings implements Serializable {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(CookieSettings.class
            .getName());

    /** The Constant instance. */
    private static final CookieSettings instance;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 22574500005000503L;

    static {
        CookieSettings ins = null;
        try {
            ins = (CookieSettings) StorageManager.getInstance()
                    .retrieveSettings(CookieSettings.class.getSimpleName(),
                            CookieSettings.class.getClassLoader());
        } catch (Exception err) {
            logger.log(Level.WARNING,
                    "getInstance(): Unable to retrieve settings.", err);
        }
        if (ins == null) {
            ins = new CookieSettings();
        }
        instance = ins;
    }

    /**
     * Instantiates a new cookie settings.
     */
    private CookieSettings() {
    }

    /** Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
    public static CookieSettings getInstance() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
        }
        return instance;
    }
}
