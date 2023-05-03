/*
 * Copyright (c) 2019-2023 Ronald Brill.
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
package org.htmlunit.cssparser.dom;

import java.io.IOException;

import org.htmlunit.cssparser.parser.CSSException;
import org.htmlunit.cssparser.parser.CSSOMParser;
import org.htmlunit.cssparser.util.ParserUtils;
import org.htmlunit.cssparser.util.ThrowCssExceptionErrorHandler;

/**
 * Implementation of CSSPageRule.
 *
 * @author Ronald Brill
 *
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

    /** {@inheritDoc} */
    @Override
    public String getCssText() {
        final StringBuilder sb = new StringBuilder();

        final String sel = getSelectorText();
        sb.append("@page ").append(sel);

        if (sel.length() > 0) {
            sb.append(' ');
        }
        sb.append("{ ");

        final CSSStyleDeclarationImpl style = getStyle();
        if (null != style) {
            sb.append(style.getCssText());
            if (style.getProperties().size() > 0) {
                sb.append("; ");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void setCssText(final String cssText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
            final AbstractCSSRuleImpl r = parser.parseRule(cssText);

            // The rule must be a page rule
            if (r instanceof CSSPageRuleImpl) {
                pseudoPage_ = ((CSSPageRuleImpl) r).pseudoPage_;
                style_ = ((CSSPageRuleImpl) r).style_;
            }
            else {
                throw new DOMException(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DOMException.EXPECTING_PAGE_RULE);
            }
        }
        catch (final CSSException | IOException e) {
            throw new DOMException(
                DOMException.SYNTAX_ERR,
                DOMException.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    /**
     * <p>getSelectorText.</p>
     *
     * @return the selector text
     */
    public String getSelectorText() {
        if (null == pseudoPage_) {
            return "";
        }
        return pseudoPage_;
    }

    /**
     * Sets the selector text.
     * @param selectorText the new selector text
     */
    public void setSelectorText(final String selectorText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            parser.setErrorHandler(ThrowCssExceptionErrorHandler.INSTANCE);
            final AbstractCSSRuleImpl r = parser.parseRule("@page " + selectorText + " {}");

            // The rule must be a page rule
            if (r instanceof CSSPageRuleImpl) {
                pseudoPage_ = ((CSSPageRuleImpl) r).pseudoPage_;
            }
            else {
                throw new DOMException(
                    DOMException.INVALID_MODIFICATION_ERR,
                        DOMException.EXPECTING_PAGE_RULE);
            }
        }
        catch (final CSSException | IOException e) {
            throw new DOMException(
                DOMException.SYNTAX_ERR,
                DOMException.SYNTAX_ERROR,
                e.getMessage());
        }
    }

    /**
     * @return the style
     */
    public CSSStyleDeclarationImpl getStyle() {
        return style_;
    }

    /**
     * Changes the style.
     *
     * @param style the new style
     */
    public void setStyle(final CSSStyleDeclarationImpl style) {
        style_ = style;
    }

    /** {@inheritDoc} */
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
            && ParserUtils.equals(getSelectorText(), cpr.getSelectorText())
            && ParserUtils.equals(getStyle(), cpr.getStyle());
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = ParserUtils.hashCode(hash, pseudoPage_);
        hash = ParserUtils.hashCode(hash, style_);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getCssText();
    }
}
