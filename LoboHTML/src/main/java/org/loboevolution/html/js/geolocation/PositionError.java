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
