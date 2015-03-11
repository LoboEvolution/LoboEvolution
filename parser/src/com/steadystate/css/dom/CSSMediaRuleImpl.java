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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSMediaRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.stylesheets.MediaList;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.LangUtils;
import com.steadystate.css.util.ThrowCssExceptionErrorHandler;


/**
 * Implementation of {@link CSSMediaRule}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CSSMediaRuleImpl extends AbstractCSSRuleImpl implements CSSMediaRule {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6603734096445214651L;

    /** The media_. */
    private MediaList media_;
    
    /** The css rules_. */
    private CSSRuleList cssRules_;

    /**
     * Sets the media.
     *
     * @param media the new media
     */
    public void setMedia(final MediaList media) {
        media_ = media;
    }

    /**
     * Sets the css rules.
     *
     * @param cssRules the new css rules
     */
    public void setCssRules(final CSSRuleList cssRules) {
        cssRules_ = cssRules;
    }

    /**
     * Instantiates a new CSS media rule impl.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     * @param media the media
     */
    public CSSMediaRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final CSSRule parentRule,
            final MediaList media) {
        super(parentStyleSheet, parentRule);
        media_ = media;
    }

    /**
     * Instantiates a new CSS media rule impl.
     */
    public CSSMediaRuleImpl() {
        super();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRule#getType()
     */
    public short getType() {
        return MEDIA_RULE;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRule#getCssText()
     */
    public String getCssText() {
        final StringBuilder sb = new StringBuilder("@media ");
        sb.append(getMedia().toString()).append(" {");
        for (int i = 0; i < getCssRules().getLength(); i++) {
            final CSSRule rule = getCssRules().item(i);
            sb.append(rule.getCssText()).append(" ");
        }
        sb.append("}");
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

            // The rule must be a media rule
            if (r.getType() == CSSRule.MEDIA_RULE) {
                media_ = ((CSSMediaRuleImpl) r).media_;
                cssRules_ = ((CSSMediaRuleImpl) r).cssRules_;
            }
            else {
                throw new DOMExceptionImpl(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DOMExceptionImpl.EXPECTING_MEDIA_RULE);
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
     * @see org.w3c.dom.css.CSSMediaRule#getMedia()
     */
    public MediaList getMedia() {
        return media_;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSMediaRule#getCssRules()
     */
    public CSSRuleList getCssRules() {
        if (cssRules_ == null) {
            cssRules_ = new CSSRuleListImpl();
        }
        return cssRules_;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSMediaRule#insertRule(java.lang.String, int)
     */
    public int insertRule(final String rule, final int index) throws DOMException {
        final CSSStyleSheetImpl parentStyleSheet = getParentStyleSheetImpl();
        if (parentStyleSheet != null && parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }

        try {
            final InputSource is = new InputSource(new StringReader(rule));
            final CSSOMParser parser = new CSSOMParser();
            parser.setParentStyleSheet(parentStyleSheet);
            parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
            // parser._parentRule is never read
            // parser.setParentRule(_parentRule);
            final CSSRule r = parser.parseRule(is);

            // Insert the rule into the list of rules
            ((CSSRuleListImpl) getCssRules()).insert(r, index);

        }
        catch (final IndexOutOfBoundsException e) {
            throw new DOMExceptionImpl(
                DOMException.INDEX_SIZE_ERR,
                DOMExceptionImpl.INDEX_OUT_OF_BOUNDS,
                e.getMessage());
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
        return index;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSMediaRule#deleteRule(int)
     */
    public void deleteRule(final int index) throws DOMException {
        final CSSStyleSheetImpl parentStyleSheet = getParentStyleSheetImpl();
        if (parentStyleSheet != null && parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }
        try {
            ((CSSRuleListImpl) getCssRules()).delete(index);
        }
        catch (final IndexOutOfBoundsException e) {
            throw new DOMExceptionImpl(
                DOMException.INDEX_SIZE_ERR,
                DOMExceptionImpl.INDEX_OUT_OF_BOUNDS,
                e.getMessage());
        }
    }

    /**
     * Sets the rule list.
     *
     * @param rules the new rule list
     */
    public void setRuleList(final CSSRuleListImpl rules) {
        cssRules_ = rules;
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
        if (!(obj instanceof CSSMediaRule)) {
            return false;
        }
        final CSSMediaRule cmr = (CSSMediaRule) obj;
        return super.equals(obj)
            && LangUtils.equals(getMedia(), cmr.getMedia())
            && LangUtils.equals(getCssRules(), cmr.getCssRules());
    }

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.AbstractCSSRuleImpl#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, media_);
        hash = LangUtils.hashCode(hash, cssRules_);
        return hash;
    }

    /**
     * Write object.
     *
     * @param out the out
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeObject(cssRules_);
        out.writeObject(media_);
    }

    /**
     * Read object.
     *
     * @param in the in
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private void readObject(final ObjectInputStream in)
        throws IOException, ClassNotFoundException {

        cssRules_ = (CSSRuleList) in.readObject();
        if (cssRules_ != null) {
            for (int i = 0; i < cssRules_.getLength(); i++) {
                final CSSRule cssRule = cssRules_.item(i);
                if (cssRule instanceof AbstractCSSRuleImpl) {
                    ((AbstractCSSRuleImpl) cssRule).setParentRule(this);
                    ((AbstractCSSRuleImpl) cssRule).setParentStyleSheet(
                        getParentStyleSheetImpl());
                }
            }
        }
        media_ = (MediaList) in.readObject();
    }
}
