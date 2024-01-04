/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLAnchorElement;
import org.loboevolution.html.dom.nodeimpl.DOMTokenListImpl;
import org.loboevolution.html.node.DOMTokenList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.renderstate.LinkRenderState;
import org.loboevolution.html.renderstate.RenderState;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * <p>HTMLAnchorElementImpl class.</p>
 */
public class HTMLAnchorElementImpl extends HTMLElementImpl implements HTMLAnchorElement {

    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLAnchorElementImpl(final String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(final RenderState prevRenderState) {
        return new LinkRenderState(prevRenderState, getHtmlRendererContext(), this);
    }

    @Override
    public String getCoords() {
        return null;
    }

    @Override
    public void setCoords(String coords) {

    }

    @Override
    public String getDownload() {
        return null;
    }

    @Override
    public void setDownload(String download) {

    }

    @Override
    public String getHreflang()  {
        return getAttribute("hreflang");
    }

    @Override
    public void setHreflang(final String hreflang) {
        setAttribute("hreflang", hreflang);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getPing() {
        return null;
    }

    @Override
    public void setPing(String ping) {

    }

    @Override
    public String getReferrerPolicy() {
        return null;
    }

    @Override
    public void setReferrerPolicy(String referrerPolicy) {

    }

    @Override
    public String getRel() {
        return getAttribute("rel");
    }

    @Override
    public void setRel(final String rel) {
        setAttribute("rel", rel);
    }

    @Override
    public DOMTokenList getRelList() {
        final DOMTokenListImpl tokList = new DOMTokenListImpl(this);
        final String rel = getRel();
        if(Strings.isNotBlank(rel)){
            final String[] listString = rel.split(" ");
            final List<String> names = Arrays.asList(listString);
            names.forEach(tokList::populate);
        }
        return tokList;
    }

    @Override
    public String getRev() {
        return getAttribute("rev");
    }

    @Override
    public void setRev(final String rev) {
        setAttribute("rev", rev);
    }

    @Override
    public String getShape() {
        return null;
    }

    @Override
    public void setShape(String shape) {

    }

    @Override
    public String getTarget() {
        final String target = getAttribute("target");
        if (target != null) {
            return target;
        }
        final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        return doc == null ? null : doc.getDefaultTarget();
    }

    @Override
    public void setTarget(final String target) {
        setAttribute("target", target);
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void setText(String text) {

    }

    @Override
    public String getType() {
        return getAttribute("type");
    }

    @Override
    public void setType(final String type) {
        setAttribute("type", type);
    }

    @Override
    public String getHash() {
        return null;
    }

    @Override
    public void setHash(String hash) {

    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public void setHost(String host) {

    }

    @Override
    public String getHostname() {
        return null;
    }

    @Override
    public void setHostname(String hostname) {

    }

    @Override
    public String getHref() {
        final String href = getAttribute("href");
        return href == null ? "" : href.trim();
    }

    @Override
    public void setHref(final String href) {
        setAttribute("href", href);
    }

    @Override
    public String getOrigin() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public String getPathname() {
        final URL url = getURL();
        return url == null ? null : url.getPath();
    }

    @Override
    public void setPathname(String pathname) {

    }

    @Override
    public String getPort() {
        return null;
    }

    @Override
    public void setPort(String port) {

    }

    @Override
    public String getProtocol() {
        return null;
    }

    @Override
    public void setProtocol(String protocol) {

    }

    @Override
    public String getSearch() {
        return null;
    }

    @Override
    public void setSearch(String search) {

    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void setUsername(String username) {

    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getHref();
    }

    private URL getURL() {
        URL url;
        try {
            final Document document = getOwnerDocument();
            url = document == null ? null : new URL(document.getDocumentURI());
        } catch (final Exception mfu) {
            url = null;
        }
        return url;
    }

    /**
     * <p>navigate.</p>
     */
    public void navigate() {
        final HtmlRendererContext rcontext = getHtmlRendererContext();
        if (rcontext != null) {
            final String href = getHref();
            if (Strings.isNotBlank(href)) {
                try {
                    final URL url = getFullURL(href);
                    if (url == null) {
                        this.warn("Unable to resolve URI: [" + href + "].");
                    } else {
                        rcontext.linkClicked(url, false);
                    }
                } catch (final MalformedURLException mfu) {
                    this.warn("Malformed URI: [" + href + "].", mfu);
                }
            }
        }
    }

    /**
     * <p>getAbsoluteHref.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getAbsoluteHref() {
        final HtmlRendererContext rcontext = getHtmlRendererContext();
        if (rcontext != null) {
            final String href = getHref();
            if (Strings.isNotBlank(href)) {
                getTarget();
                try {
                    final URL url = getFullURL(href);
                    return url == null ? null : url.toExternalForm();
                } catch (final MalformedURLException mfu) {
                    this.warn("Malformed URI: [" + href + "].", mfu);
                }
            }
        }
        return null;
    }
}
