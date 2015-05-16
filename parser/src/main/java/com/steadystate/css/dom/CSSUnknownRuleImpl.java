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
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSUnknownRule;

import com.steadystate.css.parser.CSSOMParser;
import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link CSSUnknownRule}.
 *
 * TODO: Reinstate setCssText
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CSSUnknownRuleImpl extends AbstractCSSRuleImpl implements CSSUnknownRule {

    private static final long serialVersionUID = -268104019127675990L;

    private String text_;

    public String getText() {
        return text_;
    }

    public void setText(final String text) {
        text_ = text;
    }

    public CSSUnknownRuleImpl(
            final CSSStyleSheetImpl parentStyleSheet,
            final CSSRule parentRule,
            final String text) {
        super(parentStyleSheet, parentRule);
        text_ = text;
    }

    public CSSUnknownRuleImpl() {
        super();
    }

    public short getType() {
        return UNKNOWN_RULE;
    }

    public String getCssText() {
        return getCssText(null);
    }

    /**
     * Returns a string representation of the rule based on the given format.
     * If provided format is null, the result is the same as getCssText()
     *
     * @param format the formating rules
     * @return the formated string
     */
    public String getCssText(final CSSFormat format) {
        return text_;
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

            // The rule must be an unknown rule
            if (r.getType() == CSSRule.UNKNOWN_RULE) {
                text_ = ((CSSUnknownRuleImpl) r).text_;
            }
            else {
                throw new DOMExceptionImpl(
                    DOMException.INVALID_MODIFICATION_ERR,
                    DOMExceptionImpl.EXPECTING_FONT_FACE_RULE);
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

    @Override
    public String toString() {
        return getCssText(null);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSUnknownRule)) {
            return false;
        }
        final CSSUnknownRule cur = (CSSUnknownRule) obj;
        return super.equals(obj)
            && LangUtils.equals(getCssText(), cur.getCssText());
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = LangUtils.hashCode(hash, text_);
        return hash;
    }

}
