/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.style;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.SSLCertificate;
import org.lobobrowser.util.Strings;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.io.IORoutines;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.parser.SACParserCSS3;

/**
 * The Class CSSUtilities.
 */
public class CSSUtilities {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(CSSUtilities.class.getName());

	/**
	 * Instantiates a new CSS utilities.
	 */
	private CSSUtilities() {
	}

	/**
	 * Pre process css.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 */
	public static String preProcessCss(String text) {
		try {
			BufferedReader reader = new BufferedReader(new StringReader(text));
			String line;
			StringBuffer sb = new StringBuffer();
			String pendingLine = null;
			// Only last line should be trimmed.
			while ((line = reader.readLine()) != null) {
				String tline = line.trim();
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
		} catch (IOException ioe) {
			// not possible
			throw new IllegalStateException(ioe.getMessage());
		}
	}

	/**
	 * Gets the css input source for style sheet.
	 *
	 * @param text
	 *            the text
	 * @param scriptURI
	 *            the script uri
	 * @return the css input source for style sheet
	 */
	public static InputSource getCssInputSourceForStyleSheet(String text, String scriptURI) {
		java.io.Reader reader = new StringReader(text);
		InputSource is = new InputSource(reader);
		is.setURI(scriptURI);
		return is;
	}

	/**
	 * @param href
	 * @param href
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	public static CSSStyleSheet parse(String href, HTMLDocumentImpl doc) throws Exception {

		URL url = null;
		CSSOMParser parser = new CSSOMParser(new SACParserCSS3());

		URL baseURL = new URL(doc.getBaseURI());
		URL scriptURL = Urls.createURL(baseURL, href);
		String scriptURI = scriptURL == null ? href : scriptURL.toExternalForm();

		try {
			if (scriptURI.startsWith("//")) {
				scriptURI = "http:" + scriptURI;
			}
			url = new URL(scriptURI);
		} catch (MalformedURLException mfu) {
			int idx = scriptURI.indexOf(':');
			if ((idx == -1) || (idx == 1)) {
				// try file
				url = new URL("file:" + scriptURI);
			} else {
				throw mfu;
			}
		}
		logger.info("process(): Loading URI=[" + scriptURI + "].");
		SSLCertificate.setCertificate();
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);
		connection.setRequestProperty("Cookie", "");
		if (connection instanceof HttpURLConnection) {
			HttpURLConnection hc = (HttpURLConnection) connection;
			hc.setInstanceFollowRedirects(true);
			int responseCode = hc.getResponseCode();
			logger.info("process(): HTTP response code: " + responseCode);
		}
		InputStream in = connection.getInputStream();
		byte[] content;
		try {
			content = IORoutines.load(in, 8192);
		} finally {
			in.close();
		}
		String source = new String(content, "UTF-8");

		InputSource is = getCssInputSourceForStyleSheet(source, doc.getBaseURI());
		return parser.parseStyleSheet(is, null, null);

	}

	/**
	 * Matches media.
	 *
	 * @param mediaValues
	 *            the media values
	 * @param rcontext
	 *            the rcontext
	 * @return true, if successful
	 */
	public static boolean matchesMedia(String mediaValues, UserAgentContext rcontext) {
		if ((mediaValues == null) || (mediaValues.length() == 0)) {
			return true;
		}
		if (rcontext == null) {
			return false;
		}
		StringTokenizer tok = new StringTokenizer(mediaValues, ",");
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken().trim();
			String mediaName = Strings.trimForAlphaNumDash(token);
			if (rcontext.isMedia(mediaName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Matches media.
	 *
	 * @param mediaList
	 *            the media list
	 * @param rcontext
	 *            the rcontext
	 * @return true, if successful
	 */
	public static boolean matchesMedia(MediaList mediaList, UserAgentContext rcontext) {
		if (mediaList == null) {
			return true;
		}
		int length = mediaList.getLength();
		if (length == 0) {
			return true;
		}
		if (rcontext == null) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			String mediaName = mediaList.item(i);
			if (rcontext.isMedia(mediaName)) {
				return true;
			}
		}
		return false;
	}

}
