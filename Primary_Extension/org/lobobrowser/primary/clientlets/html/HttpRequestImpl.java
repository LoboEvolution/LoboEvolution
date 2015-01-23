/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.clientlets.html;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import org.lobobrowser.context.NetworkRequestImpl;
import org.lobobrowser.html.HttpRequest;
import org.lobobrowser.html.ReadyStateChangeListener;
import org.lobobrowser.http.Header;
import org.lobobrowser.ua.NetworkRequest;
import org.lobobrowser.ua.NetworkRequestEvent;
import org.lobobrowser.ua.NetworkRequestListener;
import org.w3c.dom.Document;

public class HttpRequestImpl implements HttpRequest {
	private final NetworkRequest networkRequest;

	public HttpRequestImpl(final NetworkRequest networkRequest) {
		super();
		this.networkRequest = networkRequest;
	}

	public void abort() {
		networkRequest.abort();
	}

	public String getAllResponseHeaders() {
		return networkRequest.getAllResponseHeaders();
	}

	public int getReadyState() {
		return networkRequest.getReadyState();
	}

	public byte[] getResponseBytes() {
		return networkRequest.getResponseBytes();
	}

	public String getResponseHeader(String headerName) {
		return networkRequest.getResponseHeader(headerName);
	}

	public Image getResponseImage() {
		return networkRequest.getResponseImage();
	}

	public String getResponseText() {
		return networkRequest.getResponseText();
	}

	public Document getResponseXML() {
		return networkRequest.getResponseXML();
	}

	public int getStatus() {
		return networkRequest.getStatus();
	}

	public String getStatusText() {
		return networkRequest.getStatusText();
	}

	public void open(String method, String url, boolean asyncFlag)
			throws IOException {
		networkRequest.open(method, url, asyncFlag);
	}

	public void open(String method, String url) throws IOException {
		networkRequest.open(method, url);
	}

	public void open(String method, URL url, boolean asyncFlag,
			String userName, String password) throws IOException {
		networkRequest.open(method, url, asyncFlag, userName, password);
	}

	public void open(String method, URL url, boolean asyncFlag, String userName)
			throws IOException {
		networkRequest.open(method, url, asyncFlag, userName);
	}

	public void open(String method, URL url, boolean asyncFlag)
			throws IOException {
		networkRequest.open(method, url, asyncFlag);
	}

	public void open(String method, URL url) throws IOException {
		networkRequest.open(method, url);
	}

	public void send(String content) throws IOException {
		networkRequest.send(content);
	}

	public void addReadyStateChangeListener(
			final ReadyStateChangeListener listener) {
		networkRequest.addNetworkRequestListener(new NetworkRequestListener() {
			public void readyStateChanged(NetworkRequestEvent event) {
				listener.readyStateChanged();
			}
		});
	}

	@Override
	public void setRequestHeader(String header, String value) {
		
		NetworkRequestImpl nr = new NetworkRequestImpl();
		
		if (getReadyState() != HttpRequest.STATE_LOADING) {
            throw new IllegalStateException("The AsyncHttpRequest must be opened prior to " +
                    "setting a request header");
        }
        
        if (header == null || value == null) {
            throw new IllegalArgumentException("Neither the header, nor value, may be null");
        }
        
        if (header.equalsIgnoreCase("Accept-Charset") ||
            header.equalsIgnoreCase("Accept-Encoding") ||
            header.equalsIgnoreCase("Content-Length") ||
            header.equalsIgnoreCase("Expect") ||
            header.equalsIgnoreCase("Date") ||
            header.equalsIgnoreCase("Host") ||
            header.equalsIgnoreCase("Keep-Alive") ||
            header.equalsIgnoreCase("Referer") ||
            header.equalsIgnoreCase("TE") ||
            header.equalsIgnoreCase("Trailer") ||
            header.equalsIgnoreCase("Transfer-Encoding") ||
            header.equalsIgnoreCase("Upgrade")) {
            
            return;
        }
        
        if (header.equalsIgnoreCase("Authorization") ||
            header.equalsIgnoreCase("Content-Base") ||
            header.equalsIgnoreCase("Content-Location") ||
            header.equalsIgnoreCase("Content-MD5") ||
            header.equalsIgnoreCase("Content-Range") ||
            header.equalsIgnoreCase("Content-Type") ||
            header.equalsIgnoreCase("Content-Version") ||
            header.equalsIgnoreCase("Delta-Base") ||
            header.equalsIgnoreCase("Depth") ||
            header.equalsIgnoreCase("Destination") ||
            header.equalsIgnoreCase("ETag") ||
            header.equalsIgnoreCase("Expect") ||
            header.equalsIgnoreCase("From") ||
            header.equalsIgnoreCase("If-Modified-Since") ||
            header.equalsIgnoreCase("If-Range") ||
            header.equalsIgnoreCase("If-Unmodified-Since") ||
            header.equalsIgnoreCase("Max-Forwards") ||
            header.equalsIgnoreCase("MIME-Version") ||
            header.equalsIgnoreCase("Overwrite") ||
            header.equalsIgnoreCase("Proxy-Authorization") ||
            header.equalsIgnoreCase("SOAPAction") ||
            header.equalsIgnoreCase("Timeout")) {
        	
        	//replace the current header, if any
            for (Header h : nr.getReq().getHeaders()) {
                if (h.getName().equalsIgnoreCase(header)) {
                	nr.getReq().removeHeader(h);
                    nr.getReq().setHeader(new Header(header, value));
                    break;
                }
            }
        } else {
            //append the value to the header, if one is already specified. Else,
            //just add it as a new header
        	
            boolean appended = false;
            for (Header h : nr.getReq().getHeaders()) {
                if (h.getName().equalsIgnoreCase(header)) {
                	nr.getReq().removeHeader(h);
                	nr.getReq().setHeader(new Header(header, h.getValue() + ", " + value));
                    appended = true;
                    break;
                }
            }
            if (!appended) {
            	nr.getReq().setHeader(new Header(header, value));
            }
        }
    }
}
