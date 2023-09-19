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

package org.loboevolution.html.node;

/**
 * The validity states that an element can be in, with respect to constraint validation. Together, they help explain why an element's value fails to validate, if it's not valid.
 *
 *
 *
 */
public interface ValidityState {

    /**
     * <p>isBadInput.</p>
     *
     * @return a boolean.
     */
    boolean isBadInput();

    
    /**
     * <p>isCustomError.</p>
     *
     * @return a boolean.
     */
    boolean isCustomError();

    
    /**
     * <p>isPatternMismatch.</p>
     *
     * @return a boolean.
     */
    boolean isPatternMismatch();

    
    /**
     * <p>isRangeOverflow.</p>
     *
     * @return a boolean.
     */
    boolean isRangeOverflow();

    
    /**
     * <p>isRangeUnderflow.</p>
     *
     * @return a boolean.
     */
    boolean isRangeUnderflow();

    
    /**
     * <p>isStepMismatch.</p>
     *
     * @return a boolean.
     */
    boolean isStepMismatch();

    
    /**
     * <p>isTooLong.</p>
     *
     * @return a boolean.
     */
    boolean isTooLong();

    
    /**
     * <p>isTooShort.</p>
     *
     * @return a boolean.
     */
    boolean isTooShort();

    
    /**
     * <p>isTypeMismatch.</p>
     *
     * @return a boolean.
     */
    boolean isTypeMismatch();

    
    /**
     * <p>isValid.</p>
     *
     * @return a boolean.
     */
    boolean isValid();

    
    /**
     * <p>isValueMissing.</p>
     *
     * @return a boolean.
     */
    boolean isValueMissing();


}

