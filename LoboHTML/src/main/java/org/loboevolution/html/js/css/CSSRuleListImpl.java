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
import org.loboevolution.html.node.AbstractList;
import org.loboevolution.html.node.css.CSSRuleList;
import org.loboevolution.html.node.css.CSSStyleRule;

import java.util.List;

public class CSSRuleListImpl extends AbstractList<CSSStyleRule>  implements CSSRuleList {

    private final com.gargoylesoftware.css.dom.CSSRuleListImpl cssRuleList;

    public CSSRuleListImpl(com.gargoylesoftware.css.dom.CSSRuleListImpl cssRuleList) {
        this.cssRuleList = cssRuleList;
    }

    /** {@inheritDoc} */
    @Override
    public CSSStyleRule item(int index) {
        return this.get(index);
    }

    /** {@inheritDoc} */
    @Override
    public long getLength() {
        return this.size();
    }

    /**
     * <p> addStyleRule. </p>
     * @param newList a {@link com.gargoylesoftware.css.dom.CSSRuleListImpl} object.
     */
    public void addStyleRule(com.gargoylesoftware.css.dom.CSSRuleListImpl newList){
        List<AbstractCSSRuleImpl> ruls =  null;
        if (newList != null) {
            clear();
            ruls = newList.getRules();
        } else {
            ruls = cssRuleList.getRules();
        }

        ruls.forEach(abstractCSSRule -> {
            if (!(abstractCSSRule instanceof com.gargoylesoftware.css.dom.CSSUnknownRuleImpl))
                add(new CSSStyleRuleImpl(abstractCSSRule));
        });
    }

    @Override
    public String toString() {
        return "[object CSSRuleList]";
    }
}
