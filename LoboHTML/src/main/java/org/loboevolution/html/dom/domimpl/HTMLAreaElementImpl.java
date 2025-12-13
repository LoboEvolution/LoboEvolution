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
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.DOMTokenList;
import org.loboevolution.html.dom.HTMLAreaElement;

import java.util.Arrays;
import java.util.List;

/**
 * <p>HTMLAreaElementImpl class.</p>
 */
public class HTMLAreaElementImpl extends HTMLElementImpl implements HTMLAreaElement {

    /**
     * <p>Constructor for HTMLAreaElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLAreaElementImpl(final String name) {
        super(name);
    }

    @Override
    public String getAlt() {
        return this.getAttribute("alt");
    }

    @Override
    public void setAlt(String alt) {
        this.setAttribute("alt", alt);
    }

    @Override
    public String getCoords() {
        return this.getAttribute("coords");
    }

    @Override
    public void setCoords(String coords) {
        this.setAttribute("coords", coords);
    }

    @Override
    public String getDownload() {
        return this.getAttribute("download");
    }

    @Override
    public void setDownload(String download) {
        this.setAttribute("download", download);
    }

    @Override
    public boolean isNoHref() {
        return this.getAttributeAsBoolean("noHref");
    }

    @Override
    public void setNoHref(boolean noHref) {
        this.setAttribute("noHref", String.valueOf(noHref));
    }

    @Override
    public String getPing() {
        return this.getAttribute("ping");
    }

    @Override
    public void setPing(String ping) {
        this.setAttribute("ping", ping);
    }

    @Override
    public String getReferrerPolicy() {
        return this.getAttribute("referrerPolicy");
    }

    @Override
    public void setReferrerPolicy(String referrerPolicy) {
        this.setAttribute("referrerPolicy", referrerPolicy);
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
        if (rel != null) {
            final String[] listString = rel.split(" ");
            final List<String> names = Arrays.asList(listString);
            names.forEach(tokList::populate);
        }
        return tokList;
    }

    @Override
    public String getShape() {
        return this.getAttribute("shape");
    }

    @Override
    public void setShape(String shape) {
        this.setAttribute("shape", shape);
    }

    @Override
    public String getTarget() {
        return this.getAttribute("target");
    }

    @Override
    public void setTarget(String target) {
        this.setAttribute("target", target);
    }

    @Override
    public String getHash() {
        return this.getAttribute("hash");
    }

    @Override
    public void setHash(String hash) {
        this.setAttribute("hash", hash);
    }

    @Override
    public String getHost() {
        return this.getAttribute("host");
    }

    @Override
    public void setHost(String host) {
        this.setAttribute("host", host);
    }

    @Override
    public String getHostname() {
        return this.getAttribute("hostname");
    }

    @Override
    public void setHostname(String hostname) {
        this.setAttribute("hostname", hostname);
    }

    @Override
    public String getHref() {
        return this.getAttribute("href");
    }

    @Override
    public void setHref(String href) {
        this.setAttribute("href", href);
    }

    @Override
    public String getOrigin() {
        return this.getAttribute("origin");
    }

    @Override
    public String getPassword() {
        return this.getAttribute("password");
    }

    @Override
    public void setPassword(String password) {
        this.setAttribute("password", password);
    }

    @Override
    public String getPathname() {
        return this.getAttribute("pathname");
    }

    @Override
    public void setPathname(String pathname) {
        this.setAttribute("pathname", pathname);
    }

    @Override
    public String getPort() {
        return this.getAttribute("port");
    }

    @Override
    public void setPort(String port) {
        this.setAttribute("port", port);
    }

    @Override
    public String getProtocol() {
        return this.getAttribute("protocol");
    }

    @Override
    public void setProtocol(String protocol) {
        this.setAttribute("protocol", protocol);
    }

    @Override
    public String getSearch() {
        return this.getAttribute("search");
    }

    @Override
    public void setSearch(String search) {
        this.setAttribute("search", search);
    }

    @Override
    public String getUsername() {
        return this.getAttribute("username");
    }

    @Override
    public void setUsername(String username) {
        this.setAttribute("username", username);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[object HTMLAreaElement]";
    }
}
