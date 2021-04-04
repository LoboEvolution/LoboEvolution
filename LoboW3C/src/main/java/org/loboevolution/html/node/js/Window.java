/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html.node.js;

import java.util.List;

import org.loboevolution.html.node.BarProp;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Selection;
import org.loboevolution.html.node.css.CSS3Properties;
import org.loboevolution.html.node.css.MediaQueryList;
import org.loboevolution.html.node.events.EventTarget;
import org.loboevolution.html.node.events.GlobalEventHandlers;
import org.loboevolution.html.node.history.History;
import org.loboevolution.html.node.js.console.WindowConsole;
import org.loboevolution.html.node.views.AbstractView;

import com.gargoylesoftware.css.dom.CSSRuleListImpl;
/**
 * A window containing a DOM document; the document property points to the DOM
 * document loaded in that window.
 *
 *
 *
 */
public interface Window
		extends EventTarget, GlobalEventHandlers, WindowBase64, WindowConsole,
		WindowEventHandlers, WindowOrWorkerGlobalScope, WindowTimers, AbstractView {
	
	
	/**
	 * <p>getClientInformation.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Navigator} object.
	 */
	Navigator getClientInformation();

	/**
	 * <p>isClosed.</p>
	 *
	 * @return a boolean.
	 */
	boolean isClosed();

	/**
	 * <p>getDefaultStatus.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDefaultStatus();

	/**
	 * <p>setDefaultStatus.</p>
	 *
	 * @param defaultStatus a {@link java.lang.String} object.
	 */
	void setDefaultStatus(String defaultStatus);

	/**
	 * <p>getDevicePixelRatio.</p>
	 *
	 * @return a double.
	 */
	double getDevicePixelRatio();

	/**
	 * <p>getDoNotTrack.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getDoNotTrack();

	/**
	 * <p>getHistory.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.history.History} object.
	 */
	History getHistory();

	/**
	 * <p>getInnerHeight.</p>
	 *
	 * @return a double.
	 */
	double getInnerHeight();

	/**
	 * <p>getInnerWidth.</p>
	 *
	 * @return a double.
	 */
	double getInnerWidth();

	/**
	 * <p>isIsSecureContext.</p>
	 *
	 * @return a boolean.
	 */
	boolean isIsSecureContext();

	/**
	 * <p>getLength.</p>
	 *
	 * @return a int.
	 */
	int getLength();

	/**
	 * <p>getLocation.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Location} object.
	 */
	Location getLocation();

	/**
	 * <p>setLocation.</p>
	 *
	 * @param location a {@link org.loboevolution.html.node.js.Location} object.
	 */
	void setLocation(Location location);

	/**
	 * <p>getLocationbar.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.BarProp} object.
	 */
	BarProp getLocationbar();

	/**
	 * <p>getMenubar.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.BarProp} object.
	 */
	BarProp getMenubar();

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * <p>setName.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	void setName(String name);

	/**
	 * <p>getNavigator.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Navigator} object.
	 */
	Navigator getNavigator();

	/**
	 * <p>setOffscreenBuffering.</p>
	 *
	 * @param offscreenBuffering a {@link java.lang.String} object.
	 */
	void setOffscreenBuffering(String offscreenBuffering);

	/**
	 * <p>setOffscreenBuffering.</p>
	 *
	 * @param offscreenBuffering a boolean.
	 */
	void setOffscreenBuffering(boolean offscreenBuffering);

	/**
	 * <p>setOpener.</p>
	 *
	 * @param opener a {@link java.lang.String} object.
	 */
	void setOpener(String opener);

	/**
	 * <p>getOuterHeight.</p>
	 *
	 * @return a double.
	 */
	double getOuterHeight();

	/**
	 * <p>getOuterWidth.</p>
	 *
	 * @return a double.
	 */
	double getOuterWidth();

	/**
	 * <p>getPageXOffset.</p>
	 *
	 * @return a double.
	 */
	double getPageXOffset();

	/**
	 * <p>getPageYOffset.</p>
	 *
	 * @return a double.
	 */
	double getPageYOffset();

	/**
	 * <p>getParent.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window getParent();

	/**
	 * <p>getPersonalbar.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.BarProp} object.
	 */
	BarProp getPersonalbar();

	/**
	 * <p>getScreen.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Screen} object.
	 */
	Screen getScreen();

	/**
	 * <p>getScreenLeft.</p>
	 *
	 * @return a double.
	 */
	double getScreenLeft();

	/**
	 * <p>getScreenTop.</p>
	 *
	 * @return a double.
	 */
	double getScreenTop();

	/**
	 * <p>getScreenX.</p>
	 *
	 * @return a double.
	 */
	double getScreenX();

	/**
	 * <p>getScreenY.</p>
	 *
	 * @return a double.
	 */
	double getScreenY();

	/**
	 * <p>getScrollX.</p>
	 *
	 * @return a double.
	 */
	double getScrollX();

	/**
	 * <p>getScrollY.</p>
	 *
	 * @return a double.
	 */
	double getScrollY();

	/**
	 * <p>getScrollbars.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.BarProp} object.
	 */
	BarProp getScrollbars();

	/**
	 * <p>getSelf.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window getSelf();

	/**
	 * <p>getStatus.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getStatus();

	/**
	 * <p>setStatus.</p>
	 *
	 * @param status a {@link java.lang.String} object.
	 */
	void setStatus(String status);

	/**
	 * <p>getStatusbar.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.BarProp} object.
	 */
	BarProp getStatusbar();

	//StyleMediaImpl getStyleMedia();

	/**
	 * <p>getToolbar.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.BarProp} object.
	 */
	BarProp getToolbar();

	/**
	 * <p>getTop.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window getTop();

	/**
	 * <p>getWindow.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window getWindow();

	/**
	 * <p>alert.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	void alert(String message);

	/**
	 * <p>alert.</p>
	 */
	void alert();

	/**
	 * <p>blur.</p>
	 */
	void blur();

	/**
	 * <p>close.</p>
	 */
	void close();

	/**
	 * <p>confirm.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	boolean confirm(String message);

	/**
	 * <p>confirm.</p>
	 *
	 * @return a boolean.
	 */
	boolean confirm();

	/**
	 * <p>focus.</p>
	 */
	void focus();

	/**
	 * <p>getComputedStyle.</p>
	 *
	 * @param elt a {@link org.loboevolution.html.node.Element} object.
	 * @param pseudoElt a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.css.CSS3Properties} object.
	 */
	CSS3Properties getComputedStyle(Element elt, String pseudoElt);

	/**
	 * <p>getComputedStyle.</p>
	 *
	 * @param elt a {@link org.loboevolution.html.node.Element} object.
	 * @return a {@link org.loboevolution.html.node.css.CSS3Properties} object.
	 */
	CSS3Properties getComputedStyle(Element elt);

	/**
	 * <p>getMatchedCSSRules.</p>
	 *
	 * @param elt a {@link org.loboevolution.html.node.Element} object.
	 * @param pseudoElt a {@link java.lang.String} object.
	 * @return a {@link com.gargoylesoftware.css.dom.CSSRuleListImpl} object.
	 */
	CSSRuleListImpl getMatchedCSSRules(Element elt, String pseudoElt);

	/**
	 * <p>getMatchedCSSRules.</p>
	 *
	 * @param elt a {@link org.loboevolution.html.node.Element} object.
	 * @return a {@link com.gargoylesoftware.css.dom.CSSRuleListImpl} object.
	 */
	CSSRuleListImpl getMatchedCSSRules(Element elt);

	/**
	 * <p>getSelection.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Selection} object.
	 */
	Selection getSelection();

	/**
	 * <p>matchMedia.</p>
	 *
	 * @param query a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.css.MediaQueryList} object.
	 */
	MediaQueryList matchMedia(String query);

	/**
	 * <p>moveBy.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 */
	void moveBy(double x, double y);

	/**
	 * <p>moveTo.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 */
	void moveTo(double x, double y);

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param target a {@link java.lang.String} object.
	 * @param features a {@link java.lang.String} object.
	 * @param replace a boolean.
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window open(String url, String target, String features, boolean replace);

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param target a {@link java.lang.String} object.
	 * @param features a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window open(String url, String target, String features);

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param target a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window open(String url, String target);

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window open(String url);

	/**
	 * <p>open.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window open();

	/**
	 * <p>postMessage.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param targetOrigin a {@link java.lang.String} object.
	 */
	void postMessage(String message, String targetOrigin);

	/**
	 * <p>print.</p>
	 */
	void print();

	/**
	 * <p>prompt.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param _default a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	String prompt(String message, String _default);

	/**
	 * <p>prompt.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	String prompt(String message);

	/**
	 * <p>prompt.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String prompt();

	/**
	 * <p>resizeBy.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 */
	void resizeBy(double x, double y);

	/**
	 * <p>resizeTo.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 */
	void resizeTo(double x, double y);

	/**
	 * <p>scroll.</p>
	 */
	void scroll();

	/**
	 * <p>scroll.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 */
	void scroll(double x, double y);

	/**
	 * <p>scrollBy.</p>
	 */
	void scrollBy();

	/**
	 * <p>scrollBy.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 */
	void scrollBy(double x, double y);

	/**
	 * <p>scrollTo.</p>
	 */
	void scrollTo();

	/**
	 * <p>scrollTo.</p>
	 *
	 * @param x a double.
	 * @param y a double.
	 */
	void scrollTo(double x, double y);

	/**
	 * <p>stop.</p>
	 */
	void stop();

	/**
	 * <p>get.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.node.js.Window} object.
	 */
	Window get(int index);
	
	/**
	 * <p>getMsg.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	List<String> getMsg();
}
