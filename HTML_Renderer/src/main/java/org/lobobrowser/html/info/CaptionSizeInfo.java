package org.lobobrowser.html.info;

import java.io.Serializable;

/**
 * The Class CaptionSizeInfo.
 */
public class CaptionSizeInfo implements Serializable {

	private static final long serialVersionUID = -699151415264714503L;

	/** The height. */
	private int height;

	/** The height offset. */
	private int heightOffset;

	/** The width. */
	private int width;

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the heightOffset
	 */
	public int getHeightOffset() {
		return heightOffset;
	}

	/**
	 * @param heightOffset
	 *            the heightOffset to set
	 */
	public void setHeightOffset(int heightOffset) {
		this.heightOffset = heightOffset;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
}