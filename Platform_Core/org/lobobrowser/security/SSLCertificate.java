/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project This program
 * is free software; you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation;
 * either version 2 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA Contact info:
 * lobochief@users.sourceforge.net
 */
package org.lobobrowser.security;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * The Class SSLCertificate.
 */
public class SSLCertificate {

	private static SSLSocketFactory TRUSTED_FACTORY;

	private static HostnameVerifier TRUSTED_VERIFIER;

	/**
	 * Sets the certificate.
	 */
	public static void setCertificate() {

		HttpsURLConnection
				.setDefaultSSLSocketFactory(getTrustedFactory());

		// Create all-trusting host name verifier
		HostnameVerifier allHostsValid = new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		};
		// Install the all-trusting host verifier
		HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		/*
		 * end of the fix
		 */
	}

	public static SSLSocketFactory getTrustedFactory()
			throws HttpRequestException {
		if (TRUSTED_FACTORY == null) {
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}

				public void checkClientTrusted(X509Certificate[] chain,
						String authType) {
					// Intentionally left blank
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) {
					// Intentionally left blank
				}
			} };
			try {
				SSLContext context = SSLContext.getInstance("TLS");
				context.init(null, trustAllCerts, new SecureRandom());
				TRUSTED_FACTORY = context.getSocketFactory();
			} catch (GeneralSecurityException e) {
				IOException ioException = new IOException(
						"Security exception configuring SSL context");
				ioException.initCause(e);
				throw new HttpRequestException(ioException);
			}
		}

		return TRUSTED_FACTORY;
	}

	public static HostnameVerifier getTrustedVerifier() {
		if (TRUSTED_VERIFIER == null)
			TRUSTED_VERIFIER = new HostnameVerifier() {

				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

		return TRUSTED_VERIFIER;
	}

}
