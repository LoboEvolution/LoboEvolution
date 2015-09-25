/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.js;

import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.js.AbstractScriptableDelegate;

/**
 * The Class Navigator.
 */
public class Navigator extends AbstractScriptableDelegate {

    /** The context. */
    private final UserAgentContext context;

    /** The mime types. */
    private MimeTypesCollection mimeTypes;

    /**
     * Instantiates a new navigator.
     *
     * @param context
     *            the context
     */
    Navigator(UserAgentContext context) {
        super();
        this.context = context;
    }

    /**
     * Gets the app code name.
     *
     * @return the app code name
     */
    public String getAppCodeName() {
        return this.context.getAppCodeName();
    }

    /**
     * Gets the app name.
     *
     * @return the app name
     */
    public String getAppName() {
        return this.context.getAppName();
    }

    /**
     * Gets the app version.
     *
     * @return the app version
     */
    public String getAppVersion() {
        return this.context.getAppVersion();
    }

    /**
     * Gets the app minor version.
     *
     * @return the app minor version
     */
    public String getAppMinorVersion() {
        return this.context.getAppMinorVersion();
    }

    /**
     * Gets the platform.
     *
     * @return the platform
     */
    public String getPlatform() {
        return this.context.getPlatform();
    }

    /**
     * Gets the user agent.
     *
     * @return the user agent
     */
    public String getUserAgent() {
        return this.context.getUserAgent();
    }

    /**
     * Gets the vendor.
     *
     * @return the vendor
     */
    public String getVendor() {
        return this.context.getVendor();
    }

    /**
     * Gets the product.
     *
     * @return the product
     */
    public String getProduct() {
        return this.context.getProduct();
    }

    /**
     * Java enabled.
     *
     * @return true, if successful
     */
    public boolean javaEnabled() {
        // TODO True always?
        return true;
    }

    /**
     * Gets the language.
     *
     * @return the language
     */
    public String getLanguage() {
        // TODO
        return null;
    }

    /**
     * Checks if is on line.
     *
     * @return true, if is on line
     */
    public boolean isOnLine() {
        // TODO
        return false;
    }

    /**
     * Yield for storage updates.
     */
    public void yieldForStorageUpdates() {
        // TODO
    }

    /**
     * Checks if is content handler registered.
     *
     * @param mimeType
     *            the mime type
     * @param url
     *            the url
     * @return the string
     */
    public String isContentHandlerRegistered(String mimeType, String url) {
        // TODO
        return null;
    }

    /**
     * Checks if is protocol handler registered.
     *
     * @param scheme
     *            the scheme
     * @param url
     *            the url
     * @return the string
     */
    public String isProtocolHandlerRegistered(String scheme, String url) {
        // TODO
        return null;
    }

    /**
     * Register content handler.
     *
     * @param mimeType
     *            the mime type
     * @param url
     *            the url
     * @param title
     *            the title
     */
    public void registerContentHandler(String mimeType, String url, String title) {
        // TODO
    }

    /**
     * Register protocol handler.
     *
     * @param scheme
     *            the scheme
     * @param url
     *            the url
     * @param title
     *            the title
     */
    public void registerProtocolHandler(String scheme, String url, String title) {
        // TODO
    }

    /**
     * Unregister content handler.
     *
     * @param mimeType
     *            the mime type
     * @param url
     *            the url
     */
    public void unregisterContentHandler(String mimeType, String url) {
        // TODO
    }

    /**
     * Unregister protocol handler.
     *
     * @param scheme
     *            the scheme
     * @param url
     *            the url
     */
    public void unregisterProtocolHandler(String scheme, String url) {
        // TODO
    }

    /**
     * Gets the mime types.
     *
     * @return the mime types
     */
    public MimeTypesCollection getMimeTypes() {
        synchronized (this) {
            MimeTypesCollection mt = this.mimeTypes;
            if (mt == null) {
                mt = new MimeTypesCollection();
                this.mimeTypes = mt;
            }
            return mt;
        }
    }

    /**
     * The Class MimeTypesCollection.
     */
    public class MimeTypesCollection {
        // Class must be public to allow JavaScript access
        /**
         * Gets the length.
         *
         * @return the length
         */
        public int getLength() {
            return 0;
        }

        /**
         * Item.
         *
         * @param index
         *            the index
         * @return the object
         */
        public Object item(int index) {
            return null;
        }

        /**
         * Named item.
         *
         * @param name
         *            the name
         * @return the object
         */
        public Object namedItem(String name) {
            return null;
        }
    }
}
