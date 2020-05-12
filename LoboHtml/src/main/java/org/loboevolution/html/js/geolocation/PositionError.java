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
 * @author utente
 * @version $Id: $Id
 */
public final class PositionError {

	/**
	 * Error code used to convey "permission denied" error.
	 */
	public final static short PERMISSION_DENIED = 1;
	
	/**
	 * Error code used to convey "position unavailable" error.
	 */
	public final static short POSITION_UNAVAILABLE = 2;
	
	/**
	 * Error code used to convey "timeout" error.
	 */
	public final static short TIMEOUT = 3;
	
	/*
	 * The error code.
	 */
	private short code;
	
	/*
	 * A detailed error message used in conjunction with the error code.
	 */
	private String message;
	
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
