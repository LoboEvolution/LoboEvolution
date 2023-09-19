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
import org.htmlunit.cssparser.util.CSSProperties;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.node.css.CSSStyleRule;
import org.loboevolution.html.style.setter.BorderStyleSetter;
import org.loboevolution.html.style.setter.FourCornersSetter;
import org.loboevolution.net.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CSSStyleRuleImpl extends CSSRuleImpl implements CSSStyleRule {

    private final AbstractCSSRuleImpl abstractCSSRule;

    public CSSStyleRuleImpl(AbstractCSSRuleImpl abstractCSSRule) {
        super(abstractCSSRule);
        this.abstractCSSRule = abstractCSSRule;
    }

    /** {@inheritDoc} */
    @Override
    public String getSelectorText() {
        if (abstractCSSRule instanceof org.htmlunit.cssparser.dom.CSSStyleRuleImpl) {
            final org.htmlunit.cssparser.dom.CSSStyleRuleImpl styleRule = (org.htmlunit.cssparser.dom.CSSStyleRuleImpl) abstractCSSRule;
            return styleRule.getSelectorText();
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public CSSStyleDeclaration getStyle() {
        AtomicReference<CSSStyleDeclarationImpl> atomicReference = new AtomicReference<>();
        if (abstractCSSRule instanceof org.htmlunit.cssparser.dom.CSSStyleRuleImpl) {
            final org.htmlunit.cssparser.dom.CSSStyleRuleImpl styleRule = (org.htmlunit.cssparser.dom.CSSStyleRuleImpl) abstractCSSRule;
            List<NameValuePair> list = new ArrayList<>();

            styleRule.getStyle().getProperties().forEach(p -> {
                list.add(new NameValuePair(p.getName(), p.getValue().toString()));
            });

            atomicReference.set(new CSSStyleDeclarationImpl(styleRule.getStyle()));

            list.forEach(p -> {
                switch (p.getName()) {
                    case CSSProperties.MARGIN:
                    case CSSProperties.BORDER:
                    case CSSProperties.BORDER_COLOR:
                    case CSSProperties.BORDER_WIDTH:
                    case CSSProperties.PADDING:
                        new FourCornersSetter(p.getName(), p.getName() + "-", "").changeValue(atomicReference.get(), p.getValue());
                        break;
                    case CSSProperties.BORDER_STYLE:
                        new BorderStyleSetter(p.getName(), p.getName() + "-", "-style").changeValue(atomicReference.get(), p.getValue());
                    default:
                        break;
                }
            });
        }

        return atomicReference.get();
    }

    @Override
    public String toString() {
        return "[object CSSStyleRule]";
    }
}
