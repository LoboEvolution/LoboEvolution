/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
public class HtmlMapping {

    /**
     * Mapping tag.
     *
     * @return the map
     */
    public static Map<String, ElementInfo> mappingTag() {

        Map<String, ElementInfo> elementInfos = new HashMap<String, ElementInfo>();
        ElementInfo optionalEndElement = new ElementInfo(true,
                ElementInfo.END_ELEMENT_OPTIONAL);
        ElementInfo forbiddenEndElement = new ElementInfo(false,
                ElementInfo.END_ELEMENT_FORBIDDEN);
        ElementInfo onlyTextDE = new ElementInfo(false,
                ElementInfo.END_ELEMENT_REQUIRED, true);
        ElementInfo onlyText = new ElementInfo(false,
                ElementInfo.END_ELEMENT_REQUIRED, false);

        Set<String> tableCellStopElements = new HashSet<String>();
        tableCellStopElements.add(HtmlProperties.TH);
        tableCellStopElements.add(HtmlProperties.TD);
        tableCellStopElements.add(HtmlProperties.TR);
        ElementInfo tableCellElement = new ElementInfo(true,
                ElementInfo.END_ELEMENT_OPTIONAL, tableCellStopElements);

        Set<String> headStopElements = new HashSet<String>();
        headStopElements.add(HtmlProperties.BODY);
        headStopElements.add(HtmlProperties.DIV);
        headStopElements.add(HtmlProperties.SPAN);
        headStopElements.add(HtmlProperties.TABLE);

        ElementInfo headElement = new ElementInfo(true,
                ElementInfo.END_ELEMENT_OPTIONAL, headStopElements);

        Set<String> optionStopElements = new HashSet<String>();
        optionStopElements.add(HtmlProperties.OPTION);
        optionStopElements.add(HtmlProperties.SELECT);

        ElementInfo optionElement = new ElementInfo(true,
                ElementInfo.END_ELEMENT_OPTIONAL, optionStopElements);

        Set<String> paragraphStopElements = new HashSet<String>();
        paragraphStopElements.add(HtmlProperties.P);
        paragraphStopElements.add(HtmlProperties.DIV);
        paragraphStopElements.add(HtmlProperties.TABLE);
        paragraphStopElements.add(HtmlProperties.PRE);
        paragraphStopElements.add(HtmlProperties.UL);
        paragraphStopElements.add(HtmlProperties.OL);
        paragraphStopElements.add(HtmlProperties.NAV);
        paragraphStopElements.add(HtmlProperties.SECTION);
        paragraphStopElements.add(HtmlProperties.HEADER);
        paragraphStopElements.add(HtmlProperties.FOOTER);
        paragraphStopElements.add(HtmlProperties.ARTICLE);
        paragraphStopElements.add(HtmlProperties.CANVAS);

        ElementInfo paragraphElement = new ElementInfo(true,
                ElementInfo.END_ELEMENT_OPTIONAL, paragraphStopElements);

        elementInfos.put(HtmlProperties.NOSCRIPT, new ElementInfo(true,
                ElementInfo.END_ELEMENT_REQUIRED, null, true));
        elementInfos.put(HtmlProperties.SCRIPT, onlyText);
        elementInfos.put(HtmlProperties.STYLE, onlyText);
        elementInfos.put(HtmlProperties.TEXTAREA, onlyTextDE);
        elementInfos.put(HtmlProperties.IMG, forbiddenEndElement);
        elementInfos.put(HtmlProperties.META, forbiddenEndElement);
        elementInfos.put(HtmlProperties.LINK, forbiddenEndElement);
        elementInfos.put(HtmlProperties.BASE, forbiddenEndElement);
        elementInfos.put(HtmlProperties.INPUT, forbiddenEndElement);
        elementInfos.put(HtmlProperties.BUTTON, forbiddenEndElement);
        elementInfos.put(HtmlProperties.FRAME, forbiddenEndElement);
        elementInfos.put(HtmlProperties.BR, forbiddenEndElement);
        elementInfos.put(HtmlProperties.HR, forbiddenEndElement);
        elementInfos.put(HtmlProperties.EMBED, forbiddenEndElement);
        elementInfos.put(HtmlProperties.SPACER, forbiddenEndElement);
        elementInfos.put(HtmlProperties.P, paragraphElement);
        elementInfos.put(HtmlProperties.LI, optionalEndElement);
        elementInfos.put(HtmlProperties.DT, optionalEndElement);
        elementInfos.put(HtmlProperties.DD, optionalEndElement);
        elementInfos.put(HtmlProperties.CAPTION, optionalEndElement);
        elementInfos.put(HtmlProperties.TR, optionalEndElement);
        elementInfos.put(HtmlProperties.TH, tableCellElement);
        elementInfos.put(HtmlProperties.TD, tableCellElement);
        elementInfos.put(HtmlProperties.HEAD, headElement);
        elementInfos.put(HtmlProperties.OPTION, optionElement);
        elementInfos.put(HtmlProperties.A, optionalEndElement);
        elementInfos.put(HtmlProperties.ANCHOR, optionalEndElement);
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
        builders.put(HtmlProperties.DIV, div);
        builders.put(HtmlProperties.DL, div);
        builders.put(HtmlProperties.HEADER, div);
        builders.put(HtmlProperties.FOOTER, div);
        builders.put(HtmlProperties.ARTICLE, div);
        builders.put(HtmlProperties.EMBED, div);

        PBuilder p = new PBuilder();
        builders.put(HtmlProperties.P, p);
        builders.put(HtmlProperties.SECTION, p);

        BlockquoteBuilder bq = new BlockquoteBuilder();
        builders.put(HtmlProperties.BLOCKQUOTE, bq);
        builders.put(HtmlProperties.DD, bq);

        AnchorBuilder a = new AnchorBuilder();
        builders.put(HtmlProperties.A, a);
        builders.put(HtmlProperties.ANCHOR, a);

        CodeBuilder code = new CodeBuilder();
        builders.put(HtmlProperties.CODE, code);
        builders.put(HtmlProperties.KBD, code);
        builders.put(HtmlProperties.SAMP, code);
        builders.put(HtmlProperties.SOURCE, code);

        EmBuilder em = new EmBuilder();
        builders.put(HtmlProperties.I, em);
        builders.put(HtmlProperties.EM, em);
        builders.put(HtmlProperties.CITE, em);
        builders.put(HtmlProperties.DFN, em);
        builders.put(HtmlProperties.VAR, em);

        HeadingBuilder heading = new HeadingBuilder();
        builders.put(HtmlProperties.H1, heading);
        builders.put(HtmlProperties.H2, heading);
        builders.put(HtmlProperties.H3, heading);
        builders.put(HtmlProperties.H4, heading);
        builders.put(HtmlProperties.H5, heading);
        builders.put(HtmlProperties.H6, heading);

        StrikeBuilder strike = new StrikeBuilder();
        builders.put(HtmlProperties.STRIKE, strike);
        builders.put(HtmlProperties.S, strike);

        builders.put(HtmlProperties.HTML, new HtmlBuilder());
        builders.put(HtmlProperties.HEAD, new HeadBuilder());
        builders.put(HtmlProperties.TITLE, new TitleBuilder());
        builders.put(HtmlProperties.BASE, new BaseBuilder());
        builders.put(HtmlProperties.BODY, new BodyBuilder());
        builders.put(HtmlProperties.CENTER, new CenterBuilder());
        builders.put(HtmlProperties.CAPTION, new CaptionBuilder());
        builders.put(HtmlProperties.PRE, new PreBuilder());
        builders.put(HtmlProperties.SPAN, new SpanBuilder());
        builders.put(HtmlProperties.SCRIPT, new ScriptBuilder());
        builders.put(HtmlProperties.IMG, new ImgBuilder());
        builders.put(HtmlProperties.STYLE, new StyleBuilder());
        builders.put(HtmlProperties.LINK, new LinkBuilder());
        builders.put(HtmlProperties.TABLE, new TableBuilder());
        builders.put(HtmlProperties.TD, new TdBuilder());
        builders.put(HtmlProperties.TH, new ThBuilder());
        builders.put(HtmlProperties.TR, new TrBuilder());
        builders.put(HtmlProperties.FORM, new FormBuilder());
        builders.put(HtmlProperties.INPUT, new InputBuilder());
        builders.put(HtmlProperties.BUTTON, new ButtonBuilder());
        builders.put(HtmlProperties.TEXTAREA, new TextareaBuilder());
        builders.put(HtmlProperties.SELECT, new SelectBuilder());
        builders.put(HtmlProperties.OPTION, new OptionBuilder());
        builders.put(HtmlProperties.FRAMESET, new FramesetBuilder());
        builders.put(HtmlProperties.FRAME, new FrameBuilder());
        builders.put(HtmlProperties.IFRAME, new IFrameBuilder());
        builders.put(HtmlProperties.UL, new UlBuilder());
        builders.put(HtmlProperties.OL, new OlBuilder());
        builders.put(HtmlProperties.LI, new LiBuilder());
        builders.put(HtmlProperties.HR, new HrBuilder());
        builders.put(HtmlProperties.BR, new BrBuilder());
        builders.put(HtmlProperties.OBJECT, new HtmlObjectBuilder());
        builders.put(HtmlProperties.APPLET, new AppletBuilder());
        builders.put(HtmlProperties.FONT, new FontBuilder());
        builders.put(HtmlProperties.BASEFONT, new BaseFontBuilder());
        builders.put(HtmlProperties.TT, new TtBuilder());
        builders.put(HtmlProperties.SMALL, new SmallBuilder());
        builders.put(HtmlProperties.BIG, new BigBuilder());
        builders.put(HtmlProperties.B, new StrongBuilder());
        builders.put(HtmlProperties.STRONG, new StrongBuilder());
        builders.put(HtmlProperties.NAV, new NavBuilder());
        builders.put(HtmlProperties.U, new UnderlineBuilder());
        builders.put(HtmlProperties.SUP, new SupBuilder());
        builders.put(HtmlProperties.SUB, new SubBuilder());
        builders.put(HtmlProperties.CANVAS, new CanvasBuilder());
        builders.put(HtmlProperties.AUDIO, new AudioBuilder());
        builders.put(HtmlProperties.COMMAND, new CommandBuilder());
        builders.put(HtmlProperties.DETAILS, new DetailsBuilder());
        builders.put(HtmlProperties.KEYGEN, new KeygenBuilder());
        builders.put(HtmlProperties.MARQUEE, new MarqueeBuilder());
        builders.put(HtmlProperties.METER, new MeterBuilder());
        builders.put(HtmlProperties.PROGRESS, new ProgressBuilder());
        builders.put(HtmlProperties.OUTPUT, new OutputBuilder());
        builders.put(HtmlProperties.TIME, new TimeBuilder());
        builders.put(HtmlProperties.VIDEO, new VideoBuilder());
        return builders;
    }
}
