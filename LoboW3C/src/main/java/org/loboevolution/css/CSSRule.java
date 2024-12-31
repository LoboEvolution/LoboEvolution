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

/**
 * A CSS rule interface
 */
public interface CSSRule {


    /**
     * The rule is a <code>CSSUnknownRule</code>.
     */
    int UNKNOWN_RULE = 0;

    /**
     * The rule is a <code>CSSStyleRule</code>.
     */
    int STYLE_RULE = 1;

    /**
     * The rule is a <code>CSSCharsetRule</code>.
     */
    int CHARSET_RULE = 2;

    /**
     * The rule is a <code>CSSImportRule</code>.
     */
    int IMPORT_RULE = 3;

    /**
     * The rule is a <code>CSSMediaRule</code>.
     */
    int MEDIA_RULE = 4;

    /**
     * The rule is a <code>CSSFontFaceRule</code>.
     */
    int FONT_FACE_RULE = 5;

    /**
     * The rule is a <code>CSSPageRule</code>.
     */
    int PAGE_RULE = 6;

    /**
     * <p> getCssText. </p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getCssText();

    /**
     * <p> getParentRule. </p>
     *
     * @return a {@link org.loboevolution.css.CSSRule} object.
     */
    CSSRule getParentRule();


    /**
     * <p> parentStyleSheet. </p>
     *
     * @return a {@link CSSStyleSheet} object.
     */
    CSSStyleSheet getParentStyleSheet();

    /**
     * <p> getType. </p>
     *
     * @return a {@link java.lang.Integer} object.
     */
    int getType();
}
