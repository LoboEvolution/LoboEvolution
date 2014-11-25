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
/*
 * Created on Oct 8, 2005
 */
package org.lobobrowser.html.dombl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.builder.*;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.w3c.HTMLElement;
import org.w3c.dom.DOMException;

public class ElementFactory {
	private final Map<String, Object> builders = new HashMap<String, Object>(80);

	private ElementFactory() {
		// This sets up builders for each known element tag.
		Map<String, Object> builders = this.builders;
		
		DivBulder div = new DivBulder();
		builders.put(HtmlProperties.DIV, div);
		builders.put(HtmlProperties.DL, div);
		builders.put(HtmlProperties.HEADER, div);
		builders.put(HtmlProperties.FOOTER, div);
		builders.put(HtmlProperties.ARTICLE, div);
				
		PBuilder p = new PBuilder();
		builders.put(HtmlProperties.P, p);
		builders.put(HtmlProperties.SECTION,p);

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
		builders.put(HtmlProperties.EMBED, new NonStandardBuilder());
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
		
		
	}

	private static ElementFactory instance = new ElementFactory();

	public static ElementFactory getInstance() {
		return instance;
	}

	public final HTMLElement createElement(HTMLDocumentImpl document,String name) throws DOMException {
		String normalName = name.toUpperCase(Locale.ENGLISH);
		// No need to synchronize; read-only map at this point.
		HTMLElementBuilder builder = (HTMLElementBuilder) this.builders.get(normalName);
		if (builder == null) {
			// TODO: IE would assume name is html text here?
			HTMLElementImpl element = new HTMLElementImpl(name);
			element.setOwnerDocument(document);
			return element;
		} else {
			return builder.create(document, name);
		}
	}
}
