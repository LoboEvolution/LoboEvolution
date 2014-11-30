/*
 GNU GENERAL PUBLIC LICENSE
 Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public
 License as published by the Free Software Foundation; either
 verion 2 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 General Public License for more details.

 You should have received a copy of the GNU General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

 Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.request;

import java.awt.Component;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.RequestType;

public abstract class AbstractRequestHandler implements RequestHandler {
	protected final ClientletRequest request;
	protected final RequestType requestType;
	private final Component dialogComponent;
	private boolean cancelled = false;

	public AbstractRequestHandler(ClientletRequest request,
			Component dialogComponent) {
		this.request = request;
		this.requestType = request.getRequestType();
		this.dialogComponent = dialogComponent;
	}

	public void cancel() {
		this.cancelled = true;
	}

	public HostnameVerifier getHostnameVerifier() {
		return new LocalHostnameVerifier();
	}

	public String getLatestRequestMethod() {
		return this.request.getMethod();
	}

	public URL getLatestRequestURL() {
		return this.request.getRequestURL();
	}

	public ClientletRequest getRequest() {
		return this.request;
	}

	public abstract boolean handleException(ClientletResponse response,
			Throwable exception) throws ClientletException;

	public abstract void handleProgress(ProgressType progressType, URL url,
			String method, int value, int max);

	public boolean isCancelled() {
		return this.cancelled;
	}

	public boolean isNewNavigationEntry() {
		return false;
	}

	public RequestType getRequestType() {
		return this.requestType;
	}

	public abstract void processResponse(ClientletResponse response)
			throws ClientletException, IOException;

	private class LocalHostnameVerifier implements HostnameVerifier {
		private boolean verified;

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
		 * javax.net.ssl.SSLSession)
		 */
		public boolean verify(final String host, SSLSession arg1) {
			this.verified = false;
			final VerifiedHostsStore vhs = VerifiedHostsStore.getInstance();
			if (vhs.contains(host)) {
				return true;
			}
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					public void run() {
						boolean verified = false;
						Component dc = dialogComponent;
						if (dc != null) {
							int result = JOptionPane
									.showConfirmDialog(
											dc,
											"Host "
													+ host
													+ " does not match SSL certificate or CA not recognized. Proceed anyway?",
											"Security Warning",
											JOptionPane.YES_NO_OPTION);
							verified = result == JOptionPane.YES_OPTION;
							if (verified) {
								vhs.add(host);
							}
						}
						synchronized (LocalHostnameVerifier.this) {
							LocalHostnameVerifier.this.verified = verified;
						}
					}
				});
			} catch (InterruptedException ie) {
				throw new IllegalStateException(ie);
			} catch (InvocationTargetException ite) {
				throw new IllegalStateException(ite.getCause());
			}
			synchronized (this) {
				return this.verified;
			}
		}
	}
}