package org.loboevolution.info;

public class SizeInfo {
	
	private int actualSize;
	
	private int layoutSize;
	
	private int minSize;
	
	private int offset;
	
	private Object htmlLength;

	/**
	 * @return the actualSize
	 */
	public int getActualSize() {
		return actualSize;
	}

	/**
	 * @return the layoutSize
	 */
	public int getLayoutSize() {
		return layoutSize;
	}

	/**
	 * @return the minSize
	 */
	public int getMinSize() {
		return minSize;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @return the htmlLength
	 */
	public Object getHtmlLength() {
		return htmlLength;
	}

	/**
	 * @param actualSize the actualSize to set
	 */
	public void setActualSize(int actualSize) {
		this.actualSize = actualSize;
	}

	/**
	 * @param layoutSize the layoutSize to set
	 */
	public void setLayoutSize(int layoutSize) {
		this.layoutSize = layoutSize;
	}

	/**
	 * @param minSize the minSize to set
	 */
	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @param htmlLength the htmlLength to set
	 */
	public void setHtmlLength(Object htmlLength) {
		this.htmlLength = htmlLength;
	}
	
}