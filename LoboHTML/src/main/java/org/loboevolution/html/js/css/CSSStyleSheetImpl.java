/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.js.css;

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.html.node.css.CSSRule;
import org.loboevolution.html.node.css.CSSRuleList;
import org.loboevolution.html.node.css.CSSStyleSheet;

public class CSSStyleSheetImpl extends StyleSheetImpl implements CSSStyleSheet {

    private final com.gargoylesoftware.css.dom.CSSStyleSheetImpl cssStyleSheet;

    private final CSSRuleListImpl cssRuleList;

    public CSSStyleSheetImpl(com.gargoylesoftware.css.dom.CSSStyleSheetImpl cssStyleSheet) {
        super(cssStyleSheet);
        this.cssStyleSheet = cssStyleSheet;
        this.cssRuleList = new CSSRuleListImpl(cssStyleSheet.getCssRules());
        this.cssRuleList.addStyleRule(null);
    }

    /** {@inheritDoc} */
    @Override
    public CSSRule getOwnerRule() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public CSSRuleList getCssRules() {
        return this.cssRuleList;
    }

    /** {@inheritDoc} */
    @Override
    public long insertRule(String rule, int index) {
        try {
            this.cssStyleSheet.insertRule(rule, index);
            this.cssRuleList.addStyleRule(cssStyleSheet.getCssRules());
        } catch (Exception e) {
            throw new DOMException(
                    DOMException.INDEX_SIZE_ERR, e.getMessage());
        }
        return index;
    }

    /** {@inheritDoc} */
    @Override
    public void deleteRule(int index) {
        try {
            cssStyleSheet.deleteRule(index);
            this.cssRuleList.addStyleRule(cssStyleSheet.getCssRules());
        } catch (Exception e) {
            throw new DOMException(
                    DOMException.INDEX_SIZE_ERR, e.getMessage());
        }
    }

    public void setDisabled(boolean disabled) {
        cssStyleSheet.setDisabled(disabled);
    }

    public com.gargoylesoftware.css.dom.CSSStyleSheetImpl getCssStyleSheet() {
        return this.cssStyleSheet;
    }

    @Override
    public String toString() {
        return "[object CSSStyleSheet]";
    }
}