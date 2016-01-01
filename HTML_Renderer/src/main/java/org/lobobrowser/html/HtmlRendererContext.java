/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Aug 28, 2005
 */
package org.lobobrowser.html;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Optional;

import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.w3c.html.HTMLAnchorElement;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLElement;

/**
 * The Interface HtmlRendererContext.
 */
public interface HtmlRendererContext {
    /**
     * Navigates to the location given. Implementations should retrieve the URL
     * content, parse it and render it.
     *
     * @param url
     *            The destination URL.
     * @param target
     *            Same as the target attribute in the HTML anchor tag, i.e.
     *            _top, _blank, etc.
     */
    void navigate(URL url, String target);

    /**
     * Performs a link click. Implementations should invoke
     * {@link #navigate(URL, String)}.
     *
     * @param linkNode
     *            The HTML node that was clicked.
     * @param url
     *            The destination URL.
     * @param target
     *            Same as the target attribute in the HTML anchor tag, i.e.
     *            _top, _blank, etc.
     */
    void linkClicked(org.lobobrowser.w3c.html.HTMLElement linkNode,
            URL url, String target);

    /** Gets the frames.
	 *
	 * @return the frames
	 */
    HTMLCollection getFrames();

    /**
     * Submits a HTML form. Note that when the the method is "GET", parameters
     * are still expected to be part of <code>formInputs</code>.
     *
     * @param method
     *            The request method, GET or POST.
     * @param action
     *            The destination URL.
     * @param target
     *            Same as the target attribute in the FORM tag, i.e. _blank,
     *            _top, etc.
     * @param enctype
     *            The encoding type.
     * @param formInputs
     *            An array of {@link org.lobobrowser.html.FormInput} instances.
     */
    void submitForm(String method, URL action, String target,
            String enctype, FormInput[] formInputs);

    /**
     * Creates a {@link org.lobobrowser.html.BrowserFrame} instance.
     *
     * @return the browser frame
     */
    BrowserFrame createBrowserFrame();

    /** Gets the user agent context.
	 *
	 * @return the user agent context
	 */
    UserAgentContext getUserAgentContext();

    /**
     * Gets a <code>HtmlObject</code> instance that implements a OBJECT tag from
     * HTML.
     *
     * @param element
     *            The DOM element for the object, which may either represent an
     *            OBJECT, EMBED or an APPLET tag.
     * @return Implementations of this method must return <code>null</code> if
     *         they have any problems producing a <code>HtmlObject</code>
     *         instance. This is particularly true of OBJECT tags, where inner
     *         HTML of the tag must be rendered if the OBJECT content cannot be
     *         handled.
     */
    HtmlObject getHtmlObject(HTMLElement element);

    /**
     * This method is called when a visual element is right-clicked.
     *
     * @param element
     *            The narrowest element enclosing the mouse location.
     * @param event
     *            The mouse event.
     * @return The method should return true to continue propagating the event,
     *         or false to stop propagating it.
     */
    boolean onContextMenu(HTMLElement element, MouseEvent event);

    /**
     * This method is called when there's a mouse click on an element.
     *
     * @param element
     *            The narrowest element enclosing the mouse location.
     * @param event
     *            The mouse event.
     * @return The method should return true to continue propagating the event,
     *         or false to stop propagating it.
     */
    boolean onMouseClick(HTMLElement element, MouseEvent event);

    /**
     * This method is called when there's a mouse double-click on an element.
     *
     * @param element
     *            The narrowest element enclosing the mouse location.
     * @param event
     *            The mouse event.
     * @return The method should return true to continue propagating the event,
     *         or false to stop propagating it.
     */
    boolean onDoubleClick(HTMLElement element, MouseEvent event);

    /**
     * This method is called when the mouse first hovers over an element.
     *
     * @param element
     *            The element that the mouse has just entered.
     * @param event
     *            The mouse event.
     */
    void onMouseOver(HTMLElement element, MouseEvent event);

    /**
     * This method is called when the mouse no longer hovers a given element.
     *
     * @param element
     *            The element that the mouse has just exited.
     * @param event
     *            The mouse event.
     */
    void onMouseOut(HTMLElement element, MouseEvent event);

    /** Checks if is image loading enabled.
	 *
	 * @return true, if is image loading enabled
	 */
    boolean isImageLoadingEnabled();

    //------Methods useful for Window implementation:

    /**
     * Opens an alert dialog.
     *
     * @param message
     *            Message shown by the dialog.
     */
    void alert(String message);

    /**
     * Goes to the previous page in the browser's history.
     */
    void back();

    /**
     * Relinquishes focus.
     */
    void blur();

    /**
     * Closes the browser window, provided this is allowed for the current
     * context.
     */
    void close();

    /**
     * Opens a confirmation dialog.
     *
     * @param message
     *            The message shown by the confirmation dialog.
     * @return True if the user selects YES.
     */
    boolean confirm(String message);

    /**
     * Requests focus for the current window.
     */
    void focus();

    /**
     * Opens a separate browser window and renders a URL.
     *
     * @param url
     *            The URL to be rendered.
     * @param windowName
     *            The name of the new window.
     * @param windowFeatures
     *            The features of the new window (same as in Javascript open
     *            method).
     * @param replace
     *            the replace
     * @return A new {@link org.lobobrowser.html.HtmlRendererContext} instance.
     */
    HtmlRendererContext open(URL url, String windowName,
            String windowFeatures, boolean replace);

    /**
     * Shows a prompt dialog.
     *
     * @param message
     *            The message shown by the dialog.
     * @param inputDefault
     *            The default input value.
     * @return The user's input value.
     */
    String prompt(String message, String inputDefault);

    /**
     * Scrolls the client area.
     *
     * @param x
     *            Document's x coordinate.
     * @param y
     *            Document's y coordinate.
     */
    void scroll(int x, int y);

    /**
     * Scrolls the client area.
     *
     * @param x
     *            Horizontal pixels to scroll.
     * @param y
     *            Vertical pixels to scroll.
     */
    void scrollBy(int x, int y);

    /**
     * Resizes the window.
     *
     * @param width
     *            The new width.
     * @param height
     *            The new height.
     */
    void resizeTo(int width, int height);

    /**
     * Resizes the window.
     *
     * @param byWidth
     *            The number of pixels to resize the width by.
     * @param byHeight
     *            The number of pixels to resize the height by.
     */
    void resizeBy(int byWidth, int byHeight);

    /** Checks if is closed.
	 *
	 * @return true, if is closed
	 */
    boolean isClosed();

    /** Gets the default status.
	 *
	 * @return the default status
	 */
    String getDefaultStatus();

    /** Sets the default status.
	 *
	 * @param value
	 *            the new default status
	 */
    void setDefaultStatus(String value);

    /** Gets the name.
	 *
	 * @return the name
	 */
    String getName();

    /** Gets the parent.
	 *
	 * @return the parent
	 */
    HtmlRendererContext getParent();

    /** Gets the opener.
	 *
	 * @return the opener
	 */
    HtmlRendererContext getOpener();

    /** Sets the opener.
	 *
	 * @param opener
	 *            the new opener
	 */
    void setOpener(HtmlRendererContext opener);

    /** Gets the status.
	 *
	 * @return the status
	 */
    String getStatus();

    /** Sets the status.
	 *
	 * @param message
	 *            the new status
	 */
    void setStatus(String message);

    /** Gets the top.
	 *
	 * @return the top
	 */
    HtmlRendererContext getTop();

    /**
     * It should return true if the link provided has been visited.
     *
     * @param link
     *            the link
     * @return true, if is visited link
     */
    boolean isVisitedLink(HTMLAnchorElement link);

    /**
     * Reloads the current document.
     */
    void reload();

    /** Gets the history length.
	 *
	 * @return the history length
	 */
    int getHistoryLength();

    /** Gets the current url.
	 *
	 * @return the current url
	 */
    String getCurrentURL();

    /** Gets the next url.
	 *
	 * @return the next url
	 */
    String getNextURL();

    /** Gets the previous url.
	 *
	 * @return the previous url
	 */
    String getPreviousURL();

    /**
     * Goes forward one page.
     */
    void forward();

    /**
     * Navigates the history according to the given offset.
     *
     * @param offset
     *            A positive or negative number. -1 is equivalent to
     *            {@link #back()}. +1 is equivalent to {@link #forward()}.
     */
    void moveInHistory(int offset);

    /**
     * Navigates to a URL in the history list.
     *
     * @param url
     *            the url
     */
    void goToHistoryURL(String url);

     /** Sets the cursor.
		 *
		 * @param cursorOpt
		 *            the new cursor
		 */
     void setCursor(Optional<Cursor> cursorOpt);
}
