/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
