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
/*
 * Created on Oct 22, 2005
 */
package org.loboevolution.gui;

import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLLinkElement;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.http.UserAgentContext;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

public interface HtmlRendererContext {

    /**
     * If a {@link org.loboevolution.http.UserAgentContext} instance was provided in
     * the constructor, then that instance is returned. Otherwise, an instance of
     * {@link org.loboevolution.http.UserAgentContext} is created and returned.
     * <p>
     * The context returned by this method is used by local request facilities and
     * other parts of the renderer.
     */
    UserAgentContext getUserAgentContext();

    /**
     * Gets the current URL in history.
     */
    String getCurrentURL();

    /**
     * <p>Getter for the field htmlPanel.</p>
     */
    HtmlPanel getHtmlPanel();

    double getScrollx();

    void setScrollx(double scrollx);

    double getScrolly();

    void setScrolly(double scrolly);

    /**
     * Changes the origin of the HTML block's scrollable area according to the
     * position given.
     * <p>
     * This method may be called outside of the GUI thread. The operation is
     * scheduled immediately in that thread as needed.
     *
     * @param x The new x coordinate for the origin.
     * @param y The new y coordinate for the origin.
     */
    void scroll(double x, double y);

    /**
     * <p>scrollBy.</p>
     *
     * @param x a {@link java.lang.Double} object.
     * @param y a {@link java.lang.Double} object.
     */
    void scrollBy(double x, double y);

    /**
     * <p> getInnerWidth.</p>
     */
    int getInnerWidth();

    /**
     * <p> getInnerHeight.</p>
     */
    int getInnerHeight();

    /**
     * <p> getOuterWidth.</p>
     */
    int getOuterWidth();

    /**
     * <p> getOuterHeight.</p>
     */
    int getOuterHeight();

    /**
     * Implements the link click.
     */
    void linkClicked(URL url, boolean isNewTab);

    /**
     * It should navigate back one page. This implementation does nothing and should
     * be overridden.
     */
    void back();

    /**
     * <p>forward.</p>
     */
    void forward();

    /**
     * It should give up focus on the current browser window. This implementation
     * does nothing and should be overridden.
     */
    void blur();

    /**
     * It should close the current browser window. This implementation does nothing
     * and should be overridden.
     */
    void close();

    /**
     * It should request focus for the current browser window. This implementation
     * does nothing and should be overridden.
     */
    void focus();

    /**
     * Opens a simple confirmation window.
     *
     * @param message a {@link java.lang.String} object.
     * @return a boolean.
     */
    boolean confirm(String message);

    /**
     * Opens a simple message dialog.
     *
     * @param message a {@link java.lang.String} object.
     */
    void alert(String message);

    /**
     * <p>error.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    void error(String message);

    /**
     * <p>error.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param throwable a {@link java.lang.Throwable} object.
     */
    void error(String message, Throwable throwable);


    /**
     * Should return true if and only if the current browser window is closed. This
     * implementation returns false and should be overridden.
     *
     * @return a {@link java.lang.String} object.
     */
    String getDefaultStatus();

    /**
     * <p>Getter for the field opener.</p>
     *
     * @return a {@link HtmlRendererContext} object.
     */
    HtmlRendererContext getOpener();

    /**
     * <p>Getter parent.</p>
     *
     * @return a {@link HtmlRendererContext} object.
     */
    HtmlRendererContext getParent();

    /**
     * <p>Getter top.</p>
     *
     * @return a {@link HtmlRendererContext} object.
     */
    HtmlRendererContext getTop();

    /**
     * It should open a new browser window. This implementation does nothing and
     * should be overridden.
     *
     * @param url            The requested URL.
     * @param windowName     A window identifier.
     * @param windowFeatures WindowImpl features specified in a format equivalent to
     *                       that of window.open() in Javascript.
     * @param replace        Whether an existing window with the same name should be
     *                       replaced.
     * @return a {@link HtmlRendererContext} object.
     */
    HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace);

    /**
     * <p>isVisitedLink.</p>
     *
     * @param link a {@link org.loboevolution.html.dom.HTMLLinkElement} object.
     * @return a boolean.
     */
    boolean isVisitedLink(HTMLLinkElement link);


    /**
     * This method must be overridden to implement a context menu.
     *
     * @param element a {@link org.loboevolution.html.dom.HTMLElement} object.
     * @param event a {@link java.awt.event.MouseEvent} object.
     * @return a boolean.
     */
    boolean onContextMenu(HTMLElement element, MouseEvent event);

    /**
     * Should return true if and only if the current browser window is closed. This
     * implementation returns false and should be overridden.
     *
     * @return a boolean.
     */
    boolean isClosed();


    /**
     * <p>moveInHistory.</p>
     *
     * @param offset a int.
     */
    void moveInHistory(int offset);

    /**
     * Convenience method provided to allow loading a document into the renderer.
     *
     * @param fullURL The absolute URL of the document.
     * @see #navigate(URL, String)
     */
    void navigate(String fullURL) throws Exception;

    /**
     * Implements simple navigation with incremental rendering by invoking
     * {@link #submitForm(String, URL, String, String, FormInput[])} with a
     * GET request method.
     *
     * @param href a {@link java.net.URL} object.
     * @param target a {@link java.lang.String} object.
     */
    void navigate(final URL href, String target);

    /**
     * <p>openImageViewer.</p>
     *
     * @param srcUrl a {@link java.net.URL} object.
     */
    void openImageViewer(URL srcUrl);

    /**
     * <p>openImageViewer.</p>
     *
     * @param fullURL a {@link java.lang.String} object.
     * @param stream a {@link java.io.InputStream} object.
     */
    void openImageViewer(String fullURL, InputStream stream);

    /**
     * Shows a simple prompt dialog.
     *
     * @param message a {@link java.lang.String} object.
     * @param inputDefault a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    String prompt(String message, String inputDefault);

    /**
     * Implements reload as navigation to current URL. Override to implement a more
     * robust reloading mechanism.
     */
    void reload();

    /**
     * <p>resizeBy.</p>
     *
     * @param byWidth  a {@link java.lang.Double} object.
     * @param byHeight a {@link java.lang.Double} object.
     */
    void resizeBy(double byWidth, double byHeight);

    /**
     * <p>resizeTo.</p>
     *
     * @param width a {@link java.lang.Double} object.
     * @param height a {@link java.lang.Double} object.
     */
    void resizeTo(double width, double height);


    /**
     * <p>warn.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    void warn(String message);

    /**
     * <p>warn.</p>
     *
     * @param message a {@link java.lang.String} object.
     * @param throwable a {@link java.lang.Throwable} object.
     */
    void warn(String message, Throwable throwable);


    /**
     * <p>setDefaultStatus.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    void setDefaultStatus(String message);

    /**
     * <p>Setter for the field htmlPanel.</p>
     *
     * @param panel a {@link org.loboevolution.gui.HtmlPanel} object.
     */
    void setHtmlPanel(HtmlPanel panel);

    /**
     * <p>Setter for the field opener.</p>
     *
     * @param opener a {@link HtmlRendererContext} object.
     */
    void setOpener(HtmlRendererContext opener);

    /**
     * <p>setStatus.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    void setStatus(String message);

    /**
     * <p>setCursor.</p>
     *
     * @param cursorOpt a {@link java.util.Optional} object.
     */
    void setCursor(Optional<Cursor> cursorOpt);

    /**
     * Implements simple navigation and form submission with incremental rendering
     * and target processing, including frame lookup. Should be overridden to allow
     * for more robust browser navigation and form submission.
     * <p>
     * <b>Notes:</b>
     * <ul>
     * <li>Document encoding is defined by
     * {link #getDocumentCharset(URLConnection)}.
     * <li>Caching is not implemented.
     * <li>Cookies are not implemented.
     * <li>Incremental rendering is not optimized for ignorable document change
     * notifications.
     * <li>Other HTTP features are not implemented.
     * <li>The only form encoding type supported is
     * application/x-www-form-urlencoded.
     * <li>Navigation is normally asynchronous.
     * </ul>
     *
     * @see #navigate(URL, String
     *
     * )
     * @param method a {@link java.lang.String} object.
     * @param action a {@link java.net.URL} object.
     * @param target a {@link java.lang.String} object.
     * @param enctype a {@link java.lang.String} object.
     * @param formInputs an array of {@link org.loboevolution.html.dom.input.FormInput} objects.
     */
    void submitForm(final String method, final URL action, final String target,
                    final String enctype, final FormInput[] formInputs);


    /**
     * <p>isTestEnabled.</p>
     *
     * @return a {@link java.lang.Boolean} object.
     */
    boolean isTestEnabled();

    /**
     * <p>getNextURL.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getNextURL();

    /**
     * <p>getPreviousURL.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getPreviousURL();

    /**
     * <p>getHistoryLength.</p>
     *
     * @return a int.
     */
    int getHistoryLength();

    /**
     * <p>getStatus.</p>
     *
     * @return a {@link String} object.
     */
    String getStatus();
}
