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

import com.steadystate.css.format.CSSFormat;
import com.steadystate.css.format.CSSFormatable;

/**
 * @author koch
 * @author rbri
 */
public abstract class AbstractCSSRuleImpl extends CSSOMObjectImpl implements CSSFormatable {

    private static final long serialVersionUID = 7829784704712797815L;

    private CSSStyleSheetImpl parentStyleSheet_;
    private CSSRule parentRule_;

    protected CSSStyleSheetImpl getParentStyleSheetImpl() {
        return parentStyleSheet_;
    }

    public void setParentStyleSheet(final CSSStyleSheetImpl parentStyleSheet) {
        parentStyleSheet_ = parentStyleSheet;
    }

    public void setParentRule(final CSSRule parentRule) {
        parentRule_ = parentRule;
    }

    public AbstractCSSRuleImpl(final CSSStyleSheetImpl parentStyleSheet, final CSSRule parentRule) {
        super();
        parentStyleSheet_ = parentStyleSheet;
        parentRule_ = parentRule;
    }

    public AbstractCSSRuleImpl() {
        super();
    }

    public CSSStyleSheet getParentStyleSheet() {
        return parentStyleSheet_;
    }

    public CSSRule getParentRule() {
        return parentRule_;
    }

    /**
     * {@inheritDoc}
     */
    public abstract String getCssText(final CSSFormat format);

    /**
     * Same as {@link #getCssText(CSSFormat)} but using the default format.
     *
     * @return the formated string
     */
    public String getCssText() {
        return getCssText(null);
    }

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

    @Override
    public int hashCode() {
        final int hash = super.hashCode();
        // don't use parentRule and parentStyleSheet in hashCode()
        // recursive loop -> stack overflow!
        return hash;
    }
}