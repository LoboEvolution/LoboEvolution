package org.loboevolution.html.renderer;

abstract class BaseRenderable implements Renderable {
	private int ordinal = 0;

	/**
	 * <p>Getter for the field ordinal.</p>
	 *
	 * @return a int.
	 */
	public int getOrdinal() {
		return this.ordinal;
	}

	/**
	 * <p>getZIndex.</p>
	 *
	 * @return a int.
	 */
	public int getZIndex() {
		return 0;
	}

	/**
	 * <p>Setter for the field ordinal.</p>
	 *
	 * @param ordinal a int.
	 */
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
}
