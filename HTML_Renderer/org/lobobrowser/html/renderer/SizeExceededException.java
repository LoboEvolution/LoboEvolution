package org.lobobrowser.html.renderer;


/**
 * The Class SizeExceededException.
 */
class SizeExceededException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new size exceeded exception.
	 */
	public SizeExceededException() {
		super();
	}

	/**
	 * Instantiates a new size exceeded exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public SizeExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new size exceeded exception.
	 *
	 * @param message the message
	 */
	public SizeExceededException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new size exceeded exception.
	 *
	 * @param cause the cause
	 */
	public SizeExceededException(Throwable cause) {
		super(cause);
	}
}
