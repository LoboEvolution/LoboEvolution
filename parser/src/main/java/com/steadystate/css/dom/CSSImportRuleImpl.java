/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2015 David Schweinsberg.  All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * To contact the authors of the library:
 *
 * http://cssparser.sourceforge.net/
 * mailto:davidsch@users.sourceforge.net
 *
 */

package com.steadystate.css.dom;

import java.io.IOException;
import java.io.StringReader;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSImportRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.stylesheets.MediaList;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link CSSImportRule}.
 *
 * TODO: Implement getStyleSheet()
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CSSImportRuleImpl extends AbstractCSSRuleImpl implements CSSImportRule {

    private static final long serialVersionUID = 7807829682009179339L;

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

    public CSSImportRuleImpl() {
        super();
    }

    public short getType() {
        return IMPORT_RULE;
    }

    public String getCssText() {
        return getCssText(null);
    }

    /**
     * Returns a string representation of the rule based on the given format.
     * If provided format is null, the result is the same as getCssText()
     *
     * @param format the formating rules
     * @return the formated string
     */
    public String getCssText(final CSSFormat format) {
        final StringBuilder sb = new StringBuilder();
        sb.append("@import");

        final String href = getHref();
        if (null != href) {
            sb.append(" url(").append(href).append(")");
        }

        final MediaList ml = getMedia();
        if (null != ml && ml.getLength() > 0) {
            sb.append(" ").append(getMedia().toString());
        }
        sb.append(";");
        return sb.toString();
    }

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

    public String getHref() {
        return href_;
    }

    public MediaList getMedia() {
        return media_;
    }

    public CSSStyleSheet getStyleSheet() {
        return null;
    }

    @Override
    public String toString() {
        return getCssText(null);
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
