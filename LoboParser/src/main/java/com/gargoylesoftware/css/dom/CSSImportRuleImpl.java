/*
 * Copyright (c) 2019-2020 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.css.dom;

import java.io.IOException;

import org.w3c.dom.DOMException;

import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.util.LangUtils;

/**
 * Implementation of CSSImportRule.
 *
 * Author Ronald Brill
 *
 */
public class CSSImportRuleImpl extends AbstractCSSRuleImpl {

    private String href_;
    private MediaListImpl media_;

    /**
     * Ctor.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     * @param href the href
     * @param media the media
     */
    public CSSImportRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final AbstractCSSRuleImpl parentRule,
            final String href,
            final MediaListImpl media) {
        super(parentStyleSheet, parentRule);
        href_ = href;
        media_ = media;
    }

    /** {@inheritDoc} */
    @Override
    public String getCssText() {
        final StringBuilder sb = new StringBuilder();
        sb.append("@import");

        final String href = getHref();
        if (null != href) {
            sb.append(" url(").append(href).append(")");
        }

        final MediaListImpl ml = getMedia();
        if (null != ml && ml.getLength() > 0) {
            sb.append(" ").append(getMedia().getMediaText());
        }
        sb.append(";");
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void setCssText(final String cssText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            final AbstractCSSRuleImpl r = parser.parseRule(cssText);

            // The rule must be an import rule
            if (r instanceof CSSImportRuleImpl) {
                href_ = ((CSSImportRuleImpl) r).href_;
                media_ = ((CSSImportRuleImpl) r).media_;
            }
            else {
                throw new DOMExceptionImpl(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DOMExceptionImpl.EXPECTING_IMPORT_RULE);
            }
        }
        catch (final CSSException e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
        catch (final IOException e) {
            throw new DOMExceptionImpl(
                DOMException.SYNTAX_ERR,
                DOMExceptionImpl.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    /**
     * <p>getHref.</p>
     *
     * @return the href
     */
    public String getHref() {
        return href_;
    }

    /**
     * <p>getMedia.</p>
     *
     * @return the media lsit
     */
    public MediaListImpl getMedia() {
        return media_;
    }

    /**
     * <p>getStyleSheet.</p>
     *
     * @return the parent style sheet
     */
    public CSSStyleSheetImpl getStyleSheet() {
        return getParentStyleSheet();
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getCssText();
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSImportRuleImpl)) {
            return false;
        }
        final CSSImportRuleImpl cir = (CSSImportRuleImpl) obj;
        return super.equals(obj)
            && LangUtils.equals(getHref(), cir.getHref())
            && LangUtils.equals(getMedia(), cir.getMedia());
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, href_);
        hash = LangUtils.hashCode(hash, media_);
        return hash;
    }
}
