package org.lobobrowser.html.info;

import java.io.Serializable;

import org.lobobrowser.html.style.HtmlLength;

/**
 * The Class SizeInfo.
 */
public class SizeInfo implements Serializable {

	private static final long serialVersionUID = 3550617662873087308L;

	/** The html length. */
	private HtmlLength htmlLength;

	/** The actual size. */
	private int actualSize;

	/** The layout size. */
	private int layoutSize;

	/** The min size. */
	private int minSize;

	/** The offset. */
	private int offset;

	/**
	 * @return the htmlLength
	 */
	public HtmlLength getHtmlLength() {
		return htmlLength;
	}

	/**
	 * @param htmlLength
	 *            the htmlLength to set
	 */
	public void setHtmlLength(HtmlLength htmlLength) {
		this.htmlLength = htmlLength;
	}

	/**
	 * @return the actualSize
	 */
	public int getActualSize() {
		return actualSize;
	}

	/**
	 * @param actualSize
	 *            the actualSize to set
	 */
	public void setActualSize(int actualSize) {
		this.actualSize = actualSize;
	}

	/**
	 * @return the layoutSize
	 */
	public int getLayoutSize() {
		return layoutSize;
	}

	/**
	 * @param layoutSize
	 *            the layoutSize to set
	 */
	public void setLayoutSize(int layoutSize) {
		this.layoutSize = layoutSize;
	}

	/**
	 * @return the minSize
	 */
	public int getMinSize() {
		return minSize;
	}

	/**
	 * @param minSize
	 *            the minSize to set
	 */
	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
}
