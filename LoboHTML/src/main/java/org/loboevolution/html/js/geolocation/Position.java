/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.loboevolution.js.AbstractScriptableDelegate;

/**
 * The Position class provides Java implementation of the "Position Interface" as
 * detailed out in the W3C Specifications (<a href="http://www.w3.org/TR/geolocation-API/#position">...</a>).
 * Note: This class must not have any sub-classes to ensure W3C Specifications are being
 * strictly followed by the system or application that uses this geolocation package.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Position extends AbstractScriptableDelegate {
	
	/*
	 * The position coordinates.
	 */
	private final Coordinates coords;
	
	/*
	 * The time (in milliseconds since January 1, 1970 GMT) when this position was found (or calculated). 
	 */
	private final long timestamp;
}
