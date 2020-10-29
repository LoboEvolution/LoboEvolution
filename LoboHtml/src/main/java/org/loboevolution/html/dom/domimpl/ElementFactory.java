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
package org.loboevolution.html.dom.domimpl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.dom.HTMLElement;
import org.w3c.dom.DOMException;

class ElementFactory {
	
	private final Map<HTMLTag, HTMLElementBuilder> builders = new HashMap<HTMLTag, HTMLElementBuilder>();

	/**
	 * <p>Constructor for ElementFactory.</p>
	 *
	 * @param isRss a boolean.
	 */
	public  ElementFactory(boolean isRss) {
		// This sets up builders for each known element tag.
		final Map<HTMLTag, HTMLElementBuilder> builders = this.builders;
		builders.put(HTMLTag.HTML, new HTMLElementBuilder.Html());		
		builders.put(HTMLTag.BASE, new HTMLElementBuilder.Base());
        builders.put(HTMLTag.HEAD, new HTMLElementBuilder.Head());

		final HTMLElementBuilder div = new HTMLElementBuilder.Div();
		builders.put(HTMLTag.DIV, div);
		builders.put(HTMLTag.DL, div);

		builders.put(HTMLTag.BODY, new HTMLElementBuilder.Body());
		builders.put(HTMLTag.CENTER, new HTMLElementBuilder.Center());
		builders.put(HTMLTag.CAPTION, new HTMLElementBuilder.Caption());
		builders.put(HTMLTag.PRE, new HTMLElementBuilder.Pre());
		builders.put(HTMLTag.P, new HTMLElementBuilder.P());

		final HTMLElementBuilder quote = new HTMLElementBuilder.Quote();
		builders.put(HTMLTag.BLOCKQUOTE, quote);
		builders.put(HTMLTag.DD, quote);

		builders.put(HTMLTag.SPAN, new HTMLElementBuilder.Span());
		builders.put(HTMLTag.SCRIPT, new HTMLElementBuilder.Script());
		builders.put(HTMLTag.IMG, new HTMLElementBuilder.Img());
		builders.put(HTMLTag.STYLE, new HTMLElementBuilder.Style());
		builders.put(HTMLTag.A, new HTMLElementBuilder.Anchor());
		builders.put(HTMLTag.ANCHOR, new HTMLElementBuilder.Anchor());
		builders.put(HTMLTag.TABLE, new HTMLElementBuilder.Table());
		builders.put(HTMLTag.TD, new HTMLElementBuilder.Td());
		builders.put(HTMLTag.TH, new HTMLElementBuilder.Th());
		builders.put(HTMLTag.TR, new HTMLElementBuilder.Tr());
		builders.put(HTMLTag.FORM, new HTMLElementBuilder.Form());
		builders.put(HTMLTag.INPUT, new HTMLElementBuilder.Input());
		builders.put(HTMLTag.BUTTON, new HTMLElementBuilder.Button());
		builders.put(HTMLTag.TEXTAREA, new HTMLElementBuilder.Textarea());
		builders.put(HTMLTag.SELECT, new HTMLElementBuilder.Select());
		builders.put(HTMLTag.OPTION, new HTMLElementBuilder.Option());
		builders.put(HTMLTag.IFRAME, new HTMLElementBuilder.IFrame());
		builders.put(HTMLTag.UL, new HTMLElementBuilder.Ul());
		builders.put(HTMLTag.OL, new HTMLElementBuilder.Ol());
		builders.put(HTMLTag.LI, new HTMLElementBuilder.Li());
		builders.put(HTMLTag.HR, new HTMLElementBuilder.Hr());
		builders.put(HTMLTag.BR, new HTMLElementBuilder.Br());
		builders.put(HTMLTag.OBJECT, new HTMLElementBuilder.HtmlObject());
		builders.put(HTMLTag.EMBED, new HTMLElementBuilder.NonStandard());

		builders.put(HTMLTag.TT, new HTMLElementBuilder.Tt());
		builders.put(HTMLTag.CODE, new HTMLElementBuilder.Code());
		builders.put(HTMLTag.SMALL, new HTMLElementBuilder.Small());
		builders.put(HTMLTag.B, new HTMLElementBuilder.Strong());
		builders.put(HTMLTag.STRONG, new HTMLElementBuilder.Strong());

		builders.put(HTMLTag.U, new HTMLElementBuilder.Underline());
		builders.put(HTMLTag.STRIKE, new HTMLElementBuilder.Strike());
		builders.put(HTMLTag.SUP, new HTMLElementBuilder.Sup());
		builders.put(HTMLTag.SUB, new HTMLElementBuilder.Sub());

		final HTMLElementBuilder em = new HTMLElementBuilder.Em();
		builders.put(HTMLTag.I, em);
		builders.put(HTMLTag.EM, em);
		builders.put(HTMLTag.CITE, em);

		final HTMLElementBuilder heading = new HTMLElementBuilder.Heading();
		builders.put(HTMLTag.H1, heading);
		builders.put(HTMLTag.H2, heading);
		builders.put(HTMLTag.H3, heading);
		builders.put(HTMLTag.H4, heading);
		builders.put(HTMLTag.H5, heading);
		builders.put(HTMLTag.H6, heading);

		builders.put(HTMLTag.CANVAS, new HTMLElementBuilder.Canvas());

		builders.put(HTMLTag.SVG, new HTMLElementBuilder.SVG());
		builders.put(HTMLTag.RECT, new HTMLElementBuilder.SVGRect());
		builders.put(HTMLTag.CIRCLE, new HTMLElementBuilder.SVGCircle());
		builders.put(HTMLTag.ELLIPSE, new HTMLElementBuilder.SVGEllipse());
		builders.put(HTMLTag.LINE, new HTMLElementBuilder.SVGLine());
		builders.put(HTMLTag.POLYGON, new HTMLElementBuilder.SVGPolygon());
		builders.put(HTMLTag.POLYLINE, new HTMLElementBuilder.SVGPolyline());
		builders.put(HTMLTag.PATH, new HTMLElementBuilder.SVGPath());
		builders.put(HTMLTag.G, new HTMLElementBuilder.SVGG());
		builders.put(HTMLTag.DEFS, new HTMLElementBuilder.SVGDefs());
		builders.put(HTMLTag.USE, new HTMLElementBuilder.SVGUse());
		builders.put(HTMLTag.SYMBOL, new HTMLElementBuilder.SVGSymbol());
		builders.put(HTMLTag.TEXT, new HTMLElementBuilder.SVGText());
		builders.put(HTMLTag.RADIAL_GRADIENT, new HTMLElementBuilder.SVGRadialGradient());
		builders.put(HTMLTag.LINEAR_GRADIENT, new HTMLElementBuilder.SVGLinearGradient());
		builders.put(HTMLTag.STOP, new HTMLElementBuilder.SVGStop());
		builders.put(HTMLTag.CLIPPATH, new HTMLElementBuilder.SVGClipPath());
		builders.put(HTMLTag.ANIMATE, new HTMLElementBuilder.SVGAnimate());
		builders.put(HTMLTag.ANIMATE_TRASFORM, new HTMLElementBuilder.SVGAnimateTrasform());
		builders.put(HTMLTag.IMAGE, new HTMLElementBuilder.SVGImage());
		builders.put(HTMLTag.LINK, new HTMLElementBuilder.Link());

		builders.put(HTMLTag.RSS, new HTMLElementBuilder.RSS());
		builders.put(HTMLTag.CHANNEL, new HTMLElementBuilder.Channel());
		builders.put(HTMLTag.TITLE, isRss ? new HTMLElementBuilder.RSSTitle() : new HTMLElementBuilder.Title());
		builders.put(HTMLTag.DESCRIPTION, new HTMLElementBuilder.RSSDescription());
		builders.put(HTMLTag.ITEM, new HTMLElementBuilder.RSSItem());
	
	}

	/**
	 * <p>createElement.</p>
	 *
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public final HTMLElement createElement(HTMLDocumentImpl document, String name) throws DOMException {
	    final String normalName = name.toUpperCase(Locale.ENGLISH);
		final HTMLElementBuilder builder = this.builders.get(HTMLTag.get(normalName));
		if (builder == null) {
			final HTMLElementImpl element = new HTMLElementImpl(name);
			element.setOwnerDocument(document);
			return element;
		} else {
			return builder.create(document, name);
		}
	}
}
