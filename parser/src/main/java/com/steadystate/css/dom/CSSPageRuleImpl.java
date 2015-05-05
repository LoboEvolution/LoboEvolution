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
import org.w3c.dom.css.CSSPageRule;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link CSSPageRule}.
 *
 * TODO: Implement setSelectorText()
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CSSPageRuleImpl extends AbstractCSSRuleImpl implements CSSPageRule {

    private static final long serialVersionUID = -6007519872104320812L;

    private String pseudoPage_;
    private CSSStyleDeclaration style_;

    public CSSPageRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final CSSRule parentRule,
            final String pseudoPage) {
        super(parentStyleSheet, parentRule);
        pseudoPage_ = pseudoPage;
    }

    public CSSPageRuleImpl() {
        super();
    }

    public short getType() {
        return PAGE_RULE;
    }

    public String getCssText() {
        final StringBuilder result = new StringBuilder();

        final String sel = getSelectorText();
        result.append("@page ").append(sel);

        if ((sel.length() > 0)) {
            result.append(" ");
        }
        result.append("{");

        final CSSStyleDeclaration style = getStyle();
        if (null != style) {
            result.append(style.getCssText());
        }
        result.append("}");
        return result.toString();
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

            // The rule must be a page rule
            if (r.getType() == CSSRule.PAGE_RULE) {
                pseudoPage_ = ((CSSPageRuleImpl) r).pseudoPage_;
                style_ = ((CSSPageRuleImpl) r).style_;
            }
            else {
                throw new DOMExceptionImpl(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DOMExceptionImpl.EXPECTING_PAGE_RULE);
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

    public String getSelectorText() {
        final StringBuilder result = new StringBuilder();

        if ((null != pseudoPage_)) {
            result.append(":").append(pseudoPage_);
        }
        return result.toString();
    }

    public void setSelectorText(final String selectorText) throws DOMException {
    }

    public CSSStyleDeclaration getStyle() {
        return style_;
    }

    public void setPseudoPage(final String pseudoPage) {
        pseudoPage_ = pseudoPage;
    }

    public void setStyle(final CSSStyleDeclarationImpl style) {
        style_ = style;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSPageRule)) {
            return false;
        }
        final CSSPageRule cpr = (CSSPageRule) obj;
        return super.equals(obj)
            && LangUtils.equals(getSelectorText(), cpr.getSelectorText())
            && LangUtils.equals(getStyle(), cpr.getStyle());
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, pseudoPage_);
        hash = LangUtils.hashCode(hash, style_);
        return hash;
    }

    @Override
    public String toString() {
        return getCssText();
    }
}
