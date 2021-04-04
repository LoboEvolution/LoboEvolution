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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.css.util.LangUtils;

/**
 * Implementation of StyleSheetList.
 *
 * Author Ronald Brill
 *
 */
public class CSSStyleSheetListImpl {
    private List<CSSStyleSheetImpl> cssStyleSheets_;

    /**
     * <p>getCSSStyleSheets.</p>
     *
     * @return the list of style sheets
     */
    public List<CSSStyleSheetImpl> getCSSStyleSheets() {
        if (cssStyleSheets_ == null) {
            cssStyleSheets_ = new ArrayList<>();
        }
        return cssStyleSheets_;
    }

    /**
     * <p>getLength.</p>
     *
     * @return the number of style sheets
     */
    public int getLength() {
        return getCSSStyleSheets().size();
    }

    /**
     * Adds a CSSStyleSheet.
     *
     * @param cssStyleSheet the CSSStyleSheet
     */
    public void add(final CSSStyleSheetImpl cssStyleSheet) {
        getCSSStyleSheets().add(cssStyleSheet);
    }
    // end StyleSheetList

    /**
     * Merges all StyleSheets in this list into one.
     *
     * @return the new (merged) StyleSheet
     */
    public CSSStyleSheetImpl merge() {
        final CSSStyleSheetImpl merged = new CSSStyleSheetImpl();
        final CSSRuleListImpl cssRuleList = new CSSRuleListImpl();
        final Iterator<CSSStyleSheetImpl> it = getCSSStyleSheets().iterator();
        while (it.hasNext()) {
            final CSSStyleSheetImpl cssStyleSheet = it.next();
            final CSSMediaRuleImpl cssMediaRule = new CSSMediaRuleImpl(merged, null, cssStyleSheet.getMedia());
            cssMediaRule.setRuleList(cssStyleSheet.getCssRules());
            cssRuleList.add(cssMediaRule);
        }
        merged.setCssRules(cssRuleList);
        merged.setMediaText("all");
        return merged;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CSSStyleSheetListImpl)) {
            return false;
        }
        final CSSStyleSheetListImpl ssl = (CSSStyleSheetListImpl) obj;
        return equalsStyleSheets(ssl);
    }

    private boolean equalsStyleSheets(final CSSStyleSheetListImpl ssl) {
        if ((ssl == null) || (getLength() != ssl.getLength())) {
            return false;
        }
        int i = 0;
        for (CSSStyleSheetImpl styleSheet : cssStyleSheets_) {
            final CSSStyleSheetImpl styleSheet2 = ssl.cssStyleSheets_.get(i);
            if (!LangUtils.equals(styleSheet, styleSheet2)) {
                return false;
            }
            i++;
        }
        return true;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, cssStyleSheets_);
        return hash;
    }

}
