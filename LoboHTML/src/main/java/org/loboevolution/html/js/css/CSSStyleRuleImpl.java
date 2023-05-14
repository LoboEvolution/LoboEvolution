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
