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

package org.loboevolution.html.js.geolocation;

import lombok.Data;

/**
 * The PositionOptions class provides a way for the application to set options
 * that the Geolocation object can then use when calculating the position. These
 * include parameters such as enabling high accuracy, API timeout, cache age, etc.
 * Note: This class must not have any sub-classes to ensure W3C Specifications are being
 * strictly followed by the system or application that uses this geolocation package. These
 * specifications are avilable at <a href="http://www.w3.org/TR/geolocation-API/#position-options">...</a>
 */
@Data
public final class PositionOptions {

    /*
     * Maximum time allowed (in milliseconds) to calculate position.
     */
    private long timeout;

    /*
     * Time (in milliseconds) for which a position must be cached (and provided in case the 
     * application asks for the cached position).
     */
    private long maximumAge;
    
}
