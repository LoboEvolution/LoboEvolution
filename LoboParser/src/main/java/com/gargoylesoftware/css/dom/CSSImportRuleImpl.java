/*
 * Copyright (c) 2018 Ronald Brill.
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
import java.io.StringReader;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

import com.gargoylesoftware.css.parser.CSSException;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.InputSource;
import com.gargoylesoftware.css.util.LangUtils;

/**
 * Implementation of {@link CSSImportRule}.
 *
 * TODO: Implement getStyleSheet()
 *
 * @author Ronald Brill
 */
public class CSSImportRuleImpl extends AbstractCSSRuleImpl implements CSSImportRule {

    private String href_;
    private MediaList media_;

    public void setHref(final String href) {
        href_ = href;
    }

    public void setMedia(final MediaList media) {
        media_ = media;
    }

    public CSSImportRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final CSSRule parentRule,
            final String href,
            final MediaList media) {
        super(parentStyleSheet, parentRule);
        href_ = href;
        media_ = media;
    }

    @Override
    public short getType() {
        return IMPORT_RULE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCssText() {
        final StringBuilder sb = new StringBuilder();
        sb.append("@import");

        final String href = getHref();
        if (null != href) {
            sb.append(" url(").append(href).append(")");
        }

        final MediaList ml = getMedia();
        if (null != ml && ml.getLength() > 0) {
            sb.append(" ").append(((MediaListImpl) getMedia()).getMediaText());
        }
        sb.append(";");
        return sb.toString();
    }

    @Override
    public void setCssText(final String cssText) throws DOMException {
        final CSSStyleSheetImpl parentStyleSheet = getParentStyleSheetImpl();
        if (parentStyleSheet != null && parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }

        try {
            final InputSource is = new InputSource(new StringReader(cssText));
            final CSSOMParser parser = new CSSOMParser();
            final CSSRule r = parser.parseRule(is);

            // The rule must be an import rule
            if (r.getType() == CSSRule.IMPORT_RULE) {
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

    @Override
    public String getHref() {
        return href_;
    }

    @Override
    public MediaList getMedia() {
        return media_;
    }

    @Override
    public CSSStyleSheet getStyleSheet() {
        return null;
    }

    @Override
    public String toString() {
        return getCssText();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSImportRule)) {
            return false;
        }
        final CSSImportRule cir = (CSSImportRule) obj;
        return super.equals(obj)
            && LangUtils.equals(getHref(), cir.getHref())
            && LangUtils.equals(getMedia(), cir.getMedia());
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, href_);
        hash = LangUtils.hashCode(hash, media_);
        return hash;
    }
}
