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
import com.gargoylesoftware.css.parser.selector.SelectorList;
import com.gargoylesoftware.css.util.LangUtils;

/**
 * Implementation of CSSStyleRule.
 *
 * Author Ronald Brill
 *
 */
public class CSSStyleRuleImpl extends AbstractCSSRuleImpl {

    private SelectorList selectors_;
    private CSSStyleDeclarationImpl style_;

    /**
     * Ctor.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     * @param selectors the selectors
     */
    public CSSStyleRuleImpl(final CSSStyleSheetImpl parentStyleSheet,
        final AbstractCSSRuleImpl parentRule, final SelectorList selectors) {
        super(parentStyleSheet, parentRule);
        setSelectors(selectors);
    }

    /**
     * <p>getSelectors.</p>
     *
     * @return all selectors
     */
    public SelectorList getSelectors() {
        return selectors_;
    }

    /**
     * Updates the selectors.
     *
     * @param selectors the new selectors
     */
    public void setSelectors(final SelectorList selectors) {
        selectors_ = selectors;
    }

    /** {@inheritDoc} */
    @Override
    public String getCssText() {
        final CSSStyleDeclarationImpl style = getStyle();
        if (null == style) {
            return "";
        }

        final String selectorText = selectors_.toString();
        final String styleText = style.toString();

        if (null == styleText || styleText.length() == 0) {
            return selectorText + " { }";
        }

        return selectorText + " { " + styleText + " }";
    }

    /** {@inheritDoc} */
    @Override
    public void setCssText(final String cssText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            final AbstractCSSRuleImpl r = parser.parseRule(cssText);

            // The rule must be a style rule
            if (r instanceof CSSStyleRuleImpl) {
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

    /**
     * <p>getSelectorText.</p>
     *
     * @return the selector text
     */
    public String getSelectorText() {
        return selectors_.toString();
    }

    /**
     * Sets the selector text.
     *
     * @param selectorText the new selector text
     * @throws org.w3c.dom.DOMException in clase of error
     */
    public void setSelectorText(final String selectorText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            selectors_ = parser.parseSelectors(selectorText);
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
     * <p>getStyle.</p>
     *
     * @return the style
     */
    public CSSStyleDeclarationImpl getStyle() {
        return style_;
    }

    /**
     * Replaces the style.
     *
     * @param style the new style
     */
    public void setStyle(final CSSStyleDeclarationImpl style) {
        style_ = style;
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
        if (!(obj instanceof CSSStyleRuleImpl)) {
            return false;
        }
        final CSSStyleRuleImpl csr = (CSSStyleRuleImpl) obj;
        return super.equals(obj)
            && LangUtils.equals(getSelectorText(), csr.getSelectorText())
            && LangUtils.equals(getStyle(), csr.getStyle());
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, selectors_);
        hash = LangUtils.hashCode(hash, style_);
        return hash;
    }
}
