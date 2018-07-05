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

import java.net.MalformedURLException;
import java.net.URL;

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.ID;
import org.loboevolution.w3c.html.ApplicationCache;
import org.loboevolution.w3c.html.BarProp;
import org.loboevolution.w3c.html.HTMLCollection;
import org.loboevolution.w3c.html.Selection;
import org.loboevolution.w3c.html.UndoManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;

/**
 * The Class Window.
 */
public class Window extends WindowTimers implements AbstractView {

	/** The rcontext. */
	private HtmlRendererContext rcontext;

	/** The ua context. */
	private final UserAgentContext uaContext;

	/** The navigator. */
	private Navigator navigator;

	/** The screen. */
	private Screen screen;

	/** The location. */
	private Location location;

	/** The history. */
	private History history;

	/** The length. */
	private int length;

	/** The name. */
	private String name;

	/** The length set. */
	private boolean lengthSet = false;

	/** The name set. */
	private boolean nameSet = false;

	/**
	 * Instantiates a new window.
	 *
	 * @param rcontext
	 *            the rcontext
	 * @param uaContext
	 *            the ua context
	 */
	public Window(HtmlRendererContext rcontext, UserAgentContext uaContext) {
		super(uaContext);
		this.rcontext = rcontext;
		this.uaContext = uaContext;
		setWindowImpl(this);
	}

	public DocumentView getDocument() {
		return this.getWindowDocument();
	}

	public Window getWindow() {
		return this;
	}

	public Window getSelf() {
		return this;
	}

	public String getName() {
		if (this.nameSet) {
			return this.name;
		} else {
			HtmlRendererContext rctext = this.rcontext;
			if (rctext != null) {
				return rctext.getName();
			} else {
				return null;
			}
		}
	}

	public void setName(String name) {
		this.nameSet = true;
		this.name = name;
	}

	public Location getLocation() {
		synchronized (this) {
			Location location = this.location;
			if (location == null) {
				location = new Location(this);
				this.location = location;
			}
			return location;
		}
	}

	public void setLocation(String location) {
		this.getLocation().setHref(location);
	}

	public History getHistory() {
		synchronized (this) {
			History history = this.history;
			if (history == null) {
				history = new History(this);
				this.history = history;
			}
			return history;
		}
	}
	
	public Screen getScreen() {
		synchronized (this) {
			Screen nav = this.screen;
			if (nav == null) {
				nav = new Screen();
				this.screen = nav;
			}
			return nav;
		}
	}

	public UndoManager undoManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	public BarProp locationbar() {
		// TODO Auto-generated method stub
		return null;
	}

	public BarProp menubar() {
		// TODO Auto-generated method stub
		return null;
	}

	public BarProp personalbar() {
		// TODO Auto-generated method stub
		return null;
	}

	public BarProp scrollbars() {
		// TODO Auto-generated method stub
		return null;
	}

	public BarProp statusbar() {
		// TODO Auto-generated method stub
		return null;
	}

	public BarProp toolbar() {
		// TODO Auto-generated method stub
		return null;
	}

	public void close() {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			rctext.close();
		}
	}

	public void focus() {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			rctext.focus();
		}
	}

	public void blur() {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			rctext.blur();
		}
	}

	public HTMLCollection getFrames() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getFrames();
		}
		return null;
	}

	public int getLength() {
		if (this.lengthSet) {
			return this.length;
		} else {
			HTMLCollection frames = this.getFrames();
			return frames == null ? 0 : frames.getLength();
		}
	}

	public void setLength(int length) {
		this.lengthSet = true;
		this.length = length;
	}

	public Window getTop() {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			return getWindow(rctext.getTop());
		} else {
			return null;
		}
	}

	public Window getOpener() {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			return getWindow(rctext.getOpener());
		} else {
			return null;
		}
	}

	public void setOpener(Window opener) {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			if (opener == null) {
				rctext.setOpener(null);
			} else {
				Window impl = (Window) opener;
				rctext.setOpener(impl.rcontext);
			}
		}
	}

	public Window getParent() {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			return Window.getWindow(rctext.getParent());
		} else {
			return null;
		}
	}

	public Element getFrameElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public void open() {
		this.open("", "window:" + String.valueOf(ID.generateLong()));
	}

	public void open(String url) {
		this.open(url, "window:" + String.valueOf(ID.generateLong()));
	}

	public void open(String url, String windowName) {
		this.open(url, windowName, null, false);
	}

	public void open(String url, String windowName, String features) {
		this.open(url, windowName, features, false);
	}

	public void open(String relativeUrl, String windowName, String windowFeatures, boolean replace) {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			URL url;
			Object document = this.getWindowDocument();
			if (document instanceof HTMLDocumentImpl) {
				url = ((HTMLDocumentImpl) document).getFullURL(relativeUrl);
			} else {
				try {
					url = new URL(relativeUrl);
				} catch (MalformedURLException mfu) {
					throw new IllegalArgumentException("Malformed URI: " + relativeUrl);
				}
			}
			rctext.open(url, windowName, windowFeatures, replace);
		}
	}

	public String replace() {
		// TODO Auto-generated method stub
		return null;
	}

	public Navigator getNavigator() {
		synchronized (this) {
			Navigator nav = this.navigator;
			if (nav == null) {
				nav = new Navigator(this.uaContext);
				this.navigator = nav;
			}
			return nav;
		}
	}

	public ApplicationCache applicationCache() {
		// TODO Auto-generated method stub
		return null;
	}

	//
	public void alert(String message) {
		if (this.rcontext != null) {
			this.rcontext.alert(message);
		}
	}

	public boolean confirm(String message) {
		HtmlRendererContext rctext = this.rcontext;
		if (rctext != null) {
			return rctext.confirm(message);
		} else {
			return false;
		}
	}

	public String prompt(String message) {
		return this.prompt(message, "");
	}

	public String prompt(String message, String inputDefault) {
		HtmlRendererContext rctext = getHtmlRendererContext();
		if (rctext != null) {
			return rctext.prompt(message, inputDefault);
		} else {
			return null;
		}
	}

	public void print() {
		// TODO Auto-generated method stub

	}

	public Object showModalDialog(String url, Object arguments) {
		// TODO Auto-generated method stub
		return null;
	}

	public void postMessage(String message, String targetOrigin, Object transfer) {
		// TODO Auto-generated method stub

	}

	public HtmlRendererContext getRcontext() {
		return rcontext;
	}

	public UserAgentContext getUaContext() {
		return uaContext;
	}
}
