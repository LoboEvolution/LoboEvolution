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

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.style.CSS3Properties;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.w3c.html.HTMLElement;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class JSGlobalFunction extends WindowInternal {

	public JSGlobalFunction(UserAgentContext uaContext) {
		super(uaContext);
	}

	public Object eval(String javascript) {
		HTMLDocumentImpl document = this.getWindowDocument();
		if (document == null) {
			throw new IllegalStateException("Cannot evaluate if document is not set.");
		}
		Context ctx = Executor.createContext(document.getDocumentURL(), getUserAgentContext());
		try {
			Scriptable scope = this.getWindowScope();
			if (scope == null) {
				throw new IllegalStateException(
						"Scriptable (scope) instance was expected to be keyed as UserData to document using "
								+ Executor.SCOPE_KEY);
			}
			String scriptURI = "window.eval";
			return ctx.evaluateString(scope, javascript, scriptURI, 1, null);
		} finally {
			Context.exit();
		}
	}

	public void back() {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			rctext.back();
		}
	}

	public void scrollTo(int x, int y) {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			rctext.scroll(x, y);
		}
	}

	public void scrollBy(int x, int y) {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			rctext.scrollBy(x, y);
		}
	}

	public void resizeTo(int width, int height) {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			rctext.resizeTo(width, height);
		}
	}

	public void resizeBy(int byWidth, int byHeight) {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			rctext.resizeBy(byWidth, byHeight);
		}
	}

	public boolean isClosed() {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			return rctext.isClosed();
		} else {
			return false;
		}
	}

	public String getDefaultStatus() {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			return rctext.getDefaultStatus();
		} else {
			return null;
		}
	}

	public String getStatus() {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			return rctext.getStatus();
		} else {
			return null;
		}
	}

	public void setStatus(String message) {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			rctext.setStatus(message);
		}
	}

	public void addEventListener(String script, Function function) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).addEventListener(script, function);
		}
	}

	public void removeEventListener(String script, Function function) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).removeEventListener(script, function);
		}
	}

	public CSS3Properties getComputedStyle(HTMLElement element, String pseudoElement) {
		if (element instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) element).getComputedStyle(pseudoElement);
		} else {
			throw new IllegalArgumentException("Element implementation unknown: " + element);
		}
	}

	public CSS3Properties getComputedStyle(HTMLElement elt) {
		return getComputedStyle(elt, null);
	}

	public Node namedItem(String name) {
		HTMLDocumentImpl doc = this.getWindowDocument();
		if (doc == null) {
			return null;
		}
		Node node = doc.getElementById(name);
		if (node != null) {
			return node;
		}
		return null;
	}
}
