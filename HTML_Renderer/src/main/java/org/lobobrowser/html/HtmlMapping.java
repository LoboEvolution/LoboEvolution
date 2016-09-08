/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.lobobrowser.html.builder.AnchorBuilder;
import org.lobobrowser.html.builder.AppletBuilder;
import org.lobobrowser.html.builder.AudioBuilder;
import org.lobobrowser.html.builder.BaseBuilder;
import org.lobobrowser.html.builder.BaseFontBuilder;
import org.lobobrowser.html.builder.BigBuilder;
import org.lobobrowser.html.builder.BlockquoteBuilder;
import org.lobobrowser.html.builder.BodyBuilder;
import org.lobobrowser.html.builder.BrBuilder;
import org.lobobrowser.html.builder.ButtonBuilder;
import org.lobobrowser.html.builder.CanvasBuilder;
import org.lobobrowser.html.builder.CaptionBuilder;
import org.lobobrowser.html.builder.CenterBuilder;
import org.lobobrowser.html.builder.CodeBuilder;
import org.lobobrowser.html.builder.CommandBuilder;
import org.lobobrowser.html.builder.DetailsBuilder;
import org.lobobrowser.html.builder.DivBulder;
import org.lobobrowser.html.builder.EmBuilder;
import org.lobobrowser.html.builder.FontBuilder;
import org.lobobrowser.html.builder.FormBuilder;
import org.lobobrowser.html.builder.FrameBuilder;
import org.lobobrowser.html.builder.FramesetBuilder;
import org.lobobrowser.html.builder.HeadBuilder;
import org.lobobrowser.html.builder.HeadingBuilder;
import org.lobobrowser.html.builder.HrBuilder;
import org.lobobrowser.html.builder.HtmlBuilder;
import org.lobobrowser.html.builder.HtmlObjectBuilder;
import org.lobobrowser.html.builder.IFrameBuilder;
import org.lobobrowser.html.builder.ImgBuilder;
import org.lobobrowser.html.builder.InputBuilder;
import org.lobobrowser.html.builder.KeygenBuilder;
import org.lobobrowser.html.builder.LiBuilder;
import org.lobobrowser.html.builder.LinkBuilder;
import org.lobobrowser.html.builder.MarqueeBuilder;
import org.lobobrowser.html.builder.MeterBuilder;
import org.lobobrowser.html.builder.NavBuilder;
import org.lobobrowser.html.builder.OlBuilder;
import org.lobobrowser.html.builder.OptionBuilder;
import org.lobobrowser.html.builder.OutputBuilder;
import org.lobobrowser.html.builder.PBuilder;
import org.lobobrowser.html.builder.PreBuilder;
import org.lobobrowser.html.builder.ProgressBuilder;
import org.lobobrowser.html.builder.ScriptBuilder;
import org.lobobrowser.html.builder.SectionBuilder;
import org.lobobrowser.html.builder.SelectBuilder;
import org.lobobrowser.html.builder.SmallBuilder;
import org.lobobrowser.html.builder.SpanBuilder;
import org.lobobrowser.html.builder.StrikeBuilder;
import org.lobobrowser.html.builder.StrongBuilder;
import org.lobobrowser.html.builder.StyleBuilder;
import org.lobobrowser.html.builder.SubBuilder;
import org.lobobrowser.html.builder.SupBuilder;
import org.lobobrowser.html.builder.TableBuilder;
import org.lobobrowser.html.builder.TdBuilder;
import org.lobobrowser.html.builder.TextareaBuilder;
import org.lobobrowser.html.builder.ThBuilder;
import org.lobobrowser.html.builder.TimeBuilder;
import org.lobobrowser.html.builder.TitleBuilder;
import org.lobobrowser.html.builder.TrBuilder;
import org.lobobrowser.html.builder.TtBuilder;
import org.lobobrowser.html.builder.UlBuilder;
import org.lobobrowser.html.builder.UnderlineBuilder;
import org.lobobrowser.html.builder.VideoBuilder;
import org.lobobrowser.html.info.ElementInfo;

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

		ElementInfo paragraphElement = new ElementInfo(true, ElementInfo.END_ELEMENT_OPTIONAL, paragraphStopElements);

		elementInfos.put(NOSCRIPT, new ElementInfo(true, ElementInfo.END_ELEMENT_REQUIRED, null, true));
		elementInfos.put(SCRIPT, onlyText);
		elementInfos.put(STYLE, onlyText);
		elementInfos.put(TEXTAREA, onlyTextDE);
		elementInfos.put(IMG, forbiddenEndElement);
		elementInfos.put(META, forbiddenEndElement);
		elementInfos.put(LINK, forbiddenEndElement);
		elementInfos.put(BASE, forbiddenEndElement);
		elementInfos.put(INPUT, forbiddenEndElement);
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
		builders.put(STYLE, new StyleBuilder());
		builders.put(LINK, new LinkBuilder());
		builders.put(TABLE, new TableBuilder());
		builders.put(TD, new TdBuilder());
		builders.put(TH, new ThBuilder());
		builders.put(TR, new TrBuilder());
		builders.put(THEAD, new SectionBuilder());
		builders.put(TBODY, new SectionBuilder());
		builders.put(TFOOT, new SectionBuilder());
		builders.put(FORM, new FormBuilder());
		builders.put(INPUT, new InputBuilder());
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
		return builders;
	}
}
