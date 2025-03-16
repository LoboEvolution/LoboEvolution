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

package org.loboevolution.html.renderer.layout;

import org.loboevolution.html.HTMLTag;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>RLayout class.</p>
 */
public class RLayout {

	/** Constant elementLayout */
	public static final Map<HTMLTag, MarkupLayout> elementLayout = new HashMap<>();

	static {
		final Map<HTMLTag, MarkupLayout> el = elementLayout;

		final InLineLayout inline = new InLineLayout();
		el.put(HTMLTag.A, inline);
		el.put(HTMLTag.ANCHOR, inline);
		el.put(HTMLTag.B, inline);
		el.put(HTMLTag.CITE, inline);
		el.put(HTMLTag.CODE, inline);
		el.put(HTMLTag.EM, inline);
		el.put(HTMLTag.I, inline);
		el.put(HTMLTag.KBD, inline);
		el.put(HTMLTag.SAMP, inline);
		el.put(HTMLTag.STRONG, inline);
		el.put(HTMLTag.STRIKE, inline);
		el.put(HTMLTag.SPAN, inline);
		el.put(HTMLTag.TH, inline);
		el.put(HTMLTag.U, inline);
		el.put(HTMLTag.VAR, inline);

		final BlockLayout block = new BlockLayout();
		el.put(HTMLTag.ARTICLE, block);
		el.put(HTMLTag.BODY, block);
		el.put(HTMLTag.BLOCKQUOTE, block);
		el.put(HTMLTag.CAPTION, block);
		el.put(HTMLTag.CENTER, block);
		el.put(HTMLTag.DD, block);
		el.put(HTMLTag.DIV, block);
		el.put(HTMLTag.ADDRESS, block);
		el.put(HTMLTag.DL, block);
		el.put(HTMLTag.DT, block);
		el.put(HTMLTag.H1, block);
		el.put(HTMLTag.H2, block);
		el.put(HTMLTag.H3, block);
		el.put(HTMLTag.H4, block);
		el.put(HTMLTag.H5, block);
		el.put(HTMLTag.H6, block);
		el.put(HTMLTag.HTML, block);
		el.put(HTMLTag.MAIN, block);
		el.put(HTMLTag.PRE, block);
		el.put(HTMLTag.DETAILS, block);
		el.put(HTMLTag.P, block);

		el.put(HTMLTag.BR,new BrLayout());

		el.put(HTMLTag.NOSCRIPT,new NoScriptLayout());
		final NopLayout nop = new NopLayout();
		el.put(HTMLTag.SCRIPT,nop);
		el.put(HTMLTag.HEAD,nop);
		el.put(HTMLTag.TITLE,nop);
		el.put(HTMLTag.META,nop);
		el.put(HTMLTag.STYLE,nop);
		el.put(HTMLTag.LINK,nop);
		el.put(HTMLTag.IMG,new ImgLayout());
		el.put(HTMLTag.TABLE,new TableLayout());
		el.put(HTMLTag.TR, new TableRowLayout());
		el.put(HTMLTag.TD, new TableCellLayout());
		el.put(HTMLTag.INPUT,new InputLayout());
		el.put(HTMLTag.TEXTAREA,new TextAreaLayout());
		el.put(HTMLTag.SELECT,new SelectLayout());
		el.put(HTMLTag.BUTTON, new ButtonLayout());
		final ListItemLayout list = new ListItemLayout();
		el.put(HTMLTag.UL,list);
		el.put(HTMLTag.OL,list);
		el.put(HTMLTag.LI,list);

		final ObjectLayout ol = new ObjectLayout(false);
		el.put(HTMLTag.OBJECT,new ObjectLayout(true));
		el.put(HTMLTag.APPLET,ol);
		el.put(HTMLTag.EMBED,ol);
		el.put(HTMLTag.CANVAS,new CanvasLayout());
		el.put(HTMLTag.SVG,new SVGLayout());
		el.put(HTMLTag.IFRAME,new IFrameLayout());
		el.put(HTMLTag.RSS,new RSSLayout());
	}
}
