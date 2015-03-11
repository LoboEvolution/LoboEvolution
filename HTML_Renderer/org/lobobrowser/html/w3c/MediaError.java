package org.lobobrowser.html.w3c;


/**
 * The Interface MediaError.
 */
public interface MediaError {
	// MediaError
	/** The Constant MEDIA_ERR_ABORTED. */
	public static final short MEDIA_ERR_ABORTED = 1;
	
	/** The Constant MEDIA_ERR_NETWORK. */
	public static final short MEDIA_ERR_NETWORK = 2;
	
	/** The Constant MEDIA_ERR_DECODE. */
	public static final short MEDIA_ERR_DECODE = 3;
	
	/** The Constant MEDIA_ERR_SRC_NOT_SUPPORTED. */
	public static final short MEDIA_ERR_SRC_NOT_SUPPORTED = 4;

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public short getCode();
}
