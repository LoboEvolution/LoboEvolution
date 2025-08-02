/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import org.loboevolution.html.builder.*;

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
	public  ElementFactory(final boolean isRss) {
		final Map<HTMLTag, HTMLElementBuilder> builders = this.builders;
		builders.put(HTMLTag.HTML, new HtmlBuilder());
		builders.put(HTMLTag.BASE, new BaseBuilder());
        builders.put(HTMLTag.HEAD, new HeadBuilder());

		final HTMLElementBuilder div = new DivBuilder();
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

		builders.put(HTMLTag.MARQUEE, new MarqueeBuilder());
		builders.put(HTMLTag.METER, new MeterBuilder());
		builders.put(HTMLTag.DIR, new DirBuilder());
		builders.put(HTMLTag.DETAILS, new DetailsBuilder());
		builders.put(HTMLTag.DIALOG, new DialogBuilder());
		builders.put(HTMLTag.DL, new DlBuilder());
		builders.put(HTMLTag.BODY, new BodyBuilder());
		builders.put(HTMLTag.CENTER, new CenterBuilder());
		builders.put(HTMLTag.CAPTION, new CaptionBuilder());
		builders.put(HTMLTag.PRE, new PreBuilder());
		builders.put(HTMLTag.P, new PBuilder());
		builders.put(HTMLTag.PROGRESS, new ProgressBuilder());
		builders.put(HTMLTag.DD, new DdBuilder());

		final HTMLElementBuilder quote = new QuoteBuilder();
		builders.put(HTMLTag.BLOCKQUOTE, quote);
		builders.put(HTMLTag.FIGURE, quote);
		builders.put(HTMLTag.Q, quote);

		builders.put(HTMLTag.SPAN, new SpanBuilder());
		builders.put(HTMLTag.SCRIPT, new ScriptBuilder());
		builders.put(HTMLTag.IMG, new ImgBuilder());
		builders.put(HTMLTag.STYLE, new StyleBuilder());
		builders.put(HTMLTag.A, new AnchorBuilder());
		builders.put(HTMLTag.ANCHOR, new AnchorBuilder());
		builders.put(HTMLTag.TABLE, new TableBuilder());
		builders.put(HTMLTag.TD, new TdBuilder());
		builders.put(HTMLTag.TH, new ThBuilder());
		builders.put(HTMLTag.TR, new TrBuilder());
		builders.put(HTMLTag.TFOOT, new TFootBuilder());
		builders.put(HTMLTag.THEAD, new THeadBuilder());
		builders.put(HTMLTag.TBODY, new TBodyBuilder());
		builders.put(HTMLTag.COL, new ColBuilder());
		builders.put(HTMLTag.COLGROUP, new ColGroupBuilder());

		builders.put(HTMLTag.FORM, new FormBuilder());
		builders.put(HTMLTag.INPUT, new InputBuilder());
		builders.put(HTMLTag.BUTTON, new ButtonBuilder());
		builders.put(HTMLTag.TEXTAREA, new TextareaBuilder());
		builders.put(HTMLTag.SELECT, new SelectBuilder());
		builders.put(HTMLTag.OPTION, new OptionBuilder());
		builders.put(HTMLTag.IFRAME, new IFrameBuilder());
		builders.put(HTMLTag.UL, new UlBuilder());
		builders.put(HTMLTag.OL, new OlBuilder());
		builders.put(HTMLTag.LI, new LiBuilder());
		builders.put(HTMLTag.HR, new HrBuilder());
		builders.put(HTMLTag.BR, new BrBuilder());
		builders.put(HTMLTag.OBJECT, new HtmlObjectBuilder());
		builders.put(HTMLTag.EMBED,new NonStandardBuilder());
		builders.put(HTMLTag.NOSCRIPT, new NoScriptBuilder());
		builders.put(HTMLTag.OPTGROUP, new OptGroupBuilder());


		builders.put(HTMLTag.TT, new TtBuilder());
		builders.put(HTMLTag.SMALL, new SmallBuilder());
		builders.put(HTMLTag.B, new StrongBuilder());
		builders.put(HTMLTag.STRONG, new StrongBuilder());

		builders.put(HTMLTag.U, new UnderlineBuilder());
		builders.put(HTMLTag.STRIKE, new StrikeBuilder());
		builders.put(HTMLTag.SUP, new SupBuilder());
		builders.put(HTMLTag.SUB, new SubBuilder());

		final HTMLElementBuilder code = new CodeBuilder();
		builders.put(HTMLTag.CODE, code);
		builders.put(HTMLTag.SAMP, code);
		builders.put(HTMLTag.KBD, code);

		final HTMLElementBuilder em = new EmBuilder();
		builders.put(HTMLTag.I, em);
		builders.put(HTMLTag.EM, em);
		builders.put(HTMLTag.CITE, em);
		builders.put(HTMLTag.VAR, em);

		final HTMLElementBuilder heading = new HeadingBuilder();
		builders.put(HTMLTag.H1, heading);
		builders.put(HTMLTag.H2, heading);
		builders.put(HTMLTag.H3, heading);
		builders.put(HTMLTag.H4, heading);
		builders.put(HTMLTag.H5, heading);
		builders.put(HTMLTag.H6, heading);


		builders.put(HTMLTag.FIGURE, new FigureBuilder());
		builders.put(HTMLTag.FIELDSET, new FieldsetBuilder());
		builders.put(HTMLTag.CANVAS, new CanvasBuilder());

		builders.put(HTMLTag.SVG, new SVGBuilder());
		builders.put(HTMLTag.RECT, new SVGRectBuilder());
		builders.put(HTMLTag.CIRCLE, new SVGCircleBuilder());
		builders.put(HTMLTag.ELLIPSE, new SVGEllipseBuilder());
		builders.put(HTMLTag.LINE, new SVGLineBuilder());
		builders.put(HTMLTag.POLYGON, new SVGPolygonBuilder());
		builders.put(HTMLTag.POLYLINE, new SVGPolylineBuilder());
		builders.put(HTMLTag.PATH, new SVGPathBuilder());
		builders.put(HTMLTag.G, new SVGGBuilder());
		builders.put(HTMLTag.DEFS, new SVGDefsBuilder());
		builders.put(HTMLTag.USE, new SVGUseBuilder());
		builders.put(HTMLTag.SYMBOL, new SVGSymbolBuilder());
		builders.put(HTMLTag.TEXT, new SVGTextBuilder());
		builders.put(HTMLTag.RADIALGRADIENT, new SVGRadialGradientBuilder());
		builders.put(HTMLTag.LINEARRGRADIENT, new SVGLinearGradientBuilder());
		builders.put(HTMLTag.STOP, new SVGStopBuilder());
		builders.put(HTMLTag.CLIPPATH, new SVGClipPathBuilder());
		builders.put(HTMLTag.ANIMATE, new SVGAnimateBuilder());
		builders.put(HTMLTag.ANIMATETRASFORM, new SVGAnimateTrasformBuilder());
		builders.put(HTMLTag.IMAGE, new SVGImageBuilder());
		builders.put(HTMLTag.LINK, new LinkBuilder());
		builders.put(HTMLTag.LEGEND, new LegendBuilder());
		builders.put(HTMLTag.VIDEO, new VideoBuilder());

		builders.put(HTMLTag.RSS, new RSSBuilder());
		builders.put(HTMLTag.CHANNEL, new ChannelBuilder());
		builders.put(HTMLTag.TITLE, isRss ? new RSSTitleBuilder() : new TitleBuilder());
		builders.put(HTMLTag.DESCRIPTION, new RSSDescriptionBuilder());
		builders.put(HTMLTag.ITEM, new RSSItemBuilder());

	}

	/**
	 * <p>createElement.</p>
	 *
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.dom.HTMLElement} object.
	 */
	public final HTMLElement createElement(final HTMLDocumentImpl document, final String name) {
	    final String normalName = name.toUpperCase(Locale.ENGLISH);
		final HTMLElementBuilder builder = this.builders.get(HTMLTag.get(normalName));
		final HTMLElement element = builder == null ? new HTMLElementImpl(name) : builder.build(name);
		element.setOwnerDocument(document);
		return element;
	}
}