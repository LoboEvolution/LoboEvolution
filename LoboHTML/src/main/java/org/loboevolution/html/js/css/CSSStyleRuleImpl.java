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
import org.htmlunit.cssparser.dom.CSSPageRuleImpl;
import org.htmlunit.cssparser.util.CSSProperties;
import org.loboevolution.css.*;
import org.loboevolution.html.style.setter.*;
import org.loboevolution.net.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>CSSStyleRuleImpl class.</p>
 */
public class CSSStyleRuleImpl extends AbstractCSSStyleRule implements CSSProperties, CSSStyleRule {

    private final AbstractCSSRuleImpl abstractCSSRule;

    public CSSStyleRuleImpl(final AbstractCSSRuleImpl abstractCSSRule) {
        super(abstractCSSRule);
        this.abstractCSSRule = abstractCSSRule;
    }

    /** {@inheritDoc} */
    @Override
    public String getSelectorText() {
        if (abstractCSSRule instanceof org.htmlunit.cssparser.dom.CSSStyleRuleImpl styleRule) {
            return styleRule.getSelectorText();
        }

        if (abstractCSSRule instanceof CSSPageRuleImpl styleRule) {
            return styleRule.getSelectorText();
        }
        return null;
    }

    public void setSelectorText(final String selectorText) {
        if (abstractCSSRule instanceof CSSPageRuleImpl styleRule) {
            styleRule.setSelectorText(selectorText);
        }
    }

    @Override
    public CSSStyleSheet getParentStyleSheet() {
        return abstractCSSRule != null ? new CSSStyleSheetImpl(abstractCSSRule.getParentStyleSheet()) : null;
    }

    @Override
    public CSSMediaRule getParentRule() {
        return new CSSMediaRuleImpl(abstractCSSRule.getParentRule());
    }

    /** {@inheritDoc} */
    @Override
    public CSSStyleDeclaration getStyle() {
        final AtomicReference<CSSStyleDeclarationImpl> atomicReference = new AtomicReference<>();
        if (abstractCSSRule instanceof org.htmlunit.cssparser.dom.CSSStyleRuleImpl styleRule) {
            final List<NameValuePair> list = new ArrayList<>();

            styleRule.getStyle().getProperties().forEach(p -> list.add(new NameValuePair(p.getName(), p.getValue().toString())));

            atomicReference.set(new CSSStyleDeclarationImpl(styleRule.getStyle()));

            list.forEach(p -> {
                switch (p.getName()) {
                    case MARGIN:
                    case BORDER_COLOR:
                    case BORDER_WIDTH:
                    case PADDING:
                        new FourCornersSetter(p.getName(), p.getName() + "-", "").changeValue(atomicReference.get(), p.getValue());
                        break;
                    case BORDER_STYLE:
                        new BorderStyleSetter(p.getName(), p.getName() + "-", "-style").changeValue(atomicReference.get(), p.getValue());
                        break;
                    case BORDER:
                        new BorderSetter2(p.getName()).changeValue(atomicReference.get(), p.getValue());
                        break;
                    case BACKGROUND:
                        new BackgroundSetter().changeValue(atomicReference.get(), p.getValue());
                        break;
                    case FONT:
                        new FontSetter().changeValue(atomicReference.get(), p.getValue());
                        break;
                    default:
                        break;
                }
            });
        }

        if (abstractCSSRule instanceof CSSPageRuleImpl styleRule) {
            final List<NameValuePair> list = new ArrayList<>();

            styleRule.getStyle().getProperties().forEach(p -> list.add(new NameValuePair(p.getName(), p.getValue().toString())));

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
                        break;
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
