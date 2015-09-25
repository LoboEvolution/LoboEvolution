/*
 * $Id: MediumSecurityX509TrustManager.java 117 2006-10-24 21:12:16Z rbair $
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * California 95054, U.S.A. All rights reserved. This library is free software;
 * you can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.lobobrowser.http;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * <p>
 * MediumSecurityX509TrustManager unlike default {@link X509TrustManager}
 * accepts self-signed certificates, but prompts the user before accepting them.
 * </p>
 */
class MediumSecurityX509TrustManager implements X509TrustManager {
    private X509TrustManager standardTrustManager = null;
    /** Log object for this class. */
    private static final Logger LOG = Logger
            .getLogger(MediumSecurityX509TrustManager.class.getName());
    private SecurityHandler handler;
    private String host;
    
    /**
     * Constructor for EasyX509TrustManager.
     */
    public MediumSecurityX509TrustManager(String host, SecurityHandler handler,
            KeyStore keystore)
                    throws NoSuchAlgorithmException, KeyStoreException {
        super();
        this.host = host;
        this.handler = handler;
        TrustManagerFactory factory = TrustManagerFactory
                .getInstance(TrustManagerFactory.getDefaultAlgorithm());
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
    public void checkClientTrusted(X509Certificate[] certificates,
            String authType) throws CertificateException {
        standardTrustManager.checkClientTrusted(certificates, authType);
    }
    
    /**
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate[],
     *      String authType)
     */
    @Override
    public void checkServerTrusted(X509Certificate[] certificates,
            String authType) throws CertificateException {
        if ((certificates != null) && (certificates.length == 1)
                && handler != null) {
            certificates[0].checkValidity();
            if (!handler.isServerTrusted(host, certificates[0])) {
                throw new CertificateException("Self signed certificate from "
                        + certificates[0].getIssuerX500Principal().toString()
                        + " is not trusted");
            }
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
