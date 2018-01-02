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
/*
 * Created on Mar 5, 2005
 */
package org.loboevolution.request;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.loboevolution.clientlet.Clientlet;
import org.loboevolution.clientlet.ClientletAccess;
import org.loboevolution.clientlet.ClientletContext;
import org.loboevolution.clientlet.ClientletException;
import org.loboevolution.clientlet.ClientletRequest;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.clientlet.ComponentContent;
import org.loboevolution.context.ClientletContextImpl;
import org.loboevolution.context.ClientletFactory;
import org.loboevolution.gui.FramePanel;
import org.loboevolution.gui.WindowCallback;
import org.loboevolution.security.LocalSecurityManager;
import org.loboevolution.ua.NavigatorProgressEvent;
import org.loboevolution.ua.ProgressType;
import org.loboevolution.util.EventDispatch;
import org.loboevolution.util.Urls;

/**
 * The Class ClientletRequestHandler.
 *
 * @author J. H. S.
 */
public class ClientletRequestHandler extends AbstractRequestHandler {

	/** The window callback. */
	private final WindowCallback windowCallback;

	/** The frame. */
	private final FramePanel frame;
	
	/** The window properties. */
	private volatile Properties windowProperties = null;

	/**
	 * For progress events, but a null event is also fired when the content is
	 * set.
	 */
	public final EventDispatch evtProgress = new EventDispatch();

	/**
	 * Instantiates a new clientlet request handler.
	 *
	 * @param request
	 *            the request
	 * @param clientletUI
	 *            the clientlet ui
	 * @param frame
	 *            the frame
	 */
	public ClientletRequestHandler(ClientletRequest request, WindowCallback clientletUI, FramePanel frame) {
		super(request, frame.getComponent());
		this.windowCallback = clientletUI;
		this.frame = frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sourceforge.xamj.http.RequestHandler#handleException(Exception )
	 */
	@Override
	public boolean handleException(ClientletResponse response, Throwable exception) throws ClientletException {
		if (this.windowCallback != null) {
			this.windowCallback.handleError(this.frame, response, exception);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the context window properties.
	 *
	 * @return the context window properties
	 */
	public Properties getContextWindowProperties() {
		return this.windowProperties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sourceforge.xamj.http.RequestHandler#processResponse(org.xamjwg.dom
	 * .ClientletResponse)
	 */
	@Override
	public void processResponse(final ClientletResponse response) throws ClientletException, IOException {
		if (this.windowCallback != null) {
			this.windowCallback.handleDocumentAccess(this.frame, response);
		}
		Clientlet clientlet = ClientletFactory.getInstance().getClientlet(this.getRequest(), response);
		if (clientlet == null) {
			throw new ClientletException("Unable to find clientlet for response: " + response + ".");
		}
		this.frame.setProgressEvent(null);
		ClientletContext ctx = new ClientletContextImpl(this.frame, this.request, response) {
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
		ThreadGroup prevThreadGroup = LocalSecurityManager.getCurrentThreadGroup();
		// TODO: Thread group needs to be thought through. It's retained in
		// memory, and we need to return the right one in the GUI thread as
		// well.
		ThreadGroup newThreadGroup = null; // new
		// org.loboevolution.context.ClientletThreadGroupImpl("CTG-"
		// +
		// ctx.getResponse().getResponseURL().getHost(),
		// ctx);
		LocalSecurityManager.setCurrentThreadGroup(newThreadGroup);
		// Set context class loader because the extension was likely
		// compiled to require extension libraries.
		Thread currentThread = Thread.currentThread();
		ClassLoader prevClassLoader = currentThread.getContextClassLoader();
		currentThread.setContextClassLoader(clientlet.getClass().getClassLoader());
		try {
			clientlet.process(ctx);
		} finally {
			currentThread.setContextClassLoader(prevClassLoader);
			LocalSecurityManager.setCurrentThreadGroup(prevThreadGroup);
			ClientletAccess.setCurrentClientletContext(prevCtx);
		}
		this.frame.informResponseProcessed(response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.request.AbstractRequestHandler#handleProgress(org.
	 * loboevolution .ua.ProgressType, java.net.URL, java.lang.String, int, int)
	 */
	@Override
	public void handleProgress(ProgressType progressType, URL url, String method, int value, int max) {
		NavigatorProgressEvent event = new NavigatorProgressEvent(this, this.frame, progressType, url, method, value,
				max);
		this.evtProgress.fireEvent(event);
		this.frame.setProgressEvent(event);
	}

	/**
	 * Gets the progress message.
	 *
	 * @param progressType
	 *            the progress type
	 * @param url
	 *            the url
	 * @return the progress message
	 */
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
