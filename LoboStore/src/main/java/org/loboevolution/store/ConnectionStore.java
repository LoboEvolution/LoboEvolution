/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.store;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.net.NetRoutines;


/**
 * <p>ConnectionStore class.</p>
 *
 *
 *
 */
public class ConnectionStore implements Serializable {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ConnectionStore.class.getName());

	private static final String CONNECTIONS = "SELECT DISTINCT proxyType, userName, password, authenticated, host, port, disableProxyForLocalAddresses FROM CONNECTION";

	private static final String DELETE_CONNECTIONS = "DELETE FROM CONNECTION";

	private static final String INSERT_CONNECTIONS = "INSERT INTO CONNECTION (proxyType, userName, password, authenticated, host, port, disableProxyForLocalAddresses) VALUES(?,?,?,?,?,?,?)";

	private static final long serialVersionUID = 1L;

	/** The authenticated. */
	private boolean authenticated;

	/** The disable proxy for local addresses. */
	private boolean disableProxyForLocalAddresses;

	/** The password. */
	private String password;

	/** The proxy. */
	private transient Proxy proxy;

	/** The proxy type. */
	private Proxy.Type proxyType = Proxy.Type.DIRECT;

	/** The socket address. */
	private InetSocketAddress socketAddress = null;

	/** The user name. */
	private String userName;

	/**
	 * Instantiates a new connection settings.
	 */
	public ConnectionStore() {
	}
	
	/**
	 * <p>getConnection.</p>
	 *
	 * @return a {@link org.loboevolution.store.ConnectionStore} object.
	 */
	public static ConnectionStore getConnection() {
		final ConnectionStore setting = new ConnectionStore();
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(CONNECTIONS)) {
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs != null && rs.next()) {
					setting.setProxyType(Proxy.Type.valueOf(rs.getString(1)));
					setting.setUserName(rs.getString(2));
					setting.setPassword(rs.getString(3));
					setting.setAuthenticated(rs.getInt(4) == 1);
					final InetSocketAddress socketAddress = new InetSocketAddress(rs.getString(5), rs.getInt(6));
					setting.setInetSocketAddress(socketAddress);
					setting.setDisableProxyForLocalAddresses(rs.getInt(7) == 1);
				}
			}
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return setting;
	}

	/**
	 * <p>deleteConnection.</p>
	 */
	public void deleteConnection() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(DELETE_CONNECTIONS)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Gets the inet socket address.
	 *
	 * @return the inet socket address
	 */
	public InetSocketAddress getInetSocketAddress() {
		return this.socketAddress;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Gets the password authentication.
	 *
	 * @return the password authentication
	 */
	public PasswordAuthentication getPasswordAuthentication() {
		final String userName = this.userName;
		final String password = this.password;
		if (!isAuthenticated() || userName == null || password == null) {
			return null;
		}
		return new PasswordAuthentication(userName, password.toCharArray());
	}

	/**
	 * Gets a non-null Proxy insteance.
	 *
	 * @param host the host
	 * @return the proxy
	 */
	public Proxy getProxy(String host) {
		synchronized (this) {
			if (this.proxy == null) {
				final InetSocketAddress sa = this.socketAddress;
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
	 * Gets the proxy type.
	 *
	 * @return the proxy type
	 */
	public Proxy.Type getProxyType() {
		return this.proxyType;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * <p>insertConnection.</p>
	 */
	public void insertConnection() {
		try (Connection conn = DriverManager.getConnection(SQLiteCommon.getDatabaseDirectory());
				PreparedStatement pstmt = conn.prepareStatement(INSERT_CONNECTIONS)) {
			pstmt.setString(1, getProxyType().name());
			pstmt.setString(2, getUserName());
			pstmt.setString(3, getPassword());
			pstmt.setInt(4, isAuthenticated() ? 1 : 0);
			pstmt.setString(5, getInetSocketAddress().getHostName());
			pstmt.setInt(6, getInetSocketAddress().getPort());
			pstmt.setInt(7, isDisableProxyForLocalAddresses() ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * Checks if is authenticated.
	 *
	 * @return the authenticated
	 */
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	/**
	 * Checks if is disable proxy for local addresses.
	 *
	 * @return the disable proxy for local addresses
	 */
	public boolean isDisableProxyForLocalAddresses() {
		return this.disableProxyForLocalAddresses;
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

	/**
	 * Sets the authenticated.
	 *
	 * @param authenticated the new authenticated
	 */
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
		synchronized (this) {
			this.proxy = null;
		}
	}

	/**
	 * Sets the disable proxy for local addresses.
	 *
	 * @param disableProxyForLocalAddresses the new disable proxy for local
	 *                                      addresses
	 */
	public void setDisableProxyForLocalAddresses(boolean disableProxyForLocalAddresses) {
		this.disableProxyForLocalAddresses = disableProxyForLocalAddresses;
		synchronized (this) {
			this.proxy = null;
		}
	}

	/**
	 * Sets the inet socket address.
	 *
	 * @param socketAddress the new inet socket address
	 */
	public void setInetSocketAddress(InetSocketAddress socketAddress) {
		this.socketAddress = socketAddress;
		synchronized (this) {
			this.proxy = null;
		}
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
		synchronized (this) {
			this.proxy = null;
		}
	}

	/**
	 * Sets the proxy type.
	 *
	 * @param proxyType the new proxy type
	 */
	public void setProxyType(Proxy.Type proxyType) {
		this.proxyType = proxyType;
		synchronized (this) {
			this.proxy = null;
		}
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
		synchronized (this) {
			this.proxy = null;
		}
	}
}
