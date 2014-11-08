package org.lobobrowser.html.renderer;

class SizeExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SizeExceededException() {
		super();
	}

	public SizeExceededException(String message, Throwable cause) {
		super(message, cause);
	}

	public SizeExceededException(String message) {
		super(message);
	}

	public SizeExceededException(Throwable cause) {
		super(cause);
	}
}
