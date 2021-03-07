package org.loboevolution.html.node.history;

import org.loboevolution.jsenum.ScrollRestoration;

/**
 * Allows manipulation of the browser session history, that is the pages visited
 * in the tab or frame that the current page is loaded in.
 */
public interface History {

	int getLength();

	ScrollRestoration getScrollRestoration();

	void setScrollRestoration(ScrollRestoration scrollRestoration);

	void back();

	void forward();

	void go(int delta);

	void go();

	void pushState(Object data, String title, String url);

	void pushState(Object data, String title);

	void replaceState(Object data, String title, String url);

	void replaceState(Object data, String title);

}
