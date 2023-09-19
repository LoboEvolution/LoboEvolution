/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.style;

import org.htmlunit.cssparser.dom.CSSStyleSheetImpl;
import org.htmlunit.cssparser.dom.MediaListImpl;
import org.htmlunit.cssparser.parser.CSSOMParser;
import org.htmlunit.cssparser.parser.InputSource;
import org.htmlunit.cssparser.parser.javacc.CSS3Parser;
import org.htmlunit.cssparser.parser.selector.SelectorList;
import org.htmlunit.cssparser.util.ThrowCssExceptionErrorHandler;
import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.node.css.MediaQueryList;
import org.loboevolution.html.node.js.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.util.StringTokenizer;

/**
 * <p>CSSUtilities class.</p>
 */
public final class CSSUtilities {

	/**
	 * <p>getCssInputSourceForStyleSheet.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @param scriptURI a {@link java.lang.String} object.
	 * @return a {@link InputSource} object.
	 */
	public static InputSource getCssInputSourceForStyleSheet(String text, String scriptURI) {
		final Reader reader = new StringReader(text);
		final InputSource is = new InputSource(reader);
		is.setURI(scriptURI);
		return is;
	}

	/**
	 * <p>parseMedia.</p>
	 *
	 * @param mediaString a {@link java.lang.String} object.
	 * @return a {@link MediaListImpl} object.
	 * @throws java.lang.Exception if any.
	 */
	public static MediaListImpl parseMedia(String mediaString) throws Exception {
		final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
		return new MediaListImpl(parser.parseMedia(mediaString));
	}

	/**
	 * <p>matchesMedia.</p>
	 *
	 * @param mediaValues a {@link java.lang.String} object.
	 * @param window a {@link org.loboevolution.html.node.js.Window} object.
	 * @return a boolean.
	 */
	public static boolean matchesMedia(String mediaValues, Window window) {
		if (Strings.isBlank(mediaValues)) {
			return true;
		}
		final StringTokenizer tok = new StringTokenizer(mediaValues, ",");
		while (tok.hasMoreTokens()) {
			final String token = tok.nextToken().trim();
			String mediaName = Strings.trimForAlphaNumDash(token);
			mediaName = mediaName.trim();
			if ("screen".equals(mediaName) || "all".equals(mediaName) || "only".equals(mediaName) || "print".equals(mediaName)) {
				try {
					MediaQueryList media = window.matchMedia(mediaValues);
					return media.isMatches();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	/**
	 * <p>parseCssExternal.</p>
	 *
	 * @param config a {@link HtmlRendererConfig} object.
	 * @param href                a {@link String} object.
	 * @param scriptURL           a {@link URL} object.
	 * @param baseURI             a {@link String} object.
	 * @return a {@link CSSStyleSheetImpl} object.
	 * @throws java.lang.Exception if any.
	 */
	public static CSSStyleSheetImpl parseCssExternal(HtmlRendererConfig config, String href, URL scriptURL, String baseURI, boolean test) throws Exception {
		CSSOMParser parser = new CSSOMParser();
		String scriptURI = scriptURL == null ? href : scriptURL.toExternalForm();
		String source = config.getSourceCache(scriptURI, "CSS", test);
		InputSource is = getCssInputSourceForStyleSheet(source, baseURI);
		return parser.parseStyleSheet(is, null);
	}
	
	/**
	 * <p>getSelectorList.</p>
	 *
	 * @param selectors a {@link java.lang.String} object.
	 * @return a {@link SelectorList} object.
	 */
	public static SelectorList getSelectorList(final String selectors) throws Exception {
		final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
		parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
		return parser.parseSelectors(selectors);
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
