/*
 * Copyright (C) 1999-2017 David Schweinsberg.
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

package com.steadystate.css.dom;

import java.io.IOException;
import java.io.StringReader;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleRule;

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;
import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link CSSStyleRule}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CSSStyleRuleImpl extends AbstractCSSRuleImpl implements CSSStyleRule {

    private static final long serialVersionUID = -697009251364657426L;

    private SelectorList selectors_;
    private CSSStyleDeclaration style_;

    public SelectorList getSelectors() {
        return selectors_;
    }

    public void setSelectors(final SelectorList selectors) {
        selectors_ = selectors;
    }

    public CSSStyleRuleImpl(final CSSStyleSheetImpl parentStyleSheet,
        final CSSRule parentRule, final SelectorList selectors) {
        super(parentStyleSheet, parentRule);
        selectors_ = selectors;
    }

    public CSSStyleRuleImpl() {
        super();
    }

    public short getType() {
        return STYLE_RULE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCssText(final CSSFormat format) {
        final CSSStyleDeclaration style = getStyle();
        if (null == style) {
            return "";
        }

        final String selectorText = ((CSSFormatable) selectors_).getCssText(format);
        final String styleText = ((CSSFormatable) style).getCssText(format);

        if (null == styleText || styleText.length() == 0) {
            return selectorText + " { }";
        }

        if (format != null && format.getPropertiesInSeparateLines()) {
            return selectorText + " {" + styleText + "}";
        }

        return selectorText + " { " + styleText + " }";
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

            // The rule must be a style rule
            if (r.getType() == CSSRule.STYLE_RULE) {
                selectors_ = ((CSSStyleRuleImpl) r).selectors_;
                style_ = ((CSSStyleRuleImpl) r).style_;
            }
            else {
                throw new DOMExceptionImpl(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DOMExceptionImpl.EXPECTING_STYLE_RULE);
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
        return selectors_.toString();
    }

    public void setSelectorText(final String selectorText) throws DOMException {
        final CSSStyleSheetImpl parentStyleSheet = getParentStyleSheetImpl();
        if (parentStyleSheet != null && parentStyleSheet.isReadOnly()) {
            throw new DOMExceptionImpl(
                DOMException.NO_MODIFICATION_ALLOWED_ERR,
                DOMExceptionImpl.READ_ONLY_STYLE_SHEET);
        }

        try {
            final InputSource is = new InputSource(new StringReader(selectorText));
            final CSSOMParser parser = new CSSOMParser();
            selectors_ = parser.parseSelectors(is);
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

    public CSSStyleDeclaration getStyle() {
        return style_;
    }

    public void setStyle(final CSSStyleDeclaration style) {
        style_ = style;
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
        if (!(obj instanceof CSSStyleRule)) {
            return false;
        }
        final CSSStyleRule csr = (CSSStyleRule) obj;
        return super.equals(obj)
            && LangUtils.equals(getSelectorText(), csr.getSelectorText())
            && LangUtils.equals(getStyle(), csr.getStyle());
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, selectors_);
        hash = LangUtils.hashCode(hash, style_);
        return hash;
    }
}
