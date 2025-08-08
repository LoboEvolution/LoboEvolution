/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import org.loboevolution.css.CSSMediaRule;
import org.loboevolution.css.CSSRuleList;
import org.loboevolution.css.MediaList;

import java.util.List;

/**
 * <p>CSSMediaRuleImpl class.</p>
 */
public class CSSMediaRuleImpl extends AbstractCSSStyleRule implements CSSMediaRule {

    private final org.htmlunit.cssparser.dom.CSSMediaRuleImpl cssMediaRule;

    private final CSSRuleListImpl cssRuleList;

    public CSSMediaRuleImpl(AbstractCSSRuleImpl abstractCSSRule) {
        super(abstractCSSRule);
        this.cssMediaRule = (org.htmlunit.cssparser.dom.CSSMediaRuleImpl) abstractCSSRule;
        cssRuleList = new CSSRuleListImpl(cssMediaRule.getCssRules());
    }

    @Override
    public MediaList getMedia() {
        return null;
    }

    @Override
    public void setMedia(String media) {
    }

    @Override
    public CSSRuleList getCssRules() {
        return cssRuleList;
    }

    @Override
    public void insertRule(String rule) {
        cssMediaRule.insertRule(rule, 0);
        cssRuleList.getStyleRuleList().addFirst(new CSSStyleRuleImpl(cssMediaRule.getCssRules().getRules().getFirst()));
    }

    @Override
    public void insertRule(String rule, Integer index) {
        List<AbstractCSSRuleImpl> rules = cssMediaRule.getCssRules().getRules();
        int ruleCount = rules.size();
        int insertionIndex = (index != null) ? index : ruleCount;

        cssMediaRule.insertRule(rule, insertionIndex);
        cssRuleList.getStyleRuleList().add(new CSSStyleRuleImpl(cssMediaRule.getCssRules().getRules().get(insertionIndex)));
    }

    @Override
    public void deleteRule(Integer index) {
        cssMediaRule.deleteRule(index != null ? index : 0);
        cssRuleList.getStyleRuleList().remove(index != null ? index : 0);
    }

    @Override
    public String toString() {
        return "[object CSSMediaRule]";
    }
}
