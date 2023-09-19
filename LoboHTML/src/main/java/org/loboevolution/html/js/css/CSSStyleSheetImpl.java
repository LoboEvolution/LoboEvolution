/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.js.css;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.node.css.CSSRule;
import org.loboevolution.html.node.css.CSSRuleList;
import org.loboevolution.html.node.css.CSSStyleSheet;

public class CSSStyleSheetImpl extends StyleSheetImpl implements CSSStyleSheet {

    private final org.htmlunit.cssparser.dom.CSSStyleSheetImpl cssStyleSheet;

    private final CSSRuleListImpl cssRuleList;

    public CSSStyleSheetImpl(org.htmlunit.cssparser.dom.CSSStyleSheetImpl cssStyleSheet) {
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
        } catch (IndexOutOfBoundsException e){
            throw new DOMException(
                    DOMException.INDEX_SIZE_ERR, e.getMessage());
        } catch (Exception e) {
            return 0;
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

    public org.htmlunit.cssparser.dom.CSSStyleSheetImpl getCssStyleSheet() {
        return this.cssStyleSheet;
    }

    @Override
    public String toString() {
        return "[object CSSStyleSheet]";
    }
}