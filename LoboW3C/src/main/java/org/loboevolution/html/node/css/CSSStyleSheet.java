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

package org.loboevolution.html.node.css;


/**
 * A style sheet.
 * This extension to the W3C interface adds utility as well as factory methods. The
 * factory methods create rules that have the same origin specificity as this style sheet.
 */

public interface CSSStyleSheet extends StyleSheet {

    /**
     * <p> getOwnerRule. </p>
     * @return a {@link org.loboevolution.html.node.css.CSSRule} object.
     */
    CSSRule getOwnerRule();

    /**
     * <p> getCssRules. </p>
     * @return a {@link org.loboevolution.html.node.css.CSSRuleList} object.
     */
    CSSRuleList getCssRules();

    /**
     * <p> getCssRules. </p>
     * @param rule a {@link java.lang.String} object.
     * @param index a {@link java.lang.Integer} object.
     * @return a {@link java.lang.Long} object.
     */
    long insertRule(String rule, int index);

    /**
     * <p> getCssRules. </p>
     * @param index a {@link java.lang.Integer} object.
     */
    void deleteRule(int index);


}
