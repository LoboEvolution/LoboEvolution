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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSRuleList;

import com.steadystate.css.util.LangUtils;


/**
 * Implementation of {@link CSSRuleList}.
 *
 * @author <a href="mailto:davidsch@users.sourceforge.net">David Schweinsberg</a>
 * @author rbri
 */
public class CSSRuleListImpl implements CSSRuleList, Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1269068897476453290L;

    /** The rules_. */
    private List<CSSRule> rules_;

    /**
     * Gets the rules.
     *
     * @return the rules
     */
    public List<CSSRule> getRules() {
        if (rules_ == null) {
            rules_ = new ArrayList<CSSRule>();
        }
        return rules_;
    }

    /**
     * Sets the rules.
     *
     * @param rules the new rules
     */
    public void setRules(final List<CSSRule> rules) {
        rules_ = rules;
    }

    /**
     * Instantiates a new CSS rule list impl.
     */
    public CSSRuleListImpl() {
        super();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRuleList#getLength()
     */
    public int getLength() {
        return getRules().size();
    }

    /* (non-Javadoc)
     * @see org.w3c.dom.css.CSSRuleList#item(int)
     */
    public CSSRule item(final int index) {
        return getRules().get(index);
    }

    /**
     * Adds the.
     *
     * @param rule the rule
     */
    public void add(final CSSRule rule) {
        getRules().add(rule);
    }

    /**
     * Insert.
     *
     * @param rule the rule
     * @param index the index
     */
    public void insert(final CSSRule rule, final int index) {
        getRules().add(index, rule);
    }

    /**
     * Delete.
     *
     * @param index the index
     */
    public void delete(final int index) {
        getRules().remove(index);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getLength(); i++) {
            sb.append(item(i).toString()).append("\r\n");
        }
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSRuleList)) {
            return false;
        }
        final CSSRuleList crl = (CSSRuleList) obj;
        return equalsRules(crl);
    }

    /**
     * Equals rules.
     *
     * @param crl the crl
     * @return true, if successful
     */
    private boolean equalsRules(final CSSRuleList crl) {
        if ((crl == null) || (getLength() != crl.getLength())) {
            return false;
        }
        for (int i = 0; i < getLength(); i++) {
            final CSSRule cssRule1 = item(i);
            final CSSRule cssRule2 = crl.item(i);
            if (!LangUtils.equals(cssRule1, cssRule2)) {
                return false;
            }
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, rules_);
        return hash;
    }
}
