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

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleSheet;


/**
 * The Class AbstractCSSRuleImpl.
 *
 * @author koch
 * @author rbri
 */
public abstract class AbstractCSSRuleImpl extends CSSOMObjectImpl {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7829784704712797815L;

    /** The parent style sheet_. */
    private CSSStyleSheetImpl parentStyleSheet_;
    
    /** The parent rule_. */
    private CSSRule parentRule_;

    /**
     * Gets the parent style sheet impl.
     *
     * @return the parent style sheet impl
     */
    protected CSSStyleSheetImpl getParentStyleSheetImpl() {
        return parentStyleSheet_;
    }

    /**
     * Sets the parent style sheet.
     *
     * @param parentStyleSheet the new parent style sheet
     */
    public void setParentStyleSheet(final CSSStyleSheetImpl parentStyleSheet) {
        parentStyleSheet_ = parentStyleSheet;
    }

    /**
     * Sets the parent rule.
     *
     * @param parentRule the new parent rule
     */
    public void setParentRule(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    /**
     * Instantiates a new abstract css rule impl.
     *
     * @param parentStyleSheet the parent style sheet
     * @param parentRule the parent rule
     */
    public AbstractCSSRuleImpl(final CSSStyleSheetImpl parentStyleSheet, final CSSRule parentRule) {
        super();
        parentStyleSheet_ = parentStyleSheet;
        parentRule_ = parentRule;
    }

    /**
     * Instantiates a new abstract css rule impl.
     */
    public AbstractCSSRuleImpl() {
        super();
    }

    /**
     * Gets the parent style sheet.
     *
     * @return the parent style sheet
     */
    public CSSStyleSheet getParentStyleSheet() {
        return parentStyleSheet_;
    }

    /**
     * Gets the parent rule.
     *
     * @return the parent rule
     */
    public CSSRule getParentRule() {
        return parentRule_;
    }

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.CSSOMObjectImpl#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSRule)) {
            return false;
        }
        return super.equals(obj);
        // don't use parentRule and parentStyleSheet in equals()
        // recursive loop -> stack overflow!
    }

    /* (non-Javadoc)
     * @see com.steadystate.css.dom.CSSOMObjectImpl#hashCode()
     */
    @Override
    public int hashCode() {
        final int hash = super.hashCode();
        // don't use parentRule and parentStyleSheet in hashCode()
        // recursive loop -> stack overflow!
        return hash;
    }
}