/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.loboevolution.html.builder.AnchorBuilder;
import org.loboevolution.html.builder.AppletBuilder;
import org.loboevolution.html.builder.AudioBuilder;
import org.loboevolution.html.builder.BaseBuilder;
import org.loboevolution.html.builder.BaseFontBuilder;
import org.loboevolution.html.builder.BigBuilder;
import org.loboevolution.html.builder.BlockquoteBuilder;
import org.loboevolution.html.builder.BodyBuilder;
import org.loboevolution.html.builder.BrBuilder;
import org.loboevolution.html.builder.ButtonBuilder;
import org.loboevolution.html.builder.CanvasBuilder;
import org.loboevolution.html.builder.CaptionBuilder;
import org.loboevolution.html.builder.CenterBuilder;
import org.loboevolution.html.builder.CodeBuilder;
import org.loboevolution.html.builder.CommandBuilder;
import org.loboevolution.html.builder.DetailsBuilder;
import org.loboevolution.html.builder.DivBulder;
import org.loboevolution.html.builder.EmBuilder;
import org.loboevolution.html.builder.FontBuilder;
import org.loboevolution.html.builder.FormBuilder;
import org.loboevolution.html.builder.FrameBuilder;
import org.loboevolution.html.builder.FramesetBuilder;
import org.loboevolution.html.builder.HeadBuilder;
import org.loboevolution.html.builder.HeadingBuilder;
import org.loboevolution.html.builder.HrBuilder;
import org.loboevolution.html.builder.HtmlBuilder;
import org.loboevolution.html.builder.HtmlObjectBuilder;
import org.loboevolution.html.builder.IFrameBuilder;
import org.loboevolution.html.builder.ImgBuilder;
import org.loboevolution.html.builder.InputBuilder;
import org.loboevolution.html.builder.KeygenBuilder;
import org.loboevolution.html.builder.LabelBuilder;
import org.loboevolution.html.builder.LiBuilder;
import org.loboevolution.html.builder.LinkBuilder;
import org.loboevolution.html.builder.MarqueeBuilder;
import org.loboevolution.html.builder.MeterBuilder;
import org.loboevolution.html.builder.NavBuilder;
import org.loboevolution.html.builder.OlBuilder;
import org.loboevolution.html.builder.OptionBuilder;
import org.loboevolution.html.builder.OutputBuilder;
import org.loboevolution.html.builder.PBuilder;
import org.loboevolution.html.builder.PreBuilder;
import org.loboevolution.html.builder.ProgressBuilder;
import org.loboevolution.html.builder.ScriptBuilder;
import org.loboevolution.html.builder.SectionBuilder;
import org.loboevolution.html.builder.SelectBuilder;
import org.loboevolution.html.builder.SmallBuilder;
import org.loboevolution.html.builder.SpanBuilder;
import org.loboevolution.html.builder.StrikeBuilder;
import org.loboevolution.html.builder.StrongBuilder;
import org.loboevolution.html.builder.StyleBuilder;
import org.loboevolution.html.builder.SubBuilder;
import org.loboevolution.html.builder.SupBuilder;
import org.loboevolution.html.builder.TableBuilder;
import org.loboevolution.html.builder.TdBuilder;
import org.loboevolution.html.builder.TextareaBuilder;
import org.loboevolution.html.builder.ThBuilder;
import org.loboevolution.html.builder.TimeBuilder;
import org.loboevolution.html.builder.TitleBuilder;
import org.loboevolution.html.builder.TrBuilder;
import org.loboevolution.html.builder.TtBuilder;
import org.loboevolution.html.builder.UlBuilder;
import org.loboevolution.html.builder.UnderlineBuilder;
import org.loboevolution.html.builder.VideoBuilder;
import org.loboevolution.html.buildersvg.SVGAnimateBuilder;
import org.loboevolution.html.buildersvg.SVGAnimateColorBuilder;
import org.loboevolution.html.buildersvg.SVGAnimateTrasformBuilder;
import org.loboevolution.html.buildersvg.SVGBuilder;
import org.loboevolution.html.buildersvg.SVGCircleBuilder;
import org.loboevolution.html.buildersvg.SVGClipPathBuilder;
import org.loboevolution.html.buildersvg.SVGDefsBuilder;
import org.loboevolution.html.buildersvg.SVGEllipseBuilder;
import org.loboevolution.html.buildersvg.SVGGBuilder;
import org.loboevolution.html.buildersvg.SVGLineBuilder;
import org.loboevolution.html.buildersvg.SVGLinearGradientBuilder;
import org.loboevolution.html.buildersvg.SVGPathBuilder;
import org.loboevolution.html.buildersvg.SVGPolygonBuilder;
import org.loboevolution.html.buildersvg.SVGPolylineBuilder;
import org.loboevolution.html.buildersvg.SVGRadialGradientBuilder;
import org.loboevolution.html.buildersvg.SVGRectBuilder;
import org.loboevolution.html.buildersvg.SVGStopBuilder;
import org.loboevolution.html.buildersvg.SVGSymbolBuilder;
import org.loboevolution.html.buildersvg.SVGTextBuilder;
import org.loboevolution.html.buildersvg.SVGUseBuilder;
import org.loboevolution.html.info.ElementInfo;

/**
 * The Class HtmlMapping.
 */
public class HtmlMapping implements HtmlProperties {

	/**
	 * Mapping tag.
	 *
	 * @return the map
	 */
	public static Map<String, ElementInfo> mappingTag() {

		Map<String, ElementInfo> elementInfos = new HashMap<String, ElementInfo>();
		ElementInfo optionalEndElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL);
		ElementInfo forbiddenEndElement = new ElementInfo(false, ElementInfo.END_ELEMENT_FORBIDDEN);
		ElementInfo onlyTextDE = new ElementInfo(false, ElementInfo.END_ELEMENT_REQUIRED, true);
		ElementInfo onlyText = new ElementInfo(false, ElementInfo.END_ELEMENT_REQUIRED, false);

		Set<String> tableCellStopElements = new HashSet<String>();
		tableCellStopElements.add(TH);
		tableCellStopElements.add(TD);
		tableCellStopElements.add(TR);
		ElementInfo tableCellElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, tableCellStopElements);

		Set<String> headStopElements = new HashSet<String>();
		headStopElements.add(BODY);
		headStopElements.add(DIV);
		headStopElements.add(SPAN);
		headStopElements.add(TABLE);

		ElementInfo headElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, headStopElements);

		Set<String> optionStopElements = new HashSet<String>();
		optionStopElements.add(OPTION);
		optionStopElements.add(SELECT);

		ElementInfo optionElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, optionStopElements);

		Set<String> paragraphStopElements = new HashSet<String>();
		paragraphStopElements.add(P);
		paragraphStopElements.add(DIV);
		paragraphStopElements.add(TABLE);
		paragraphStopElements.add(PRE);
		paragraphStopElements.add(UL);
		paragraphStopElements.add(OL);
		paragraphStopElements.add(NAV);
		paragraphStopElements.add(SECTION);
		paragraphStopElements.add(HEADER);
		paragraphStopElements.add(FOOTER);
		paragraphStopElements.add(ARTICLE);
		paragraphStopElements.add(CANVAS);
		paragraphStopElements.add(SVG);

		ElementInfo paragraphElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, paragraphStopElements);

		elementInfos.put(NOSCRIPT, new ElementInfo(true, ElementInfo.END_ELEMENT_REQUIRED, null, true));
		elementInfos.put(SCRIPT, onlyText);
		elementInfos.put(STYLE_HTML, onlyText);
		elementInfos.put(TEXTAREA, onlyTextDE);
		elementInfos.put(IMG, forbiddenEndElement);
		elementInfos.put(META, forbiddenEndElement);
		elementInfos.put(LINK_HTML, forbiddenEndElement);
		elementInfos.put(BASE, forbiddenEndElement);
		elementInfos.put(INPUT_HTML, forbiddenEndElement);
		elementInfos.put(BUTTON, forbiddenEndElement);
		elementInfos.put(FRAME, forbiddenEndElement);
		elementInfos.put(BR, forbiddenEndElement);
		elementInfos.put(HR, forbiddenEndElement);
		elementInfos.put(EMBED, forbiddenEndElement);
		elementInfos.put(SPACER, forbiddenEndElement);
		elementInfos.put(P, paragraphElement);
		elementInfos.put(LI, optionalEndElement);
		elementInfos.put(DT, optionalEndElement);
		elementInfos.put(DD, optionalEndElement);
		elementInfos.put(CAPTION, optionalEndElement);
		elementInfos.put(THEAD, optionalEndElement);
		elementInfos.put(TBODY, optionalEndElement);
		elementInfos.put(TFOOT, optionalEndElement);
		elementInfos.put(TR, optionalEndElement);
		elementInfos.put(TH, tableCellElement);
		elementInfos.put(TD, tableCellElement);
		elementInfos.put(HEAD, headElement);
		elementInfos.put(OPTION, optionElement);
		elementInfos.put(A, optionalEndElement);
		elementInfos.put(ANCHOR, optionalEndElement);
		return elementInfos;

	}

	/**
	 * Mapping html.
	 *
	 * @return the map
	 */
	public static Map<String, Object> mappingHtml() {

		Map<String, Object> builders = new HashMap<String, Object>();
		DivBulder div = new DivBulder();
		builders.put(DIV, div);
		builders.put(DL, div);
		builders.put(HEADER, div);
		builders.put(FOOTER, div);
		builders.put(ARTICLE, div);
		builders.put(EMBED, div);

		PBuilder p = new PBuilder();
		builders.put(P, p);
		builders.put(SECTION, p);

		BlockquoteBuilder bq = new BlockquoteBuilder();
		builders.put(BLOCKQUOTE, bq);
		builders.put(DD, bq);

		AnchorBuilder a = new AnchorBuilder();
		builders.put(A, a);
		builders.put(ANCHOR, a);

		CodeBuilder code = new CodeBuilder();
		builders.put(CODE, code);
		builders.put(KBD, code);
		builders.put(SAMP, code);
		builders.put(SOURCE, code);

		EmBuilder em = new EmBuilder();
		builders.put(I, em);
		builders.put(EM, em);
		builders.put(CITE, em);
		builders.put(DFN, em);
		builders.put(VAR, em);

		HeadingBuilder heading = new HeadingBuilder();
		builders.put(H1, heading);
		builders.put(H2, heading);
		builders.put(H3, heading);
		builders.put(H4, heading);
		builders.put(H5, heading);
		builders.put(H6, heading);

		StrikeBuilder strike = new StrikeBuilder();
		builders.put(STRIKE, strike);
		builders.put(S, strike);
		builders.put(DEL, strike);

		builders.put(HTML, new HtmlBuilder());
		builders.put(HEAD, new HeadBuilder());
		builders.put(TITLE, new TitleBuilder());
		builders.put(BASE, new BaseBuilder());
		builders.put(BODY, new BodyBuilder());
		builders.put(CENTER, new CenterBuilder());
		builders.put(CAPTION, new CaptionBuilder());
		builders.put(PRE, new PreBuilder());
		builders.put(SPAN, new SpanBuilder());
		builders.put(SCRIPT, new ScriptBuilder());
		builders.put(IMG, new ImgBuilder());
		builders.put(STYLE_HTML, new StyleBuilder());
		builders.put(LINK_HTML, new LinkBuilder());
		builders.put(TABLE, new TableBuilder());
		builders.put(TD, new TdBuilder());
		builders.put(TH, new ThBuilder());
		builders.put(TR, new TrBuilder());
		builders.put(THEAD, new SectionBuilder());
		builders.put(TBODY, new SectionBuilder());
		builders.put(TFOOT, new SectionBuilder());
		builders.put(FORM, new FormBuilder());
		builders.put(INPUT_HTML, new InputBuilder());
		builders.put(BUTTON, new ButtonBuilder());
		builders.put(TEXTAREA, new TextareaBuilder());
		builders.put(SELECT, new SelectBuilder());
		builders.put(OPTION, new OptionBuilder());
		builders.put(FRAMESET, new FramesetBuilder());
		builders.put(FRAME, new FrameBuilder());
		builders.put(IFRAME, new IFrameBuilder());
		builders.put(UL, new UlBuilder());
		builders.put(OL, new OlBuilder());
		builders.put(LI, new LiBuilder());
		builders.put(HR, new HrBuilder());
		builders.put(BR, new BrBuilder());
		builders.put(OBJECT, new HtmlObjectBuilder());
		builders.put(APPLET, new AppletBuilder());
		builders.put(FONT, new FontBuilder());
		builders.put(BASEFONT, new BaseFontBuilder());
		builders.put(TT, new TtBuilder());
		builders.put(SMALL, new SmallBuilder());
		builders.put(BIG, new BigBuilder());
		builders.put(B, new StrongBuilder());
		builders.put(STRONG, new StrongBuilder());
		builders.put(NAV, new NavBuilder());
		builders.put(U, new UnderlineBuilder());
		builders.put(INS, new UnderlineBuilder());
		builders.put(SUP, new SupBuilder());
		builders.put(SUB, new SubBuilder());
		builders.put(CANVAS, new CanvasBuilder());
		builders.put(AUDIO, new AudioBuilder());
		builders.put(COMMAND, new CommandBuilder());
		builders.put(DETAILS, new DetailsBuilder());
		builders.put(KEYGEN, new KeygenBuilder());
		builders.put(MARQUEE, new MarqueeBuilder());
		builders.put(METER, new MeterBuilder());
		builders.put(PROGRESS, new ProgressBuilder());
		builders.put(OUTPUT, new OutputBuilder());
		builders.put(TIME, new TimeBuilder());
		builders.put(VIDEO, new VideoBuilder());
		builders.put(LABEL, new LabelBuilder());
		builders.put(SVG, new SVGBuilder());
		builders.put(CIRCLE, new SVGCircleBuilder());
		builders.put(RECT, new SVGRectBuilder());
		builders.put(ELLIPSE, new SVGEllipseBuilder());
		builders.put(LINE, new SVGLineBuilder());
		builders.put(POLYGON, new SVGPolygonBuilder());
		builders.put(POLYLINE, new SVGPolylineBuilder());
		builders.put(PATH, new SVGPathBuilder());
		builders.put(G, new SVGGBuilder());
		builders.put(DEFS, new SVGDefsBuilder());
		builders.put(USE, new SVGUseBuilder());
		builders.put(SYMBOL, new SVGSymbolBuilder());
		builders.put(TEXT_HTML, new SVGTextBuilder());
		builders.put(RADIAL_GRADIENT, new SVGRadialGradientBuilder());
		builders.put(LINEAR_GRADIENT, new SVGLinearGradientBuilder());
		builders.put(STOP, new SVGStopBuilder());
		builders.put(CLIPPATH, new SVGClipPathBuilder());
		builders.put(ANIMATE, new SVGAnimateBuilder());
		builders.put(ANIMATE_TRASFORM, new SVGAnimateTrasformBuilder());
		builders.put(ANIMATE_COLOR, new SVGAnimateColorBuilder());
		return builders;
	}
}
