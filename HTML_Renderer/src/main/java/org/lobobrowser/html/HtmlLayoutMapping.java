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
import java.util.Map;

import org.lobobrowser.html.layout.AnchorLayout;
import org.lobobrowser.html.layout.BlockQuoteLayout;
import org.lobobrowser.html.layout.BrLayout;
import org.lobobrowser.html.layout.ButtonLayout;
import org.lobobrowser.html.layout.CanvasLayout;
import org.lobobrowser.html.layout.ChildrenLayout;
import org.lobobrowser.html.layout.CommonBlockLayout;
import org.lobobrowser.html.layout.DivLayout;
import org.lobobrowser.html.layout.EmLayout;
import org.lobobrowser.html.layout.HLayout;
import org.lobobrowser.html.layout.HeadLayout;
import org.lobobrowser.html.layout.HrLayout;
import org.lobobrowser.html.layout.IFrameLayout;
import org.lobobrowser.html.layout.ImgLayout;
import org.lobobrowser.html.layout.InputLayout;
import org.lobobrowser.html.layout.ListItemLayout;
import org.lobobrowser.html.layout.NoScriptLayout;
import org.lobobrowser.html.layout.NopLayout;
import org.lobobrowser.html.layout.ObjectLayout;
import org.lobobrowser.html.layout.PLayout;
import org.lobobrowser.html.layout.SelectLayout;
import org.lobobrowser.html.layout.SpanLayout;
import org.lobobrowser.html.layout.StrikeLayout;
import org.lobobrowser.html.layout.StrongLayout;
import org.lobobrowser.html.layout.TableLayout;
import org.lobobrowser.html.layout.TextAreaLayout;
import org.lobobrowser.html.layout.ULayout;

/**
 * The Class HtmlLayoutMapping.
 */
public class HtmlLayoutMapping {

	/**
	 * Layout.
	 *
	 * @return the map
	 */
	public static Map<String, Object> layout() {

		Map<String, Object> el = new HashMap<String, Object>();

		EmLayout em = new EmLayout();
		el.put(HtmlProperties.I, em);
		el.put(HtmlProperties.EM, em);
		el.put(HtmlProperties.CITE, em);
		el.put(HtmlProperties.H1, new HLayout(24));
		el.put(HtmlProperties.H2, new HLayout(18));
		el.put(HtmlProperties.H3, new HLayout(15));
		el.put(HtmlProperties.H4, new HLayout(12));
		el.put(HtmlProperties.H5, new HLayout(10));
		el.put(HtmlProperties.H6, new HLayout(8));

		StrongLayout strong = new StrongLayout();
		el.put(HtmlProperties.B, strong);
		el.put(HtmlProperties.STRONG, strong);
		el.put(HtmlProperties.TH, strong);
		el.put(HtmlProperties.U, new ULayout());
		el.put(HtmlProperties.STRIKE, new StrikeLayout());
		el.put(HtmlProperties.BR, new BrLayout());
		el.put(HtmlProperties.P, new PLayout());
		el.put(HtmlProperties.SECTION, new PLayout());
		el.put(HtmlProperties.NOSCRIPT, new NoScriptLayout());

		NopLayout nop = new NopLayout();
		el.put(HtmlProperties.SCRIPT, nop);
		el.put(HtmlProperties.TITLE, nop);
		el.put(HtmlProperties.META, nop);
		el.put(HtmlProperties.STYLE, nop);
		el.put(HtmlProperties.LINK, nop);
		el.put(HtmlProperties.HEAD, new HeadLayout());
		el.put(HtmlProperties.IMG, new ImgLayout());
		el.put(HtmlProperties.TABLE, new TableLayout());

		ChildrenLayout children = new ChildrenLayout();
		el.put(HtmlProperties.HTML, children);

		AnchorLayout anchor = new AnchorLayout();
		el.put(HtmlProperties.A, anchor);
		el.put(HtmlProperties.ANCHOR, anchor);
		el.put(HtmlProperties.INPUT, new InputLayout());
		el.put(HtmlProperties.BUTTON, new ButtonLayout());
		el.put(HtmlProperties.TEXTAREA, new TextAreaLayout());
		el.put(HtmlProperties.SELECT, new SelectLayout());

		ListItemLayout list = new ListItemLayout();
		el.put(HtmlProperties.UL, list);
		el.put(HtmlProperties.OL, list);
		el.put(HtmlProperties.LI, list);

		CommonBlockLayout cbl = new CommonBlockLayout();
		el.put(HtmlProperties.PRE, cbl);
		el.put(HtmlProperties.CENTER, cbl);
		el.put(HtmlProperties.CAPTION, cbl);
		el.put(HtmlProperties.NAV, cbl);

		DivLayout div = new DivLayout();
		el.put(HtmlProperties.DIV, div);
		el.put(HtmlProperties.BODY, div);
		el.put(HtmlProperties.DL, div);
		el.put(HtmlProperties.DT, div);
		el.put(HtmlProperties.HEADER, div);
		el.put(HtmlProperties.FOOTER, div);
		el.put(HtmlProperties.ARTICLE, div);

		BlockQuoteLayout bq = new BlockQuoteLayout();
		el.put(HtmlProperties.BLOCKQUOTE, bq);
		el.put(HtmlProperties.DD, bq);
		el.put(HtmlProperties.HR, new HrLayout());
		el.put(HtmlProperties.SPAN, new SpanLayout());

		ObjectLayout ol = new ObjectLayout(false, true);
		el.put(HtmlProperties.OBJECT, new ObjectLayout(true, true));
		el.put(HtmlProperties.APPLET, ol);
		el.put(HtmlProperties.EMBED, ol);
		el.put(HtmlProperties.IFRAME, new IFrameLayout());
		el.put(HtmlProperties.CANVAS, new CanvasLayout());

		return el;

	}
}
