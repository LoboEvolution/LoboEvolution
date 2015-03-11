package org.lobobrowser.html.w3c;


/**
 * The Interface HTMLVideoElement.
 */
public interface HTMLVideoElement extends HTMLMediaElement {
	// HTMLVideoElement
	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public String getWidth();

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(String width);

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public String getHeight();

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(String height);

	/**
	 * Gets the video width.
	 *
	 * @return the video width
	 */
	public int getVideoWidth();

	/**
	 * Gets the video height.
	 *
	 * @return the video height
	 */
	public int getVideoHeight();

	/**
	 * Gets the poster.
	 *
	 * @return the poster
	 */
	public String getPoster();

	/**
	 * Sets the poster.
	 *
	 * @param poster the new poster
	 */
	public void setPoster(String poster);
}
