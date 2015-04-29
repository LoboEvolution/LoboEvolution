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

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.request.UserAgentImpl;
import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.store.StorageManager;

/**
 * General browser settings. This is a singleton class with an instance obtained
 * by calling {@link #getInstance()}.
 */
public class GeneralSettings implements java.io.Serializable {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(GeneralSettings.class
            .getName());

    /** The Constant DEFAULT_STARTUP. */
    private static final String DEFAULT_STARTUP = "";

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 22574500070000402L;

    /** The Constant instance. */
    private static final GeneralSettings instance;

    /** The startup ur ls. */
    private volatile Collection<String> startupURLs;

    /** The spoof ie. */
    private volatile boolean spoofIE;

    /** The ie version. */
    private volatile String ieVersion;

    /** The moz version. */
    private volatile String mozVersion;

    /** The initial window bounds. */
    private volatile Rectangle initialWindowBounds;

    static {
        GeneralSettings ins = null;
        try {
            ins = (GeneralSettings) StorageManager.getInstance()
                    .retrieveSettings(GeneralSettings.class.getSimpleName(),
                            GeneralSettings.class.getClassLoader());
        } catch (Exception err) {
            logger.log(Level.WARNING,
                    "getInstance(): Unable to retrieve settings.", err);
        }
        if (ins == null) {
            ins = new GeneralSettings();
        }
        instance = ins;
    }

    /**
     * Gets the class singleton.
     *
     * @return single instance of GeneralSettings
     */
    public static GeneralSettings getInstance() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
        }
        return instance;
    }

    /**
     * Instantiates a new general settings.
     */
    private GeneralSettings() {
        // Only called if not persisted
        this.restoreDefaults();
    }

    /**
     * Save.
     */
    public void save() {
        try {
            this.saveChecked();
        } catch (IOException ioe) {
            logger.log(Level.WARNING, "save(): Unable to save settings", ioe);
        }
    }

    /**
     * Restore defaults.
     */
    public void restoreDefaults() {
        this.startupURLs = Collections.singletonList(DEFAULT_STARTUP);
        this.spoofIE = true;
        this.ieVersion = "9.0";
        this.mozVersion = "32.0";
    }

    /**
     * Save checked.
     *
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void saveChecked() throws IOException {
        StorageManager.getInstance().saveSettings(
                this.getClass().getSimpleName(), this);
    }

    /**
     * Gets URLs that the browser should open when it starts up.
     *
     * @return the startup ur ls
     * @see #setStartupURLs(String[])
     */
    public String[] getStartupURLs() {
        // Cannot return empty or null
        Collection<String> urls = this.startupURLs;
        if ((urls == null) || (urls.size() == 0)) {
            return new String[] {DEFAULT_STARTUP};
        }
        return urls.toArray(new String[0]);
    }

    /**
     * Sets the startup ur ls.
     *
     * @param urls
     *            the new startup ur ls
     */
    public void setStartupURLs(String[] urls) {
        this.startupURLs = Arrays.asList(urls);
    }

    /**
     * Gets the initial window bounds.
     *
     * @return the initial window bounds
     */
    public Rectangle getInitialWindowBounds() {
        Rectangle bounds = initialWindowBounds;
        if (bounds == null) {
            return GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getMaximumWindowBounds();
        }
        if (bounds.width < 100) {
            bounds.width = 100;
        }
        if (bounds.height < 100) {
            bounds.height = 100;
        }
        return bounds;
    }

    /**
     * Sets the initial window bounds.
     *
     * @param initialWindowBounds
     *            the new initial window bounds
     */
    public void setInitialWindowBounds(Rectangle initialWindowBounds) {
        this.initialWindowBounds = initialWindowBounds;
    }

    /**
     * Gets the ie version.
     *
     * @return the ie version
     */
    public String getIeVersion() {
        return ieVersion;
    }

    /**
     * Sets the ie version.
     *
     * @param ieVersion
     *            the new ie version
     */
    public void setIeVersion(String ieVersion) {
        this.ieVersion = ieVersion;
        UserAgentImpl.getInstance().invalidateUserAgent();
    }

    /**
     * Checks if is spoof ie.
     *
     * @return true, if is spoof ie
     */
    public boolean isSpoofIE() {
        return spoofIE;
    }

    /**
     * Sets the spoof ie.
     *
     * @param spoofIE
     *            the new spoof ie
     */
    public void setSpoofIE(boolean spoofIE) {
        this.spoofIE = spoofIE;
        UserAgentImpl.getInstance().invalidateUserAgent();
    }

    /**
     * Gets the moz version.
     *
     * @return the moz version
     */
    public String getMozVersion() {
        return mozVersion;
    }

    /**
     * Sets the moz version.
     *
     * @param mozVersion
     *            the new moz version
     */
    public void setMozVersion(String mozVersion) {
        this.mozVersion = mozVersion;
        UserAgentImpl.getInstance().invalidateUserAgent();
    }
}
