package org.lobobrowser.html.w3c;


/**
 * The Interface ValidityState.
 */
public interface ValidityState {
	// ValidityState
	/**
	 * Gets the value missing.
	 *
	 * @return the value missing
	 */
	public boolean getValueMissing();

	/**
	 * Gets the type mismatch.
	 *
	 * @return the type mismatch
	 */
	public boolean getTypeMismatch();

	/**
	 * Gets the pattern mismatch.
	 *
	 * @return the pattern mismatch
	 */
	public boolean getPatternMismatch();

	/**
	 * Gets the too long.
	 *
	 * @return the too long
	 */
	public boolean getTooLong();

	/**
	 * Gets the range underflow.
	 *
	 * @return the range underflow
	 */
	public boolean getRangeUnderflow();

	/**
	 * Gets the range overflow.
	 *
	 * @return the range overflow
	 */
	public boolean getRangeOverflow();

	/**
	 * Gets the step mismatch.
	 *
	 * @return the step mismatch
	 */
	public boolean getStepMismatch();

	/**
	 * Gets the custom error.
	 *
	 * @return the custom error
	 */
	public boolean getCustomError();

	/**
	 * Gets the valid.
	 *
	 * @return the valid
	 */
	public boolean getValid();
}
