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
 * Implementation of CSSPageRule.
 *
 * @author Ronald Brill
 */
public class CSSPageRuleImpl extends AbstractCSSRuleImpl {

    private String pseudoPage_;
    private CSSStyleDeclarationImpl style_;

    /**
     * Ctor.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     * @param pseudoPage the pseudo page
     */
    public CSSPageRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final AbstractCSSRuleImpl parentRule,
            final String pseudoPage) {
        super(parentStyleSheet, parentRule);
        pseudoPage_ = pseudoPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCssText() {
        final StringBuilder sb = new StringBuilder();

        final String sel = getSelectorText();
        sb.append("@page ").append(sel);

        if (sel.length() > 0) {
            sb.append(" ");
        }
        sb.append("{");

        final CSSStyleDeclarationImpl style = getStyle();
        if (null != style) {
            sb.append(style.getCssText());
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCssText(final String cssText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            final AbstractCSSRuleImpl r = parser.parseRule(cssText);

            // The rule must be a page rule
            if (r instanceof CSSPageRuleImpl) {
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

    /**
     * @return the selector text
     */
    public String getSelectorText() {
        if (null == pseudoPage_) {
            return "";
        }
        return pseudoPage_;
    }

    /**
     * @return the style
     */
    public CSSStyleDeclarationImpl getStyle() {
        return style_;
    }

    /**
     * Changes the style.
     * @param style the new style
     */
    public void setStyle(final CSSStyleDeclarationImpl style) {
        style_ = style;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSPageRuleImpl)) {
            return false;
        }
        final CSSPageRuleImpl cpr = (CSSPageRuleImpl) obj;
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
