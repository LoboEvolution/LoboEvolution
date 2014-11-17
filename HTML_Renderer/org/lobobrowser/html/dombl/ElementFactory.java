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

import org.lobobrowser.html.builder.*;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementBuilder;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.w3c.HTMLElement;
import org.w3c.dom.DOMException;

public class ElementFactory {
	private final Map<String, Object> builders = new HashMap<String, Object>(80);

	private ElementFactory() {
		// This sets up builders for each known element tag.
		Map<String, Object> builders = this.builders;
		
		DivBulder div = new DivBulder();
		builders.put("DIV", div);
		builders.put("DL", div);
		builders.put("HEADER", div);
		builders.put("FOOTER", div);
		builders.put("ARTICLE", div);
				
		PBuilder p = new PBuilder();
		builders.put("P", p);
		builders.put("SECTION",p);

		BlockquoteBuilder bq = new BlockquoteBuilder();
		builders.put("BLOCKQUOTE", bq);
		builders.put("DD", bq);
		
		AnchorBuilder a = new AnchorBuilder();
		builders.put("A", a);
		builders.put("ANCHOR", a);
		
		CodeBuilder code = new CodeBuilder();
		builders.put("CODE", code);
		builders.put("KBD", code);
		builders.put("SAMP", code);
		
		EmBuilder em = new EmBuilder();
		builders.put("I", em);
		builders.put("EM", em);
		builders.put("CITE", em);
		builders.put("DFN", em);
		builders.put("VAR", em);

		HeadingBuilder heading = new HeadingBuilder();
		builders.put("H1", heading);
		builders.put("H2", heading);
		builders.put("H3", heading);
		builders.put("H4", heading);
		builders.put("H5", heading);
		builders.put("H6", heading);
		
		StrikeBuilder strike = new StrikeBuilder();
		builders.put("STRIKE", strike);
		builders.put("S", strike);
		
		builders.put("HTML", new HtmlBuilder());
		builders.put("TITLE", new TitleBuilder());
		builders.put("BASE", new BaseBuilder());
		builders.put("BODY", new BodyBuilder());
		builders.put("CENTER", new CenterBuilder());
		builders.put("CAPTION", new CaptionBuilder());
		builders.put("PRE", new PreBuilder());
		builders.put("SPAN", new SpanBuilder());
		builders.put("SCRIPT", new ScriptBuilder());
		builders.put("IMG", new ImgBuilder());
		builders.put("STYLE", new StyleBuilder());
		builders.put("LINK", new LinkBuilder());
		builders.put("TABLE", new TableBuilder());
		builders.put("TD", new TdBuilder());
		builders.put("TH", new ThBuilder());
		builders.put("TR", new TrBuilder());
		builders.put("FORM", new FormBuilder());
		builders.put("INPUT", new InputBuilder());
		builders.put("BUTTON", new ButtonBuilder());
		builders.put("TEXTAREA", new TextareaBuilder());
		builders.put("SELECT", new SelectBuilder());
		builders.put("OPTION", new OptionBuilder());
		builders.put("FRAMESET", new FramesetBuilder());
		builders.put("FRAME", new FrameBuilder());
		builders.put("IFRAME", new IFrameBuilder());
		builders.put("UL", new UlBuilder());
		builders.put("OL", new OlBuilder());
		builders.put("LI", new LiBuilder());
		builders.put("HR", new HrBuilder());
		builders.put("BR", new BrBuilder());
		builders.put("OBJECT", new HtmlObjectBuilder());
		builders.put("APPLET", new AppletBuilder());
		builders.put("EMBED", new NonStandardBuilder());
		builders.put("FONT", new FontBuilder());
		builders.put("BASEFONT", new BaseFontBuilder());
		builders.put("TT", new TtBuilder());
		builders.put("SMALL", new SmallBuilder());
		builders.put("BIG", new BigBuilder());
		builders.put("B", new StrongBuilder());
		builders.put("STRONG", new StrongBuilder());
		builders.put("NAV", new NavBuilder());
		builders.put("U", new UnderlineBuilder());
		builders.put("SUP", new SupBuilder());
		builders.put("SUB", new SubBuilder());
		builders.put("CANVAS", new CanvasBuilder());
		
		
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
