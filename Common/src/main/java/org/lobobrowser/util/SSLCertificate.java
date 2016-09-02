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
package org.lobobrowser.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * The Class SSLCertificate.
 */
public class SSLCertificate {
    /** The Constant logger. */
    private static final Logger logger = LogManager
            .getLogger(SSLCertificate.class);
            
    /**
     * Sets the certificate fix for Exception in thread "main"
     * javax.net.ssl.SSLHandshakeException:
     * sun.security.validator.ValidatorException: PKIX path building failed:
     * sun.security.provider.certpath.SunCertPathBuilderException: unable to
     * find valid certification path to requested target
     */
    public static void setCertificate() {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager()
                {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs,
                            String authType) {
                    }
                    
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs,
                            String authType) {
                    }
                } };
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            /*
             * end of the fix
             */
        } catch (KeyManagementException e) {
            logger.error(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
    }
}
