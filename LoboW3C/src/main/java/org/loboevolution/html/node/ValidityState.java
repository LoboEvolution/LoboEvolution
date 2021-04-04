/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

