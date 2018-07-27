/*
 * Copyright (c) 2018 Ronald Brill.
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.css.CSSStyleSheet;

import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.StyleSheetList;

import com.gargoylesoftware.css.util.LangUtils;

/**
 * Implementation of {@link StyleSheetList}.
 *
 * @author Ronald Brill
 */
public class CSSStyleSheetListImpl implements StyleSheetList {
    private List<CSSStyleSheet> cssStyleSheets_;

    /**
     * @return the list of style sheets
     */
    public List<CSSStyleSheet> getCSSStyleSheets() {
        if (cssStyleSheets_ == null) {
            cssStyleSheets_ = new ArrayList<CSSStyleSheet>();
        }
        return cssStyleSheets_;
    }

   // start StyleSheetList
    @Override
    public int getLength() {
        return getCSSStyleSheets().size();
    }

    @Override
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
