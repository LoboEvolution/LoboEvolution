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

package org.loboevolution.html.control;

import org.loboevolution.common.Urls;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.svg.SVGSVGElement;
import org.loboevolution.svg.dom.SVGSVGElementImpl;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Serial;
import java.net.Proxy;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * <p>SVGControl class.</p>
 */
public class SVGControl extends BaseControl {

	/** The Constant serialVersionUID. */
	@Serial
	private static final long serialVersionUID = 1L;

	private final SVGSVGElement modelNode;

	/**
	 * <p>Constructor for SVGControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.svg.SVGSVGElement} object.
	 */
	public SVGControl(final SVGSVGElement modelNode) {
		super(modelNode);
		this.modelNode = modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		modelNode.draw(g2d);
	}

	/**
	 * <p>Read svg from tag img</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLImageElementImpl} object.
	 */
	public static Image loadSvg(final HTMLImageElementImpl modelNode) throws Exception {
		final String baseUri = modelNode.getBaseURI();
		final String href = modelNode.getSrc();
		final UserAgentContext userAgentContext = modelNode.getUserAgentContext();
		final HtmlRendererContext rendererContext = modelNode.getHtmlRendererContext();
		final HtmlRendererConfig config = modelNode.getHtmlRendererConfig();
		final URI uri = Urls.createURI(baseUri, href);
		if (uri == null) {
			return null;
		}
		final URLConnection connection = HttpNetwork.getURLConnection(uri, Proxy.NO_PROXY, null);
		try (var input = HttpNetwork.openConnectionCheckRedirects(connection)) {
			final String documentUri = connection.getURL().toExternalForm();
			final InputSourceImpl source = new InputSourceImpl(input, documentUri, Charset.forName(Urls.getCharset(connection)));
			final Document document = new DocumentBuilderImpl(userAgentContext, rendererContext, config).parse(source);
			final SVGSVGElementImpl svg = findSvgElement(document);
			if (svg == null) {
				return null;
			}
			final int width = Math.max(1, (int) Math.ceil(svg.getWidth().getBaseVal().getValue()));
			final int height = Math.max(1, (int) Math.ceil(svg.getHeight().getBaseVal().getValue()));
			final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D graphics = image.createGraphics();
			try {
				svg.draw(graphics);
			} finally {
				graphics.dispose();
			}
			return image;
		}
	}

	private static SVGSVGElementImpl findSvgElement(final Node node) {
		if (node instanceof SVGSVGElementImpl svg) {
			return svg;
		}
		for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
			final SVGSVGElementImpl svg = findSvgElement(child);
			if (svg != null) {
				return svg;
			}
		}
		return null;
	}
}
