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

package org.loboevolution.html.js.geolocation;

/**
 * The PositionError class provides a way to provide error when the position
 * cannot be calculated due to any reason. An object of this class is created by the
 * Geolocation object and is provided to the calling application via the
 * PositionCallback interface.
 *
 * Note: This class must not have any sub-classes to ensure W3C Specifications are being
 * strictly followed by the system or application that uses this geolocation package. These
 * specifications are avilable at http://www.w3.org/TR/geolocation-API/#position-error
 *
 *
 *
 */
public final class PositionError {

	/**
	 * Error code used to convey "permission denied" error.
	 */
	public static final  short PERMISSION_DENIED = 1;
	
	/**
	 * Error code used to convey "position unavailable" error.
	 */
	public static final  short POSITION_UNAVAILABLE = 2;
	
	/**
	 * Error code used to convey "timeout" error.
	 */
	public static final  short TIMEOUT = 3;
	
	/*
	 * The error code.
	 */
	private final short code;
	
	/*
	 * A detailed error message used in conjunction with the error code.
	 */
	private final String message;
	
	/**
	 * Constructs an instance of PositionError class using only the
	 * error code; Sets the error message to null.
	 *
	 * @param code	the error code.
	 */
	public PositionError(short code) {
		this(code, null);
	}
	
	/**
	 * Constructs an instance of PositionError class using the error code
	 * and error message.
	 *
	 * @param code	the error code.
	 * @param message	the error message.
	 */
	public PositionError(short code, final String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Returns the error code.
	 *
	 * @return	the error code.
	 */
	public short getCode() {
		return code;
	}

	/**
	 * Returns the error message.
	 *
	 * @return	the error message.
	 */
	public String getMessage() {
		return message;
	}
}
