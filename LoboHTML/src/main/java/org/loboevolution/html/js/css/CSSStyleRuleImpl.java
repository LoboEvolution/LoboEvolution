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

import com.gargoylesoftware.css.dom.AbstractCSSRuleImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.node.css.CSSStyleRule;

public class CSSStyleRuleImpl extends CSSRuleImpl implements CSSStyleRule {

    private final AbstractCSSRuleImpl abstractCSSRule;

    public CSSStyleRuleImpl(AbstractCSSRuleImpl abstractCSSRule) {
        super(abstractCSSRule);
        this.abstractCSSRule = abstractCSSRule;
    }

    /** {@inheritDoc} */
    @Override
    public String getSelectorText() {
        if (abstractCSSRule instanceof com.gargoylesoftware.css.dom.CSSStyleRuleImpl) {
            final com.gargoylesoftware.css.dom.CSSStyleRuleImpl styleRule = (com.gargoylesoftware.css.dom.CSSStyleRuleImpl) abstractCSSRule;
            return styleRule.getSelectorText();
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public CSSStyleDeclaration getStyle() {
        if (abstractCSSRule instanceof com.gargoylesoftware.css.dom.CSSStyleRuleImpl) {
            final com.gargoylesoftware.css.dom.CSSStyleRuleImpl styleRule = (com.gargoylesoftware.css.dom.CSSStyleRuleImpl) abstractCSSRule;
            return new CSSStyleDeclarationImpl(styleRule.getStyle());
        }
        return null;
    }

    @Override
    public String toString() {
        return "[object CSSStyleRule]";
    }
}
