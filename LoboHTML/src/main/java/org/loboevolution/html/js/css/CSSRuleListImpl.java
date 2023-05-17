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
