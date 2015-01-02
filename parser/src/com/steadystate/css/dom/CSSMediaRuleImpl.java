/*
 * CSS Parser Project
 *
 * Copyright (C) 1999-2014 David Schweinsberg.  All rights reserved.
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

    private static final long serialVersionUID = 6603734096445214651L;

    private MediaList media_;
    private CSSRuleList cssRules_;

    public void setMedia(final MediaList media) {
        media_ = media;
    }

    public void setCssRules(final CSSRuleList cssRules) {
        cssRules_ = cssRules;
    }

    public CSSMediaRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final CSSRule parentRule,
            final MediaList media) {
        super(parentStyleSheet, parentRule);
        media_ = media;
    }

    public CSSMediaRuleImpl() {
        super();
    }

    public short getType() {
        return MEDIA_RULE;
    }

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

    public MediaList getMedia() {
        return media_;
    }

    public CSSRuleList getCssRules() {
        if (cssRules_ == null) {
            cssRules_ = new CSSRuleListImpl();
        }
        return cssRules_;
    }

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

    public void setRuleList(final CSSRuleListImpl rules) {
        cssRules_ = rules;
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
        if (!(obj instanceof CSSMediaRule)) {
            return false;
        }
        final CSSMediaRule cmr = (CSSMediaRule) obj;
        return super.equals(obj)
            && LangUtils.equals(getMedia(), cmr.getMedia())
            && LangUtils.equals(getCssRules(), cmr.getCssRules());
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, media_);
        hash = LangUtils.hashCode(hash, cssRules_);
        return hash;
    }

    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeObject(cssRules_);
        out.writeObject(media_);
    }

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
