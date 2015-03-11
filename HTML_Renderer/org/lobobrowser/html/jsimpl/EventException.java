package org.lobobrowser.html.jsimpl;


/**
 * The Class EventException.
 */
public class EventException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant UNSPECIFIED_EVENT_TYPE_ERR. */
	public static final short UNSPECIFIED_EVENT_TYPE_ERR = 0;
	
	/** The Constant DISPATCH_REQUEST_ERR. */
	public static final short DISPATCH_REQUEST_ERR = 1;
	
	/** The code. */
	public short code;
	
	/**
	 * Instantiates a new event exception.
	 *
	 * @param code the code
	 * @param message the message
	 */
	public EventException(short code, String message) {
		super(message);
		this.code = code;
	}
}
