/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
import org.lobobrowser.html.layout.SVGLayout;
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
public class HtmlLayoutMapping implements HtmlProperties {

	/**
	 * Layout.
	 *
	 * @return the map
	 */
	public static Map<String, Object> layout() {

		Map<String, Object> el = new HashMap<String, Object>();

		EmLayout em = new EmLayout();
		el.put(I, em);
		el.put(EM, em);
		el.put(CITE, em);
		el.put(H1, new HLayout(24));
		el.put(H2, new HLayout(18));
		el.put(H3, new HLayout(15));
		el.put(H4, new HLayout(12));
		el.put(H5, new HLayout(10));
		el.put(H6, new HLayout(8));

		StrongLayout strong = new StrongLayout();
		el.put(B, strong);
		el.put(STRONG, strong);
		el.put(TH, strong);
		el.put(U, new ULayout());
		el.put(STRIKE, new StrikeLayout());
		el.put(BR, new BrLayout());
		el.put(P, new PLayout());
		el.put(SECTION, new PLayout());
		el.put(NOSCRIPT, new NoScriptLayout());

		NopLayout nop = new NopLayout();
		el.put(SCRIPT, nop);
		el.put(TITLE, nop);
		el.put(META, nop);
		el.put(STYLE_HTML, nop);
		el.put(LINK_HTML, nop);
		el.put(HEAD, new HeadLayout());
		el.put(IMG, new ImgLayout());
		el.put(TABLE, new TableLayout());

		ChildrenLayout children = new ChildrenLayout();
		el.put(HTML, children);

		AnchorLayout anchor = new AnchorLayout();
		el.put(A, anchor);
		el.put(ANCHOR, anchor);
		el.put(INPUT_HTML, new InputLayout());
		el.put(BUTTON, new ButtonLayout());
		el.put(TEXTAREA, new TextAreaLayout());
		el.put(SELECT, new SelectLayout());

		ListItemLayout list = new ListItemLayout();
		el.put(UL, list);
		el.put(OL, list);
		el.put(LI, list);

		CommonBlockLayout cbl = new CommonBlockLayout();
		el.put(PRE, cbl);
		el.put(CENTER, cbl);
		el.put(CAPTION, cbl);
		el.put(NAV, cbl);

		DivLayout div = new DivLayout();
		el.put(DIV, div);
		el.put(BODY, div);
		el.put(DL, div);
		el.put(DT, div);
		el.put(HEADER, div);
		el.put(FOOTER, div);
		el.put(ARTICLE, div);
		el.put(LABEL, div);

		BlockQuoteLayout bq = new BlockQuoteLayout();
		el.put(BLOCKQUOTE, bq);
		el.put(DD, bq);
		el.put(HR, new HrLayout());
		el.put(SPAN, new SpanLayout());

		ObjectLayout ol = new ObjectLayout(false, true);
		el.put(OBJECT, new ObjectLayout(true, true));
		el.put(APPLET, ol);
		el.put(EMBED, ol);
		el.put(IFRAME, new IFrameLayout());
		el.put(CANVAS, new CanvasLayout());
		el.put(SVG, new SVGLayout());

		return el;

	}
}
