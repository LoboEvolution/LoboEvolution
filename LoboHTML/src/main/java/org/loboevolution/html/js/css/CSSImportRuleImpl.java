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

import org.loboevolution.css.CSSImportRule;
import org.loboevolution.css.CSSStyleSheet;

/**
 * <p>CSSImportRuleImpl class.</p>
 */
public class CSSImportRuleImpl extends AbstractCSSStyleRule implements CSSImportRule {

    private final org.htmlunit.cssparser.dom.CSSImportRuleImpl abstractCSSRule;

    public CSSImportRuleImpl(final org.htmlunit.cssparser.dom.CSSImportRuleImpl abstractCSSRule) {
        super(abstractCSSRule);
        this.abstractCSSRule = abstractCSSRule;
    }

    public MediaListImpl getMedia() {
        return new MediaListImpl(abstractCSSRule.getMedia());
    }

    @Override
    public void setMedia(String media) {

    }

    public CSSStyleSheet getStyleSheet() {
        return new CSSStyleSheetImpl(abstractCSSRule.getStyleSheet());
    }

    public String getHref() {
        return abstractCSSRule.getHref();
    }

    @Override
    public String toString() {
        return "[object CSSImportRule]";
    }

}