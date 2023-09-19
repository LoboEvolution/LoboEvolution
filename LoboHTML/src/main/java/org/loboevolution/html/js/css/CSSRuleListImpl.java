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
import org.htmlunit.cssparser.dom.CSSUnknownRuleImpl;
import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.css.CSSRuleList;
import org.loboevolution.html.node.css.CSSStyleRule;

import java.util.List;

public class CSSRuleListImpl extends AbstractList<CSSStyleRule>  implements CSSRuleList {

    private final org.htmlunit.cssparser.dom.CSSRuleListImpl cssRuleList;

    public CSSRuleListImpl(org.htmlunit.cssparser.dom.CSSRuleListImpl cssRuleList) {
        this.cssRuleList = cssRuleList;
    }

    /** {@inheritDoc} */
    @Override
    public CSSStyleRule item(int index) {
        try{
            return this.get(index);
        } catch (Exception e){
            return null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public long getLength() {
        return this.size();
    }

    /**
     * <p> addStyleRule. </p>
     * @param newList a {@link org.htmlunit.cssparser.dom.CSSRuleListImpl} object.
     */
    public void addStyleRule(org.htmlunit.cssparser.dom.CSSRuleListImpl newList){
        List<AbstractCSSRuleImpl> ruls =  null;
        if (newList != null) {
            clear();
            ruls = newList.getRules();
        } else {
            ruls = cssRuleList.getRules();
        }

        ruls.forEach(abstractCSSRule -> {
            if (!(abstractCSSRule instanceof CSSUnknownRuleImpl))
                add(new CSSStyleRuleImpl(abstractCSSRule));
        });
    }

    @Override
    public String toString() {
        return "[object CSSRuleList]";
    }
}
