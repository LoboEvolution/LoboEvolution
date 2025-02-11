/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
package org.loboevolution.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Urls;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.IToolBar;
import org.loboevolution.html.dom.HTMLAnchorElement;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.http.UserAgentContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.net.*;

@Data
@AllArgsConstructor
@Slf4j
public class LocalHtmlRendererContext implements HtmlRendererContext{
    private UserAgentContext bcontext;

    protected URLConnection currentConnection;

    private HtmlPanel htmlPanel;

    private volatile HtmlRendererContext opener;

    private final HtmlRendererContext parentRcontext;

    private final boolean testEnabled = false;

    private double scrollx;

    private double scrolly;

    /**
     * Constructs a HtmlRendererContextImpl.
     *
     * @param htmlPanel a {@link HtmlPanel} object.
     * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
     */
    public LocalHtmlRendererContext(final HtmlPanel htmlPanel, final UserAgentContext ucontext) {
        this.htmlPanel = htmlPanel;
        this.parentRcontext = null;
        this.bcontext = ucontext;
    }

    /** {@inheritDoc} */
    @Override
    public void alert(final String message) {
        JOptionPane.showMessageDialog(this.htmlPanel, message);
    }

    /** {@inheritDoc} */
    @Override
    public void back() {
    }

    /** {@inheritDoc} */
    @Override
    public void blur() {
        this.warn("back(): Not overridden");
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        this.warn("close(): Not overridden");
    }

    /** {@inheritDoc} */
    @Override
    public boolean confirm(final String message) {
        final int retValue = JOptionPane.showConfirmDialog(this.htmlPanel, message, "Confirm",
                JOptionPane.YES_NO_OPTION);
        return retValue == JOptionPane.YES_OPTION;
    }

    /** {@inheritDoc} */
    @Override
    public void error(final String message) {
        if (log.isErrorEnabled()) {
            log.error(message);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void error(final String message, final Throwable throwable) {
        if (log.isErrorEnabled()) {
            log.error(message, throwable);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void focus() {
        this.warn("focus(): Not overridden");
    }

    /** {@inheritDoc} */
    @Override
    public void forward() {
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentURL() {
        final HtmlPanel html = htmlPanel;
        final IBrowserPanel panel = html.getBrowserPanel();
        if (panel != null) {
            final IBrowserFrame frame = panel.getBrowserFrame();
            final IToolBar toolbar = frame.getToolbar();
            final JTextField jtf = toolbar.getAddressBar();
            return jtf.getText();
        } else {
            return "";
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getDefaultStatus() {
        this.warn("getDefaultStatus(): Not overridden");
        return "";
    }

    /** {@inheritDoc} */
    @Override
    public HtmlPanel getHtmlPanel() {
        return this.htmlPanel;
    }

    /** {@inheritDoc} */
    @Override
    public HtmlRendererContext getOpener() {
        return this.opener;
    }

    /** {@inheritDoc} */
    @Override
    public HtmlRendererContext getParent() {
        return this.parentRcontext;
    }

    /** {@inheritDoc} */
    @Override
    public HtmlRendererContext getTop() {
        final HtmlRendererContext ancestor = this.parentRcontext;
        if (ancestor == null) {
            return this;
        }
        return ancestor.getTop();
    }

    /** {@inheritDoc} */
    @Override
    public UserAgentContext getUserAgentContext() {
        synchronized (this) {
            if (this.bcontext == null) {
                this.warn("getUserAgentContext(): UserAgentContext not provided in constructor. Creating a simple one.");
                this.bcontext = new UserAgentContext(new LocalHtmlRendererConfig());
            }
            return this.bcontext;
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean isClosed() {
        this.warn("isClosed(): Not overridden");
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isVisitedLink(final HTMLAnchorElement link) {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public void linkClicked(final URL url, final boolean isNewTab) {
    }

    /** {@inheritDoc} */
    @Override
    public void moveInHistory(final int offset) {
        if (log.isWarnEnabled()) {
            log.warn("moveInHistory() does nothing, unless overridden.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getPreviousURL() {
        if (log.isWarnEnabled()) {
            log.warn("getPreviousURL() does nothing, unless overridden.");
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getNextURL() {
        if (log.isWarnEnabled()) {
            log.warn("getNextURL() does nothing, unless overridden.");
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public int getHistoryLength() {
        return 0;
    }

    @Override
    public String getStatus() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public void navigate(final String fullURL) throws Exception {
        URI uri = Urls.createURI(null, fullURL);
        if (uri != null) {
            this.navigate(uri.toURL(), "_this");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void navigate(final URL href, final String target) {
        submitForm("GET", href, target, null, null);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onContextMenu(final HTMLElement element, final MouseEvent event) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public HtmlRendererContext open(final URL url, final String windowName, final String windowFeatures, final boolean replace) {
        return null;
    }


    /** {@inheritDoc} */
    @Override
    public void openImageViewer(final URL srcUrl) {}

    /** {@inheritDoc} */
    @Override
    public void openImageViewer(final String fullURL, final InputStream stream) { }

    /** {@inheritDoc} */
    @Override
    public String prompt(final String message, final String inputDefault) {
        return JOptionPane.showInputDialog(this.htmlPanel, message);
    }

    /** {@inheritDoc} */
    @Override
    public void reload() {
        final HTMLDocumentImpl document = (HTMLDocumentImpl) this.htmlPanel.getRootNode();
        if (document != null) {
            try {
                final URL url = new URI(document.getDocumentURI()).toURL();
                this.navigate(url, null);
            } catch (Exception throwable) {
                this.warn("reload(): Malformed URL", throwable);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void resizeBy(final double byWidth, final double byHeight) {
        final Window window = getWindow(this.htmlPanel);
        if (window != null) {
            window.setSize(window.getWidth() + (int)byWidth, window.getHeight() + (int)byHeight);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void resizeTo(final double width, final double height) {
        final Window window = getWindow(this.htmlPanel);
        if (window != null) {
            window.setSize((int)width, (int)height);
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getInnerHeight() {
        final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
        if (bpanel != null && bpanel.getHeight() > 0) {
            return bpanel.getHeight();
        }
        return 500;
    }

    /** {@inheritDoc} */
    @Override
    public int getInnerWidth() {
        final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
        if (bpanel != null && bpanel.getWidth() > 0) {
            return bpanel.getWidth();
        }
        return 800;
    }

    /** {@inheritDoc} */
    @Override
    public int getOuterHeight() {
        final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
        if (bpanel != null && bpanel.getHeight() > 0) {
            return bpanel.getHeight();
        }
        return 500;
    }

    /** {@inheritDoc} */
    @Override
    public int getOuterWidth() {
        final IBrowserPanel bpanel = htmlPanel.getBrowserPanel();
        if (bpanel != null && bpanel.getWidth() > 0) {
            return bpanel.getWidth();
        }
        return 900;
    }


    /** {@inheritDoc} */
    @Override
    public void scroll(final double x, final double y) {
        this.htmlPanel.scroll(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void scrollBy(final double x, final double y) {
        this.htmlPanel.scrollBy(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public double getScrollx() {
        return scrollx;
    }

    /** {@inheritDoc} */
    @Override
    public void setScrollx(final double scrollx) {
        this.scrollx = scrollx;
    }

    /** {@inheritDoc} */
    @Override
    public double getScrolly() {
        return scrolly;
    }

    /** {@inheritDoc} */
    @Override
    public void setScrolly(final double scrolly) {
        this.scrolly = scrolly;
    }

    /** {@inheritDoc} */
    @Override
    public void setDefaultStatus(final String message) {
        this.warn("setDefaultStatus(): Not overridden.");
    }

    /** {@inheritDoc} */
    @Override
    public void setHtmlPanel(final HtmlPanel panel) {
        this.htmlPanel = panel;
    }

    /** {@inheritDoc} */
    @Override
    public void setOpener(final HtmlRendererContext opener) {
        this.opener = opener;
    }

    /** {@inheritDoc} */
    @Override
    public void setStatus(final String message) {
        this.warn("setStatus(): Not overridden");
    }

    /** {@inheritDoc} */
    @Override
    public void setCursor(final Cursor cursorOpt) {
        final Cursor cursor = cursorOpt != null ? cursorOpt : Cursor.getDefaultCursor();
        htmlPanel.setCursor(cursor);
    }

    /** {@inheritDoc} */
    @Override
    public void submitForm(final String method, final URL action, final String target, final String enctype, final FormInput[] formInputs) {
    }

    /** {@inheritDoc} */
    @Override
    public void warn(final String message) {
        if (log.isWarnEnabled()) {
            log.warn(message);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void warn(final String message, final Throwable throwable) {
        if (log.isWarnEnabled()) {
            log.warn(message, throwable);
        }
    }

    private static Window getWindow(final Component c) {
        Component current = c;
        while (current != null && !(current instanceof Window)) {
            current = current.getParent();
        }
        return (Window) current;
    }

    private Proxy getProxy() {
        return Proxy.NO_PROXY;
    }

    private void submitFormSync(final String method, final java.net.URL action, final String target, final String enctype,
                                final FormInput[] formInputs) throws Exception {
    }

    /**
     * <p>isTest.</p>
     *
     * @return a boolean.
     */
    public boolean isTestEnabled() {
        return true;
    }
}


