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
        return new CSSRuleImpl(abstractCSSRule.getParentRule());
    }

    /** {@inheritDoc} */
    @Override
    public CSSStyleSheet parentStyleSheet() {
        return new CSSStyleSheetImpl(abstractCSSRule.getParentStyleSheet());
    }

    @Override
    public String toString() {
        return "[object CSSRule]";
    }
}
