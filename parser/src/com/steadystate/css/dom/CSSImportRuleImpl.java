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

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7807829682009179339L;

    /** The href_. */
    private String href_;
    
    /** The media_. */
    private MediaList media_;

    /**
     * Sets the href.
     *
     * @param href the new href
     */
    public void setHref(final String href) {
        href_ = href;
    }

    /**
     * Sets the media.
     *
     * @param media the new media
     */
    public void setMedia(final MediaList media) {
        media_ = media;
    }

    /**
     * Instantiates a new CSS import rule impl.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     * @param href the href
     * @param media the media
     */
    public CSSImportRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final CSSRule parentRule,
            final String href,
            final MediaList media) {
        super(parentStyleSheet, parentRule);
        href_ = href;
        media_ = media;
    }

    /**
     * Instantiates a new CSS import rule impl.
     */
    public CSSImportRuleImpl() {
        super();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRule#getType()
     */
    public short getType() {
        return IMPORT_RULE;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRule#getCssText()
     */
    public String getCssText() {
        final StringBuilder sb = new StringBuilder();
        sb.append("@import url(")
            .append(getHref())
            .append(")");
        if (getMedia().getLength() > 0) {
            sb.append(" ").append(getMedia().toString());
        }
        sb.append(";");
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRule#setCssText(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSImportRule#getHref()
     */
    public String getHref() {
        return href_;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSImportRule#getMedia()
     */
    public MediaList getMedia() {
        return media_;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSImportRule#getStyleSheet()
     */
    public CSSStyleSheet getStyleSheet() {
        return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getCssText();
    }

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.AbstractCSSRuleImpl#equals(java.lang.Object)
     */
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

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.AbstractCSSRuleImpl#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, href_);
        hash = LangUtils.hashCode(hash, media_);
        return hash;
    }
}
