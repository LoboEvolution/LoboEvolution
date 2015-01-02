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

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSUnknownRule;

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
        return text_;
    }

    public void setCssText(final String cssText) throws DOMException {
/*
        if( _parentStyleSheet != null && _parentStyleSheet.isReadOnly() )
        throw new DOMExceptionImpl(
        DOMException.NO_MODIFICATION_ALLOWED_ERR,
        DOMExceptionImpl.READ_ONLY_STYLE_SHEET );

        try
        {
            //
            // Parse the rule string and retrieve the rule
            //
            StringReader sr = new StringReader( cssText );
            CSS2Parser parser = new CSS2Parser( sr );
            ASTStyleSheetRuleSingle ssrs = parser.styleSheetRuleSingle();
            CSSRule r = (CSSRule) ssrs.jjtGetChild( 0 );

            //
            // The rule must be an unknown rule
            //
            if( r.getType() == CSSRule.UNKNOWN_RULE )
            {
                _text = ((ASTUnknownRule)r)._text;
                setChildren( ((SimpleNode)r).getChildren() );
            }
            else
            {
                throw new DOMExceptionImpl(
                DOMException.INVALID_MODIFICATION_ERR,
                DOMExceptionImpl.EXPECTING_UNKNOWN_RULE );
            }
        }
        catch( ParseException e )
        {
            throw new DOMExceptionImpl(
            DOMException.SYNTAX_ERR,
            DOMExceptionImpl.SYNTAX_ERROR,
            e.getMessage() );
        }
*/
    }

    // There's no need to override the methods from AbstractCSSRuleImpl
//    public CSSStyleSheet getParentStyleSheet() {
//        return parentStyleSheet;
//    }
//
//    public CSSRule getParentRule() {
//        return parentRule;
//    }

    @Override
    public String toString() {
        return getCssText();
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
