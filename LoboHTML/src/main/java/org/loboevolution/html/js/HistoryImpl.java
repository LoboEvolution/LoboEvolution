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
package org.loboevolution.html.js;

import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.node.history.History;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.type.ScrollRestoration;

/**
 * The History class.
 */
public class HistoryImpl extends AbstractScriptableDelegate implements History {
	private final WindowImpl window;

	public HistoryImpl(WindowImpl window) {
		this.window = window;
	}

	/** {@inheritDoc} */
	@Override
	public void back() {
		final HtmlRendererContext ctx = this.window.getHtmlRendererContext();
		if (ctx != null) {
			ctx.back();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void forward() {
		final HtmlRendererContext ctx = this.window.getHtmlRendererContext();
		if (ctx != null) {
			ctx.forward();
		}
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		final HtmlRendererContext ctx = this.window.getHtmlRendererContext();
		return ctx != null ? ctx.getHistoryLength() : 0;
	}

	/** {@inheritDoc} */
	@Override
	public void go(int offset) {
		final HtmlRendererContext ctx = this.window.getHtmlRendererContext();
		if (ctx != null) {
			ctx.moveInHistory(offset);
		}
	}

	/** {@inheritDoc} */
	@Override
	public ScrollRestoration getScrollRestoration() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setScrollRestoration(ScrollRestoration scrollRestoration) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void go() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void pushState(Object data, String title, String url) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void pushState(Object data, String title) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void replaceState(Object data, String title, String url) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void replaceState(Object data, String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		return "[object History]";
	}
}
