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
package org.loboevolution.css;

public interface CSSMediaRule extends CSSRule {

    /**
     * <p>getMedia.</p>
     * @return a {@link MediaList} object representing the media queries for this rule.
     */
    MediaList getMedia();

    /**
     * <p>setMedia.</p>
     * @param media a {@link java.lang.String} object specifying the media query text.
     */
    void setMedia(String media);

    /**
     * <p>getCssRules.</p>
     * @return a {@link CSSRuleList} object containing all CSS rules in this media rule.
     */
    CSSRuleList getCssRules();

    /**
     * <p>insertRule.</p>
     * Adds a new rule to the end of this media rule's CSS rule list.
     * @param rule a {@link java.lang.String} object containing the CSS rule text to insert.
     */
    void insertRule(String rule);

    /**
     * <p>insertRule.</p>
     * Adds a new rule at the specified position in this media rule's CSS rule list.
     * @param rule a {@link java.lang.String} object containing the CSS rule text to insert.
     * @param index an {@link java.lang.Integer} specifying the position at which to insert the rule.
     */
    void insertRule(String rule, Integer index);

    /**
     * <p>deleteRule.</p>
     * Removes a rule at the specified position from this media rule's CSS rule list.
     * @param index an {@link java.lang.Integer} specifying the position of the rule to remove.
     */
    void deleteRule(Integer index);
}
