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
 * Created on May 14, 2005
 */
package org.loboevolution.primary.clientlets.html;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.clientlet.Clientlet;
import org.loboevolution.clientlet.ClientletContext;
import org.loboevolution.clientlet.ClientletException;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.dombl.DocumentNotificationAdapter;
import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.Urls;
import org.loboevolution.primary.info.RefreshInfo;
import org.loboevolution.ua.NavigatorFrame;
import org.loboevolution.ua.RequestType;
import org.loboevolution.util.Strings;
import org.loboevolution.util.io.RecordedInputStream;
import org.loboevolution.w3c.html.HTMLElement;
import org.w3c.dom.Element;

/**
 * The Class HtmlClientlet.
 *
 * @author J. H. S.
 */
public class HtmlClientlet implements Clientlet {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HtmlClientlet.class);

	/** The Constant NON_VISIBLE_ELEMENTS. */
	private static final Set<String> NON_VISIBLE_ELEMENTS = new HashSet<String>();

	// Maximum buffer size required to determine if a reload due
	// to Http-Equiv is necessary.
	/** The Constant MAX_IS_BUFFER_SIZE. */
	private static final int MAX_IS_BUFFER_SIZE = 1024 * 100;

	static {
		// Elements that may be encountered and which
		// by themselves don't warrant rendering the page yet.
		Set<String> nve = NON_VISIBLE_ELEMENTS;
		nve.add("html");
		nve.add("body");
		nve.add("head");
		nve.add("title");
		nve.add("meta");
		nve.add("script");
		nve.add("style");
		nve.add("link");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.clientlet.Clientlet#parse(org.xamjwg.dom.XDocument)
	 */
	@Override
	public void process(ClientletContext cc) throws ClientletException {
		this.processImpl(cc, null, null);
	}

	/**
	 * Process impl.
	 *
	 * @param cc
	 *            the cc
	 * @param httpEquivData
	 *            the http equiv data
	 * @param rin
	 *            the rin
	 * @throws ClientletException
	 *             the clientlet exception
	 */
	private void processImpl(final ClientletContext cc, Map<String, String> httpEquivData, RecordedInputStream rin)
			throws ClientletException {
		// This method may be executed twice, depending on http-equiv meta
		// elements.
		try {
			ClientletResponse response = cc.getResponse();
			boolean charsetProvided = response.isCharsetProvided();
			String contentLanguage = response.getHeader("Content-Language");
			Set<Locale> locales = contentLanguage == null ? null : this.extractLocales(contentLanguage);
			RefreshInfo refresh = null;
			Iterator hi = response.getHeaderNames();
			// TODO: What is the behavior if you have
			// a Refresh header and also a Refresh HTTP-EQUIV?
			while (hi.hasNext()) {
				String headerName = (String) hi.next();
				String[] headerValues = response.getHeaders(headerName);
				if (headerValues != null && headerValues.length > 0 && "refresh".equalsIgnoreCase(headerName)) {
					refresh = this.extractRefresh(headerValues[headerValues.length - 1]);
				}
				
			}
			String httpEquivCharset = null;
			if (httpEquivData != null) {
				Iterator<Map.Entry<String, String>> i = httpEquivData.entrySet().iterator();
				while (i.hasNext()) {
					Map.Entry<String, String> entry = i.next();
					String httpEquiv = entry.getKey();
					String content = entry.getValue();
					if (content != null) {
						if ("content-type".equalsIgnoreCase(httpEquiv)) {
							httpEquivCharset = this.extractCharset(content);
						} else if ("refresh".equalsIgnoreCase(httpEquiv)) {
							refresh = this.extractRefresh(content);
						} else if ("content-language".equalsIgnoreCase(httpEquiv)) {
							locales = this.extractLocales(content);
						}
					}
				}
			}
			HtmlRendererContextImpl rcontext = HtmlRendererContextImpl.getHtmlRendererContext(cc.getNavigatorFrame());
			DocumentBuilderImpl builder = new DocumentBuilderImpl(rcontext.getUserAgentContext(), rcontext);
			if (rin == null) {
				InputStream in = response.getInputStream();
				rin = in instanceof RecordedInputStream ? (RecordedInputStream) in
						: new RecordedInputStream(in, MAX_IS_BUFFER_SIZE);
				rin.mark(Short.MAX_VALUE);
			} else {
				rin.reset();
			}
			URL responseURL = response.getResponseURL();
			String uri = responseURL.toExternalForm();
			String charset;
			if (!charsetProvided) {
				charset = httpEquivCharset;
			} else {
				// See bug # 2051468. A charset provided
				// in headers takes precendence.
				charset = response.getCharset();
			}
			if (charset == null) {
				charset = "UTF-8";
			}

			InputSourceImpl is = new InputSourceImpl(rin, uri, charset);
			HTMLDocumentImpl document = (HTMLDocumentImpl) builder.createDocument(is);
			document.setLocales(locales);
			String referrer = cc.getRequest().getReferrer();
			document.setReferrer(referrer == null ? "" : referrer);
			HtmlPanel panel = rcontext.getHtmlPanel();
			// Create a listener that will switch to rendering when appropriate.
			final HtmlContent content = new HtmlContent(document, panel, rin, charset);
			LocalDocumentNotificationListener listener = new LocalDocumentNotificationListener(document, panel,
					rcontext, cc, content, httpEquivData == null);
			document.addDocumentNotificationListener(listener);
			// Set resulting content before parsing
			// to enable incremental rendering.
			// The load() call starts parsing.
			try {
				document.load(false);
			} catch (HttpEquivRetryException retry) {
				this.processImpl(cc, retry.getHttpEquivData(), rin);
				return;
			}

			// We're done parsing, but let's make sure
			// the listener actually renderered the document.
			listener.ensureSwitchedToRendering();
			// Scroll to see anchor.
			String ref = responseURL.getRef();
			if (!Strings.isBlank(ref)) {
				panel.scrollToElement(ref);
			}
			if (refresh != null) {
				String destUri = refresh.getDestinationUrl();
				URL currentURL = response.getResponseURL();
				URL destURL;
				if (destUri == null) {
					destURL = currentURL;
				} else {
					destURL = Urls.createURL(currentURL, destUri);
				}
				final URL finalURL = destURL;
				java.awt.event.ActionListener action = e -> {
					NavigatorFrame frame = cc.getNavigatorFrame();
					if (frame.getComponentContent() == content) {
						// Navigate only if the original document is there.
						// TODO: Address bar shouldn't change if it's being
						// edited.
						// TODO: A nagivation action should cancel this
						// altogether.
						frame.navigate(finalURL, RequestType.PROGRAMMATIC);
					}
				};
				int waitMillis = refresh.getWaitSeconds() * 1000;
				if (waitMillis <= 0) {
					waitMillis = 1;
				}
				javax.swing.Timer timer = new javax.swing.Timer(waitMillis, action);
				timer.setRepeats(false);
				timer.start();
			}
		} catch (Exception err) {
			throw new ClientletException(err);
		}
	}

	/**
	 * Extract charset.
	 *
	 * @param responseURL
	 *            the response url
	 * @param contentType
	 *            the content type
	 * @return the string
	 */
	private String extractCharset(String contentType) {
		StringTokenizer tok = new StringTokenizer(contentType, ";");
		if (tok.hasMoreTokens()) {
			tok.nextToken();
			while (tok.hasMoreTokens()) {
				String assignment = tok.nextToken().trim();
				int eqIdx = assignment.indexOf('=');
				if (eqIdx != -1) {
					String varName = assignment.substring(0, eqIdx).trim();
					if ("charset".equalsIgnoreCase(varName)) {
						String varValue = assignment.substring(eqIdx + 1);
						return Strings.unquote(varValue.trim());
					}
				}
			}
		}
		return null;
	}

	/**
	 * Extract locales.
	 *
	 * @param contentLanguage
	 *            the content language
	 * @return the sets the
	 */
	private Set<Locale> extractLocales(String contentLanguage) {
		Set<Locale> locales = new HashSet<Locale>(3);
		StringTokenizer tok = new StringTokenizer(contentLanguage, ",");
		while (tok.hasMoreTokens()) {
			String lang = tok.nextToken().trim();
			locales.add(new Locale(lang));
		}
		return locales;
	}

	/**
	 * Extract refresh.
	 *
	 * @param refresh
	 *            the refresh
	 * @return the refresh info
	 */
	private final RefreshInfo extractRefresh(String refresh) {
		String delayText = null;
		String urlText = null;
		StringTokenizer tok = new StringTokenizer(refresh, ";");
		if (tok.hasMoreTokens()) {
			delayText = tok.nextToken().trim();
			while (tok.hasMoreTokens()) {
				String assignment = tok.nextToken().trim();
				int eqIdx = assignment.indexOf('=');
				if (eqIdx != -1) {
					String varName = assignment.substring(0, eqIdx).trim();
					if ("url".equalsIgnoreCase(varName)) {
						String varValue = assignment.substring(eqIdx + 1);
						urlText = Strings.unquote(varValue.trim());
					}
				} else {
					urlText = Strings.unquote(assignment);
				}
			}
		}
		int delay;
		try {
			delay = Integer.parseInt(delayText);
		} catch (NumberFormatException nfe) {
			logger.warn("extractRefresh(): Bad META refresh delay: " + delayText + ".");
			delay = 0;
		}
		return new RefreshInfo(delay, urlText);
	}

	/**
	 * The listener interface for receiving localDocumentNotification events.
	 * The class that is interested in processing a localDocumentNotification
	 * event implements this interface, and the object created with that class
	 * is registered with a component using the component's
	 * <code>addLocalDocumentNotificationListener</code> method. When the
	 * localDocumentNotification event occurs, that object's appropriate method
	 * is invoked.
	 *
	 * @see LocalDocumentNotificationEvent
	 */
	private static class LocalDocumentNotificationListener extends DocumentNotificationAdapter {

		/** The Constant MAX_WAIT. */
		private static final int MAX_WAIT = 7000;

		/** The document. */
		private final HTMLDocumentImpl document;

		/** The html panel. */
		private final HtmlPanel htmlPanel;

		/** The start timestamp. */
		private final long startTimestamp;

		/** The rcontext. */
		private final HtmlRendererContext rcontext;

		/** The ccontext. */
		private final ClientletContext ccontext;

		/** The content. */
		private final HtmlContent content;

		/** The detect http equiv. */
		private final boolean detectHttpEquiv;

		/** The has visible elements. */
		private boolean hasVisibleElements = false;

		/** The has switched to rendering. */
		private boolean hasSwitchedToRendering = false;

		/** The http equiv elements. */
		private Collection<HTMLElement> httpEquivElements;

		/**
		 * Instantiates a new local document notification listener.
		 *
		 * @param doc
		 *            the doc
		 * @param panel
		 *            the panel
		 * @param rcontext
		 *            the rcontext
		 * @param cc
		 *            the cc
		 * @param content
		 *            the content
		 * @param detectHttpEquiv
		 *            the detect http equiv
		 */
		public LocalDocumentNotificationListener(HTMLDocumentImpl doc, HtmlPanel panel, HtmlRendererContext rcontext,
				ClientletContext cc, HtmlContent content, boolean detectHttpEquiv) {
			this.document = doc;
			this.startTimestamp = System.currentTimeMillis();
			this.htmlPanel = panel;
			this.rcontext = rcontext;
			this.ccontext = cc;
			this.content = content;
			this.detectHttpEquiv = detectHttpEquiv;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.loboevolution.html.dombl.DocumentNotificationListener#
		 * externalScriptLoading (org.loboevolution.html.domimpl.DOMNodeImpl)
		 */
		@Override
		public void externalScriptLoading(DOMNodeImpl node) {
			// We can expect this to occur only in the parser thread.
			if (this.hasVisibleElements) {
				this.ensureSwitchedToRendering();
			}
		}

		/**
		 * Adds the http equiv element.
		 *
		 * @param element
		 *            the element
		 */
		private void addHttpEquivElement(HTMLElement element) {
			Collection<HTMLElement> httpEquivElements = this.httpEquivElements;
			if (httpEquivElements == null) {
				httpEquivElements = new LinkedList<HTMLElement>();
				this.httpEquivElements = httpEquivElements;
			}
			httpEquivElements.add(element);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.loboevolution.html.dombl.DocumentNotificationListener#nodeLoaded(
		 * org. loboevolution.html.domimpl.DOMNodeImpl)
		 */
		@Override
		public void nodeLoaded(DOMNodeImpl node) {

			if (this.detectHttpEquiv && node instanceof HTMLElement) {
				HTMLElement element = (HTMLElement) node;
				String tagName = element.getTagName();
				if ("meta".equalsIgnoreCase(tagName)) {
					String httpEquiv = element.getAttribute("http-equiv");
					if (httpEquiv != null) {
						this.addHttpEquivElement(element);
					}
				}
				if ("head".equalsIgnoreCase(tagName) || "script".equalsIgnoreCase(tagName)
						|| "html".equalsIgnoreCase(tagName)) {
					Map<String, String> httpEquiv = this.getHttpEquivData();
					if (httpEquiv != null && !httpEquiv.isEmpty()) {
						throw new HttpEquivRetryException(httpEquiv);
					}
				}
			}

			if (!this.hasVisibleElements && this.mayBeVisibleElement(node)) {
				this.hasVisibleElements = true;

			}
			if (this.hasVisibleElements && System.currentTimeMillis() - this.startTimestamp > MAX_WAIT) {
				this.ensureSwitchedToRendering();
			}
		}

		/**
		 * May be visible element.
		 *
		 * @param node
		 *            the node
		 * @return true, if successful
		 */
		private final boolean mayBeVisibleElement(DOMNodeImpl node) {
			if (node instanceof HTMLElement) {
				HTMLElement element = (HTMLElement) node;
				boolean visible = !NON_VISIBLE_ELEMENTS.contains(element.getTagName().toLowerCase());
				return visible;
			} else {
				return false;
			}
		}

		/**
		 * Ensure switched to rendering.
		 */
		public void ensureSwitchedToRendering() {
			synchronized (this) {
				if (this.hasSwitchedToRendering) {
					return;
				}
				this.hasSwitchedToRendering = true;
			}
			final HTMLDocumentImpl document = this.document;
			document.removeDocumentNotificationListener(this);
			if (SwingUtilities.isEventDispatchThread()) {
				// Should have nicer effect (less flicker) in GUI thread.
				htmlPanel.setDocument(document, rcontext);
				ccontext.setResultingContent(content);
			} else {
				SwingUtilities.invokeLater(() -> {
					// Should have nicer effect (less flicker) in GUI
					// thread.
					htmlPanel.setDocument(document, rcontext);
					ccontext.setResultingContent(content);
				});
			}
		}

		/**
		 * Gets the http equiv data.
		 *
		 * @return the http equiv data
		 */
		private Map<String, String> getHttpEquivData() {
			Collection<HTMLElement> httpEquivElements = this.httpEquivElements;
			if (httpEquivElements == null) {
				return null;
			}
			Map<String, String> httpEquivData = new HashMap<String, String>(0);
			for (Element element : httpEquivElements) {
				String httpEquiv = element.getAttribute("http-equiv");
				if (httpEquiv != null) {
					String content = element.getAttribute("content");
					httpEquivData.put(httpEquiv, content);
				}
			}
			return httpEquivData;
		}
	}

	/**
	 * The Class HttpEquivRetryException.
	 */
	private static class HttpEquivRetryException extends RuntimeException {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The http equiv data. */
		private final Map<String, String> httpEquivData;

		/**
		 * Instantiates a new http equiv retry exception.
		 *
		 * @param httpEquiv
		 *            the http equiv
		 */
		public HttpEquivRetryException(final Map<String, String> httpEquiv) {
			super();
			this.httpEquivData = httpEquiv;
		}

		/**
		 * Gets the http equiv data.
		 *
		 * @return the http equiv data
		 */
		public Map<String, String> getHttpEquivData() {
			return httpEquivData;
		}
	}
}
