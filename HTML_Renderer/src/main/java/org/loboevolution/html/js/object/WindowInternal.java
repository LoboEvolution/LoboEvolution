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
package org.loboevolution.html.js.object;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.js.JSFunction;
import org.loboevolution.http.UserAgentContext;
import org.w3c.dom.Document;

public class WindowInternal extends JSFunction {
	
	/** The window. */
	private Window window;
	
	/** The Constant CONTEXT_WINDOWS. */
	private static final Map<HtmlRendererContext, WeakReference<Window>> CONTEXT_WINDOWS = new WeakHashMap<HtmlRendererContext, WeakReference<Window>>();
	
	public WindowInternal(UserAgentContext uaContext) {
		super(uaContext);
	}

	public HtmlRendererContext getHtmlRendererContext() {
		return getWindowImpl().getRcontext();
	}
	
	public UserAgentContext getUserAgentContext() {
		return getWindowImpl().getUaContext();
	}

	public Document getDocumentNode() {
		return this.getWindowDocument();
	}
	
	public String getStatus() {
		HtmlRendererContext rctext = getWindowImpl().getRcontext();
		if (rctext != null) {
			return rctext.getStatus();
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the window.
	 *
	 * @param rcontext
	 *            the rcontext
	 * @return the window
	 */
	public static Window getWindow(HtmlRendererContext rcontext) {
		if (rcontext == null) {
			return null;
		}
		synchronized (CONTEXT_WINDOWS) {
			Reference<?> wref = CONTEXT_WINDOWS.get(rcontext);
			if (wref != null) {
				Window window = (Window) wref.get();
				if (window != null) {
					return window;
				}
			}
			Window window = new Window(rcontext, rcontext.getUserAgentContext());
			CONTEXT_WINDOWS.put(rcontext, new WeakReference<Window>(window));
			return window;
		}
	}
	
	public void forceGC() {
		System.gc();
	}

	public Window getWindowImpl() {
		return window;
	}
	
	public void setWindowImpl(Window window) {
		this.window = window;
	}

}
