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
 * Implementation of CSSCharsetRule.
 *
 * Author Ronald Brill
 *
 */
public class CSSCharsetRuleImpl extends AbstractCSSRuleImpl {

    private String encoding_;

    /**
     * Ctor.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     * @param encoding the encoding
     */
    public CSSCharsetRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final AbstractCSSRuleImpl parentRule,
            final String encoding) {
        super(parentStyleSheet, parentRule);
        encoding_ = encoding;
    }

    /** {@inheritDoc} */
    @Override
    public void setCssText(final String cssText) throws DOMException {
        try {
            final CSSOMParser parser = new CSSOMParser();
            final AbstractCSSRuleImpl r = parser.parseRule(cssText);

            // The rule must be a charset rule
            if (r instanceof CSSCharsetRuleImpl) {
                encoding_ = ((CSSCharsetRuleImpl) r).encoding_;
            }
            else {
                throw new DOMExceptionImpl(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DOMExceptionImpl.EXPECTING_CHARSET_RULE);
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
     * <p>getEncoding.</p>
     *
     * @return the encoding
     */
    public String getEncoding() {
        return encoding_;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSCharsetRuleImpl)) {
            return false;
        }
        final CSSCharsetRuleImpl ccr = (CSSCharsetRuleImpl) obj;
        return super.equals(obj)
            && LangUtils.equals(getEncoding(), ccr.getEncoding());
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, encoding_);
        return hash;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return getCssText();
    }

    /** {@inheritDoc} */
    @Override
    public String getCssText() {
        final StringBuilder sb = new StringBuilder();

        sb.append("@charset \"");

        final String enc = getEncoding();
        if (null != enc) {
            sb.append(enc);
        }
        sb.append("\";");
        return sb.toString();
    }
}
