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
/*
 * Created on Mar 5, 2005
 */
package org.lobobrowser.request;

import java.io.IOException;
import java.net.URL;

import org.lobobrowser.clientlet.Clientlet;
import org.lobobrowser.clientlet.ClientletAccess;
import org.lobobrowser.clientlet.ClientletContext;
import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletRequest;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.context.ClientletContextImpl;
import org.lobobrowser.context.ClientletFactory;
import org.lobobrowser.gui.FramePanel;
import org.lobobrowser.gui.WindowCallback;
import org.lobobrowser.security.LocalSecurityManager;
import org.lobobrowser.ua.NavigatorProgressEvent;
import org.lobobrowser.ua.ProgressType;
import org.lobobrowser.util.EventDispatch;
import org.lobobrowser.util.Urls;

/**
 * @author J. H. S.
 */
public class ClientletRequestHandler extends AbstractRequestHandler {
	private final WindowCallback windowCallback;
	private final FramePanel frame;

	/**
	 * For progress events, but a null event is also fired when the content is
	 * set.
	 */
	public final EventDispatch evtProgress = new EventDispatch();

	public ClientletRequestHandler(ClientletRequest request,
			WindowCallback clientletUI, FramePanel frame) {
		super(request, frame.getComponent());
		this.windowCallback = clientletUI;
		this.frame = frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.http.RequestHandler#handleException(java.lang.Exception
	 * )
	 */
	public boolean handleException(ClientletResponse response,
			Throwable exception) throws ClientletException {
		if (this.windowCallback != null) {
			this.windowCallback.handleError(this.frame, response, exception);
			return true;
		} else {
			return false;
		}
	}

	private volatile java.util.Properties windowProperties = null;

	public java.util.Properties getContextWindowProperties() {
		return this.windowProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.http.RequestHandler#processResponse(org.xamjwg.dom
	 * .ClientletResponse)
	 */
	public void processResponse(final ClientletResponse response)
			throws ClientletException, IOException {
		if (this.windowCallback != null) {
			this.windowCallback.handleDocumentAccess(this.frame, response);
		}
		Clientlet clientlet = ClientletFactory.getInstance().getClientlet(
				this.getRequest(), response);
		if (clientlet == null) {
			throw new ClientletException(
					"Unable to find clientlet for response: " + response + ".");
		}
		this.frame.setProgressEvent(null);
		ClientletContext ctx = new ClientletContextImpl(this.frame,
				this.request, response) {
			@Override
			public void setResultingContent(ComponentContent content) {
				// Frame content should be replaced as
				// soon as this method is called to allow
				// for incremental rendering.
				super.setResultingContent(content);
				windowProperties = this.getOverriddingWindowProperties();
				// Replace content before firing progress
				// event to avoid window flickering.

				frame.replaceContent(response, content);
				evtProgress.fireEvent(null);
			}
		};
		ClientletContext prevCtx = ClientletAccess.getCurrentClientletContext();
		ClientletAccess.setCurrentClientletContext(ctx);
		ThreadGroup prevThreadGroup = LocalSecurityManager
				.getCurrentThreadGroup();
		// TODO: Thread group needs to be thought through. It's retained in
		// memory, and we need to return the right one in the GUI thread as
		// well.
		ThreadGroup newThreadGroup = null; // new
											// org.lobobrowser.context.ClientletThreadGroupImpl("CTG-"
											// +
											// ctx.getResponse().getResponseURL().getHost(),
											// ctx);
		LocalSecurityManager.setCurrentThreadGroup(newThreadGroup);
		// Set context class loader because the extension was likely
		// compiled to require extension libraries.
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		currentThread.setContextClassLoader(clientlet.getClass()
				.getClassLoader());
		try {
			clientlet.process(ctx);
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
			LocalSecurityManager.setCurrentThreadGroup(prevThreadGroup);
			ClientletAccess.setCurrentClientletContext(prevCtx);
		}
		this.frame.informResponseProcessed(response);
	}

	public void handleProgress(ProgressType progressType, URL url,
			String method, int value, int max) {
		NavigatorProgressEvent event = new NavigatorProgressEvent(this,
				this.frame, progressType, url, method, value, max);
		this.evtProgress.fireEvent(event);
		this.frame.setProgressEvent(event);
	}

	public static String getProgressMessage(ProgressType progressType, URL url) {
		String urlText = url == null ? "[null]" : Urls.getNoRefForm(url);
		switch (progressType) {
		case CONNECTING:
			String host = url.getHost();
			if (host == null || "".equals(host)) {
				return "Opening " + urlText;
			} else {
				return "Contacting " + host;
			}
		case SENDING:
			return "Sending data to " + urlText;
		case WAITING_FOR_RESPONSE:
			return "Waiting on " + urlText;
		case CONTENT_LOADING:
			return "Loading " + urlText;
		case BUILDING:
			return "Building " + urlText;
		case DONE:
			return "Processed " + urlText;
		default:
			return "[?]" + urlText;
		}
	}
}
