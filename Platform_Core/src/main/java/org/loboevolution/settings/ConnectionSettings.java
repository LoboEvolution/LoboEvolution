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
/*
 * Created on Jul 9, 2005
 */
package org.loboevolution.settings;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.security.GenericLocalPermission;
import org.loboevolution.util.io.NetRoutines;

import com.loboevolution.store.SQLiteCommon;

/**
 * Connection settings. This is a singleton class with an instance obtained by
 * calling {@link #getInstance()}.
 */
public class ConnectionSettings implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 22574500000000301L;
	
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ConnectionSettings.class);

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

	/**
	 * Instantiates a new connection settings.
	 */
	public ConnectionSettings() {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(GenericLocalPermission.EXT_GENERIC);
		}
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
		deleteConnection();
		insertConnection();
	}
	
	public void deleteConnection() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.DELETE_CONNECTIONS)) {
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public void insertConnection() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				 PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.INSERT_CONNECTIONS)) {
			pstmt.setString(1, getProxyType().name());
			pstmt.setString(2, getUserName());
			pstmt.setString(3, getPassword());
			pstmt.setInt(4, isAuthenticated() ? 1 : 0);
			pstmt.setString(5, getInetSocketAddress().getHostName());
			pstmt.setInt(6, getInetSocketAddress().getPort());
			pstmt.setInt(7, isDisableProxyForLocalAddresses() ? 1 : 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	public static ConnectionSettings getConnection() {
		ConnectionSettings setting = new ConnectionSettings();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(SQLiteCommon.CONNECTIONS)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					setting.setProxyType(Proxy.Type.valueOf(rs.getString(1)));
					setting.setUserName(rs.getString(2));
					setting.setPassword(rs.getString(3));
					setting.setAuthenticated(rs.getInt(4) == 1 ? true : false);
					InetSocketAddress socketAddress = new InetSocketAddress(rs.getString(5), rs.getInt(6));
					setting.setInetSocketAddress(socketAddress);
					setting.setDisableProxyForLocalAddresses(rs.getInt(7) == 1 ? true : false);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return setting;
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
				if (this.proxyType == Proxy.Type.DIRECT || sa == null) {
					this.proxy = Proxy.NO_PROXY;
				} else {
					this.proxy = new Proxy(this.proxyType, sa);
				}
			}
			Proxy proxy = this.proxy;
			if (proxy != Proxy.NO_PROXY && this.disableProxyForLocalAddresses && NetRoutines.isLocalAddress(host)) {
				proxy = Proxy.NO_PROXY;
			}
			return proxy;
		}
	}

	/**
	 * Gets the password authentication.
	 *
	 * @return the password authentication
	 */
	public PasswordAuthentication getPasswordAuthentication() {
		String userName = this.userName;
		String password = this.password;
		if (!this.isAuthenticated() || userName == null || password == null) {
			return null;
		}
		return new PasswordAuthentication(userName, password.toCharArray());
	}

	/**
	 * Checks if is authenticated.
	 *
	 * @return the authenticated
	 */
	public boolean isAuthenticated() {
		return authenticated;
	}

	/**
	 * Sets the authenticated.
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

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
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

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
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

	/**
	 * Gets the proxy type.
	 *
	 * @return the proxy type
	 */
	public Proxy.Type getProxyType() {
		return proxyType;
	}

	/**
	 * Sets the proxy type.
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

	/**
	 * Gets the inet socket address.
	 *
	 * @return the inet socket address
	 */
	public InetSocketAddress getInetSocketAddress() {
		return socketAddress;
	}

	/**
	 * Sets the inet socket address.
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

	/**
	 * Checks if is disable proxy for local addresses.
	 *
	 * @return the disable proxy for local addresses
	 */
	public boolean isDisableProxyForLocalAddresses() {
		return disableProxyForLocalAddresses;
	}

	/**
	 * Sets the disable proxy for local addresses.
	 *
	 * @param disableProxyForLocalAddresses
	 *            the new disable proxy for local addresses
	 */
	public void setDisableProxyForLocalAddresses(boolean disableProxyForLocalAddresses) {
		this.disableProxyForLocalAddresses = disableProxyForLocalAddresses;
		synchronized (this) {
			this.proxy = null;
		}
	}
}
