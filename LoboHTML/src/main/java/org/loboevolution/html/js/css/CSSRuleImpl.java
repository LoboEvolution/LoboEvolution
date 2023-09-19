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

import org.htmlunit.cssparser.dom.AbstractCSSRuleImpl;
import org.loboevolution.html.node.css.CSSRule;
import org.loboevolution.html.node.css.CSSStyleSheet;

public class CSSRuleImpl implements CSSRule {

    private final AbstractCSSRuleImpl abstractCSSRule;

    public CSSRuleImpl(AbstractCSSRuleImpl abstractCSSRule) {
        this.abstractCSSRule = abstractCSSRule;
    }

    /** {@inheritDoc} */
    @Override
    public String getCssText() {
        return abstractCSSRule.getCssText();
    }

    /** {@inheritDoc} */
    @Override
    public CSSRule getParentRule() {
        AbstractCSSRuleImpl parent = abstractCSSRule.getParentRule();
        if (parent != null) {
            return new CSSRuleImpl(parent);
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public CSSStyleSheet getParentStyleSheet() {
        return new CSSStyleSheetImpl(abstractCSSRule.getParentStyleSheet());
    }

    /** {@inheritDoc} */
    @Override
    public int getType() {
        return 1;
    }

    @Override
    public String toString() {
        return "[object CSSRule]";
    }
}
