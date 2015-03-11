package org.lobobrowser.html.w3c;


/**
 * The Interface TimeRanges.
 */
public interface TimeRanges {
	// TimeRanges
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength();

	/**
	 * Start.
	 *
	 * @param index the index
	 * @return the float
	 */
	public float start(int index);

	/**
	 * End.
	 *
	 * @param index the index
	 * @return the float
	 */
	public float end(int index);
}
