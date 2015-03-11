/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.context;

import java.awt.Component;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.clientlet.ContentBuffer;
import org.lobobrowser.clientlet.SimpleComponentContent;
import org.lobobrowser.io.ManagedStore;
import org.lobobrowser.request.UserAgentImpl;
import org.lobobrowser.store.StorageManager;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.NavigatorProgressEvent;
import org.lobobrowser.ua.NetworkRequest;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.ua.UserAgent;


/**
 * The Class ClientletContextImpl.
 */
public class ClientletContextImpl implements ClientletContext {
	
	/** The frame. */
	private final NavigatorFrame frame;
	
	/** The request. */
	private final ClientletRequest request;
	
	/** The response. */
	private final ClientletResponse response;

	/**
	 * Instantiates a new clientlet context impl.
	 *
	 * @param frame the frame
	 * @param request the request
	 * @param response the response
	 */
	public ClientletContextImpl(NavigatorFrame frame, ClientletRequest request,
			ClientletResponse response) {
		this.frame = frame;
		this.request = request;
		this.response = response;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#createContentBuffer(java.lang.String, byte[])
	 */
	public ContentBuffer createContentBuffer(String contentType, byte[] content) {
		return new VolatileContentImpl(contentType, content);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#createContentBuffer(java.lang.String, java.lang.String, java.lang.String)
	 */
	public ContentBuffer createContentBuffer(String contentType,
			String content, String encoding)
			throws UnsupportedEncodingException {
		byte[] bytes = content.getBytes(encoding);
		return new VolatileContentImpl(contentType, bytes);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getNavigatorFrame()
	 */
	public NavigatorFrame getNavigatorFrame() {
		return this.frame;
	}

	/** The items. */
	private Map<String, Object> items = null;

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getItem(java.lang.String)
	 */
	public Object getItem(String name) {
		synchronized (this) {
			Map items = this.items;
			if (items == null) {
				return null;
			}
			return items.get(name);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getManagedStore()
	 */
	public ManagedStore getManagedStore() throws IOException {
		ClientletResponse response = this.response;
		if (response == null) {
			throw new SecurityException("There is no client response");
		}
		String hostName = response.getResponseURL().getHost();
		return StorageManager.getInstance().getRestrictedStore(hostName, true);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getManagedStore(java.lang.String)
	 */
	public ManagedStore getManagedStore(String hostName) throws IOException {
		return StorageManager.getInstance().getRestrictedStore(hostName, true);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getRequest()
	 */
	public ClientletRequest getRequest() {
		return this.request;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getResponse()
	 */
	public ClientletResponse getResponse() {
		return this.response;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getUserAgent()
	 */
	public UserAgent getUserAgent() {
		return UserAgentImpl.getInstance();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#setItem(java.lang.String, java.lang.Object)
	 */
	public void setItem(String name, Object value) {
		synchronized (this) {
			Map<String, Object> items = this.items;
			if (items == null) {
				items = new HashMap<String, Object>(1);
				this.items = items;
			}
			items.put(name, value);
		}
	}

	/** The resulting content. */
	private volatile ComponentContent resultingContent;

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getResultingContent()
	 */
	public ComponentContent getResultingContent() {
		return this.resultingContent;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#navigate(java.lang.String)
	 */
	public void navigate(String url) throws MalformedURLException {
		URL responseURL = this.response.getResponseURL();
		URL newURL = org.lobobrowser.util.Urls.guessURL(responseURL,
				url);
		this.frame.navigate(newURL);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#setResultingContent(java.awt.Component)
	 */
	public final void setResultingContent(Component content) {
		// Must call other overload, which may be overridden.
		this.setResultingContent(new SimpleComponentContent(content));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#setResultingContent(org.lobobrowser.clientlet.ComponentContent)
	 */
	public void setResultingContent(ComponentContent content) {
		this.resultingContent = content;
	}

	/** The window properties. */
	private volatile Properties windowProperties;

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#overrideWindowProperties(java.util.Properties)
	 */
	public void overrideWindowProperties(Properties properties) {
		this.windowProperties = properties;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getOverriddingWindowProperties()
	 */
	public Properties getOverriddingWindowProperties() {
		return this.windowProperties;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#isResultingContentSet()
	 */
	public boolean isResultingContentSet() {
		return this.resultingContent != null;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#setProgressEvent(org.lobobrowser.ua.ProgressType, int, int)
	 */
	public void setProgressEvent(ProgressType progressType, int value, int max) {
		this.setProgressEvent(progressType, value, max, this.getResponse()
				.getResponseURL());
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#getProgressEvent()
	 */
	public NavigatorProgressEvent getProgressEvent() {
		return this.frame.getProgressEvent();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#setProgressEvent(org.lobobrowser.ua.ProgressType, int, int, java.net.URL)
	 */
	public void setProgressEvent(ProgressType progressType, int value, int max,
			URL url) {
		ClientletResponse response = this.getResponse();
		NavigatorFrame frame = this.getNavigatorFrame();
		String method = response.getLastRequestMethod();
		frame.setProgressEvent(new NavigatorProgressEvent(this, frame,
				progressType, url, method, value, max));
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#setProgressEvent(org.lobobrowser.ua.NavigatorProgressEvent)
	 */
	public void setProgressEvent(NavigatorProgressEvent event) {
		this.getNavigatorFrame().setProgressEvent(event);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#createNetworkRequest()
	 */
	public NetworkRequest createNetworkRequest() {
		return new NetworkRequestImpl();
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#alert(java.lang.String)
	 */
	public void alert(String message) {
		this.getNavigatorFrame().alert(message);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.clientlet.ClientletContext#createNavigatorFrame()
	 */
	public NavigatorFrame createNavigatorFrame() {
		return this.getNavigatorFrame().createFrame();
	}
}
