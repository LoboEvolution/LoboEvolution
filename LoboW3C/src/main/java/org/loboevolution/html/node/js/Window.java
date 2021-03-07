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
 */
public interface Window
		extends EventTarget, GlobalEventHandlers, WindowBase64, WindowConsole,
		WindowEventHandlers, WindowOrWorkerGlobalScope, WindowTimers, AbstractView {
	
	
	Navigator getClientInformation();

	boolean isClosed();

	String getDefaultStatus();

	void setDefaultStatus(String defaultStatus);

	double getDevicePixelRatio();

	String getDoNotTrack();

	History getHistory();

	double getInnerHeight();

	double getInnerWidth();

	boolean isIsSecureContext();

	int getLength();

	Location getLocation();

	void setLocation(Location location);

	BarProp getLocationbar();

	BarProp getMenubar();

	String getName();

	void setName(String name);

	Navigator getNavigator();

	void setOffscreenBuffering(String offscreenBuffering);

	void setOffscreenBuffering(boolean offscreenBuffering);

	void setOpener(String opener);

	double getOuterHeight();

	double getOuterWidth();

	double getPageXOffset();

	double getPageYOffset();

	Window getParent();

	BarProp getPersonalbar();

	Screen getScreen();

	double getScreenLeft();

	double getScreenTop();

	double getScreenX();

	double getScreenY();

	double getScrollX();

	double getScrollY();

	BarProp getScrollbars();

	Window getSelf();

	String getStatus();

	void setStatus(String status);

	BarProp getStatusbar();

	//StyleMediaImpl getStyleMedia();

	BarProp getToolbar();

	Window getTop();

	Window getWindow();

	void alert(String message);

	void alert();

	void blur();

	void close();

	boolean confirm(String message);

	boolean confirm();

	void focus();

	CSS3Properties getComputedStyle(Element elt, String pseudoElt);

	CSS3Properties getComputedStyle(Element elt);

	CSSRuleListImpl getMatchedCSSRules(Element elt, String pseudoElt);

	CSSRuleListImpl getMatchedCSSRules(Element elt);

	Selection getSelection();

	MediaQueryList matchMedia(String query);

	void moveBy(double x, double y);

	void moveTo(double x, double y);

	Window open(String url, String target, String features, boolean replace);

	Window open(String url, String target, String features);

	Window open(String url, String target);

	Window open(String url);

	Window open();

	void postMessage(String message, String targetOrigin);

	void print();

	String prompt(String message, String _default);

	String prompt(String message);

	String prompt();

	void resizeBy(double x, double y);

	void resizeTo(double x, double y);

	void scroll();

	void scroll(double x, double y);

	void scrollBy();

	void scrollBy(double x, double y);

	void scrollTo();

	void scrollTo(double x, double y);

	void stop();

	Window get(int index);
	
	List<String> getMsg();
}
