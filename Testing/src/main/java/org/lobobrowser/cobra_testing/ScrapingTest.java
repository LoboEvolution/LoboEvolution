/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.cobra_testing;

import java.awt.Cursor;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.lobobrowser.html.AbstractHtmlRendererContext;
import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLFormElementImpl;
import org.lobobrowser.html.domimpl.HTMLInputElementImpl;
import org.lobobrowser.html.parser.DocumentBuilderImpl;
import org.lobobrowser.html.parser.InputSourceImpl;
import org.lobobrowser.html.test.SimpleUserAgentContext;
import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * The Class ScrapingTest.
 */
public class ScrapingTest {
	// Note: This code makes assumptions about the
	// implementation of a website. It might need
	// to be modified to work in the future.

	/** The Constant START_LOCATION. */
	private static final String START_LOCATION = "http://metacrawler.com";

	/** The Constant SEARCH_PHRASE. */
	private static final String SEARCH_PHRASE = "java";

	/** The Constant TEXT_FIELD_ID. */
	private static final String TEXT_FIELD_ID = "icePage$SearchBoxTop$qkw";

	/** The Constant SEARCH_FORM_XPATH. */
	private static final String SEARCH_FORM_XPATH = "//form[@id='aspnetForm']";

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(ScrapingTest.class.getName());

	public static void main(String[] args) throws Exception {
		UserAgentContext uacontext = new LocalUserAgentContext();
		LocalHtmlRendererContext rcontext = new LocalHtmlRendererContext(uacontext);

		// First, we navigate to the starting location.
		rcontext.navigate(START_LOCATION);

		// Next, we search for a form in the resulting
		// document.
		HTMLDocumentImpl startingDoc = rcontext.getCurrentDocument();
		if (startingDoc == null) {
			throw new IllegalStateException("No document available for startup location.");
		}
		XPath xpath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xpath.evaluate(SEARCH_FORM_XPATH, startingDoc, XPathConstants.NODESET);
		if (nodeList.getLength() == 0) {
			throw new IllegalStateException("Expected search form not found in the page.");
		}
		HTMLFormElementImpl form = (HTMLFormElementImpl) nodeList.item(0);

		// We now look for the text field where
		// the search phrase goes.
		HTMLInputElementImpl textInput = (HTMLInputElementImpl) startingDoc.getElementById(TEXT_FIELD_ID);
		if (textInput == null) {
			throw new IllegalStateException("Did not find a text field named '" + TEXT_FIELD_ID + "'.");
		}
		textInput.setValue(SEARCH_PHRASE);

		// We submit the form as if the "submit" image button
		// had been pressed. We expect to get a new document as a result.
		FormInput submitInput1 = new FormInput("icePage$SearchBoxTop$qkwsubmit.x", "1");
		FormInput submitInput2 = new FormInput("icePage$SearchBoxTop$qkwsubmit.y", "1");
		form.submit(new FormInput[] { submitInput1, submitInput2 });
		HTMLDocumentImpl searchResultsDoc = rcontext.getCurrentDocument();
		if (searchResultsDoc == null) {
			throw new IllegalStateException("No document available for search results page.");
		}

		// Finally, we print out the search results.
		NodeList resultList = (NodeList) xpath.evaluate("//a[@class='resultsLink']", searchResultsDoc,
				XPathConstants.NODESET);
		int length = resultList.getLength();
		System.out.println(length + " results found.");
		for (int i = 0; i < length; i++) {
			Element element = (Element) resultList.item(i);
			System.out.println((i + 1) + ". " + element.getTextContent());
			System.out.println("   [" + element.getAttribute("href") + "]");
		}
	}

	/**
	 * The Class LocalUserAgentContext.
	 */
	private static class LocalUserAgentContext extends SimpleUserAgentContext {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.html.test.SimpleUserAgentContext#isScriptingEnabled()
		 */
		@Override
		public boolean isScriptingEnabled() {
			// We don't need Javascript for this.
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.html.test.SimpleUserAgentContext#isExternalCSSEnabled
		 * ()
		 */
		@Override
		public boolean isExternalCSSEnabled() {
			// We don't need to load remote CSS documents.
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.lobobrowser.html.test.SimpleUserAgentContext#getUserAgent()
		 */
		@Override
		public String getUserAgent() {
			return "Mozilla/4.0 (compatible; MSIE 6.0;) Cobra/ScrapingTest Parser Demo";
		}
	}

	/**
	 * The Class LocalHtmlRendererContext.
	 */
	private static class LocalHtmlRendererContext extends AbstractHtmlRendererContext {
		// We need a renderer context to do form submission,
		// but we don't need to extend SimpleHtmlRendererContext
		// which is a GUI-based context. This simple
		// implementation should be enough.
		/** The uacontext. */
		private final UserAgentContext uacontext;

		/** The document. */
		private HTMLDocumentImpl document;

		public LocalHtmlRendererContext(final UserAgentContext uacontext) {
			this.uacontext = uacontext;
		}

		/**
		 * Gets the current document.
		 *
		 * @return the current document
		 */
		public HTMLDocumentImpl getCurrentDocument() {
			// This field is set by the local submitForm()
			// implementation.
			return this.document;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.lobobrowser.html.AbstractHtmlRendererContext#
		 * isImageLoadingEnabled()
		 */
		@Override
		public boolean isImageLoadingEnabled() {
			// We don't need to load images.
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.lobobrowser.html.AbstractHtmlRendererContext#getUserAgentContext(
		 * )
		 */
		@Override
		public UserAgentContext getUserAgentContext() {
			// For consistency, this should return the
			// same UserAgentContext that was used to
			// parse the document.
			return this.uacontext;
		}

		public void navigate(String urlOrPath) {
			try {
				// We implement a convenience navigate() method
				// that is based on submitForm().
				URL url = org.lobobrowser.util.Urls.guessURL(urlOrPath);
				this.submitForm("GET", url, "_this", null, null);
			} catch (java.net.MalformedURLException mfu) {
				logger.log(Level.WARNING, "navigate()", mfu);
			}
		}

		@Override
		public void submitForm(String method, URL action, String target, String enctype, FormInput[] formInputs) {
			// This is the code that does form submission.
			try {
				final String actualMethod = method.toUpperCase();
				URL resolvedURL;
				if ("GET".equals(actualMethod) && formInputs != null) {
					boolean firstParam = true;
					StringBuffer newUrlBuffer = new StringBuffer(action.toExternalForm());
					if (action.getQuery() == null) {
						newUrlBuffer.append("?");
					} else {
						newUrlBuffer.append("&");
					}
					for (int i = 0; i < formInputs.length; i++) {
						FormInput parameter = formInputs[i];
						String name = parameter.getName();
						String encName = URLEncoder.encode(name, "UTF-8");
						if (parameter.isText()) {
							if (firstParam) {
								firstParam = false;
							} else {
								newUrlBuffer.append("&");
							}
							String valueStr = parameter.getTextValue();
							String encValue = URLEncoder.encode(valueStr, "UTF-8");
							newUrlBuffer.append(encName);
							newUrlBuffer.append("=");
							newUrlBuffer.append(encValue);
						}
					}
					resolvedURL = new java.net.URL(newUrlBuffer.toString());
				} else {
					resolvedURL = action;
				}
				URL urlForLoading;
				if (resolvedURL.getProtocol().equals("file")) {
					// Remove query so it works.
					try {
						String ref = action.getRef();
						String refText = ref == null || ref.length() == 0 ? "" : "#" + ref;
						urlForLoading = new URL(resolvedURL.getProtocol(), action.getHost(), action.getPort(),
								action.getPath() + refText);
					} catch (java.net.MalformedURLException throwable) {
						urlForLoading = action;
					}
				} else {
					urlForLoading = resolvedURL;
				}
				// Using potentially different URL for loading.
				boolean isPost = "POST".equals(actualMethod);
				URLConnection connection = urlForLoading.openConnection();
				connection.setRequestProperty("User-Agent", getUserAgentContext().getUserAgent());
				connection.setRequestProperty("Cookie", "");
				HttpURLConnection hc = null;
				if (connection instanceof HttpURLConnection) {
					hc = (HttpURLConnection) connection;
					hc.setRequestMethod(actualMethod);
					// Do not follow redirects
					hc.setInstanceFollowRedirects(false);
				}
				if (isPost) {
					connection.setDoOutput(true);
					ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
					boolean firstParam = true;
					if (formInputs != null) {
						for (int i = 0; i < formInputs.length; i++) {
							FormInput parameter = formInputs[i];
							String name = parameter.getName();
							String encName = URLEncoder.encode(name, "UTF-8");
							if (parameter.isText()) {
								if (firstParam) {
									firstParam = false;
								} else {
									bufOut.write((byte) '&');
								}
								String valueStr = parameter.getTextValue();
								String encValue = URLEncoder.encode(valueStr, "UTF-8");
								bufOut.write(encName.getBytes("UTF-8"));
								bufOut.write((byte) '=');
								bufOut.write(encValue.getBytes("UTF-8"));
							}
						}
					}
					byte[] postContent = bufOut.toByteArray();
					if (connection instanceof HttpURLConnection) {
						((HttpURLConnection) connection).setFixedLengthStreamingMode(postContent.length);
					}
					connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					OutputStream postOut = connection.getOutputStream();
					postOut.write(postContent);
					postOut.flush();
				}
				if (hc != null) {
					// We handle redirects.
					int responseCode = hc.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_MOVED_PERM
							|| responseCode == HttpURLConnection.HTTP_MOVED_TEMP
							|| responseCode == HttpURLConnection.HTTP_SEE_OTHER) {
						String location = hc.getHeaderField("Location");
						URL newAction = new URL(action, location);
						// Recurse
						this.submitForm("GET", newAction, target, null, null);
						return;
					}
				}
				InputStream in = connection.getInputStream();
				try {
					InputStream bin = new BufferedInputStream(in, 8192);
					String actualURI = urlForLoading.toExternalForm();
					// Note that DocumentBuilderImpl needs to be
					// constructed by passing both a UserAgentContext
					// and an HtmlRendererContext in this case, so
					// that form.submit() can take effect.
					DocumentBuilderImpl builder = new DocumentBuilderImpl(this.uacontext, this);
					String charset = org.lobobrowser.util.Urls.getCharset(connection);
					InputSourceImpl is = new InputSourceImpl(bin, actualURI, charset);
					this.document = (HTMLDocumentImpl) builder.parse(is);
				} finally {
					in.close();
				}
			} catch (Exception err) {
				this.document = null;
				logger.log(Level.WARNING, "submitForm()", err);
			}
		}

		@Override
		public void setCursor(Optional<Cursor> cursorOpt) {
			// TODO Auto-generated method stub

		}

	}
}
