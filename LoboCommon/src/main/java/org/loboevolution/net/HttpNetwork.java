/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

package org.loboevolution.net;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLImageElement;
import org.loboevolution.html.dom.HTMLInputElement;
import org.loboevolution.svg.SVGImageElement;
import org.loboevolution.info.TimingInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

/**
 * <p>HttpNetwork class.</p>
 */
@Slf4j
public class HttpNetwork {

	/** Constant GZIP_ENCODING="gzip" */
	public static final String GZIP_ENCODING = "gzip";
	
	/** Constant TIMEOUT_VALUE="2000" */
	public static final int TIMEOUT_VALUE = 2000;

	/**
	 * <p>getInputStream.</p>
	 *
	 * @param connection a {@link java.net.URLConnection} object.
	 * @return a {@link java.io.InputStream} object.
	 * @throws java.io.IOException if any.
	 */
	public static InputStream getInputStream(final URLConnection connection) throws IOException {
		InputStream in;
		if (connection instanceof HttpURLConnection) {
			in = getGzipStreamError((HttpURLConnection) connection);
			if (in == null) {
				in = getGzipStream(connection);
			}
		} else {
			in = connection.getInputStream();
		}
		return in;
	}

	/**
	 * <p>getImage.</p>
	 *
	 * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @param useBaseUri a {@link java.lang.Boolean} object.
	 * @return a {@link java.awt.Image} object.
	 */
	public static Image getImage(final HTMLElement element, final TimingInfo info, final boolean useBaseUri) {
		final Instant start = Instant.now();
		String href = null;
		if(element instanceof HTMLImageElement) {
			href = ((HTMLImageElement) element).getSrc();
		} else if(element instanceof HTMLInputElement){
			href = ((HTMLInputElement) element).getSrc();
		} else if(element instanceof SVGImageElement){
			href = ((SVGImageElement) element).getHref().getBaseVal();
		}

		final String baseUri = useBaseUri ? element.getBaseURI() : null;
		try {

			if (Strings.isBlank(href))
				return null;

			if (href.contains(";base64,")) {
				final String base64 = href.split(";base64,")[1];
				final byte[] decodedBytes = Base64.getDecoder().decode(Strings.linearize(base64));
				try (final InputStream stream = new ByteArrayInputStream(decodedBytes)) {
					return ImageIO.read(stream);
				}
			} else {
				URI uri = Strings.isNotBlank(baseUri) ? Urls.createURI(baseUri, href) : new URI(href);
				URLConnection connection = getURLConnection(uri, Proxy.NO_PROXY, "GET", null);
				String contentType = connection.getContentType();

				if (contentType != null && contentType.startsWith("text/html")) {
					log.warn("Expected image but received HTML from {}", connection.getURL());
					return null;
				}

				try (InputStream in = HttpNetwork.getInputStream(connection)) {
					if (href.contains(";base64,")) {
						final String base64 = href.split(";base64,")[1];
						final byte[] decodedBytes = Base64.getDecoder().decode(base64);
						final InputStream stream = new ByteArrayInputStream(decodedBytes);
						return ImageIO.read(stream);
					} else if (href.endsWith(".svg")) {
						return null; //TODO SVG From URL
					} else {
						BufferedImage image = ImageIO.read(in);
						return Toolkit.getDefaultToolkit().createImage(image.getSource());
					}
				} catch (final FileNotFoundException e) {
					log.error(e.getMessage(), e);
				}
			}
		} catch (final Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			final Instant finish = Instant.now();
			final long timeElapsed = Duration.between(start, finish).toMillis();
			info.setTimeElapsed(timeElapsed);
		}
		return null;
	}

	/**
	 * <p>getSource.</p>
	 *
	 * @param uri        a {@link String} object.
	 * @param headers    a {@link Map} object.
	 * @param integrity  a {@link String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.lang.Exception if any.
	 */
	public static String getSource(URI uri, Map<String, String> headers, final String integrity) throws Exception {

		URLConnection connection = getURLConnection(uri, Proxy.NO_PROXY, "GET", headers);
		try (InputStream in = HttpNetwork.getInputStream(connection)) {
			if (in == null) {
				return "";
			}

			final byte[] content = IOUtil.readFully(in);
			if (AlgorithmDigest.validate(content, integrity)) {
				return new String(content, StandardCharsets.UTF_8);
			}

		} catch (final SocketTimeoutException e) {
			log.error("More time elapsed {}", TIMEOUT_VALUE);
		}

		return "";
	}


	/**
	 * <p>getURLConnection.</p>
	 *
	 * @param uri       a {@link String} object.
	 * @param proxy     a {@link Proxy} object.
	 * @param method    a {@link String} object.
	 * @param extraHeaders  a {@link Map} object.
	 * @return a {@link String} object.
	 * @throws Exception if any.
	 */
	public static URLConnection getURLConnection(URI uri, Proxy proxy, String method, Map<String, String> extraHeaders) throws Exception {

		URL url = uri.toURL();
		URLConnection connection;

		connection = (proxy == null || proxy.equals(Proxy.NO_PROXY))
				? url.openConnection()
				: url.openConnection(proxy);

		if (Strings.isNotBlank(method) && connection instanceof HttpURLConnection hc) {
			hc.setRequestMethod(method.toUpperCase());
		}

		// Timeout
		connection.setConnectTimeout(TIMEOUT_VALUE);
		connection.setReadTimeout(TIMEOUT_VALUE);

		connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
		connection.setRequestProperty("Accept-Encoding", "gzip");
		connection.setRequestProperty("Connection", "keep-alive");

		// Header anti‑bot (Chrome-like)
		connection.setRequestProperty("Sec-Fetch-Dest", "style");
		connection.setRequestProperty("Sec-Fetch-Mode", "no-cors");
		connection.setRequestProperty("Sec-Fetch-Site", "same-site");

		// Header
		if (extraHeaders != null) {
			for (Map.Entry<String, String> e : extraHeaders.entrySet()) {
				connection.setRequestProperty(e.getKey(), e.getValue());
			}
		}


		connection.connect();
		if (connection instanceof HttpURLConnection h) {
			h.setInstanceFollowRedirects(true);
		}
		return connection;
	}

	/**
	 * <p>sourceResponse.</p>
	 *
	 * @param scriptURI a {@link URI} object.
	 * @param integrity a {@link String} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public static String sourceResponse(final URI scriptURI, final String integrity) {
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("Accept", "text/css,*/*;q=0.1");
			return getSource(scriptURI, headers, integrity);
		} catch (final Exception err) {
			log.error(err.getMessage(), err);
			return "";
		}
	}

	/**
	 * <p>toString.</p>
	 *
	 * @param inputStream a {@link java.io.InputStream} object.
	 * @return a {@link java.lang.String} object.
	 * @throws java.io.IOException if any.
	 */
	public static String toString(final InputStream inputStream) throws IOException {
		final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		final Stream<String> lines = new BufferedReader(inputStreamReader).lines();
		final String text = lines.collect(Collectors.joining("\n"));
		return removeNonASCIIChar(text);
	}
	
	private static String removeNonASCIIChar(final String str) {
		final StringBuilder buff = new StringBuilder();
		final char[] chars = str.toCharArray();
		for (final char c : chars) {
			if (0 < c && c < 127) {
				buff.append(c);
			}
		}
		return buff.toString();
	}

	private static InputStream getGzipStream(final URLConnection con) throws IOException {
		final InputStream cis = con.getInputStream();
		if (cis != null) {
			if (GZIP_ENCODING.equals(con.getContentEncoding())) {
				return new GZIPInputStream(con.getInputStream());
			} else {
				return con.getInputStream();
			}
		} else {
			return null;
		}
	}

	private static InputStream getGzipStreamError(final HttpURLConnection con) throws IOException {
		final InputStream cis = con.getErrorStream();
		if (cis != null) {
			if (GZIP_ENCODING.equals(con.getContentEncoding())) {
				return new GZIPInputStream(con.getErrorStream());
			} else {
				return con.getErrorStream();
			}
		} else {
			return null;
		}
	}
}