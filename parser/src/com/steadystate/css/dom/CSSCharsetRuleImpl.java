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
import org.w3c.dom.css.CSSCharsetRule;
import org.w3c.dom.css.CSSRule;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.LangUtils;


/**
 * Implementation of {@link CSSCharsetRule}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CSSCharsetRuleImpl extends AbstractCSSRuleImpl implements CSSCharsetRule {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2472209213089007127L;

    /** The encoding_. */
    private String encoding_;

    /**
     * Instantiates a new CSS charset rule impl.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     * @param encoding the encoding
     */
    public CSSCharsetRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final CSSRule parentRule,
            final String encoding) {
        super(parentStyleSheet, parentRule);
        encoding_ = encoding;
    }

    /**
     * Instantiates a new CSS charset rule impl.
     */
    public CSSCharsetRuleImpl() {
        super();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRule#getType()
     */
    public short getType() {
        return CHARSET_RULE;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRule#getCssText()
     */
    public String getCssText() {
        return "@charset \"" + getEncoding() + "\";";
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

            // The rule must be a charset rule
            if (r.getType() == CSSRule.CHARSET_RULE) {
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

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSCharsetRule#getEncoding()
     */
    public String getEncoding() {
        return encoding_;
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSCharsetRule#setEncoding(java.lang.String)
     */
    public void setEncoding(final String encoding) throws DOMException {
        encoding_ = encoding;
    }

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.AbstractCSSRuleImpl#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSCharsetRule)) {
            return false;
        }
        final CSSCharsetRule ccr = (CSSCharsetRule) obj;
        return super.equals(obj)
            && LangUtils.equals(getEncoding(), ccr.getEncoding());
    }

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.AbstractCSSRuleImpl#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, encoding_);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getCssText();
    }

}
