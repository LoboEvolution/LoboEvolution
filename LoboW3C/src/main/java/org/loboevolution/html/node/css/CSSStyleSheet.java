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
