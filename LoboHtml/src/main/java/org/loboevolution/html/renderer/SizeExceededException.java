package org.loboevolution.html.renderer;

class SizeExceededException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for SizeExceededException.</p>
	 */
	public SizeExceededException() {
		super();
	}

	/**
	 * <p>Constructor for SizeExceededException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public SizeExceededException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for SizeExceededException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public SizeExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for SizeExceededException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public SizeExceededException(Throwable cause) {
		super(cause);
	}
}
