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
package org.loboevolution.http;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class LowSecurityX509TrustManager.
 */
public class LowSecurityX509TrustManager implements X509TrustManager {

	/** The standard trust manager. */
	private X509TrustManager standardTrustManager = null;
	/** Log object for this class. */
	private static final Logger LOG = LogManager.getLogger(LowSecurityX509TrustManager.class);

	/**
	 * Constructor for LowSecurityX509TrustManager.
	 */
	public LowSecurityX509TrustManager(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
		super();
		TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		factory.init(keystore);
		TrustManager[] trustmanagers = factory.getTrustManagers();
		if (trustmanagers.length == 0) {
			throw new NoSuchAlgorithmException("no trust manager found");
		}
		this.standardTrustManager = (X509TrustManager) trustmanagers[0];
	}

	/**
	 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(X509Certificate[],
	 *      String authType)
	 */
	@Override
	public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
		standardTrustManager.checkClientTrusted(certificates, authType);
	}

	/**
	 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate[],
	 *      String authType)
	 */
	@Override
	public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
		if (certificates != null && LOG.isInfoEnabled()) {
			LOG.debug("Server certificate chain:");
			for (int i = 0; i < certificates.length; i++) {
				LOG.debug("X509Certificate[" + i + "]=" + certificates[i]);
			}
		}
		if (certificates != null && certificates.length == 1) {
			certificates[0].checkValidity();
		} else {
			standardTrustManager.checkServerTrusted(certificates, authType);
		}
	}

	/**
	 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return this.standardTrustManager.getAcceptedIssuers();
	}
}
