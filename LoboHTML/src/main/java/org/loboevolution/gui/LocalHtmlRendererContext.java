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
package org.loboevolution.gui;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.loboevolution.common.Urls;
import org.loboevolution.component.IBrowserFrame;
import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.component.IToolBar;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLLinkElement;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.input.FormInput;
import org.loboevolution.http.UserAgentContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
@AllArgsConstructor
public class LocalHtmlRendererContext implements HtmlRendererContext{

    private static final Logger logger = Logger.getLogger(LocalHtmlRendererContext.class.getName());

    private UserAgentContext bcontext;

    protected URLConnection currentConnection;

    private HtmlPanel htmlPanel;

    private volatile HtmlRendererContext opener;

    private final HtmlRendererContext parentRcontext;

    private boolean testEnabled = false;

    private double scrollx;

    private double scrolly;

    /**
     * Constructs a HtmlRendererContextImpl.
     *
     * @param htmlPanel a {@link HtmlPanel} object.
     * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
     */
    public LocalHtmlRendererContext(HtmlPanel htmlPanel, UserAgentContext ucontext) {
        this.htmlPanel = htmlPanel;
        this.parentRcontext = null;
        this.bcontext = ucontext;
    }

    /** {@inheritDoc} */
    @Override
    public void alert(String message) {
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
    public boolean confirm(String message) {
        final int retValue = JOptionPane.showConfirmDialog(this.htmlPanel, message, "Confirm",
                JOptionPane.YES_NO_OPTION);
        return retValue == JOptionPane.YES_OPTION;
    }

    /** {@inheritDoc} */
    @Override
    public void error(String message) {
        if (logger.isLoggable(Level.SEVERE)) {
            logger.log(Level.SEVERE, message);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void error(String message, Throwable throwable) {
        if (logger.isLoggable(Level.SEVERE)) {
            logger.log(Level.SEVERE, message, throwable);
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
        HtmlPanel html = htmlPanel;
        IBrowserPanel panel = html.getBrowserPanel();
        if (panel != null) {
            IBrowserFrame frame = panel.getBrowserFrame();
            IToolBar toolbar = frame.getToolbar();
            JTextField jtf = toolbar.getAddressBar();
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
    public boolean isVisitedLink(HTMLLinkElement link) {
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public void linkClicked(URL url, boolean isNewTab) {
    }

    /** {@inheritDoc} */
    @Override
    public void moveInHistory(int offset) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, "moveInHistory() does nothing, unless overridden.");
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getPreviousURL() {
        if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, "getPreviousURL() does nothing, unless overridden.");
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getNextURL() {
        if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, "getNextURL() does nothing, unless overridden.");
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
    public void navigate(String fullURL) throws Exception {
        final URL href = Urls.createURL(null, fullURL);
        this.navigate(href, "_this");
    }

    /** {@inheritDoc} */
    @Override
    public void navigate(final URL href, String target) {
        submitForm("GET", href, target, null, null);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onContextMenu(HTMLElement element, MouseEvent event) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public HtmlRendererContext open(URL url, String windowName, String windowFeatures, boolean replace) {
        return null;
    }


    /** {@inheritDoc} */
    @Override
    public void openImageViewer(URL srcUrl) {}

    /** {@inheritDoc} */
    @Override
    public void openImageViewer(String fullURL, InputStream stream) { }

    /** {@inheritDoc} */
    @Override
    public String prompt(String message, String inputDefault) {
        return JOptionPane.showInputDialog(this.htmlPanel, message);
    }

    /** {@inheritDoc} */
    @Override
    public void reload() {
        final HTMLDocumentImpl document = (HTMLDocumentImpl) this.htmlPanel.getRootNode();
        if (document != null) {
            try {
                final URL url = new URL(document.getDocumentURI());
                this.navigate(url, null);
            } catch (final MalformedURLException throwable) {
                this.warn("reload(): Malformed URL", throwable);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void resizeBy(double byWidth, double byHeight) {
        final Window window = getWindow(this.htmlPanel);
        if (window != null) {
            window.setSize(window.getWidth() + (int)byWidth, window.getHeight() + (int)byHeight);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void resizeTo(double width, double height) {
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
        return 900;
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
    public void scroll(double x, double y) {
        this.htmlPanel.scroll(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void scrollBy(double x, double y) {
        this.htmlPanel.scrollBy(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public double getScrollx() {
        return scrollx;
    }

    /** {@inheritDoc} */
    @Override
    public void setScrollx(double scrollx) {
        this.scrollx = scrollx;
    }

    /** {@inheritDoc} */
    @Override
    public double getScrolly() {
        return scrolly;
    }

    /** {@inheritDoc} */
    @Override
    public void setScrolly(double scrolly) {
        this.scrolly = scrolly;
    }

    /** {@inheritDoc} */
    @Override
    public void setDefaultStatus(String message) {
        this.warn("setDefaultStatus(): Not overridden.");
    }

    /** {@inheritDoc} */
    @Override
    public void setHtmlPanel(HtmlPanel panel) {
        this.htmlPanel = panel;
    }

    /** {@inheritDoc} */
    @Override
    public void setOpener(HtmlRendererContext opener) {
        this.opener = opener;
    }

    /** {@inheritDoc} */
    @Override
    public void setStatus(String message) {
        this.warn("setStatus(): Not overridden");
    }

    /** {@inheritDoc} */
    @Override
    public void setCursor(Optional<Cursor> cursorOpt) {
        Cursor cursor = cursorOpt.orElse(Cursor.getDefaultCursor());
        htmlPanel.setCursor(cursor);
    }

    /** {@inheritDoc} */
    @Override
    public void submitForm(final String method, final URL action, final String target, final String enctype, final FormInput[] formInputs) {
    }

    /** {@inheritDoc} */
    @Override
    public void warn(String message) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, message);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void warn(String message, Throwable throwable) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, message, throwable);
        }
    }

    private static Window getWindow(Component c) {
        Component current = c;
        while (current != null && !(current instanceof Window)) {
            current = current.getParent();
        }
        return (Window) current;
    }

    private Proxy getProxy() {
        return Proxy.NO_PROXY;
    }

    private void submitFormSync(final String method, final java.net.URL action, final String target, String enctype,
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


