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

package org.loboevolution.html.style;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.InputSource;
import com.gargoylesoftware.css.parser.javacc.CSS3Parser;
import com.gargoylesoftware.css.parser.selector.SelectorList;
import org.loboevolution.common.Strings;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.store.ExternalResourcesStore;
import org.w3c.dom.stylesheets.MediaList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>CSSUtilities class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class CSSUtilities {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(CSSUtilities.class.getName());

	/**
	 * <p>getCssInputSourceForStyleSheet.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @param scriptURI a {@link java.lang.String} object.
	 * @return a {@link com.gargoylesoftware.css.parser.InputSource} object.
	 */
	public static InputSource getCssInputSourceForStyleSheet(String text, String scriptURI) {
		final Reader reader = new StringReader(text);
		final InputSource is = new InputSource(reader);
		is.setURI(scriptURI);
		return is;
	}

	/**
	 * <p>matchesMedia.</p>
	 *
	 * @param mediaList a {@link org.w3c.dom.stylesheets.MediaList} object.
	 * @param rcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @return a boolean.
	 */
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

	/**
	 * <p>matchesMedia.</p>
	 *
	 * @param mediaValues a {@link java.lang.String} object.
	 * @param rcontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @return a boolean.
	 */
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
	
	/**
	 * <p>parseCssExternal.</p>
	 *
	 * @param href a {@link java.lang.String} object.
	 * @param scriptURL a {@link java.net.URL} object.
	 * @param baseURI a {@link java.lang.String} object.
	 * @return a {@link com.gargoylesoftware.css.dom.CSSStyleSheetImpl} object.
	 * @throws java.lang.Exception if any.
	 */
	public static CSSStyleSheetImpl parseCssExternal(String href, URL scriptURL, String baseURI) throws Exception {
		CSSOMParser parser = new CSSOMParser();
		String scriptURI = scriptURL == null ? href : scriptURL.toExternalForm();
		String source = ExternalResourcesStore.getSourceCache(scriptURI, "CSS");
		InputSource is = getCssInputSourceForStyleSheet(source, baseURI);
		return parser.parseStyleSheet(is, null);
	}
	
	/**
	 * <p>getSelectorList.</p>
	 *
	 * @param selectors a {@link java.lang.String} object.
	 * @return a {@link com.gargoylesoftware.css.parser.selector.SelectorList} object.
	 */
	public static SelectorList getSelectorList(final String selectors) {
		final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
		SelectorList selectorList = null;
		try {
			selectorList = parser.parseSelectors(selectors);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return selectorList;
	}

	/**
	 * <p>preProcessCss.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String preProcessCss(String text) {
		try {
			final BufferedReader reader = new BufferedReader(new StringReader(text));
			String line;
			final StringBuilder sb = new StringBuilder();
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
