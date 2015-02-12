package org.lobobrowser.html.jsimpl;

public class EventException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	public static final short UNSPECIFIED_EVENT_TYPE_ERR = 0;
	public static final short DISPATCH_REQUEST_ERR = 1;
	public short code;
	
	public EventException(short code, String message) {
		super(message);
		this.code = code;
	}
}
