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
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.dom.HTMLElement;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class ElementFactory {
	
	private final Map<HTMLTag, HTMLElementBuilder> builders = new HashMap<>();

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
		builders.put(HTMLTag.ARTICLE, div);
		builders.put(HTMLTag.ADDRESS, div);
		builders.put(HTMLTag.ASIDE, div);
		builders.put(HTMLTag.DIV, div);
		builders.put(HTMLTag.DT, div);
		builders.put(HTMLTag.FIGCAPTION, div);
		builders.put(HTMLTag.MAIN, div);
		builders.put(HTMLTag.MENU, div);
		builders.put(HTMLTag.NAV, div);
		builders.put(HTMLTag.SECTION, div);
		builders.put(HTMLTag.SUMMARY, div);
		builders.put(HTMLTag.HEADER, div);
		builders.put(HTMLTag.FOOTER, div);

		builders.put(HTMLTag.MARQUEE, new HTMLElementBuilder.Marquee());
		builders.put(HTMLTag.DIR, new HTMLElementBuilder.Dir());
		builders.put(HTMLTag.DETAILS, new HTMLElementBuilder.Details());
		builders.put(HTMLTag.DIALOG, new HTMLElementBuilder.Dialog());
		builders.put(HTMLTag.DL, new HTMLElementBuilder.Dl());
		builders.put(HTMLTag.BODY, new HTMLElementBuilder.Body());
		builders.put(HTMLTag.CENTER, new HTMLElementBuilder.Center());
		builders.put(HTMLTag.CAPTION, new HTMLElementBuilder.Caption());
		builders.put(HTMLTag.PRE, new HTMLElementBuilder.Pre());
		builders.put(HTMLTag.P, new HTMLElementBuilder.P());
		builders.put(HTMLTag.PROGRESS, new HTMLElementBuilder.Progress());
		builders.put(HTMLTag.DD, new HTMLElementBuilder.Dd());

		final HTMLElementBuilder quote = new HTMLElementBuilder.Quote();
		builders.put(HTMLTag.BLOCKQUOTE, quote);
		builders.put(HTMLTag.FIGURE, quote);
		builders.put(HTMLTag.Q, quote);

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
		builders.put(HTMLTag.TFOOT, new HTMLElementBuilder.TFoot());
		builders.put(HTMLTag.THEAD, new HTMLElementBuilder.THead());
		builders.put(HTMLTag.TBODY, new HTMLElementBuilder.TBody());
		builders.put(HTMLTag.COL, new HTMLElementBuilder.Col());
		builders.put(HTMLTag.COLGROUP, new HTMLElementBuilder.ColGroup());
		
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
		builders.put(HTMLTag.EMBED,new HTMLElementBuilder.NonStandard());
		builders.put(HTMLTag.NOSCRIPT, new HTMLElementBuilder.NoScript());
		builders.put(HTMLTag.OPTGROUP, new HTMLElementBuilder.OptGroup());


		builders.put(HTMLTag.TT, new HTMLElementBuilder.Tt());
		builders.put(HTMLTag.SMALL, new HTMLElementBuilder.Small());
		builders.put(HTMLTag.B, new HTMLElementBuilder.Strong());
		builders.put(HTMLTag.STRONG, new HTMLElementBuilder.Strong());

		builders.put(HTMLTag.U, new HTMLElementBuilder.Underline());
		builders.put(HTMLTag.STRIKE, new HTMLElementBuilder.Strike());
		builders.put(HTMLTag.SUP, new HTMLElementBuilder.Sup());
		builders.put(HTMLTag.SUB, new HTMLElementBuilder.Sub());

		final HTMLElementBuilder code = new HTMLElementBuilder.Code();
		builders.put(HTMLTag.CODE, code);
		builders.put(HTMLTag.SAMP, code);
		builders.put(HTMLTag.KBD, code);

		final HTMLElementBuilder em = new HTMLElementBuilder.Em();
		builders.put(HTMLTag.I, em);
		builders.put(HTMLTag.EM, em);
		builders.put(HTMLTag.CITE, em);
		builders.put(HTMLTag.VAR, em);

		final HTMLElementBuilder heading = new HTMLElementBuilder.Heading();
		builders.put(HTMLTag.H1, heading);
		builders.put(HTMLTag.H2, heading);
		builders.put(HTMLTag.H3, heading);
		builders.put(HTMLTag.H4, heading);
		builders.put(HTMLTag.H5, heading);
		builders.put(HTMLTag.H6, heading);


		builders.put(HTMLTag.FIGURE, new HTMLElementBuilder.Figure());
		builders.put(HTMLTag.FIELDSET, new HTMLElementBuilder.Fieldset());
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
		builders.put(HTMLTag.LEGEND, new HTMLElementBuilder.Legend());
		builders.put(HTMLTag.VIDEO, new HTMLElementBuilder.Video());

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
	 */
	public final HTMLElement createElement(HTMLDocumentImpl document, String name) {
	    final String normalName = name.toUpperCase(Locale.ENGLISH);
		final HTMLElementBuilder builder = this.builders.get(HTMLTag.get(normalName));
		HTMLElement element = builder == null ? new HTMLElementImpl(name) : builder.build(name);
		element.setOwnerDocument(document);
		return element;
	}
}