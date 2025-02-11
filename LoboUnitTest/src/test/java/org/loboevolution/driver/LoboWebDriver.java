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

package org.loboevolution.driver;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.gui.LocalHtmlRendererContext;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.io.WritableLineReader;
import org.loboevolution.http.UserAgentContext;

import java.awt.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

/**
 * <p>LoboWebDriver class.</p>
 */
@Slf4j
public class LoboWebDriver {

    /**
     * <p>loadHtml.</p>
     *
     * @param in  a {@link java.io.InputStream} object.
     * @param url a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
     */
    protected static HTMLDocumentImpl loadHtml(final InputStream in, final String url) {
        HTMLDocumentImpl doc = null;
        try (final WritableLineReader wis = new WritableLineReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            final HtmlRendererConfig config = new LocalHtmlRendererConfig();
            final UserAgentContext ucontext = new UserAgentContext(config, true);
            final HtmlPanel panel = new HtmlPanel();
            panel.setPreferredSize(new Dimension(800, 400));
            final HtmlRendererContext rendererContext = new LocalHtmlRendererContext(panel, ucontext);
            ucontext.setUserAgentEnabled(true);
            doc = new HTMLDocumentImpl(ucontext, rendererContext, config, wis, url);
            doc.load();
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return doc;
    }

    /**
     * <p>loadHtml.</p>
     *
     * @param html a {@link java.lang.String} object.
     * @return a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
     */
    protected HTMLDocumentImpl loadHtml(final String html) {
        HTMLDocumentImpl doc = null;
        try (final WritableLineReader wis = new WritableLineReader(new StringReader(html))) {
            final HtmlRendererConfig config = new LocalHtmlRendererConfig();
            final UserAgentContext ucontext = new UserAgentContext(config, true);
            final HtmlPanel panel = new HtmlPanel();
            panel.setPreferredSize(new Dimension(800, 400));
            final HtmlRendererContext rendererContext = new LocalHtmlRendererContext(panel, ucontext);
            ucontext.setUserAgentEnabled(true);
            String local = "file://" + System.getProperty("user.dir").replace("\\", "/") + "/";
            doc = new HTMLDocumentImpl(ucontext, rendererContext, config, wis, local);
            doc.load();
        } catch (final Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return doc;
    }
}
