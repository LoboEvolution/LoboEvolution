/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.net.NetRoutines;


/**
 * <p>ConnectionStore class.</p>
 */
@Slf4j
@Data
public class ConnectionStore implements QueryStore, Serializable {

	/** The Constant DB_PATH. */
	private static final String DB_PATH = DatabseSQLite.getDatabaseDirectory();

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
	private InetSocketAddress inetSocketAddress = null;

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
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(CONNECTIONS)) {
			try (final ResultSet rs = pstmt.executeQuery()) {
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
			log.error(e.getMessage(), e);
		}
		return setting;
	}

	/**
	 * <p>deleteConnection.</p>
	 */
	public void deleteConnection() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(DELETE_CONNECTIONS)) {
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		}
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
	public Proxy getProxy(final String host) {
		synchronized (this) {
			if (this.proxy == null) {
				final InetSocketAddress sa = this.inetSocketAddress;
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
	 * <p>insertConnection.</p>
	 */
	public void insertConnection() {
		try (final Connection conn = DriverManager.getConnection(DB_PATH);
             final PreparedStatement pstmt = conn.prepareStatement(INSERT_CONNECTIONS)) {
			pstmt.setString(1, getProxyType().name());
			pstmt.setString(2, getUserName());
			pstmt.setString(3, getPassword());
			pstmt.setInt(4, isAuthenticated() ? 1 : 0);
			pstmt.setString(5, getInetSocketAddress().getHostName());
			pstmt.setInt(6, getInetSocketAddress().getPort());
			pstmt.setInt(7, isDisableProxyForLocalAddresses() ? 1 : 0);
			pstmt.executeUpdate();
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
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
		this.inetSocketAddress = null;
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
	public void setAuthenticated(final boolean authenticated) {
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
	public void setDisableProxyForLocalAddresses(final boolean disableProxyForLocalAddresses) {
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
	public void setInetSocketAddress(final InetSocketAddress socketAddress) {
		this.inetSocketAddress = socketAddress;
		synchronized (this) {
			this.proxy = null;
		}
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(final String password) {
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
	public void setProxyType(final Proxy.Type proxyType) {
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
	public void setUserName(final String userName) {
		this.userName = userName;
		synchronized (this) {
			this.proxy = null;
		}
	}
}
