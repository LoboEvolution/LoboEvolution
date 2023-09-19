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

package org.loboevolution.html.dom.smil;

import org.htmlunit.cssparser.dom.DOMException;

/**
 * Defines the test attributes interface. See the Test attributes definition .
 */
public interface ElementTest {
    /**
     * The systemBitrate value.
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a int.
     */
    int getSystemBitrate();

    /**
     * <p>setSystemBitrate.</p>
     *
     * @param systemBitrate a int.
     * @throws DOMException if any.
     */
    void setSystemBitrate(int systemBitrate) throws DOMException;

    /**
     * The systemCaptions value.
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a boolean.
     */
    boolean getSystemCaptions();

    /**
     * <p>setSystemCaptions.</p>
     *
     * @param systemCaptions a boolean.
     * @throws DOMException if any.
     */
    void setSystemCaptions(boolean systemCaptions) throws DOMException;

    /**
     * The systemLanguage value.
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getSystemLanguage();

    /**
     * <p>setSystemLanguage.</p>
     *
     * @param systemLanguage a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setSystemLanguage(String systemLanguage) throws DOMException;

    /**
     * The result of the evaluation of the systemRequired attribute.
     *
     * @return a boolean.
     */
    boolean getSystemRequired();

    /**
     * The result of the evaluation of the systemScreenSize attribute.
     *
     * @return a boolean.
     */
    boolean getSystemScreenSize();

    /**
     * The result of the evaluation of the systemScreenDepth attribute.
     *
     * @return a boolean.
     */
    boolean getSystemScreenDepth();

    /**
     * The value of the systemOverdubOrSubtitle attribute.
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getSystemOverdubOrSubtitle();

    /**
     * <p>setSystemOverdubOrSubtitle.</p>
     *
     * @param systemOverdubOrSubtitle a {@link java.lang.String} object.
     * @throws DOMException if any.
     */
    void setSystemOverdubOrSubtitle(String systemOverdubOrSubtitle) throws DOMException;

    /**
     * The value of the systemAudioDesc attribute.
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a boolean.
     */
    boolean getSystemAudioDesc();

    /**
     * <p>setSystemAudioDesc.</p>
     *
     * @param systemAudioDesc a boolean.
     * @throws DOMException if any.
     */
    void setSystemAudioDesc(boolean systemAudioDesc) throws DOMException;

}
