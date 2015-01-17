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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.css.CSSStyleSheet;

import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

import com.steadystate.css.util.LangUtils;

/**
 * Implementation of {@link StyleSheetList}.
 *
 * @author <a href="mailto:waldbaer@users.sourceforge.net">Johannes Koch</a>
 */
public class CSSStyleSheetListImpl implements StyleSheetList {
    private List<CSSStyleSheet> cssStyleSheets_;

    public List<CSSStyleSheet> getCSSStyleSheets() {
        if (cssStyleSheets_ == null) {
            cssStyleSheets_ = new ArrayList<CSSStyleSheet>();
        }
        return cssStyleSheets_;
    }

    public void setCSSStyleSheets(final List<CSSStyleSheet> cssStyleSheets) {
        cssStyleSheets_ = cssStyleSheets;
    }

    /**
     * Creates a new instance of CSSStyleSheetListImpl
     */
    public CSSStyleSheetListImpl() {
        super();
    }

    // start StyleSheetList
    public int getLength() {
        return getCSSStyleSheets().size();
    }

    public StyleSheet item(final int index) {
        return getCSSStyleSheets().get(index);
    }

    /**
     * Adds a CSSStyleSheet.
     *
     * @param cssStyleSheet the CSSStyleSheet
     */
    public void add(final CSSStyleSheet cssStyleSheet) {
        getCSSStyleSheets().add(cssStyleSheet);
    }
    // end StyleSheetList

    /**
     * Merges all StyleSheets in this list into one.
     *
     * @return the new (merged) StyleSheet
     */
    public StyleSheet merge() {
        final CSSStyleSheetImpl merged = new CSSStyleSheetImpl();
        final CSSRuleListImpl cssRuleList = new CSSRuleListImpl();
        final Iterator<CSSStyleSheet> it = getCSSStyleSheets().iterator();
        while (it.hasNext()) {
            final CSSStyleSheetImpl cssStyleSheet = (CSSStyleSheetImpl) it.next();
            final CSSMediaRuleImpl cssMediaRule = new CSSMediaRuleImpl(merged, null, cssStyleSheet.getMedia());
            cssMediaRule.setRuleList((CSSRuleListImpl) cssStyleSheet.getCssRules());
            cssRuleList.add(cssMediaRule);
        }
        merged.setCssRules(cssRuleList);
        merged.setMediaText("all");
        return merged;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StyleSheetList)) {
            return false;
        }
        final StyleSheetList ssl = (StyleSheetList) obj;
        return equalsStyleSheets(ssl);
    }

    private boolean equalsStyleSheets(final StyleSheetList ssl) {
        if ((ssl == null) || (getLength() != ssl.getLength())) {
            return false;
        }
        for (int i = 0; i < getLength(); i++) {
            final StyleSheet styleSheet1 = item(i);
            final StyleSheet styleSheet2 = ssl.item(i);
            if (!LangUtils.equals(styleSheet1, styleSheet2)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, cssStyleSheets_);
        return hash;
    }

}
