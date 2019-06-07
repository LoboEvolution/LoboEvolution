/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/

package org.lobobrowser.html.style;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobo.common.Strings;
import org.lobo.common.Urls;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.UserAgentContext;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.InputSource;

public class CSSUtilities {
	private static final Logger logger = Logger.getLogger(CSSUtilities.class.getName());

	public static InputSource getCssInputSourceForStyleSheet(String text, String scriptURI) {
		final java.io.Reader reader = new StringReader(text);
		final InputSource is = new InputSource(reader);
		is.setURI(scriptURI);
		return is;
	}

	public static boolean matchesMedia(MediaList mediaList, UserAgentContext rcontext) {
		if (mediaList == null) {
			return true;
		}
		final int length = mediaList.getLength();
		if (length == 0) {
			return true;
		}
		if (rcontext == null) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			final String mediaName = mediaList.item(i);
			if (rcontext.isMedia(mediaName)) {
				return true;
			}
		}
		return false;
	}

	public static boolean matchesMedia(String mediaValues, UserAgentContext rcontext) {
		if (mediaValues == null || mediaValues.length() == 0) {
			return true;
		}
		if (rcontext == null) {
			return false;
		}
		final StringTokenizer tok = new StringTokenizer(mediaValues, ",");
		while (tok.hasMoreTokens()) {
			final String token = tok.nextToken().trim();
			final String mediaName = Strings.trimForAlphaNumDash(token);
			if (rcontext.isMedia(mediaName)) {
				return true;
			}
		}
		return false;
	}

	public static CSSStyleSheet parse(org.w3c.dom.Node ownerNode, String href, HTMLDocumentImpl doc, String baseUri,
			boolean considerDoubleSlashComments) throws Exception {
		final UserAgentContext bcontext = doc.getUserAgentContext();
		final HttpRequest request = bcontext.createHttpRequest();
		final URL baseURL = new URL(baseUri);
		final URL scriptURL = Urls.createURL(baseURL, href);
		final String scriptURI = scriptURL == null ? href : scriptURL.toExternalForm();
		// Perform a synchronous request
		final SecurityManager sm = System.getSecurityManager();
		if (sm == null) {
			try {
				request.open("GET", scriptURI, false);
				request.send(null);
			} catch (Exception thrown) {
				logger.log(Level.WARNING, "parse()", thrown);
			}

		} else {
			// Code might have restrictions on loading items from elsewhere.
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				try {
					request.open("GET", scriptURI, false);
					request.send(null);
				} catch (Exception thrown) {
					logger.log(Level.WARNING, "parse()", thrown);
				}
				return null;
			});
		}
		final int status = request.getStatus();
		if (status != 200 && status != 0) {
			logger.warning("Unable to parse CSS. URI=[" + scriptURI + "]. Response status was " + status + ".");
			return null;
		}

		final String text = request.getResponseText();
		if (Strings.isNotBlank(text)) {
			final String processedText = considerDoubleSlashComments ? preProcessCss(text) : text;
			final CSSOMParser parser = new CSSOMParser();
			final InputSource is = getCssInputSourceForStyleSheet(processedText, scriptURI);
			is.setURI(scriptURI);
			try {
				final CSSStyleSheetImpl sheet = (CSSStyleSheetImpl) parser.parseStyleSheet(is, null);
				sheet.setHref(scriptURI);
				sheet.setOwnerNode(ownerNode);
				return sheet;
			} catch (final Throwable err) {
				logger.log(Level.WARNING, "Unable to parse CSS. URI=[" + scriptURI + "].", err);
				return null;
			}
		} else {
			return null;
		}
	}

	public static String preProcessCss(String text) {
		try {
			final BufferedReader reader = new BufferedReader(new StringReader(text));
			String line;
			final StringBuffer sb = new StringBuffer();
			String pendingLine = null;
			// Only last line should be trimmed.
			while ((line = reader.readLine()) != null) {
				final String tline = line.trim();
				if (tline.length() != 0) {
					if (pendingLine != null) {
						sb.append(pendingLine);
						sb.append("\r\n");
						pendingLine = null;
					}
					if (tline.startsWith("//")) {
						pendingLine = line;
						continue;
					}
					sb.append(line);
					sb.append("\r\n");
				}
			}
			return sb.toString();
		} catch (final IOException ioe) {
			// not possible
			throw new IllegalStateException(ioe.getMessage());
		}
	}

	private CSSUtilities() {
	}

}
