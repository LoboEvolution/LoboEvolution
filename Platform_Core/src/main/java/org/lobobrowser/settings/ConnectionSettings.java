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
/*
 * Created on Jul 9, 2005
 */
package org.lobobrowser.settings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.security.GenericLocalPermission;
import org.lobobrowser.store.StorageManager;
import org.lobobrowser.util.io.NetRoutines;

/**
 * Connection settings. This is a singleton class with an instance obtained by
 * calling {@link #getInstance()}.
 */
public class ConnectionSettings implements java.io.Serializable {

    /** The Constant logger. */
    private static final Logger logger = LogManager
            .getLogger(ConnectionSettings.class);

    /** The Constant instance. */
    private static final ConnectionSettings instance;

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 22574500000000301L;

    /** The proxy type. */
    private Proxy.Type proxyType = Proxy.Type.DIRECT;

    /** The socket address. */
    private InetSocketAddress socketAddress = null;

    /** The user name. */
    private String userName;

    /** The password. */
    private String password;

    /** The authenticated. */
    private boolean authenticated;

    /** The disable proxy for local addresses. */
    private boolean disableProxyForLocalAddresses;

    /** The proxy. */
    private transient Proxy proxy;

    static {
        ConnectionSettings ins = null;
        try {
            ins = (ConnectionSettings) StorageManager.getInstance()
                    .retrieveSettings(ConnectionSettings.class.getSimpleName(),
                            ConnectionSettings.class.getClassLoader());
        } catch (Exception err) {
            logger.error(
                    "getInstance(): Unable to retrieve settings.", err);
        }
        if (ins == null) {
            ins = new ConnectionSettings();
        }
        instance = ins;
    }

    /**
     * Instantiates a new connection settings.
     */
    private ConnectionSettings() {
        restoreDefaults();
    }

    /**
     * Restore defaults.
     */
    public void restoreDefaults() {
        this.proxyType = Proxy.Type.DIRECT;
        this.userName = "";
        this.password = "";
        this.authenticated = false;
        this.socketAddress = null;
        this.disableProxyForLocalAddresses = true;
        synchronized (this) {
            this.proxy = null;
        }
    }

    /** Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
    public static ConnectionSettings getInstance() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
        }
        return instance;
    }

    /**
     * Gets a non-<code>null</code> <code>Proxy</code> insteance.
     *
     * @param host
     *            the host
     * @return the proxy
     */
    public Proxy getProxy(String host) {
        synchronized (this) {
            if (this.proxy == null) {
                InetSocketAddress sa = this.socketAddress;
                if ((this.proxyType == Proxy.Type.DIRECT) || (sa == null)) {
                    this.proxy = Proxy.NO_PROXY;
                } else {
                    this.proxy = new Proxy(this.proxyType, sa);
                }
            }
            Proxy proxy = this.proxy;
            if ((proxy != Proxy.NO_PROXY) && this.disableProxyForLocalAddresses && NetRoutines.isLocalAddress(host)) {
            	 proxy = Proxy.NO_PROXY;
            }
            return proxy;
        }
    }

    /** Gets the password authentication.
	 *
	 * @return the password authentication
	 */
    public PasswordAuthentication getPasswordAuthentication() {
        String userName = this.userName;
        String password = this.password;
        if (!this.isAuthenticated() || (userName == null) || (password == null)) {
            return null;
        }
        return new PasswordAuthentication(userName, password.toCharArray());
    }

    /** Checks if is authenticated.
	 *
	 * @return the authenticated
	 */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /** Sets the authenticated.
	 *
	 * @param authenticated
	 *            the new authenticated
	 */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
        synchronized (this) {
            this.proxy = null;
        }
    }

    /** Gets the password.
	 *
	 * @return the password
	 */
    public String getPassword() {
        return password;
    }

    /** Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
    public void setPassword(String password) {
        this.password = password;
        synchronized (this) {
            this.proxy = null;
        }
    }

    /** Gets the user name.
	 *
	 * @return the user name
	 */
    public String getUserName() {
        return userName;
    }

    /** Sets the user name.
	 *
	 * @param userName
	 *            the new user name
	 */
    public void setUserName(String userName) {
        this.userName = userName;
        synchronized (this) {
            this.proxy = null;
        }
    }

    /** Gets the proxy type.
	 *
	 * @return the proxy type
	 */
    public Proxy.Type getProxyType() {
        return proxyType;
    }

    /** Sets the proxy type.
	 *
	 * @param proxyType
	 *            the new proxy type
	 */
    public void setProxyType(Proxy.Type proxyType) {
        this.proxyType = proxyType;
        synchronized (this) {
            this.proxy = null;
        }
    }

    /** Gets the inet socket address.
	 *
	 * @return the inet socket address
	 */
    public InetSocketAddress getInetSocketAddress() {
        return socketAddress;
    }

    /** Sets the inet socket address.
	 *
	 * @param socketAddress
	 *            the new inet socket address
	 */
    public void setInetSocketAddress(InetSocketAddress socketAddress) {
        this.socketAddress = socketAddress;
        synchronized (this) {
            this.proxy = null;
        }
    }

    /** Checks if is disable proxy for local addresses.
	 *
	 * @return the disable proxy for local addresses
	 */
    public boolean isDisableProxyForLocalAddresses() {
        return disableProxyForLocalAddresses;
    }

    /** Sets the disable proxy for local addresses.
	 *
	 * @param disableProxyForLocalAddresses
	 *            the new disable proxy for local addresses
	 */
    public void setDisableProxyForLocalAddresses(
            boolean disableProxyForLocalAddresses) {
        this.disableProxyForLocalAddresses = disableProxyForLocalAddresses;
        synchronized (this) {
            this.proxy = null;
        }
    }

    /**
     * Save.
     */
    public void save() {
        try {
            StorageManager.getInstance().saveSettings(
                    this.getClass().getSimpleName(), this);
        } catch (IOException ioe) {
            logger.error("save(): Unable to save settings", ioe);
        }
    }
}
