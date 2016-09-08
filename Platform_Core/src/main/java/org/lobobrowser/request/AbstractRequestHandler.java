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

/**
 * The Class AbstractRequestHandler.
 */
public abstract class AbstractRequestHandler implements RequestHandler {

    /** The request. */
    protected final ClientletRequest request;

    /** The request type. */
    protected final RequestType requestType;

    /** The dialog component. */
    private final Component dialogComponent;

    /** The cancelled. */
    private boolean cancelled = false;

    /**
     * Instantiates a new abstract request handler.
     *
     * @param request
     *            the request
     * @param dialogComponent
     *            the dialog component
     */
    public AbstractRequestHandler(ClientletRequest request,
            Component dialogComponent) {
        this.request = request;
        this.requestType = request.getRequestType();
        this.dialogComponent = dialogComponent;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#cancel()
     */
    @Override
    public void cancel() {
        this.cancelled = true;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#getHostnameVerifier()
     */
    @Override
    public HostnameVerifier getHostnameVerifier() {
        return new LocalHostnameVerifier();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#getLatestRequestMethod()
     */
    @Override
    public String getLatestRequestMethod() {
        return this.request.getMethod();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#getLatestRequestURL()
     */
    @Override
    public URL getLatestRequestURL() {
        return this.request.getRequestURL();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#getRequest()
     */
    @Override
    public ClientletRequest getRequest() {
        return this.request;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.request.RequestHandler#handleException(org.lobobrowser.clientlet
     * .ClientletResponse, java.lang.Throwable)
     */
    @Override
    public abstract boolean handleException(ClientletResponse response,
            Throwable exception) throws ClientletException;

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.request.RequestHandler#handleProgress(org.lobobrowser.ua.
     * ProgressType, java.net.URL, java.lang.String, int, int)
     */
    @Override
    public abstract void handleProgress(ProgressType progressType, URL url,
            String method, int value, int max);

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#isCancelled()
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /** Checks if is new navigation entry.
	 *
	 * @return true, if is new navigation entry
	 */
    public boolean isNewNavigationEntry() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.request.RequestHandler#getRequestType()
     */
    @Override
    public RequestType getRequestType() {
        return this.requestType;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.request.RequestHandler#processResponse(org.lobobrowser.clientlet
     * .ClientletResponse)
     */
    @Override
    public abstract void processResponse(ClientletResponse response)
            throws ClientletException, IOException;

    /**
     * The Class LocalHostnameVerifier.
     */
    private class LocalHostnameVerifier implements HostnameVerifier {

        /** The verified. */
        private boolean verified;

        /*
         * (non-Javadoc)
         * @see javax.net.ssl.HostnameVerifier#verify(String, javax.net.ssl.SSLSession)
         */
        @Override
        public boolean verify(final String host, SSLSession arg1) {
            this.verified = false;
            final VerifiedHostsStore vhs = VerifiedHostsStore.getInstance();
            if (vhs.contains(host)) {
                return true;
            }
            
			if (SwingUtilities.isEventDispatchThread()) {
				boolean verified = false;
				Component dc = dialogComponent;
				if (dc != null) {
					int result = JOptionPane.showConfirmDialog(dc,
							"Host " + host + " does not match SSL certificate or CA not recognized. Proceed anyway?",
							"Security Warning", JOptionPane.YES_NO_OPTION);
					verified = result == JOptionPane.YES_OPTION;
					if (verified) {
						vhs.add(host);
					}
				}
				synchronized (LocalHostnameVerifier.this) {
					LocalHostnameVerifier.this.verified = verified;
				}
			} else {
				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						@Override
						public void run() {
							boolean verified = false;
							Component dc = dialogComponent;
							if (dc != null) {
								int result = JOptionPane.showConfirmDialog(dc,
										"Host " + host
												+ " does not match SSL certificate or CA not recognized. Proceed anyway?",
										"Security Warning", JOptionPane.YES_NO_OPTION);
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
			}
            synchronized (this) {
                return this.verified;
            }
        }
    }
}
