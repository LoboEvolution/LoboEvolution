package org.lobobrowser.html.renderer;


/**
 * The Class BaseRenderable.
 */
abstract class BaseRenderable implements Renderable {
	
	/** The ordinal. */
	private int ordinal = 0;

	/**
	 * Gets the ordinal.
	 *
	 * @return the ordinal
	 */
	public int getOrdinal() {
		return this.ordinal;
	}

	/**
	 * Gets the z index.
	 *
	 * @return the z index
	 */
	public int getZIndex() {
		return 0;
	}

	/**
	 * Sets the ordinal.
	 *
	 * @param ordinal the new ordinal
	 */
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
}
